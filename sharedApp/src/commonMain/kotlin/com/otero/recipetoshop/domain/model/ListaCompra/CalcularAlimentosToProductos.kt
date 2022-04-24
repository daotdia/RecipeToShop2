package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.datasource.static.Productos_prueba
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.TipoUnidad
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.Float.Companion.MAX_VALUE
import kotlin.math.ceil

class CalcularAlimentosToProductos {
    val productos: Productos = Productos(arrayListOf())
    fun iniciarCalculadora(): Unit {
        println("He llegado a calculo de JSON")
        val json: JsonArray? = Productos_prueba.json.jsonObject.get("productos")?.jsonArray
        if (json != null) {
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
                        }
                    )
                    producto.add(elemento)
                }
                productos.productos.add(producto)
            }
        }
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

    fun seleccionarMejorProducto(productos: ArrayList<ArrayList<Productos.Producto>>): ArrayList<Productos.Producto>{
        val result: ArrayList<Productos.Producto> = arrayListOf()
        for(tipo in productos){
            var best: Float = MAX_VALUE
            var ganador: Productos.Producto? = null
            for(elemento in tipo){
                val precio_peso = elemento.precio_peso
                    .replace(',', '.')
                    .filter { it.isDigit() || it.equals('.') }
                    .toFloat()
                if(precio_peso< best){
                    ganador = elemento
                    best = precio_peso
                }
            }
            result.add(ganador!!)
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

    fun calcularCantidadesProductos(alimentos: ArrayList<Alimento>, productos: ArrayList<Productos.Producto>): ArrayList<Productos.Producto>{
        //Primero calculo la cantidad de una unidad de producto
        for(producto in productos){
            val cantidad_tipo = producto.calcularCantidad()
            if(cantidad_tipo != null){
                producto.peso = cantidad_tipo.get("peso") as Float
                producto.tipoUnidad = cantidad_tipo.get("tipounidad") as TipoUnidad
            }
        }
        //Después determino la cantidad de unidades de producto necesarias
        for(alimento in alimentos){
            for(producto in productos){
                if(producto.query.trim().lowercase().equals(alimento.nombre.trim().lowercase())){
                    println("He matcheado el producto con el alimento correctamente en calculo de cantidades: " + producto.query)
                    producto.cantidad = CalcularDiferenciaCantidad(alimento.cantidad,alimento.tipoUnidad,producto.peso,producto.tipoUnidad)
                    println("La cantidad calculada es: " + producto.cantidad + "para un peso de alimento " + alimento.cantidad + " y de producto unitario " + producto.peso)
                }
            }
        }
        return productos
    }

    private fun CalcularDiferenciaCantidad(cantidadAlimento: Int, tipoUnidadAlimento: TipoUnidad, pesoProducto: Float, tipoUnidadProducto: TipoUnidad?): Int {
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