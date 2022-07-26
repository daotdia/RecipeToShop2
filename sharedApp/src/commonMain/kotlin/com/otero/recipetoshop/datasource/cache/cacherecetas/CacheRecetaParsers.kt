package com.otero.recipetoshop.datasource.cache.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.*
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.util.SupermercadosEnum
import com.otero.recipetoshop.domain.util.TipoUnidad
/*
En este fichero hay distintas funciones auxiliares para parsear la respuesta de la caché.
 */
//Parsers CestaCompra.
fun CestaCompra_Entity.toCestaCompra(): CestaCompra{
    return CestaCompra(
        id_cestaCompra = id_cestaCompra.toInt(),
        nombre = nombre,
    )
}

fun List<CestaCompra_Entity>.toListaCestasCompra(): List<CestaCompra>{
    return map{it.toCestaCompra()}
}

//Parsers Receta
fun Receta_Entity.toReceta(): Receta {
    return Receta(
        id_cestaCompra = id_cestaCompra.toInt(),
        id_Receta = id_receta.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        user = user,
        active = active,
        imagenSource = imageSource,
        isFavorita = favorita
    )
}

fun List<Receta_Entity>.toListaRecetas(): List<Receta>{
    return map { it.toReceta() }
}


//Parsers Alimentos
fun Alimentos_Entity.toAlimento(): Alimento{
    return Alimento(
        id_cestaCompra = id_cestaCompra.toInt(),
        id_alimento = id_alimento.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        tipoUnidad = TipoUnidad.valueOf(tipo),
        active = active
    )
}

fun List<Alimentos_Entity>.toListaAlimentos(): List<Alimento>{
    return map{it.toAlimento()}
}

//Parser Ingredientes
fun Ingredients_Entity.toIngrediente(): Alimento{
    return Alimento(
        id_cestaCompra = id_cestaCompra.toInt(),
        id_receta = id_receta.toInt(),
        id_alimento = id_ingrediente.toInt(),
        nombre = nombre,
        cantidad = cantidad.toInt(),
        tipoUnidad = TipoUnidad.valueOf(tipo),
        active = active
    )
}

fun List<Ingredients_Entity>.toListaIngredientes(): List<Alimento>{
    return map { it.toIngrediente() }
}

fun Producto_Entity.toProducto(): Productos.Producto{
    return Productos.Producto(
        id_cestaCompra = id_cestaCompra.toInt(),
        imagen_src = imagen_src,
        nombre = nombre,
        oferta = oferta,
        precio_texto = precio_texto,
        precio_peso = precio_peso,
        query = query,
        cantidad = cantidad.toInt(),
        peso = peso.toFloat(),
        tipoUnidad = TipoUnidad.valueOf(tipoUnidad),
        precio_numero = precio_numero.toFloat(),
        supermercado = SupermercadosEnum.parseString(supermercado)
    )
}

fun List<Producto_Entity>.toProductos(): Productos{
    val listaProductos = map { it.toProducto() }
    return Productos(
        id_cestaCompra = first().id_cestaCompra.toInt(),
        productos_cache = listaProductos
    )
}