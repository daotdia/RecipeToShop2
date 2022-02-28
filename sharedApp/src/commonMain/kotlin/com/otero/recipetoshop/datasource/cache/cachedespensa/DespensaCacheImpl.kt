package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDB
import com.otero.recipetoshop.datasource.cachedespensa.RecipeToShopDBQueries
import com.otero.recipetoshop.domain.model.despensa.Alimento

/*
Esta es la implementación del servicio, utiliza la base de datos generada en otra sección. Debe de utilizar funciones de parseo para amoldar
la respuesta de la base de datos al dominio interno.
 */
class DespensaCacheImpl(
    private val cestaCompraDataBase: RecipeToShopDB
) :DespensaCache{
    private val queries: RecipeToShopDBQueries = cestaCompraDataBase.recipeToShopDBQueries

    override fun insertarAlimentoDespensa(alimento: Alimento) {
        if(alimento.id_alimento != null){
            queries.removeAlimentoDespensaById(alimento.id_alimento.toLong())
        }
        queries.insertAlimentoDespensa(
            nombre = alimento.nombre,
            tipo = alimento.tipoUnidad.name,
            cantidad = alimento.cantidad.toLong(),
            active = alimento.active
        )
    }

    override fun insertAlimentosDespensa(alimentos: List<Alimento>) {
        for(alimento in alimentos){
            insertarAlimentoDespensa(alimento)
        }
    }

    override fun searchAlimentosDespensa(query: String): List<Alimento> {
        return queries.searchAlimentosDespensa(
            query = query,
        ).executeAsList().toListaAlimentos()
    }

    override fun getAllAlimentosDespensa(): List<Alimento> {
        return queries.getAllAlimentosDespensa()
            .executeAsList()
            .toListaAlimentos()
    }

    override fun getAlimentoDespensaByActive(active: Boolean): List<Alimento>? {
        return queries.getAlimentoDespensaByActive(active = active)
            .executeAsList()
            .toListaAlimentos()
    }

    override fun getAlimentoDespensaById(id_alimento: Int): Alimento? {
        return try {
            queries.getAlimentoDespensaById(id_alimento.toLong())
                .executeAsOne()
                .toAlimento()
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con id: ${id_alimento}")
            null
        }
    }

    override fun removeAllAlimentosDespensa() {
        return queries.removeAllAlimentosDespensa()
    }

    override fun removeAlimentoDespensaById(id_alimento: Int) {
        return try {
            queries.removeAlimentoDespensaById(id_despensa = id_alimento.toLong())
        }catch (e: NullPointerException){
            println(e.message + "    ////  No se ha podido obtener el alimento con id: ${id_alimento}")
        }
    }
}