package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.SupermercadosEnum
import com.otero.recipetoshop.domain.util.TipoUnidad

data class Productos(
    val id_cestaCompra: Int = -1,
    var productos: ArrayList<List<Producto>> = arrayListOf(),
    val productos_cache: List<Producto> = listOf()
) {
    data class Producto(
        val id_producto: Int,
        val id_cestaCompra: Int,
        val imagen_src: String,
        val nombre: String,
        val oferta: String,
        val precio_texto: String,
        val precio_peso: String,
        val query: String,
        var cantidad: Int,
        var peso: Float,
        var tipoUnidad: TipoUnidad?,
        val precio_numero: Float,
        val noEncontrado: Boolean = false,
        val supermercado: SupermercadosEnum = SupermercadosEnum.CARREFOUR,
        val active: Boolean = true
    )
}

fun Productos.matchElementos(nombreAlimento: String): ArrayList<Productos.Producto>{
    val result: ArrayList<Productos.Producto> = arrayListOf()
    for (tipo in this.productos){
        for(elemento in tipo){
            if(
                elemento.query.lowercase().trim().equals(nombreAlimento.lowercase().trim()) &&
                elemento.isComplete()
            ){
                println("He encontrado un producto coiencidente con el alimento: " + nombreAlimento)
                result.add(elemento)
            }
        }
    }
    return result
}

fun Productos.Producto.isComplete(): Boolean {
    return this.precio_peso.isNotBlank() &&
            this.precio_texto.isNotBlank() &&
            this.nombre.isNotBlank() &&
            this.imagen_src.isNotBlank() &&
            this.query.isNotBlank()
}

fun Productos.Producto.toAlimento(): Alimento{
    return Alimento(
        id_cestaCompra = id_cestaCompra,
        id_receta = -1,
        id_alimento = null,
        nombre = query,
        cantidad = peso.toInt(),
        tipoUnidad = tipoUnidad!!,
        active = true
    )
}

fun List<Productos.Producto>.toAlimentos(): List<Alimento>{
    return map { it.toAlimento() }
}

fun List<List<Productos.Producto>>.getPesoMedio(): Float{
    var peso_total = 0f
    var num_productos = 0
    this.forEach { it.forEach {
        peso_total += it.peso
        num_productos += 1
    } }
   return peso_total / num_productos
}

fun List<List<Productos.Producto>>.getPrecioMedio(): Float{
    var peso_total = 0f
    var num_productos = 0f
    this.forEach { it.forEach {
        val precio = ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(it)
        if (precio != null) {
            peso_total +=  precio
            num_productos += 1
        }
    } }
    return peso_total / num_productos
}

fun List<List<Productos.Producto>>.getNumeroProductos(): Int{
    var num_productos = 0
    this.forEach { it.forEach {
        num_productos += 1
    } }
    return num_productos
}