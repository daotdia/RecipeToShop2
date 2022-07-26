package com.otero.recipetoshop.domain.model.despensa

import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.util.TipoUnidad

data class Alimento(
    val id_cestaCompra: Int? = null,
    val id_receta: Int? = null,
    val id_alimento: Int? = null,
    val nombre: String,
    var cantidad: Int = 0,
    val tipoUnidad: TipoUnidad,
    var active: Boolean
)

fun Alimento.toProducto(): Productos.Producto{
    return Productos.Producto(
        id_producto = -1,
        id_cestaCompra = id_cestaCompra!!.toInt(),
        nombre = nombre,
        peso = cantidad.toFloat(),
        oferta = "",
        imagen_src = "",
        precio_texto = "",
        cantidad = cantidad,
        precio_numero = 0f,
        precio_peso = "",
        query = nombre.lowercase().trim(),
        tipoUnidad = tipoUnidad
    )
}

fun List<Alimento>.toProductos(): List<Productos.Producto>{
    return map { it.toProducto() }
}