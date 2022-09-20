package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.domain.dataEstructres.FilterEnum
import com.otero.recipetoshop.domain.dataEstructres.SupermercadosEnum
import com.otero.recipetoshop.domain.dataEstructres.TipoUnidad
import com.otero.recipetoshop.domain.model.CestaCompra.Receta
import com.otero.recipetoshop.domain.model.despensa.Alimento

data class Fakes(
    val alimentosFake: List<Alimento> = listOf(
        Alimento(
            nombre = "mostaza",
            cantidad = 100,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = true
        ),
        Alimento(
            nombre = "mostaza",
            cantidad = 100,
            tipoUnidad = TipoUnidad.ML,
            active = true
        ),
        Alimento(
            nombre = "mostaza",
            cantidad = 100,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = false
        ),
        Alimento(
            nombre = "mostaza",
            cantidad = 0,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = true
        ),
        Alimento(
            nombre = "patata",
            cantidad = 100,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = true
        ),
        Alimento(
            nombre = "manzana",
            cantidad = 100,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = false
        ),
        Alimento(
            nombre = "arroz",
            cantidad = 10000,
            tipoUnidad = TipoUnidad.GRAMOS,
            active = true
        )
    ),

    val recetasFake: List<Receta> = listOf(
        Receta(
            nombre = "paella",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = true
        ),
        Receta(
            nombre = "paella",
            cantidad = 2,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = true
        ),
        Receta(
            nombre = "paella",
            cantidad = 1,
            ingredientes = listOf(),
            id_cestaCompra = -1,
            user = true,
            active = true
        ),
        Receta(
            nombre = "paella",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = false,
            active = true
        ),
        Receta(
            nombre = "paella",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = false
        ),
        Receta(
            nombre = "paella",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = false,
            active = false
        ),
        Receta(
            nombre = "huevos",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = true
        ),
        Receta(
            nombre = "",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = true
        ),
        Receta(
            nombre = "fideos",
            cantidad = 1,
            ingredientes = alimentosFake,
            id_cestaCompra = -1,
            user = true,
            active = true
        )
    ),
    val productos: ArrayList<Productos.Producto> = arrayListOf(
        Productos.Producto(
            cantidad = 1,
            id_producto = -1,
            id_cestaCompra = -1,
            tipoUnidad = TipoUnidad.GRAMOS,
            nombre = "PRIMA salsa mostaza cero bote 550 gr",
            imagen_src = "",
            peso = 550f,
            precio_numero = 0f,
            precio_peso = "1",
            precio_texto = "",
            oferta = "",
            query = "mostaza",
            supermercado = SupermercadosEnum.DIA
        ),
        Productos.Producto(
            cantidad = 1,
            id_producto = -1,
            id_cestaCompra = -1,
            tipoUnidad = TipoUnidad.GRAMOS,
            nombre = "PRIMA salsa mostaza cero bote 250 gr",
            imagen_src = "",
            peso = 250f,
            precio_numero = 0f,
            precio_peso = "2",
            precio_texto = "",
            oferta = "",
            query = "mostaza",
            supermercado = SupermercadosEnum.MERCADONA
        ),
        Productos.Producto(
            cantidad = 1,
            id_producto = -1,
            id_cestaCompra = -1,
            tipoUnidad = TipoUnidad.GRAMOS,
            nombre = "PRIMA salsa mostaza cero bote 100 gr",
            imagen_src = "",
            peso = 10f,
            precio_numero = 0f,
            precio_peso = "5",
            precio_texto = "",
            oferta = "",
            query = "mostaza",
            supermercado = SupermercadosEnum.CARREFOUR
        )
    ),
    //Funcion para imitar el caso de uso de calcular la lista de la compra.
    val fakeCaseUse: (
        List<Alimento>,
        List<Alimento>,
        MutableSet<SupermercadosEnum>,
        FilterEnum
    ) -> List<Productos.Producto> = { despensa, cesta, supermercados, filter ->
        //Instancio la calculadora.
        val calculadora = CalcularAlimentosToProductos()

        //Inicio la calculadora con los supermercados pasados por canstructor
        val unit = calculadora.iniciarCalculadora(supermercados = supermercados)

        //Calculo los alimentos necesarios unificados.
        val alimentos_unificados = calculadora.unificarAlimentos(ArrayList(cesta))

        //Calculo las necesidades de alimentos teniendo en cuenta la despensa.
        val alimentos_netos = calculadora.calcularNecesidadesAlimentos(alimentos_cesta = alimentos_unificados, alimentos_despensa = ArrayList(despensa))

        //Encuentro los productos en referencia a los alimentos netos.
        val productos_brutos = calculadora.encontrarProductos(alimentos_netos)

        //Calculo la cantidad de producto necesaria para cobrir las necesidades netas de alimentos.
        val productos_netos = calculadora.calcularCantidadesProductos(alimentos = alimentos_netos, productos = productos_brutos)

        //Determino los mejores productos según el filtro seleccionado por el usuario.
        val productos_ganadores = calculadora.seleccionarMejorProducto(productos = productos_netos, filterEnum = filter)

        //Devuelvo los productos ganadores.
        productos_ganadores
    }
) {}