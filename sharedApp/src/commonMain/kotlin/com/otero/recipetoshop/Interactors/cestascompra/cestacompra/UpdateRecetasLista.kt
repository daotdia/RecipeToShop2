package com.otero.recipetoshop.Interactors.cestascompra.cestacompra

import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.util.CommonFLow
import com.otero.recipetoshop.domain.util.DataState
import com.otero.recipetoshop.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class UpdateRecetasLista(
    private  val recetaCache: RecetaCache
){
    //Metodo para obtener todas las recetas a tener en cuenta por la lista de recetas
    fun updateRecetas(
        id_listaRecetas: Int
    ): CommonFLow<DataState<HashMap<String, List<Any>?>>> = flow {

        emit(DataState.loading())

        val result: HashMap<String,List<Any>?> = hashMapOf()

        //Obtengo todas las recetas actuales guardadas en la caché de la aplicación de todas las listas.
        val allRecetas = recetaCache.getAllRecetasInCestasCompra()
        //Guardo las recetas en la key "allRecetas"
        result.put("allRecetas", allRecetas)
        if(allRecetas != null) {
            //De todas las recetas extraigo las recetas cuyo id_cestacompra sea el actual y estén activas.
            val recetasListaActivas = allRecetas.filter { receta ->
                receta.id_cestaCompra == id_listaRecetas
                        && receta.active
            }
            //Guardo las recetas de la lista activas en la key "recetasListaActivas"
            result.put("recetasListaActivas", recetasListaActivas)

            //De todas las recetas extraigo las recetas que son de la lista pero no están activas
            val recetasListaNoActivas = allRecetas.filter { receta ->
                receta.id_cestaCompra == id_listaRecetas
                        && !receta.active
            }
            //Guardo las recetas de la lista no activas con la key "recetasListaNoActivas"
            result.put("recetasListaNoActivas", recetasListaNoActivas)

            //De todas las recetas extraigo las recetas que no son de la lista pero están activas.
            val recetasOutActivas = allRecetas.filter { receta ->
                receta.id_cestaCompra != id_listaRecetas
                        && receta.active
            }
            //Guardo las recetas que no son de la lista y están activas con la key "recetasOutActivas"
            result.put("recetasOutActivas", recetasOutActivas)

            //De todas las recetas extraigo las que no son de la lista y no están activas
            val recetasOutNoActivas = allRecetas.filter { receta ->
                receta.id_cestaCompra != id_listaRecetas
                        && !receta.active
            }
            //Guardo las recetas que no son de la lista y no están activas con la key "recetasOutNoActivas"
            result.put("recetasOutNoActivas", recetasOutNoActivas)

            //Obtengo todos los alimentos de la lista de recetas activos.
            val alimentosLista = recetaCache.getAlimentosByCestaCompra(id_listaRecetas)

            if(alimentosLista != null){
                result.put("alimentosLista", alimentosLista)

                //Obtengo los alimentos activos de la lista y los guardo
                result.put(
                    "alimentosListaActivos",
                    alimentosLista.filter{ alimento ->
                        alimento.id_cestaCompra == id_listaRecetas
                                && alimento.active
                    }
                )

                //Obtengo los alimentos inactivos de la lista y los guardo
                result.put(
                    "alimentosListaNoActivos",
                    alimentosLista.filter { alimento ->
                        alimento.id_cestaCompra == id_listaRecetas
                                && !alimento.active
                    }
                )
            }

        } else{
            //Añado listas vacias al mapa.
            result.put("allRecetas", emptyList())
            result.put("recetasListaActivas", emptyList())
            result.put("recetasListaNoActivas", emptyList())
            result.put("recetasOutActivas", emptyList())
            result.put("recetasOutNoActivas", emptyList())
            result.put("alimentosLista", emptyList())
            result.put("alimentosListaActivos", emptyList())
            result.put("alimentosListaNoActivos", emptyList())
        }
        //Emito el mapa con las recetas actualizadas.
        emit(DataState.data(data = result, message = null))
    }.asCommonFlow()
}