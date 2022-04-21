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
                        tipoUnidad = null,
                        peso = 0f
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

    fun calcularNecesidadesAlimentos(alimentos_despensa: List<Alimento>, alimentos_cesta: List<Alimento>): List<Alimento> {
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
        return alimentos_cesta.filter { it.active == true }
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
                if(producto.query.equals(alimento.nombre)){
                    producto.cantidad = CalcularDiferenciaCantidad(alimento.cantidad,alimento.tipoUnidad,producto.peso,producto.tipoUnidad)
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