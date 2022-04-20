package com.otero.recipetoshop.domain.model.ListaCompra

data class Productos(
    val productos: ArrayList<List<Producto>>
) {
    data class Producto(
        val imagen_src: String,
        val nombre: String,
        val oferta: String,
        val precio: String,
        val precio_peso: String,
        val query: String,
        val cantidad: Int,
    )
}

fun Productos.matchElementos(nombreAlimento: String): ArrayList<Productos.Producto>{
    val result: ArrayList<Productos.Producto> = arrayListOf()
    for (tipo in productos){
        for(elemento in tipo){
            if(
                elemento.nombre.equals(nombreAlimento) &&
                elemento.isComplete()
            ){
                result.add(elemento)
            }
        }
    }
    return result
}

fun Productos.Producto.isComplete(): Boolean {
    return this.precio_peso.isNotBlank() &&
            this.precio.isNotBlank() &&
            this.nombre.isNotBlank() &&
            this.imagen_src.isNotBlank() &&
            this.query.isNotBlank()
}