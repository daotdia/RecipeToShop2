package com.otero.recipetoshop.datasource.cache.cachedespensa

import com.otero.recipetoshop.domain.model.despensa.Alimento
/*
Esta es la interfaz del servicio.
 */
interface DespensaCache {
    fun insertarAlimentoDespensa(alimento: Alimento): Unit

    fun insertAlimentosDespensa(alimentos: List<Alimento>): Unit

    fun searchAlimentosDespensa(query: String): List<Alimento>?

    fun getAllAlimentosDespensa(): List<Alimento>?

    fun getAlimentoDespensaByActive(active: Boolean): List<Alimento>?

    fun getAlimentoDespensaById(id_food: Int): Alimento?

    fun removeAllAlimentosDespensa(): Unit

    fun removeAlimentoDespensaById(id_food: Int): Unit
}