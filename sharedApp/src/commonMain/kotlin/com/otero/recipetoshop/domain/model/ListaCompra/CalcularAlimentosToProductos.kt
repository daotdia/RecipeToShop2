package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.datasource.static.Productos_prueba
import com.otero.recipetoshop.domain.model.despensa.Alimento
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.Float.Companion.MAX_VALUE

class CalcularAlimentosToProductos {
    val productos: Productos = Productos(arrayListOf())
    init {
        val json: JsonArray? = Productos_prueba.json.jsonObject.get("productos")?.jsonArray
        if (json != null) {
            for (tipo in json) {
                val producto: ArrayList<Productos.Producto> = arrayListOf()
                for (instancia in tipo.jsonArray) {
                    val elemento: Productos.Producto = Productos.Producto(
                        nombre = instancia.jsonObject.getValue("nombre").jsonPrimitive.content,
                        imagen_src = instancia.jsonObject.getValue("imagen_src").jsonPrimitive.content,
                        oferta = instancia.jsonObject.getValue("oferta").jsonPrimitive.content,
                        precio = instancia.jsonObject.getValue("precio").jsonPrimitive.content,
                        precio_peso = instancia.jsonObject.getValue("precio_peso").jsonPrimitive.content,
                        query = instancia.jsonObject.getValue("query").jsonPrimitive.content,
                        cantidad = 0,
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
                if(elemento.precio_peso.toFloat() < best){
                    ganador = elemento
                    best = elemento.precio_peso.toFloat()
                }
            }
            result.add(ganador!!)
        }
        return result
    }
}