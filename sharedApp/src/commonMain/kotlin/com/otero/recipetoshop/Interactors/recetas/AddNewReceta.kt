package com.otero.recipetoshop.Interactors.recetas

import com.otero.recipetoshop.datasource.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.model.UIComponentType
import com.otero.recipetoshop.domain.model.despensa.Food
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddNewListaReceta(
    private val recetaCache: RecetaCache
) {
    fun addListaReceta(
        listareceta: ListaRecetas
    ): Flow<DataState<Boolean>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Compruebo que el alimento no se encuentre ya.
        var igual: Boolean = false
        val currentListaDeListaRecetas = recetaCache.getAllListaRecetas()
        currentListaDeListaRecetas.forEach { listaDeListaRecetas ->
            if (listaDeListaRecetas.nombre.equals(listareceta.nombre)) {
                igual = true
            }
        }
        //Si no se ha encontrado
        if (!igual) {
            //Guardo en cache el alimento con la cantidad modificada
            recetaCache.insertListaRecetas(listareceta)
            // Emito el alimento ya seteado en cantidad
            emit(DataState.data(message = null, data = true))
        } else {
            emit(DataState.data(message = null, data = false))
            //En caso contrario emito un error.
//                emit(DataState.error<Food>(
//                    message = GenericMessageInfo.Builder()
//                        .id("Error Actualizando Cantidad Alimento")
//                        .title("Error")
//                        .uiComponentType(UIComponentType.Dialog)
//                        .description("Invalid Event")
//                )
//            )
        }
    }
}