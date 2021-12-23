package com.otero.recipetoshop.Interactors.recetas.listaldelistasrecetas

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.recetas.ListaRecetas
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddNewListaRecetas(
    private val recetaCache: RecetaCache
) {
    fun addListaRecetas(
        listareceta: ListaRecetas
    ): Flow<DataState<Int>> = flow {
        //Emito de manera predefinida que se está cargando la modificación.
        emit(DataState.loading())

        //Compruebo que el alimento no se encuentre ya.
        var igual: Boolean = false
        val currentListaDeListaRecetas = recetaCache.getAllListaRecetas()
        if(currentListaDeListaRecetas != null){
            currentListaDeListaRecetas.forEach { listaDeListaRecetas ->
                if (listaDeListaRecetas.nombre.equals(listareceta.nombre)) {
                    igual = true
                }
            }
        }
        //Guardo en cache el alimento con la cantidad modificada
        val id: Int = recetaCache.insertListaRecetas(listareceta)
        if(id != -1) {
            emit(DataState.data(message = null, data = id))
        } else {
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