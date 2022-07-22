package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.datasource.static.Productos_Carrefour
import com.otero.recipetoshop.datasource.static.productos_Dia
import com.otero.recipetoshop.datasource.static.productos_Mercadona
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.FilterEnum
import com.otero.recipetoshop.domain.util.SupermercadosEnum
import com.otero.recipetoshop.domain.util.TipoUnidad
import kotlinx.serialization.json.*
import kotlin.Float.Companion.MAX_VALUE
import kotlin.math.ceil

class CalcularAlimentosToProductos {

    var productos: Productos = Productos()

    fun iniciarCalculadora(
        supermercados: MutableSet<SupermercadosEnum> = mutableSetOf(SupermercadosEnum.CARREFOUR)
    ): Unit {
        println("He llegado a calculo de JSON")
        val jsons: ArrayList<JsonArray?> = arrayListOf()

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
                            println("Producto en json alcanzado: " + instancia.jsonObject.getValue("nombre").jsonPrimitive.content)
                            val elemento: Productos.Producto = Productos.Producto(
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
                        }
                        productos.productos.add(producto)
                    }
                }
            }
            println("La cantidad de productos añadidos son: " + productos.productos.size)
            println("Los productos son: " + productos.productos.toString())
        }
    }

    fun unificarAlimentos(alimentos: ArrayList<Alimento>): ArrayList<Alimento>{
        print("Los alimentos a unificar son: " + alimentos.toString())
        //Los alimentos con mismo nombre se unen en una  misma necesidad de alimento y para ello hace falta una nueva lista.
        val alimentos_unificados: ArrayList<Alimento> = arrayListOf()
        //Obtengo lista con alimentos únicos.
        //Elimino los alimentos unificados con nombre repetido conservando su orden.
        val alimentos_unicos = ArrayList(alimentos.distinctBy { it.nombre })
        println("El número de alimentos distintos necesarios son: " + alimentos_unicos.size)
        for(alimento in alimentos_unicos){
            //Obtengo los alimentos repetidos para cada alimento si es que los hubiese.
            val repetidos= alimentos.filter {
                alimento.nombre.equals(it.nombre)
            }
            //En el caso de que hubiese al menos un alimento repetido.
            if(repetidos.size > 1){
                println("Encontrados alimentos repetidos con el nombre: " + repetidos.first().nombre)
                val iterator = repetidos.iterator()
                //Obtengo el primer alimenyo.
                val alimento_representativo = iterator.next()
                //Mientras existan alimentos repetidos se añade la cantidad al alimento repetido encontrado.
                while(iterator.hasNext()){
                    //Sumo las cantidades
                    alimento_representativo.cantidad += iterator.next().cantidad
                }
                //Añado el alimento unificado a la lista
                println("Alimento unificado de peso: " + alimento_representativo.nombre + " , peso " + alimento_representativo.cantidad)
                alimentos_unificados.add(alimento_representativo)
            } else {
                //En caso de que no esté repetido simplemento lo añado a la lista de alimentos unificados.
                alimentos_unificados.add(alimento)
            }
        }
        println("El número de alimentos unificados es: " + alimentos_unificados.size)
        return alimentos_unificados
    }

    fun encontrarProductos(alimentos: List<Alimento>): ArrayList<ArrayList<Productos.Producto>> {
        val result: ArrayList<ArrayList<Productos.Producto>> = arrayListOf()
        println("Los alimentos a encontrar producto son: " + alimentos.toString())
        for (alimento in alimentos) {
            val elementos = productos.matchElementos(alimento.nombre)
            if(elementos.isNotEmpty()){
                result.add(elementos)
            }
        }
        println("Los productos seleccionados son: " + result.toString())
        return result
    }

    //TODO: Hacerlo sgún supermercado, es diferente el filtro a aplicar
    fun seleccionarMejorProducto(productos: ArrayList<ArrayList<Productos.Producto>>, filterEnum: FilterEnum = FilterEnum.BARATOS): ArrayList<Productos.Producto>{
        val result: ArrayList<Productos.Producto> = arrayListOf()
        println("El numero de tipo de productos a seleccionar es: " + productos.size)
        println("Los productos a seleccionar son: " + productos.toString())
        when(filterEnum){
            FilterEnum.BARATOS ->
                for(tipo in productos){
                    var best: Float = MAX_VALUE
                    var ganador: Productos.Producto? = null
                    for(elemento in tipo){
                        val precio_peso = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(elemento)
                        if(precio_peso != null && precio_peso < best){
                            ganador = elemento
                            best = precio_peso
                        }
                    }
                    println("El ganador ha sido: " + ganador?.nombre)
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
        }

        return result
    }

    fun calcularNecesidadesAlimentos(alimentos_despensa: ArrayList<Alimento>, alimentos_cesta: ArrayList<Alimento>): ArrayList<Alimento> {
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
                        println("He matcheado el producto con el alimento correctamente en calculo de cantidades: " + producto.query)
                        producto.cantidad = calcularDiferenciaCantidad(alimento.cantidad,alimento.tipoUnidad,producto.peso,producto.tipoUnidad)
                        println("La cantidad calculada es: " + producto.cantidad + "para un peso de alimento " + alimento.cantidad + " y de producto unitario " + producto.peso)
                    }
                }
            }
        }
        println("Los productos finales a comprar son: " + productos.toString())
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
}