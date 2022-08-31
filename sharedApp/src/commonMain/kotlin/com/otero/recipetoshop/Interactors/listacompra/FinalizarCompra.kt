package com.otero.recipetoshop.Interactors.listacompra

import com.otero.recipetoshop.datasource.cache.cachedespensa.DespensaCache
import com.otero.recipetoshop.datasource.cache.cacherecetas.RecetaCache
import com.otero.recipetoshop.domain.model.ListaCompra.CalcularFinalCompra
import com.otero.recipetoshop.domain.model.ListaCompra.Productos
import com.otero.recipetoshop.domain.model.ListaCompra.toAlimento
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.dataEstructres.DataState
import com.otero.recipetoshop.domain.dataEstructres.asCommonFlow
import com.otero.recipetoshop.domain.dataEstructres.CommonFLow
import kotlinx.coroutines.flow.flow

class FinalizarCompra (
    private val recetaCache: RecetaCache,
    private val despensaCache: DespensaCache
) {
        fun finalizarCompra(
            id_cestaCompra: Int,
            productos:  ArrayList<Productos.Producto>
        ): CommonFLow<DataState<Unit>> = flow {

            emit(DataState.loading())

            var eliminarDespensa = false

            //Obtengo los alimentos de la despensa activos
            val alimentos_despensa = despensaCache.getAlimentoDespensaByActive(active = true)

            //Obetngo los productos encontrados que actualmente estén seleccionados como comprados.
            val productos_comprados = productos.filter { it.active == false }
            println("Los productos comprados son: ")
            println(productos_comprados)

            //Obtengo los alimentos de la cesta de la compra necesarios
            var alimentos_cesta: ArrayList<Alimento> = arrayListOf()
            val alimentos = recetaCache.getAlimentosByActiveInCestaCompra(
                active = true,
                id_cestaCompra = id_cestaCompra
            )
            val ingredientes = recetaCache.getIngredientesByActiveByIdCestaCompra(
                id_cestaCompra = id_cestaCompra,
                active = true
            )
            if(alimentos != null){
                alimentos_cesta.addAll(alimentos)
            }
            if(ingredientes != null){
                alimentos_cesta.addAll(ingredientes)
            }

            var alimentos_despensa_SinGastar: List<Alimento> = listOf()
            //Determino cuanto de los alimentos de la despensa no se va a gastar.
            if(alimentos_cesta.isNotEmpty() && !alimentos_despensa.isNullOrEmpty()){
                alimentos_despensa_SinGastar = CalcularFinalCompra.calcDespensaAGastar(
                    alimentos_despensa = alimentos_despensa,
                    alimentos_cesta = alimentos_cesta
                )
                eliminarDespensa = true
            }
            var productos_NoGastados: List<Productos.Producto> = arrayListOf()
            //Determino cuanto de los productos comprados no se van a gastar
            if(productos_comprados.isNotEmpty()){
                productos_NoGastados = CalcularFinalCompra.calcularProductosNoGastados(
                    alimentos_cesta = alimentos_cesta,
                    productos =  ArrayList(productos_comprados)
                )
                eliminarDespensa = true
            }
            //Transoformo la lista de los productos sin gastar a alimentos
            val alimentos_cesta_SinGastar = productos_NoGastados.map { it.toAlimento() }

            //Obtengo los alimentos a unficiar
            val alimentos_despensa_AUnificar = alimentos_despensa_SinGastar.filter { alimento_despensa ->
                alimentos_cesta_SinGastar.any{ it.nombre.lowercase().trim().equals(alimento_despensa.nombre.lowercase().trim()) }
            }
            //Añado las cantidades de los alimentos a unificar,
            if(alimentos_despensa_AUnificar.isNotEmpty()){
                alimentos_despensa_AUnificar.forEach { alimento_despensa ->
                    alimentos_cesta_SinGastar.forEach { alimento_cesta ->
                        if(alimento_despensa.nombre.lowercase().trim().equals(alimento_cesta.nombre.lowercase().trim())){
                            alimento_despensa.cantidad += alimento_cesta.cantidad
                        }
                    }
                }
            }
            //Unifico los alimentos de despensa
            val alimentos_despensa_Unificados = alimentos_despensa_AUnificar + alimentos_despensa_SinGastar.filter { alimento_sinunificar ->
                alimentos_despensa_AUnificar.none { it.nombre.equals(alimento_sinunificar.nombre) }
            }
            //Añado a los alimentos d ela despensa sin gastar unificados los alimentos de la cesta comprados sin gastar que no hayan sido unificados con los de la despensa.
            val new_despensa = alimentos_despensa_Unificados + alimentos_cesta_SinGastar.filter {  alimento_cesta ->
                alimentos_despensa_Unificados.none{ it.nombre.lowercase().trim().equals(alimento_cesta.nombre.lowercase().trim()) }
            }

            println("La nueva despensa es: ")
            println(new_despensa)
            //Guardo en la despensa los nuevos alimentos no usados, antes elimino la anterior claro.
            if(eliminarDespensa){
                despensaCache.removeAllAlimentosDespensa()
                despensaCache.insertAlimentosDespensa(new_despensa)
            }

            //Elimino la lista de la compra
            recetaCache.deleteProductos()

            emit(DataState.data(data = Unit))
        }.asCommonFlow()
}