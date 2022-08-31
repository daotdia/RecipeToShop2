package com.otero.recipetoshop.Interactors.despensa

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditAlimento (
    private val despensaCache: DespensaCache
) {
    fun editaAlimento(
        id_alimento: Int,
        nombre: String,
        cantidad: Int,
        tipo: String
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.loading())

        //Croe el nuevo alimento.
        val new_alimento = Alimento(
            id_alimento  = id_alimento,
            nombre =  nombre,
            cantidad = cantidad,
            tipoUnidad = TipoUnidad.parseTipoUnidad(tipo),
            active = true
        )

        //Elimino el alimento antiguo.
        despensaCache.removeAlimentoDespensaById(id_alimento)

        //Inserto el alimento editado
        despensaCache.insertarAlimentoDespensa(new_alimento)

        emit(DataState(data = Unit))
    }.asCommonFlow()
}