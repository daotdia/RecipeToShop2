package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.domain.util.TipoUnidad

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
        var cantidad: Int,
        var peso: Float,
        var tipoUnidad: TipoUnidad?
    )
}

fun Productos.matchElementos(nombreAlimento: String): ArrayList<Productos.Producto>{
    val result: ArrayList<Productos.Producto> = arrayListOf()
    for (tipo in this.productos){
        for(elemento in tipo){
            if(
                elemento.query.equals(nombreAlimento) &&
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

fun Productos.Producto.calcularCantidad(): Map<String, Any>?{
    val nombre: String = this.nombre
    //Obtengo la primera aparición de un número seguido de todos los carácteres restantes.
    val pattern = Regex("[0-9]+[\\w\\s]*")
    val match = pattern.find(nombre)
    if(match != null)
        return parseCantidad(match.value)
    else
        return null
}

private fun parseCantidad(nombreUnidades: String):  Map<String, Any>? {
    val cantidad_producto: HashMap<String, Any>?
    var tipo_unidad: String? = null
    //Obtengo el tipo de unidad
    val pattern_tipoUnidad = Regex("bolsas|bolsa|brik|briks|mg|g|dg|cg|kg|ml|cl|dl|l|ud", RegexOption.IGNORE_CASE)
    val pattern_cantidad = Regex("[0-9]+".plus(pattern_tipoUnidad))
    val sequencia_unidad = pattern_tipoUnidad.findAll(nombreUnidades)
    val valor_multiplicador = pattern_cantidad.findAll(nombreUnidades)
    var nombre_unidad: String? = null
    var multiplicador_unidad: Float = 1f
    var cantidad_unidad_bruta: Float? = null
    //En el caso de que se ahay encontrado al menos una ocurrencia se puede sacar las unidades.
    if(sequencia_unidad.toList().size > 0){
        nombre_unidad = sequencia_unidad.last().value
        //En el caso de que haya más de una ocurrencia, se obtiene el multiplicador de la primera.
        if(valor_multiplicador.toList().size > 1){
            multiplicador_unidad = valor_multiplicador
                .first()
                .value
                .filter { it.isDigit() }
                .toFloat()
        }
        //En el caso de que haya al menos una ocurrencia se obtiene la cantidad
        //de la multiplicaciń del multiplicador por la última ocurrencia.
        if(valor_multiplicador.toList().size > 0){
            cantidad_unidad_bruta = multiplicador_unidad * valor_multiplicador
                .last()
                .value
                .filter { it.isDigit() }
                .toFloat()
        }
    }
    if(nombre_unidad == null || cantidad_unidad_bruta == null){
        return null
    } else {
        return parseUnidad(nombre_unidad, cantidad_unidad_bruta)
    }
}

private fun parseUnidad(nombreUnidad: String, cantidad_bruta: Float): HashMap<String, Any>? {
    val result: HashMap<String, Any> = hashMapOf()
    when(nombreUnidad.lowercase()){
        "mg" -> {
            result.put("tipounidad", TipoUnidad.GRAMOS)
            result.put("peso", cantidad_bruta/1000)
        }
        "g" -> {
            result.put("tipounidad", TipoUnidad.GRAMOS)
            result.put("peso", cantidad_bruta)
        }
        "kg" -> {
            result.put("tipounidad", TipoUnidad.GRAMOS)
            result.put("peso", cantidad_bruta*1000)
        }
        "ml" -> {
            result.put("tipounidad", TipoUnidad.ML)
            result.put("peso", cantidad_bruta)
        }
        "cl" -> {
            result.put("tipounidad", TipoUnidad.ML)
            result.put("peso", cantidad_bruta*10)
        }
        "dl" -> {
            result.put("tipounidad", TipoUnidad.ML)
            result.put("peso", cantidad_bruta*100)
        }
        "l" -> {
            result.put("tipounidad", TipoUnidad.ML)
            result.put("peso", cantidad_bruta*1000)
        }
        "ud" -> {
            result.put("tipounidad", TipoUnidad.UNIDADES)
            result.put("peso", cantidad_bruta)
        }
        else -> return null
    }
    if(!result.isEmpty())
        return result
    else
        return null
}