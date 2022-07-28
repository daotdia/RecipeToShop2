package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.domain.util.TipoUnidad

class CalcularFinalCompra {
    companion object {
        fun calcDespensaAGastar(
            alimentos_despensa: List<Alimento>,
            alimentos_cesta: List<Alimento>
        ): List<Alimento>{
            //Obtengo los alimentos de la despensa que se van a gastar por ser necesarios
            val alimentos_AGastar = alimentos_despensa.filter { alimento_despensa ->
                alimentos_cesta.any{
                    it.nombre.equals(alimento_despensa.nombre)
                }
            }

            //Determino la cantidad de cada alimento que queda tras ser gastado según la necesidad
            alimentos_AGastar.forEach { alimento_despensa ->
                var cantidad_cesta = 0
                //Obtengo los alimentos en la cesta que se llaman igual que él mismo.
                val alimentos_cesta_despensa = alimentos_cesta.filter { it.nombre.equals(alimento_despensa) }
                //Obtengo la cantidad de alimento a restar
                alimentos_cesta_despensa.forEach {
                    if(
                        !it.tipoUnidad.equals(TipoUnidad.UNIDADES) ||
                        it.tipoUnidad.equals(alimento_despensa.tipoUnidad)
                    ){
                        cantidad_cesta += it.cantidad
                    }
                }
                //Resto la cantidad de la cesta a la cantidad en despensa
                alimento_despensa.cantidad = alimento_despensa.cantidad - cantidad_cesta
                //Si la cantidad que queda es menor que cero se iguala a cero
                if(alimento_despensa.cantidad < 0){
                    alimento_despensa.cantidad = 0
                }
            }
            //Eliminos los alimentos que se van a gastar por las recetas.
            val alimentos_SinGastar = alimentos_AGastar.filter {
                it.cantidad != 0
            }

            return alimentos_SinGastar
        }

        fun calcularProductosNoGastados(
            alimentos_cesta: List<Alimento>,
            productos: ArrayList<Productos.Producto>
        ): List<Productos.Producto>{
            //A la cantidad de cada producto le resto la cantidad necesitada
            productos.forEach { producto ->
                var cantidad_cesta: Int = 0
                //Obtengo los alimentos de la cesta correspondientes al producto.
                val alimentos_producto = alimentos_cesta.filter { it.nombre ==  producto.query }
                //Calculo la cantidad en la cesta a necesitar del producto
                alimentos_producto.forEach {
                    if(
                        it.tipoUnidad != TipoUnidad.UNIDADES ||
                        it.tipoUnidad.equals(producto.tipoUnidad)
                    ){
                        cantidad_cesta += it.cantidad
                    }
                }
                //Resto la cantidad a necesitar con la cantiad comprada
                producto.cantidad = producto.cantidad - cantidad_cesta
                //Si la cantidad es menor lo seteo a 0 aunque no debería ser así
                if(producto.cantidad < 0){
                    println("no deberíamos llegar hasta aquí ojo")
                    producto.cantidad = 0
                }
            }
            //Devuelvo los productos cuya cantidad no sea cero
            return productos.filter { it.cantidad != 0 }
        }
    }
}