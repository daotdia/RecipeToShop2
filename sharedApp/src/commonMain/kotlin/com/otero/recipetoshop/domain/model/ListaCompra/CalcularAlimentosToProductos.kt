package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.datasource.static.Productos_Carrefour
import com.otero.recipetoshop.datasource.static.productos_Dia
import com.otero.recipetoshop.datasource.static.productos_Mercadona
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.FilterEnum
import com.otero.recipetoshop.domain.dataEstructres.SupermercadosEnum
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
import kotlinx.serialization.json.*
import kotlin.Float.Companion.MAX_VALUE
import kotlin.Float.Companion.NEGATIVE_INFINITY
import kotlin.math.ceil

class CalcularAlimentosToProductos {

    var productos: Productos = Productos()

    fun iniciarCalculadora(
        supermercados: MutableSet<SupermercadosEnum> = mutableSetOf(SupermercadosEnum.CARREFOUR)
    ): Unit {
        val jsons: ArrayList<JsonArray?> = arrayListOf()

        //Determina qué productos añadir de qué supermercados.
        for (supermercado in supermercados) {
            when(supermercado) {
                SupermercadosEnum.CARREFOUR ->
                    jsons.add(Productos_Carrefour.json.jsonObject.get("productos")?.jsonArray)
                SupermercadosEnum.DIA ->
                    jsons.add(productos_Dia.json.jsonObject.get("productos")?.jsonArray)
                SupermercadosEnum.MERCADONA ->
                    jsons.add(productos_Mercadona.json.jsonObject.get("productos")?.jsonArray)
            }
        }

        if (!jsons.isEmpty()) {
            for (json in jsons) {
                if(!json.isNullOrEmpty()) {
                    for (tipo in json) {
                        val producto: ArrayList<Productos.Producto> = arrayListOf()
                        for (instancia in tipo.jsonArray) {
                            try {
                                val elemento: Productos.Producto = Productos.Producto(
                                    id_producto = -1,
                                    id_cestaCompra = -1,
                                    nombre = instancia.jsonObject.getValue("nombre").jsonPrimitive.content,
                                    imagen_src = instancia.jsonObject.getValue("imagen_src").jsonPrimitive.content,
                                    oferta = instancia.jsonObject.getValue("oferta").jsonPrimitive.content,
                                    precio_texto = instancia.jsonObject.getValue("precio").jsonPrimitive.content,
                                    precio_peso = instancia.jsonObject.getValue("precio_peso").jsonPrimitive.content,
                                    query = instancia.jsonObject.getValue("query").jsonPrimitive.content,
                                    cantidad = 0,
                                    tipoUnidad = TipoUnidad.GRAMOS,
                                    peso = 0f,
                                    precio_numero = if(!instancia.jsonObject.getValue("precio").jsonPrimitive.content.isEmpty()){
                                        instancia.jsonObject.getValue("precio").jsonPrimitive.content
                                            .replace(',', '.')
                                            .filter { it.isDigit() || it.equals('.') }
                                            .toFloat()
                                    } else {
                                        0f
                                    },
                                    supermercado = SupermercadosEnum.parseString(instancia.jsonObject.getValue("supermercado").jsonPrimitive.content)
                                )

                                //En el caso de que no se haya añadido ya un producto con nombre identico se añade a los productos existentes,.
                                if(
                                    !productos.productos.any {
                                        it.any{ producto ->
                                            producto.nombre.equals(elemento.nombre)
                                        }
                                    }
                                ){
                                    producto.add(elemento)
                                }
                            } catch (exception: Exception){
                                println("Mal formato de producto alcanzado")
                            }
                        }
                        productos.productos.add(producto)
                    }
                }
            }
        }
    }

    fun unificarAlimentos(alimentos: ArrayList<Alimento>): ArrayList<Alimento>{
        //Los alimentos con mismo nombre se unen en una  misma necesidad de alimento y para ello hace falta una nueva lista.
        val alimentos_unificados: ArrayList<Alimento> = arrayListOf()
        //Obtengo lista con alimentos únicos.
        //Elimino los alimentos unificados con nombre repetido conservando su orden.
        val alimentos_unicos = ArrayList(alimentos.distinctBy { it.nombre })
        for(alimento in alimentos_unicos){
            //Obtengo los alimentos repetidos para cada alimento si es que los hubiese.
            val repetidos= alimentos.filter {
                alimento.nombre.equals(it.nombre)
            }
            //En el caso de que hubiese al menos un alimento repetido.
            if(repetidos.size > 1){
                val iterator = repetidos.iterator()
                //Obtengo el primer alimenyo.
                val alimento_representativo = iterator.next()
                //Mientras existan alimentos repetidos se añade la cantidad al alimento repetido encontrado.
                while(iterator.hasNext()){
                    //Sumo las cantidades
                    alimento_representativo.cantidad += iterator.next().cantidad
                }
                //Añado el alimento unificado a la lista
                alimentos_unificados.add(alimento_representativo)
            } else {
                //En caso de que no esté repetido simplemento lo añado a la lista de alimentos unificados.
                alimentos_unificados.add(alimento)
            }
        }
        return alimentos_unificados
    }

    fun encontrarProductos(alimentos: List<Alimento>): ArrayList<ArrayList<Productos.Producto>> {
        val result: ArrayList<ArrayList<Productos.Producto>> = arrayListOf()
        for (alimento in alimentos) {
            val elementos = productos.matchElementos(alimento.nombre)
            if(elementos.isNotEmpty()){
                result.add(elementos)
            }
        }
        return result
    }

    fun calcularNecesidadesAlimentos(
        alimentos_despensa: ArrayList<Alimento>, alimentos_cesta: ArrayList<Alimento>
    ): ArrayList<Alimento> {
        for(al_cesta in alimentos_cesta){
            for (al_desp in alimentos_despensa){
                if(al_cesta.nombre.equals(al_desp.nombre)){
                    al_cesta.cantidad = al_cesta.cantidad - al_desp.cantidad
                    if(al_cesta.cantidad <= 0) {
                        al_cesta.active = false
                    }
                }
            }
        }
        return ArrayList(alimentos_cesta.filter { it.active == true })
    }

    fun calcularCantidadesProductos(alimentos: ArrayList<Alimento>, productos: ArrayList<ArrayList<Productos.Producto>>): ArrayList<ArrayList<Productos.Producto>>{
        //Primero calculo la cantidad de una unidad de producto
        for(tipo in productos){
            for(producto in tipo){
                val cantidad_tipoUnidad = ParsersJsonToProducto.parsePesoJsonToProductoJson(producto)
                if(cantidad_tipoUnidad != null){
                    producto.peso = cantidad_tipoUnidad.get("peso") as Float
                    producto.tipoUnidad = cantidad_tipoUnidad.get("tipounidad") as TipoUnidad
                }
            }
        }

        //Después determino la cantidad de unidades de producto necesarias
        for(alimento in alimentos){
            for(tipo in productos){
                for(producto in tipo){
                    if(producto.query.trim().lowercase().equals(alimento.nombre.trim().lowercase())){
                        producto.cantidad = calcularDiferenciaCantidad(alimento.cantidad,alimento.tipoUnidad,producto.peso,producto.tipoUnidad)
                    }
                }
            }
        }
        return productos
    }

    private fun calcularDiferenciaCantidad(cantidadAlimento: Int, tipoUnidadAlimento: TipoUnidad, pesoProducto: Float, tipoUnidadProducto: TipoUnidad?): Int {
        when(tipoUnidadAlimento){
            TipoUnidad.UNIDADES -> {
                when(tipoUnidadProducto){
                    TipoUnidad.UNIDADES -> {
                        return ceil(cantidadAlimento-pesoProducto).toInt()
                    }
                    else -> {
                        return -1
                    }
                }
            }
            else -> {
                when(tipoUnidadProducto){
                    TipoUnidad.UNIDADES -> {
                        return -1
                    }
                    else -> {
                        return ceil(cantidadAlimento/pesoProducto).toInt()
                    }
                }
            }
        }
    }

    fun seleccionarMejorProducto(
        productos: ArrayList<ArrayList<Productos.Producto>>,
        filterEnum: FilterEnum = FilterEnum.BARATOS
    ): ArrayList<Productos.Producto>{
        val result: ArrayList<Productos.Producto> = arrayListOf()
        when(filterEnum){
            FilterEnum.BARATOS ->
                for(tipo in productos){
                    var best: Float = MAX_VALUE
                    var ganador: Productos.Producto? = null
                    for(elemento in tipo){
                        val precio_peso = ParsersJsonToProducto
                            .parseJsonPrecioPesoToProductoPrecioPeso(elemento)
                        if(precio_peso != null && precio_peso < best){
                            ganador = elemento
                            best = precio_peso
                        }
                    }
                    result.add(ganador!!)
                }

            FilterEnum.LIGEROS ->
                for(tipo in productos){
                    var best: Float = MAX_VALUE
                    var ganador: Productos.Producto? = null
                    for(elemento in tipo){
                        val peso = elemento.peso
                        if(peso < best){
                            ganador = elemento
                            best = peso
                        }
                    }
                    println("El ganador ha sido: " + ganador?.nombre)
                    result.add(ganador!!)
                }

            FilterEnum.AJUSTADOS -> {
                //Obtengo el peso medio de los productos
                val peso_medio = productos.getPesoMedio()

                //Obtengo el precio medio de los productos
                val precio_medio = productos.getPrecioMedio()
                println("He obtenido los precios medios")
                //Determino la mejor combinación de peso y precio ponderado al 50% cada campo.
                for(tipo in productos){
                    var best: Float = NEGATIVE_INFINITY
                    var ganador: Productos.Producto? = null
                    for(producto in tipo) {
                        var valor = NEGATIVE_INFINITY
                        var precio_ponderado = 0f
                        val peso_ponderado = 1 - (producto.peso / peso_medio)
                        val precio = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                        if (precio != null) {
                            precio_ponderado  = 1 - (precio / precio_medio)
                            valor = (0.5*peso_ponderado + 0.5*precio_ponderado).toFloat()
                            println("El valor ponderado del alimento " + producto.nombre + " es: " + valor)
                        }
                        if (valor >= best) {
                            best = valor
                            ganador = producto
                        }
                    }
                    println("El ganador ha sido: " + ganador?.nombre)
                    result.add(ganador!!)
                }
            }

            FilterEnum.UNICOBARATO -> {
                val result_carr: ArrayList<Productos.Producto> = arrayListOf()
                val result_dia: ArrayList<Productos.Producto> = arrayListOf()
                val result_merc: ArrayList<Productos.Producto> = arrayListOf()

                for (tipo in productos){
                    var best_sup_merc: Float = MAX_VALUE
                    var best_sup_carr: Float = MAX_VALUE
                    var best_sup_dia: Float = MAX_VALUE

                    var ganador_sup_carr: Productos.Producto? = null
                    var ganador_sup_merc: Productos.Producto? = null
                    var ganador_sup_dia: Productos.Producto? = null

                    for(producto in tipo){
                        when(producto.supermercado){
                            SupermercadosEnum.CARREFOUR -> {
                                val precio_peso = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if(precio_peso != null && precio_peso < best_sup_carr){
                                    ganador_sup_carr = producto
                                    best_sup_carr = precio_peso
                                }
                            }
                            SupermercadosEnum.MERCADONA -> {
                                val precio_peso = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if(precio_peso != null && precio_peso < best_sup_merc){
                                    ganador_sup_merc = producto
                                    best_sup_merc = precio_peso
                                }
                            }
                            SupermercadosEnum.DIA -> {
                                val precio_peso = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if(precio_peso != null && precio_peso < best_sup_dia){
                                    ganador_sup_dia = producto
                                    best_sup_dia = precio_peso
                                }
                            }
                        }
                    }
                    if (ganador_sup_carr != null) {
                        result_carr.add(ganador_sup_carr)
                    }
                    if (ganador_sup_dia != null) {
                        result_dia.add(ganador_sup_dia)
                    }
                    if (ganador_sup_merc != null) {
                        result_merc.add(ganador_sup_merc)
                    }
                }

                val max_num_productos = arrayListOf(result_carr,result_merc,result_dia).maxOf {
                    it.size
                }
                val listas_mas_completas = arrayListOf(result_carr,result_merc,result_dia).filter {
                    it.size == max_num_productos
                }

                var best_precio_total = MAX_VALUE
                var sup_ganador: SupermercadosEnum? = null

                if(listas_mas_completas.isNotEmpty()){
                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            var precio_total = 0f
                            it.forEach {
                                val precio_peso = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(it)
                                if (precio_peso != null) {
                                    precio_total += precio_peso
                                }
                            }
                            if(precio_total < best_precio_total){
                                best_precio_total = precio_total
                                sup_ganador = it.first().supermercado
                            }
                        }
                    }

                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            it.forEach {
                                if(it.supermercado.equals(sup_ganador)){
                                    result.add(it)
                                }
                            }
                        }
                    }
                }
            }

            FilterEnum.UNICOLIGERO -> {
                val result_carr: ArrayList<Productos.Producto> = arrayListOf()
                val result_dia: ArrayList<Productos.Producto> = arrayListOf()
                val result_merc: ArrayList<Productos.Producto> = arrayListOf()

                for (tipo in productos){
                    var best_sup_merc: Float = MAX_VALUE
                    var best_sup_carr: Float = MAX_VALUE
                    var best_sup_dia: Float = MAX_VALUE

                    var ganador_sup_carr: Productos.Producto? = null
                    var ganador_sup_merc: Productos.Producto? = null
                    var ganador_sup_dia: Productos.Producto? = null

                    for(producto in tipo){
                        when(producto.supermercado){
                            SupermercadosEnum.CARREFOUR -> {
                                if(producto.peso < best_sup_carr){
                                    ganador_sup_carr = producto
                                    best_sup_carr = producto.peso
                                }
                            }
                            SupermercadosEnum.MERCADONA -> {
                                if(producto.peso < best_sup_merc){
                                    ganador_sup_merc = producto
                                    best_sup_merc = producto.peso
                                }
                            }
                            SupermercadosEnum.DIA -> {
                                if(producto.peso < best_sup_dia){
                                    ganador_sup_dia = producto
                                    best_sup_dia = producto.peso
                                }
                            }
                        }
                    }
                    if (ganador_sup_carr != null) {
                        result_carr.add(ganador_sup_carr)
                    }
                    if (ganador_sup_dia != null) {
                        result_dia.add(ganador_sup_dia)
                    }
                    if (ganador_sup_merc != null) {
                        result_merc.add(ganador_sup_merc)
                    }
                }

                val max_num_productos = arrayListOf(result_carr,result_merc,result_dia).maxOf {
                    it.size
                }
                val listas_mas_completas = arrayListOf(result_carr,result_merc,result_dia).filter {
                    it.size == max_num_productos
                }

                var best_precio_total = MAX_VALUE
                var sup_ganador: SupermercadosEnum? = null

                if(listas_mas_completas.isNotEmpty()){
                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            var peso_total = 0f
                            it.forEach {
                                peso_total += it.peso
                            }
                            if(peso_total < best_precio_total){
                                best_precio_total = peso_total
                                sup_ganador = it.first().supermercado
                            }
                        }
                    }

                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            it.forEach {
                                if(it.supermercado.equals(sup_ganador)){
                                    result.add(it)
                                }
                            }
                        }
                    }
                }
            }

            FilterEnum.UNICOAJUSTADO -> {
                val result_carr: ArrayList<Productos.Producto> = arrayListOf()
                val result_dia: ArrayList<Productos.Producto> = arrayListOf()
                val result_merc: ArrayList<Productos.Producto> = arrayListOf()

                var pond_carr = 0f
                var pond_merc = 0f
                var pond_dia = 0f

                for (tipo in productos){
                    var best_sup_merc: Float = NEGATIVE_INFINITY
                    var best_sup_carr: Float = NEGATIVE_INFINITY
                    var best_sup_dia: Float = NEGATIVE_INFINITY

                    var ganador_sup_carr: Productos.Producto? = null
                    var ganador_sup_merc: Productos.Producto? = null
                    var ganador_sup_dia: Productos.Producto? = null

                    //Obtengo el peso medio de los productos de este supermercado.
                    val peso_medio = listOf(tipo).getPesoMedio()

                    //Obtengo el precio medio de los productos de este supermercado.
                    val precio_medio = listOf(tipo).getPrecioMedio()

                    for(producto in tipo){
                        when(producto.supermercado){
                            SupermercadosEnum.CARREFOUR -> {
                                var precio_ponderado = 0f
                                val peso_ponderado = 1 - (producto.peso / peso_medio)
                                val precio = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if (precio != null) {
                                    precio_ponderado  = 1 - (precio / precio_medio)
                                }
                                val valor = (0.5*peso_ponderado + 0.5*precio_ponderado).toFloat()
                                if (valor >= best_sup_carr) {
                                    best_sup_carr = valor
                                    ganador_sup_carr = producto
                                }
                            }
                            SupermercadosEnum.MERCADONA -> {
                                var precio_ponderado = 0f
                                val peso_ponderado = 1 - (producto.peso / peso_medio)
                                val precio = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if (precio != null) {
                                    precio_ponderado  = 1 - (precio / precio_medio)
                                }
                                val valor = (0.5*peso_ponderado + 0.5*precio_ponderado).toFloat()
                                if (valor >= best_sup_merc) {
                                    best_sup_merc = valor
                                    ganador_sup_merc = producto
                                }
                            }
                            SupermercadosEnum.DIA -> {
                                var precio_ponderado = 0f
                                val peso_ponderado = 1 - (producto.peso / peso_medio)
                                val precio = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)
                                if (precio != null) {
                                    precio_ponderado  = 1 - (precio / precio_medio)
                                }
                                val valor = (0.5*peso_ponderado + 0.5*precio_ponderado).toFloat()
                                if (valor >= best_sup_dia) {
                                    best_sup_dia = valor
                                    ganador_sup_dia = producto
                                }
                            }
                        }
                    }
                    if (ganador_sup_carr != null) {
                        result_carr.add(ganador_sup_carr)
                        pond_carr += best_sup_carr
                    }
                    if (ganador_sup_dia != null) {
                        result_dia.add(ganador_sup_dia)
                        pond_dia += best_sup_dia
                    }
                    if (ganador_sup_merc != null) {
                        result_merc.add(ganador_sup_merc)
                        pond_merc += best_sup_merc
                    }
                }

                val max_num_productos = arrayListOf(result_carr,result_merc,result_dia).maxOf {
                    it.size
                }
                val listas_mas_completas = arrayListOf(result_carr,result_merc,result_dia).filter {
                    it.size == max_num_productos
                }

                var best_ponderacion_total: Float
                var sup_ganador: SupermercadosEnum? = null

                if(listas_mas_completas.isNotEmpty()){
                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            best_ponderacion_total = listOf(
                                if(listas_mas_completas.any { it.any { it.supermercado == SupermercadosEnum.CARREFOUR }}) pond_carr else NEGATIVE_INFINITY,
                                if(listas_mas_completas.any { it.any { it.supermercado == SupermercadosEnum.DIA }}) pond_dia else NEGATIVE_INFINITY,
                                if(listas_mas_completas.any { it.any { it.supermercado == SupermercadosEnum.MERCADONA }}) pond_merc else NEGATIVE_INFINITY
                            ).maxOrNull()!!
                            println("La ponderación máxima ha sido: " + best_ponderacion_total)
                            when(best_ponderacion_total){
                                pond_carr -> {
                                    sup_ganador = result_carr.first().supermercado
                                }
                                pond_dia -> {
                                    sup_ganador = result_dia.first().supermercado
                                }
                                pond_merc -> {
                                    sup_ganador = result_merc.first().supermercado
                                }
                                else -> {
                                    sup_ganador = SupermercadosEnum.CARREFOUR
                                }
                            }
                        }
                    }

                    listas_mas_completas.forEach {
                        if(it.isNotEmpty()){
                            it.forEach {
                                println("El contnido de las listas completas: " + it.nombre)
                                if(it.supermercado == sup_ganador){
                                    println("El ganador de ajuste ha sido: " + it.nombre)
                                    result.add(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        return result
    }
}