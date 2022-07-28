package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.CalcularFinalCompra
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.ListaCompra.toAlimento
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FinalizarCompra (
    private val recetaCache: RecetaCache,
    private val despensaCache: DespensaCache
) {
        fun finalizarCompra(
            id_cestaCompra: Int
        ): Flow<DataState<Unit>> = flow {

            emit(DataState.loading())

            //Obtengo los alimentos de la despensa activos
            val alimentos_despensa = despensaCache.getAlimentoDespensaByActive(active = true)

            //Obetngo los productos encontrados que actualmente estén seleccionados como comprados.
            val productos_encontrados = recetaCache.getProductosEncontrados().productos
            val productos_comprados: ArrayList<Productos.Producto> = arrayListOf()
            //Filto de los productos encontrados aquellos que hayan sido ticados como comprados
            for (productos in productos_encontrados) {
                productos_comprados.addAll(productos.filter {
                    it.active == false
                })
            }

            //Obtengo los alimentos de la cesta de la compra necesarios
            val alimentos_cesta = recetaCache.getAlimentosByActiveInCestaCompra(
                active = true,
                id_cestaCompra = id_cestaCompra
            )

            var alimentos_despensa_SinGastar: List<Alimento> = listOf()
            //Determino cuanto de los alimentos de la despensa no se va a gastar.
            if(alimentos_cesta != null && alimentos_despensa != null){
                alimentos_despensa_SinGastar = CalcularFinalCompra.calcDespensaAGastar(
                    alimentos_despensa = alimentos_despensa,
                    alimentos_cesta = alimentos_cesta
                )
            }
            var productos_NoGastados: List<Productos.Producto> = arrayListOf()
            //Determino cuanto de los productos comprados no se van a gastar
            if(alimentos_cesta != null && productos_comprados.isNotEmpty()){
                productos_NoGastados = CalcularFinalCompra.calcularProductosNoGastados(
                    alimentos_cesta = alimentos_cesta,
                    productos =  productos_comprados
                )
            }
            //Transoformo la lista de los productos sin gastar a alimentos
            val alimentos_cesta_SinGastar = productos_NoGastados.map { it.toAlimento() }

            //Los alimentos en despensa son los productos y los alimentos de la despensa no gastados.
            val new_despensa: List<Alimento> = alimentos_despensa_SinGastar + alimentos_cesta_SinGastar

            //Guardo en la despensa los nuevos alimentos no usados, antes elimino la anterior claro.
            despensaCache.removeAllAlimentosDespensa()
            despensaCache.insertAlimentosDespensa(new_despensa)

            //Elimino la lista de la compra
            recetaCache.deleteProductos()

            emit(DataState.data(data = Unit))
        }
}