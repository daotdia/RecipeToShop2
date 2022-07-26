package com.otero.recipetoshop.datasource.cache.cacherecetas

import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDBQueries
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.model.CestaCompra.CestaCompra
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.model.ListaCompra.Productos

class RecetaCacheImpl(
    private val cestaCompraDataBase: RecipeToShopDB
) :RecetaCache
{
    //Lista Recetas
    private val queries: RecipeToShopDBQueries = cestaCompraDataBase.recipeToShopDBQueries

    override fun insertCestaCompra(cestaCompra: CestaCompra): Int {
        var id: Int = -1
        cestaCompraDataBase.transaction {
            queries.insertCestaCompra(
                nombre = cestaCompra.nombre
            )
            id = lastIdInserted()
        }
        return id
    }

    override fun insertCestasCompra(cestasCompra: List<CestaCompra>): List<Int> {
        val idList: ArrayList<Int> = ArrayList()
        for(cestaCompra in cestasCompra){
            idList.add(insertCestaCompra(cestaCompra))
        }
        return idList
    }

    //Lista de lista de recetas con ingredientes nulos.
    override fun getAllCestasCompra(): List<CestaCompra> {
        return queries.getAllCestasCompra().executeAsList().toListaCestasCompra()
    }

    //Devuelve lista de receta con ingredientes nulos, hay que llamar otra vez con su nombre por si se quieren los ingredientes.
    override fun getCestaCompraById(id_cestaCompra: Int): CestaCompra? {
        return try {
            queries
                .getCestaCompraById(id_cestaCompra = id_cestaCompra.toLong())
                .executeAsOne()
                .toCestaCompra()
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_cestaCompra}")
            null
        }
    }
    override fun removeAllCestasCompra() {
        return queries.removeAllCestasCompra()
    }

    override fun removeCestaCompraById(id_cestaCompra: Int) {
        return try {
            queries.removeCestaCompraById(id_cestaCompra = id_cestaCompra.toLong())
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido encontrar la lista de recetas con id: ${id_cestaCompra}")
        }
    }

    override fun lastIdInserted(): Int {
        return queries.lastIdInserted().executeAsOne().toInt()
    }


    //Recetas
    override fun insertRecetaToCestaCompra(receta: Receta): Int{
        var id: Int = -1
        if(receta.id_Receta != null){
            queries.removeRecetaByIdInCestaCompra(receta.id_Receta.toLong())
        }
        cestaCompraDataBase.transaction {
            queries.insertRecetaToCestaCompra(
                id_cestaCompra = receta.id_cestaCompra.toLong(),
                nombre = receta.nombre,
                cantidad = receta.cantidad.toLong(),
                user = receta.user,
                active = receta.active,
                imageSource = receta.imagenSource,
                rating = if(receta.rating != null) receta.rating.toLong() else null,
                favorita = receta.isFavorita
            )
            id = lastIdInserted()
        }
        return id
    }

    override fun insertRecetasToCestaCompra(recetas: List<Receta>) {
        for(receta in recetas){
            insertRecetaToCestaCompra(receta)
        }
    }

    override fun getAllRecetasInCestasCompra(): List<Receta> {
        return queries
            .getAllRecetasInCestasCompra()
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByUserInCestaCompra(user: Boolean, id_cestaCompra: Int): List<Receta> {
        return queries
            .getRecetasByUserInCestaCompra(user = user, id_cestaCompra = id_cestaCompra.toLong())
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByActiveInCestaCompra(active: Boolean, id_cestaCompra: Int): List<Receta> {
        return queries
            .getRecetasByActiveInCestaCompra(active = active, id_cestaCompra = id_cestaCompra.toLong())
            .executeAsList()
            .toListaRecetas()
    }

    override fun getRecetasByCestaCompra(id_cestaCompra: Int): List<Receta>? {
        return try{
             queries
                .getRecetasByCestaCompra(id_cestaCompra = id_cestaCompra.toLong())
                .executeAsList()
                .toListaRecetas()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_cestaCompra}")
            null
        }
    }

    override fun getRecetaByIdInCestaCompra(id_receta: Int): Receta? {
        return try{
            queries
                .getRecetaByIdInCestaCompra(id_receta = id_receta.toLong())
                .executeAsOne()
                .toReceta()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
            null
        }
    }

    override fun removeAllRecetasInCestasCompra() {
        queries.removeAllRecetasInCestasCompra()
    }

    override fun removeRecetasByCestaCompra(id_cestaCompra: Int) {
        try{
            queries.removeRecetasByCestaCompra(id_cestaCompra = id_cestaCompra.toLong())
        } catch (e:Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_cestaCompra}")
        }
    }

    override fun removeRecetaByIdInCestaCompra(id_receta: Int) {
        try{
            queries.removeRecetaByIdInCestaCompra(id_receta = id_receta.toLong())
        } catch (e:Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
        }
    }

    override fun getAllRecetasFavoritas(): List<Receta> {
        return try{
            queries.getRecetasFavoritas(isfavorita = true)
                .executeAsList()
                .toListaRecetas()
        } catch (e:Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas favoritas")
            emptyList()
        }
    }

    //Alimentos
    override fun insertAlimentoToCestaCompra(alimento: Alimento) {
        if(alimento.id_alimento != null){
            queries.removeAlimentoByIdInCestaCompra(alimento.id_alimento.toLong())
        }
        queries.insertAlimentoToCestaCompra(
            id_cestaCompra = alimento.id_cestaCompra!!.toLong(),
            nombre = alimento.nombre,
            cantidad = alimento.cantidad.toLong(),
            tipo = alimento.tipoUnidad.name,
            active = alimento.active
        )
    }

    override fun insertAlimentosToCestaCompra(alimentos: List<Alimento>) {
        for(alimento in alimentos){
            insertAlimentoToCestaCompra(alimento)
        }
    }

    override fun getAlimentosByCestaCompra(id_cestaCompra: Int): List<Alimento>? {
        return try{
            queries
                .getAlimentosByCestaCompra(id_cestaCompra = id_cestaCompra.toLong())
                .executeAsList()
                .toListaAlimentos()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_cestaCompra}")
            null
        }
    }

    override fun getAlimentosByActiveInCestaCompra(active: Boolean, id_cestaCompra: Int): List<Alimento> {
        return queries
            .getAlimentosByActiveInCestaCompra(active =active, id_cestaCompra = id_cestaCompra.toLong())
            .executeAsList()
            .toListaAlimentos()
    }

    override fun getAllAlimentosInCestasCompra(): List<Alimento> {
        return queries
            .getAllAlimentosInCestasCompra()
            .executeAsList()
            .toListaAlimentos()
    }

    override fun getAlimentoByIdInCestaCompra(id_alimento: Int): Alimento? {
        return try {
            queries
                .getAlimentoByIdInCestaCompra(id_alimento = id_alimento.toLong())
                .executeAsOne()
                .toAlimento()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_alimento}")
            null
        }
    }

    override fun removeAllAlimentosInCestasCompra() {
        queries.removeAllAlimentosInCestasCompra()
    }

    override fun removeAlimentosByCestaCompra(id_cestaCompra: Int) {
        try{
            queries.removeAlimentosByCestaCompra(id_cestaCompra = id_cestaCompra.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_cestaCompra}")
        }
    }

    override fun removeAlimentoByIdInCestaCompra(id_alimento: Int) {
        try{
            queries.removeAlimentoByIdInCestaCompra(id_alimento = id_alimento.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_alimento}")
        }
    }


    //Ingredientes
    override fun insertIngredienteToReceta(ingrediente: Alimento) {
        queries.insertIngredienteToReceta(
            id_receta = ingrediente.id_receta!!.toLong(),
            id_cestaCompra = ingrediente.id_cestaCompra!!.toLong(),
            nombre = ingrediente.nombre,
            cantidad = ingrediente.cantidad.toLong(),
            tipo = ingrediente.tipoUnidad.name,
            active = ingrediente.active
        )
    }

    override fun insertIngredientesToReceta(ingredientes: List<Alimento>) {
        for(ingrediente in ingredientes){
            insertIngredienteToReceta(ingrediente)
        }
    }

    override fun getIngredientesByReceta(id_receta: Int): List<Alimento>? {
        return try{
            queries
                .getIngredientesByReceta(id_receta = id_receta.toLong())
                .executeAsList()
                .toListaIngredientes()
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
            null
        }
    }

    override fun getAllIngredientesInCestasCompra(): List<Alimento> {
        return queries
                .getAllIngredientesInCestasCompra()
                .executeAsList()
                .toListaIngredientes()

    }

    override fun getIngredientsByActiveInRecta(active: Boolean, id_receta: Int): List<Alimento> {
        return queries
            .getIngredientsByActiveInReceta(active = active, id_receta = id_receta.toLong())
            .executeAsList()
            .toListaIngredientes()
    }

    override fun getIngredientesByActiveByIdCestaCompra(
        active: Boolean,
        id_cestaCompra: Int
    ): List<Alimento>? {
        return try{
            queries
                .getIngredientesByActiveByIdCestaCompra(active = active, id_cestaCompra = id_cestaCompra.toLong())
                .executeAsList()
                .toListaIngredientes()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtener la cesta de la compra con id: ${id_cestaCompra}")
            null
        }
    }

    override fun getIngredienteByIdInReceta(id_ingrediente: Int): Alimento? {
        return try{
            queries
                .getIngredienteByIdInReceta(id_alimento = id_ingrediente.toLong())
                .executeAsOne()
                .toIngrediente()
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_ingrediente}")
            null
        }
    }

    override fun removeAllIngredientesInCestasCompra() {
        queries.removeAllIngredientesInCestasCompra()
    }

    override fun removeIngredientesByRecetaInReceta(id_receta: Int) {
        try{
            queries.removeIngredientesByRecetaInReceta(id_receta = id_receta.toLong())
        }catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_receta}")
        }
    }

    override fun removeIngredienteByIdInReceta(id_ingrediente: Int) {
        try {
            queries.removeIngredienteByIdInReceta(id_alimento = id_ingrediente.toLong())
        } catch (e: Exception){
            println(e.message + "    ////  No se ha podido obtenerla lista de recetas con id: ${id_ingrediente}")

        }
    }

    override fun insertProducto(id_cestaCompra: Int, producto: Productos.Producto): Boolean {
       try {
           if(producto.id_producto != -1){
               deleteProducto(producto.id_producto)
           }
           queries.insertProducto(
               id_cestaCompra = id_cestaCompra.toLong(),
               nombre = producto.nombre,
               imagen_src = producto.imagen_src,
               cantidad = producto.cantidad.toLong(),
               peso = producto.peso,
               precio_numero = producto.precio_numero,
               precio_peso = producto.precio_peso,
               precio_texto = producto.precio_texto,
               oferta = producto.oferta,
               tipoUnidad = producto.tipoUnidad!!.name,
               query = producto.query,
               supermercado = producto.supermercado.name,
               noEnocntrado = producto.noEncontrado,
               active = producto.active
           )
           println("El precio numeral en cache es: " + producto.precio_numero)
           return true
       }catch (e: Exception){
           println("Probalmeas al insertar producto: " + producto.nombre)
           return false
       }
    }

    override fun getProductosEncontrados(): Productos {
        return try {
            queries
                .getProductosSegunEncontrado(noencontrado = false)
                .executeAsList()
                .toProductos()
        }catch (e: Exception){
            println("Problemas al obtener los productos encontrados de cache: " + e.message)
            Productos(
                productos = arrayListOf()
            )
        }
    }

    override fun getProductosNoEncontrados(): List<Productos.Producto> {
        try {
            val productos = queries
                .getProductosSegunEncontrado(noencontrado = true)
                .executeAsList()
                .toProductos()
            return productos.productos_cache
        }catch (e: Exception){
            println("Problemas al obtener los productos no enocntrados de cache: " + e.message)
            return emptyList()
        }
    }

    override fun deleteProducto(id_producto: Int) {
        try {
            queries.deleteProductoByID(id_producto = id_producto.toLong())
        }catch (e: Exception){
            println("Probelmas al eleiminar el producto por su ID")
        }
    }
    override fun deleteProductos(): Boolean {
        try{
            queries.deleteProductos()
            return true
        }catch (e:Exception){
            println("Problemas al eliminar los productos: " + e.message)
            return false
        }
    }
}