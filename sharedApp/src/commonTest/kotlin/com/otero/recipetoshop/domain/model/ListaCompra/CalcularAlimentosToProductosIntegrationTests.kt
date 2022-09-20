package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.Interactors.listacompra.CalcularProductos
import com.otero.recipetoshop.domain.dataEstructres.FilterEnum
import com.otero.recipetoshop.domain.dataEstructres.SupermercadosEnum
import com.otero.recipetoshop.domain.model.despensa.Alimento
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalcularAlimentosToProductosIntegrationTests {

    private lateinit var fakes: Fakes

    private lateinit var supermercados: MutableSet<SupermercadosEnum>

    private lateinit var calculadora: CalcularAlimentosToProductos

    @BeforeTest
    fun onBefore(){
        fakes = Fakes()

        supermercados = mutableSetOf(
            SupermercadosEnum.CARREFOUR,
            SupermercadosEnum.DIA,
            SupermercadosEnum.MERCADONA
        )

        calculadora = CalcularAlimentosToProductos()
        calculadora.iniciarCalculadora()
    }

    @Test
    fun chechTests(){
        assertEquals(true,true)
    }

    //Caso: Calculo de lista de la compra sin alimentos necesarios
    //Expected: Devolución de una lista vacía de productos.
    @Test
    fun `si lista de la compra sin alimentos necesarios entonces lista de productos vacia`(){
        //Given
        val cesta: List<Alimento> = listOf()
        val despensa: List<Alimento> = listOf()
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)

        //Then
        assertEquals(expected = true, actual = productos.isEmpty())
    }

    //Caso: Calculo de lista de la compra sin supermercados seleccionados.
    //Expected: Devolución de una lista vacía de productos.
    @Test
    fun `si no se selecciona supermercados al calcular compra entonces lista de productos vacia`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, mutableSetOf(), filter)

        //Then
        assertEquals(expected = true, actual = productos.isEmpty())
    }

    //Caso: Calculo de lista de la compra con un supermercado seleccionado.
    //Expected: Devolución de una lista de productos de sólo dicho supermercado.
    @Test
    fun `si  se selecciona un supermercado al calcular compra entonces productos de solo ese supermercado`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, mutableSetOf(SupermercadosEnum.CARREFOUR), filter)

        //Then
        assertEquals(expected = true, actual = productos.all { it.supermercado.equals(SupermercadosEnum.CARREFOUR) })
    }


    //Caso: Calculo de la lista de la compra con necesidad de alimentos cubierta totalmente por la despensa.
    //Expected: Devolución de una lista de productos que cubre las necesidades netas de alimentos.
    @Test
    fun `si la despensa cubre las necesidade alimentarias entonces lista de productos vacia`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad*10)}
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, mutableSetOf(SupermercadosEnum.CARREFOUR), filter)

        //Then
        assertEquals(expected = true, actual = productos.isEmpty())
    }


    //Caso: Calculo de la lista de la compra con necesidad de alimentos cubierta parcialmente por la despensa.
    //Expected: Devolución de una lista de productos que cubre las necesidades netas de alimentos.
    @Test
    fun `si la despensa cubre parcialmente las necesidade alimentarias entonces lista de productos con las cantidades netas`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        despensa.first().cantidad = 10000
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)

        //Then
        assertEquals(expected = true, actual = productos.none{it.query.equals(despensa.first().nombre)})
    }

    //Caso: Calculo de la lista de la compra sin alimentos en despensa.
    //Expected: Devolución de lista de productos que cubre las necesidades de la lista de recetas.
    @Test
    fun `si lista de la compra sin despensa entonces lista de productos cubre necesidades brutas`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = listOf()
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)

        //Then
        assertEquals(expected = true, actual = productos.all { producto ->
            cesta.filter {
                it.nombre.equals(producto.query)
            }.all {
                producto.peso >= it.cantidad
            }
        })
    }

    //Caso: Calculo de la lista de la compra sin seleccionar filtro.
    //Expected: Devolución de lista de productos más baratos que cubren las necesidades netas de alimentos.
    @Test
    fun `si no se selecciona filtro entonces lista de productos mas baratos`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        val filter: FilterEnum = FilterEnum.BARATOS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)

        //Then
        assertEquals(expected = true, actual = productos.all { producto ->
            calculadora.productos.productos.map { it.filter {
                it.query.equals(producto.query)
            }}.all { it.all {
                ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)!! <=
                        ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(it)!!
            }}
        })
    }


    //Caso: Calculo de la lista de la compra con filtro Ajustado.
    //Expected: Devolución de lista de productos más ajustados que cubren las necesidades netas de alimentos.
    @Test
    fun `si se selecciona filtro ajustado entonces lista de productos mas ajustados`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        val filter: FilterEnum = FilterEnum.AJUSTADOS


        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)
        val peso_medio = arrayListOf(productos).getPesoMedio()
        val precio_medio = arrayListOf(productos).getPrecioMedio()

        //Then
        assertEquals(expected = true, actual = productos.all { producto ->
            calculadora.productos.productos.map { it.filter {
                it.query.equals(producto.query)
            }}.all { it.all {
                0.5 * (1 - (it.peso/peso_medio)) + 0.5 * (1 - (ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(it)!!/precio_medio)).toInt() <=
                        0.5 * (1 - (producto.peso/peso_medio)) + 0.5 * (1 - (ParsersJsonToProducto.parseJsonPrecioPesoToProductoPrecioPeso(producto)!!/precio_medio)).toInt()
            }}
        })
    }

    //Caso: Calculo de la lista de la compra con filtro Ligeros.
    //Expected: Devolución de lista de productos más ligeros que cubren las necesidades netas de alimentos.
    @Test
    fun `si se selecciona filtro ligeros entonces lista de productos mas ligeros`(){
        //Given
        val cesta: List<Alimento> = fakes.alimentosFake
        val despensa: List<Alimento> = fakes.alimentosFake.map { it.copy(cantidad = it.cantidad/10)}
        val filter: FilterEnum = FilterEnum.LIGEROS

        //When
        val productos = fakes.fakeCaseUse(despensa, cesta, supermercados, filter)

        //Then
        assertEquals(expected = true, actual = productos.all { producto ->
            calculadora.productos.productos.map { it.filter {
                it.query.equals(producto.query)
            }}.all { it.all {
                it.peso <= producto.peso
            }}
        })
    }


}