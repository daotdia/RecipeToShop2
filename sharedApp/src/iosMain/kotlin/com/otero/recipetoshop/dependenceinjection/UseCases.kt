package com.otero.recipetoshop.dependenceinjection

import com.otero.recipetoshop.Interactors.Common.ActualizarAutoComplete
import com.otero.recipetoshop.Interactors.cestascompra.AddNewCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.AddPictureCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.GetCestaCompra
import com.otero.recipetoshop.Interactors.cestascompra.PrintListaCestasCompra
import com.otero.recipetoshop.Interactors.cestascompra.cestacompra.*
import com.otero.recipetoshop.Interactors.cestascompra.recetas.*
import com.otero.recipetoshop.Interactors.cestascompra.recetas.busquedarecetas.BuscarRecetasAPI
import com.otero.recipetoshop.Interactors.despensa.*
import com.otero.recipetoshop.Interactors.listacompra.*

//Aquí están instanciados todos los casos de uso
class UseCases(
    val cacheModule: CacheModule
) {

    //CASOS DE USO DE DESPENSA.
    val alimentoCantidadChange: AlimentoCantidadChange by lazy {
        AlimentoCantidadChange(
            despensaCache = cacheModule.despensaCache
        )
    }

    val deleteAlimento: DeleteAlimento by lazy {
        DeleteAlimento(
            despensaCache = cacheModule.despensaCache
        )
    }

    //...

    val deleteAlimentos: DeleteAlimentos by lazy {
        DeleteAlimentos(
            despensaCache = cacheModule.despensaCache
        )
    }

    val getAlimentos: GetAlimentos by lazy {
        GetAlimentos(
            despensaCache = cacheModule.despensaCache
        )
    }

    val insertNewAlimento: InsertNewAlimento by lazy {
        InsertNewAlimento(
            despensaCache = cacheModule.despensaCache
        )
    }

    val onCLickAlimento: OnCLickAlimento by lazy {
        OnCLickAlimento(
            despensaCache = cacheModule.despensaCache
        )
    }

    //CASOS DE USO COMUNES

    val actualizarAutoComplete: ActualizarAutoComplete by lazy {
        ActualizarAutoComplete()
    }

    //CASOS DE USO CESTA DE LA COMPRA

    val addNewCestaCompra: AddNewCestaCompra by lazy {
        AddNewCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val getCestaCompra: GetCestaCompra by lazy {
        GetCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val printListaCestasCompra: PrintListaCestasCompra by lazy {
        PrintListaCestasCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val addAlimentoCestaCompra: AddAlimentoCestaCompra by lazy {
        AddAlimentoCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val addRecetaCestaCompra: AddRecetaCestaCompra by lazy {
        AddRecetaCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val deletaAlimentoCestaCompra: DeleteAlimentoCestaCompra by lazy {
        DeleteAlimentoCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val deleteRecetaCestaCompra: DeleteRecetaCestaCompra by lazy {
        DeleteRecetaCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val onEnterCestaCompra: OnEnterCestaCompra by lazy {
        OnEnterCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val updateAlimentoCestaCompra: UpdateAlimentoCestaCompra by lazy {
        UpdateAlimentoCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val updateRecetaCestaCompra: UpdateRecetaCestaCompra by lazy {
        UpdateRecetaCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val addIngredienteReceta: AddIngredienteReceta by lazy {
        AddIngredienteReceta(
            recetaCache = cacheModule.recetasCache
        )
    }

    val deleteIngredienteReceta: DeleteIngredienteReceta by lazy {
        DeleteIngredienteReceta(
            recetaCache = cacheModule.recetasCache
        )
    }

    val getDatosReceta: GetDatosReceta by lazy {
        GetDatosReceta(
            recetaCache = cacheModule.recetasCache
        )
    }

    val getRecetasFavoritas: GetRecetasFavoritas by lazy {
        GetRecetasFavoritas(
            recetaCache = cacheModule.recetasCache
        )
    }

    //CASOS DE USO DE LA LISTA DE LA COMPRA

    val calcularProductos: CalcularProductos by lazy {
        CalcularProductos(
            recetaCache = cacheModule.recetasCache,
            despensaCache = cacheModule.despensaCache
        )
    }

    val deleteProductos: DeleteProductos by lazy {
        DeleteProductos(
            recetaCache = cacheModule.recetasCache
        )
    }

    val getAlimentosNoEncontradosCache: GetAlimentosNoEncontradosCache by lazy {
        GetAlimentosNoEncontradosCache(
            recetaCache = cacheModule.recetasCache
        )
    }

    val obtenerProductos: ObtenerProductos by lazy {
        ObtenerProductos(
            recetaCache = cacheModule.recetasCache
        )
    }

    val saveListaCompra: SaveListaCompra by lazy {
        SaveListaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val updateRecetasLista: UpdateRecetasLista by lazy{
        UpdateRecetasLista(
            recetaCache = cacheModule.recetasCache
        )
    }

    val deleteCestaCompra: DeleteCestaCompra by lazy {
        DeleteCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val searchRecetasCache: SearchRecetasCache by lazy {
        SearchRecetasCache(
            recetaCache = cacheModule.recetasCache
        )
    }

    val updateProducto: UpdateProducto by lazy {
        UpdateProducto(
            recetaCache = cacheModule.recetasCache
        )
    }

    val finalizarCompra: FinalizarCompra by lazy {
        FinalizarCompra(
            recetaCache = cacheModule.recetasCache,
            despensaCache = cacheModule.despensaCache
        )
    }

    val onClickAlimento: OnCLickAlimento by lazy {
        OnCLickAlimento(
            despensaCache = cacheModule.despensaCache
        )
    }

    val editAlimento: EditAlimento by lazy {
        EditAlimento(
            despensaCache = cacheModule.despensaCache
        )
    }

    val addPictureCestaCompra by lazy {
        AddPictureCestaCompra(
            recetaCache = cacheModule.recetasCache
        )
    }

    val getProductos by lazy {
        ObtenerProductos(
            recetaCache = cacheModule.recetasCache
        )
    }

    val editIngrediente by lazy {
        EditIngrediente(
            recetaCache = cacheModule.recetasCache
        )
    }
}