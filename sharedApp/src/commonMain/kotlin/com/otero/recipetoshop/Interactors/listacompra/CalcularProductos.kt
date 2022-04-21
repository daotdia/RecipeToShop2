package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.CalcularAlimentosToProductos
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CalcularProductos(
    private val recetaCache: RecetaCache,
    private val despensaCache: DespensaCache
) {
    fun calcularProductos(
        id_cestaCompra: Int
    ): Flow<DataState<ArrayList<Productos.Producto>>> = flow {
        emit(DataState.loading())

        //Obtengo todos los alimentos activos de la cesta de la compra actual
        val alimentos_cesta = recetaCache.getAlimentosByActiveInCestaCompra(active = true, id_cestaCompra = id_cestaCompra)
        val ingredientes_cesta = recetaCache.getIngredientesByActiveByIdCestaCompra(active = true, id_cestaCompra = id_cestaCompra)
        var all_alimentos_cesta: List<Alimento> = emptyList()
        if (alimentos_cesta == null && ingredientes_cesta == null){
            emit(DataState.data(data = arrayListOf(), message = null))
        } else{
            if(alimentos_cesta == null && ingredientes_cesta != null)
                all_alimentos_cesta.plus(ingredientes_cesta)
            else {
                if (alimentos_cesta != null && ingredientes_cesta == null)
                    all_alimentos_cesta.plus(alimentos_cesta)
                else
                    all_alimentos_cesta.plus(ingredientes_cesta).plus(alimentos_cesta)
            }
        }
        //Obtengo los alimentos activos de la despensa.
        val alimentos_despensa = despensaCache.getAlimentoDespensaByActive(active = true)
        //En el caso de que se hayan encontrado alimentos a traducir a productos.
        if(all_alimentos_cesta.isNotEmpty()){
            //Instancio la calculador de productos.
            val caluladoraProductos = CalcularAlimentosToProductos()
            //Calculo la cantidad de alimentos necesarios; restando lo que ya se tiene en despensa.
            if(alimentos_despensa != null){
                all_alimentos_cesta = caluladoraProductos.calcularNecesidadesAlimentos(alimentos_despensa = alimentos_despensa, alimentos_cesta = all_alimentos_cesta)
            }
            //Encuentro los productos
            val productos_brutos = caluladoraProductos.encontrarProductos(all_alimentos_cesta)
            //Selecciono los mejores productos
            val mejores_productos = caluladoraProductos.seleccionarMejorProducto(productos_brutos)
            //Calculo la cantidad de productos.
            val mejores_productos_unidades = caluladoraProductos.calcularCantidadesProductos(alimentos = ArrayList(all_alimentos_cesta), productos = ArrayList(mejores_productos))

            emit(DataState.data(data = mejores_productos_unidades, message = null))
        }
    }
}