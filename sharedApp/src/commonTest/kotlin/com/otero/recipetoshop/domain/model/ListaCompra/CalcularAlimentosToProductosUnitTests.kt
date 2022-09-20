package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.datasource.static.Productos_Carrefour
import com.otero.recipetoshop.datasource.static.productos_Dia
import com.otero.recipetoshop.datasource.static.productos_Mercadona
import com.otero.recipetoshop.domain.dataEstructres.FilterEnum
import com.otero.recipetoshop.domain.dataEstructres.SupermercadosEnum
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
import com.otero.recipetoshop.domain.model.despensa.Alimento
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalcularAlimentosToProductosUnitTests{

    private lateinit var calculadora: CalcularAlimentosToProductos

    private lateinit var alimentos_unificados: ArrayList<Alimento>

    private lateinit var producto_mostaza: Productos.Producto

    private lateinit var alimento_mostaza: Alimento

    @BeforeTest
    fun onBefore(){
        calculadora = CalcularAlimentosToProductos()

        alimentos_unificados = calculadora.unificarAlimentos(ArrayList(Fakes().alimentosFake))

        producto_mostaza = Productos.Producto(
            cantidad = 1,
            id_producto = -1,
            id_cestaCompra = -1,
            tipoUnidad = TipoUnidad.GRAMOS,
            nombre = "PRIMA salsa mostaza cero bote 325 gr",
            imagen_src = "",
            peso = 0f,
            precio_numero = 0f,
            precio_peso = "",
            precio_texto = "",
            oferta = "",
            query = "mostaza"
        )
         alimento_mostaza = Alimento(
            nombre = "mostaza",
            cantidad = 500,
            active = true,
            tipoUnidad = TipoUnidad.GRAMOS
        )
    }

    //Testear si los tests están bien configurados y funcionan
    @Test
    fun `check tests funcionan`() {
        assertEquals(true, true)
    }

    //INICIAR CALCULADORA

    @Test
    fun `iniciar calculadora sin supermercados seleccionados proporciona lista vacia de productos`(){
        //Given
        val emptySetSupermercados: MutableSet<SupermercadosEnum> = mutableSetOf()

        //When
        calculadora.iniciarCalculadora(supermercados = emptySetSupermercados)

        //Then
        assertEquals(expected = true, actual = calculadora.productos.productos.isEmpty() )
    }

    @Test
    fun `se obtienen todos los productos de carrefour del JSON estatico`(){
        //Given
        val productosCarrefour = Productos_Carrefour.json.jsonObject.get("productos")?.jsonArray

        //When
        calculadora.iniciarCalculadora(mutableSetOf(SupermercadosEnum.CARREFOUR))

        //Then
        if (productosCarrefour != null) {
            assertEquals(expected = productosCarrefour.size, actual = calculadora.productos.productos.size)
        } else {
            assertEquals(expected = 0, actual = calculadora.productos.productos.size)
        }
    }

    @Test
    fun `se obtienen todos los productos de mercadona del JSON estatico`(){
        //Given
        val productosMercadona = productos_Mercadona.json.jsonObject.get("productos")?.jsonArray

        //When
        calculadora.iniciarCalculadora(mutableSetOf(SupermercadosEnum.MERCADONA))

        //Then
        if (productosMercadona != null) {
            assertEquals(expected = productosMercadona.size, actual = calculadora.productos.productos.size)
        } else {
            assertEquals(expected = 0, actual = calculadora.productos.productos.size)
        }
    }

    @Test
    fun `se obtienen todos los productos de dia del JSON estatico`(){
        //Given
        val productosDia = productos_Dia.json.jsonObject.get("productos")?.jsonArray

        //When
        calculadora.iniciarCalculadora(mutableSetOf(SupermercadosEnum.DIA))

        //Then
        if (productosDia != null) {
            assertEquals(expected = productosDia.size, actual = calculadora.productos.productos.size)
        } else {
            assertEquals(expected = 0, actual = calculadora.productos.productos.size)
        }
    }

    //UNIFICAR ALIMENTOS

    @Test
    fun `al pasar una lista de alimentos vacia se obtiene una lista de alimentos unificados vacia` (){
        //Given
        val alimentosVacios: ArrayList<Alimento> = arrayListOf()

        //When
        val alimentos_unificados = calculadora.unificarAlimentos(alimentosVacios)

        //Then
        assertEquals(expected = true, actual = alimentos_unificados.isEmpty())
    }

    @Test
    fun `al pasar una lista de alimentos con 4 mostazas repetidas devuelve solo una mostaza`(){
        //Given
        val alimentos_repetidos = ArrayList(Fakes().alimentosFake)

        //When
        val alimentos_unificados = calculadora.unificarAlimentos(alimentos_repetidos)

        //Then
        assertEquals(expected = 1, actual = alimentos_unificados.filter { it.nombre.equals("mostaza") }.size)
    }


    //ENCONTRAR PRODUCTOS

    @Test
    fun `al pasar una lista de 4 alimentos distintos con productos disponibles devuelve una lista con al menos un producto por alimento`(){
        //Given
        calculadora.iniciarCalculadora()

        //When
        val productos = calculadora.encontrarProductos(alimentos = alimentos_unificados)

        //Then
        assertEquals(expected = true, actual = productos.map { it.distinctBy { it.query } }.size > 3)
    }

    @Test
    fun `al pasar una lista vacia de alimentos devuelve una lista de productos vacia`(){
        //Given
        val alimentos_vacios: List<Alimento> = listOf()
        calculadora.iniciarCalculadora()

        //When
        val poductos_vacios = calculadora.encontrarProductos(alimentos_vacios)

        //Then
        assertEquals(expected = true, actual = poductos_vacios.isEmpty())
    }

    @Test
    fun `al pasar el alimento mostaza se obtienen 4 productos completos de tipo mostaza`(){
        //Given
        val mostaza = listOf(Alimento(nombre = "mostaza", active = true, tipoUnidad = TipoUnidad.GRAMOS))
        calculadora.iniciarCalculadora()

        //When
        val productos_mostaza = calculadora.encontrarProductos(mostaza)

        //Then
        assertEquals(expected = true, actual = productos_mostaza.first().size == 4)
    }


    //DETERMINAR CANTIDAD DE PRODUCTOS

    @Test
    fun `al pasar necesidad de mostaza de 500g sin productos disponibles no se devuelven productos`(){
        //Given
        calculadora.iniciarCalculadora()

        //When
        val producto_cantidad = calculadora.calcularCantidadesProductos(
            alimentos = arrayListOf(alimento_mostaza.copy()),
            productos = arrayListOf(arrayListOf())
        )

        //Then
        assertEquals(expected = true, actual = producto_cantidad.first().isEmpty())
    }

    @Test
    fun `al pasar necesidad de 500g de mostaza se devuelven 2 productos de mostaza de 325g`(){
        //Given

        calculadora.iniciarCalculadora()

        //When
        val producto_cantidad = calculadora.calcularCantidadesProductos(
            alimentos = arrayListOf(alimento_mostaza),
            productos = arrayListOf(arrayListOf(producto_mostaza))
        )

        //Then
        assertEquals(expected = 2, actual = producto_cantidad.first().first().cantidad)
    }


    //CALCULAR NECESIDADES DE ALIMENTOS.

    @Test
    fun`al pasar necesidad de alimento sin existencias en despensa se devuelve la necesidad del alimento inicial`(){
        //Given
        val mostaza_cesta = alimento_mostaza.copy(cantidad = 100)
        calculadora.iniciarCalculadora()

        //When
        val necesidad_alimentos = calculadora.calcularNecesidadesAlimentos(alimentos_despensa = arrayListOf(), alimentos_cesta = arrayListOf(mostaza_cesta))

        //Then
        assertEquals(expected = 100, actual = necesidad_alimentos.first().cantidad)
    }

    @Test
    fun`al pasar necesidad de alimento de 200g y existencias de 100g se devuelve como resultado 100g`(){
        //Given
        val mostaza_cesta = alimento_mostaza.copy(cantidad = 200)
        val mostaza_despensa = alimento_mostaza.copy(cantidad = 100)
        calculadora.iniciarCalculadora()

        //When
        val necesidades_alimentos = calculadora.calcularNecesidadesAlimentos(
            alimentos_despensa = arrayListOf(mostaza_despensa),
            alimentos_cesta = arrayListOf(mostaza_cesta)
        )

        //Then
        assertEquals(expected = 100, actual = necesidades_alimentos.first().cantidad)
    }

    @Test
    fun `al pasar mayor cantidad de existencias de alimento que cantidades necesitadas no se devuelve el alimento`(){
        //Given
        val mostaza_cesta = alimento_mostaza.copy(cantidad = 100)
        val mostaza_despensa = alimento_mostaza.copy(cantidad = 200)
        calculadora.iniciarCalculadora()

        //When
        val necesidades_alimentos = calculadora.calcularNecesidadesAlimentos(
            alimentos_despensa = arrayListOf(mostaza_despensa),
            alimentos_cesta = arrayListOf(mostaza_cesta)
        )

        //Then
        assertEquals(expected = true, actual = necesidades_alimentos.isEmpty())
    }

    //CALCULAR MEJORES PRODUCTOS.

    @Test
    fun `al pasar necesidad de mostaza mas barata se obtiene la mostaza mas barata`(){
        //Given
        calculadora.iniciarCalculadora()

        //When
        val producto = calculadora.seleccionarMejorProducto(
            productos = arrayListOf(Fakes().productos),
            FilterEnum.BARATOS
        )

        //Then
        assertEquals(expected = "PRIMA salsa mostaza cero bote 550 gr", actual = producto.first().nombre)
    }

    @Test
    fun `al pasar necesidad de mostaza mas ligera se obtiene mas ligera`(){
        //Given
        calculadora.iniciarCalculadora()

        //When
        val producto = calculadora.seleccionarMejorProducto(
            productos = arrayListOf(Fakes().productos),
            FilterEnum.LIGEROS
        )

        //Then
        assertEquals(expected = "PRIMA salsa mostaza cero bote 100 gr", actual = producto.first().nombre)
    }

    @Test
    fun `al pasar necesidad de mostaza mas ajustada se obtiene la mas ligera`(){
        //Given
        calculadora.iniciarCalculadora()

        //When
        val producto = calculadora.seleccionarMejorProducto(
            productos = arrayListOf(Fakes().productos),
            FilterEnum.AJUSTADOS
        )

        //Then
        assertEquals(expected = "PRIMA salsa mostaza cero bote 250 gr", actual = producto.first().nombre)
    }
}