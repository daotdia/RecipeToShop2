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
    ): Flow<DataState<Pair<ArrayList<Alimento>, ArrayList<Productos.Producto>>>> = flow {
        emit(DataState.loading())

        //Obtengo todos los alimentos activos de la cesta de la compra actual
        val alimentos_cesta = recetaCache.getAlimentosByActiveInCestaCompra(active = true, id_cestaCompra = id_cestaCompra)
        val ingredientes_cesta = recetaCache.getIngredientesByActiveByIdCestaCompra(active = true, id_cestaCompra = id_cestaCompra)
        var all_alimentos_cesta: ArrayList<Alimento> = arrayListOf()
        var mejores_productos_unidades: ArrayList<Productos.Producto> = arrayListOf()
        if (alimentos_cesta == null && ingredientes_cesta == null){
            emit(DataState.data(data = Pair(arrayListOf(), arrayListOf()), message = null))
        } else{
            if(alimentos_cesta == null && ingredientes_cesta != null)
                all_alimentos_cesta.addAll(ingredientes_cesta)
            else {
                if (alimentos_cesta != null && ingredientes_cesta == null)
                    all_alimentos_cesta.addAll(alimentos_cesta)
                else
                    all_alimentos_cesta.addAll(ingredientes_cesta!!)
                    all_alimentos_cesta.addAll(alimentos_cesta!!)
            }
        }
        //Obtengo los alimentos activos de la despensa.
        val responseDespensa = despensaCache.getAlimentoDespensaByActive(active = true)
        var alimentos_despensa = arrayListOf<Alimento>()
        if(responseDespensa == null){
            alimentos_despensa = arrayListOf()
        } else {
            alimentos_despensa = ArrayList(alimentos_despensa)
        }
        //En el caso de que se hayan encontrado alimentos a traducir a productos.
        println("He llegado a justo antes del calculo de los productos, con cesta de tamaño: " + all_alimentos_cesta.size)
        if(all_alimentos_cesta.isNotEmpty()){
            //Instancio la calculador de productos.
            val caluladoraProductos = CalcularAlimentosToProductos()
            println("He instanciado la calculadora")
            //Preparo la calculadora
            caluladoraProductos.iniciarCalculadora()
            //Calculo la cantidad de alimentos necesarios; restando lo que ya se tiene en despensa.
            if(!alimentos_despensa.isNullOrEmpty()){
                all_alimentos_cesta = caluladoraProductos.calcularNecesidadesAlimentos(alimentos_despensa = alimentos_despensa, alimentos_cesta = all_alimentos_cesta)
            }
            //Encuentro los productos
            val productos_brutos = caluladoraProductos.encontrarProductos(all_alimentos_cesta)
            var otravez = true
            //Selecciono los mejores productos
            val mejores_productos = caluladoraProductos.seleccionarMejorProducto(productos_brutos)
            //Calculo la cantidad de productos.
            mejores_productos_unidades = caluladoraProductos.calcularCantidadesProductos(alimentos = ArrayList(all_alimentos_cesta), productos = ArrayList(mejores_productos))
            for(producto in mejores_productos_unidades){
                println("La cantidad del producto es: " + producto.cantidad)
                println("El peso del producto es: " + producto.peso)
            }
//            while(otravez){
//                otravez = false
//                //Selecciono los mejores productos
//                val mejores_productos = caluladoraProductos.seleccionarMejorProducto(productos_brutos)
//                //Calculo la cantidad de productos.
//                mejores_productos_unidades = caluladoraProductos.calcularCantidadesProductos(alimentos = ArrayList(all_alimentos_cesta), productos = ArrayList(mejores_productos))
//
//                //Determino si hay algun producto con el que no se ha podido calcular bien su cantidad, lo elimino y repito hasta que encuentre una opción válida.
//                val lista_alternativas: ArrayList<Productos.Producto> = arrayListOf()
//                for(producto in mejores_productos_unidades){
//                    if(producto.cantidad <= 0){
//                        //Si encuentro un producto con cantidad negativa lo elimino de la lista de prodctos brutos
//                        for(tipo in productos_brutos){
//                            tipo.filter { !it.nombre.equals(producto.nombre) }
//                        }
//                        //Busco el grupo de productos de su tipo y lo añado a las alternativas para saber si quedan.
//                        for(tipo in productos_brutos){
//                            lista_alternativas.addAll(tipo.filter { it.query.trim().lowercase().equals(producto.query.trim().lowercase()) })
//                            println("La lista de alternativas añade entre otros: " + producto.nombre)
//                        }
//                        //En el caso de que encuentre alguna alternativa sigue la busqueda, en caso contrario la termina.
//                        if (!lista_alternativas.isEmpty()){
//                             otravez = true
//                        }
//                    }
//                }
//            }

            emit(DataState.data(data = Pair(all_alimentos_cesta, mejores_productos_unidades), message = null))
        }
    }
}