//
//  ListaCompraViewModel.swift
//  IOSApp
//
//  Created by David Otero Diaz on 8/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class ListaCompraViewModel: ObservableObject {
    
    private let caseUses: UseCases
    private let id_listaRecetas: Int
    
    @Published var state: ListaCompraState = ListaCompraState()
    
    init(
        caseUses: UseCases,
        id_listaRecetas: Int
    ){
        print("ID lista de la compra: " + String(id_listaRecetas))
        
        self.caseUses = caseUses
        self.id_listaRecetas = id_listaRecetas
        
        //Primero hay que eliminar la lista anterior.
        self.deleteListaCompra()
        
        if(self.id_listaRecetas != -1){
            //TODO: Falta que se elijan los supermercados dinámicamente.
            self.state = self.state.doCopy(
                listaProductos: self.state.listaProductos,
                id_cestaCompra: Int32(self.id_listaRecetas),
                precio_total: self.state.precio_total,
                peso_total: self.state.peso_total,
                alimentos_cesta: self.state.alimentos_cesta,
                alimentos_no_encontrados: self.state.alimentos_no_encontrados,
                supermercados: KotlinMutableSet<SupermercadosEnum>(array: [
                    SupermercadosEnum.carrefour,
                    SupermercadosEnum.dia,
                    SupermercadosEnum.mercadona
                ])
            )
            print("Los supermercados son: ")
            print(self.state.supermercados.count)
            dump(self.state.supermercados)
            self.aplicarFiltro(filter: FilterEnum.baratos)
        }
    }
    
    func onTriggerEvent(event: ListaCompraEvents) -> Void {
        switch event {
            case is ListaCompraEvents.onCLickFilter:
                self.aplicarFiltro(
                    filter: ParserFilterEnum().parseStringToFilter(
                        nombre_filtro: (event as! ListaCompraEvents.onCLickFilter).filter_nombre
                    )
                )
            case is ListaCompraEvents.onClickProducto:
                print("He llegado a intentar actualizar")
                print((event as! ListaCompraEvents.onClickProducto).producto.active)
                self.updateProducto(
                    active: !(event as! ListaCompraEvents.onClickProducto).producto.active,
                    producto: (event as! ListaCompraEvents.onClickProducto).producto
                )
            case is ListaCompraEvents.onFinishCompra:
                self.finalizarCompra()
            default:
                print("La lista de la compra no está preparada para el evento")
        }
    }
    
    private func finalizarCompra() -> Void{
        self.caseUses.finalizarCompra.finalizarCompra(
            id_cestaCompra: self.state.id_cestaCompra,
            productos: NSMutableArray(array: self.state.listaProductos)
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                //Navegar a la pantalla de despensa.
                if(dataState?.data != nil){
                    self.deleteListaCompra()
                }
            })
    }
    
    private func actualizarLista() -> Void{
        //Primero obtengo los productos en caché
        self.caseUses.getProductos.obtenerProductos().collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if(dataState?.data != nil){
                    //Si se han encontrado productos
                    if(!(dataState?.data?.productos_cache.isEmpty)!){
                        print("Los productos obtenidos son: ")
                        dump(dataState?.data?.productos_cache)
                        self.state = self.state.doCopy(
                            listaProductos: (dataState?.data!.productos_cache)!,
                            id_cestaCompra: (dataState?.data!.id_cestaCompra)!,
                            precio_total: self.state.precio_total,
                            peso_total: self.state.peso_total,
                            alimentos_cesta: self.state.alimentos_cesta,
                            alimentos_no_encontrados: self.state.alimentos_no_encontrados,
                            supermercados: self.state.supermercados
                        )
                    }
                    
                    //Obtengo los alimentos no encontrados en caché
                    self.caseUses.getAlimentosNoEncontradosCache.getAlimentosNoEncontradosCache().collectFlow(
                        coroutineScope: nil,
                        callback: { productosNoEncontrados in
                            if(productosNoEncontrados?.data != nil){
                                self.state = self.state.doCopy(
                                    listaProductos: self.state.listaProductos,
                                    id_cestaCompra: self.state.id_cestaCompra,
                                    precio_total: self.state.precio_total,
                                    peso_total: self.state.peso_total,
                                    alimentos_cesta: self.state.alimentos_cesta,
                                    alimentos_no_encontrados: (
                                        productosNoEncontrados?.data?.map{ producto in
                                            (producto as! Productos.Producto).toAlimento().doCopy(
                                                id_cestaCompra:  ((producto as! Productos.Producto).id_cestaCompra as! KotlinInt),
                                                id_receta:  -1,
                                                id_alimento: -1,
                                                nombre:  (producto as! Productos.Producto).nombre,
                                                cantidad:  Int32((producto as! Productos.Producto).peso),
                                                tipoUnidad:  (producto as! Productos.Producto).tipoUnidad!,
                                                active: true
                                            )
                                        }
                                    )as! [Alimento],
                                    supermercados: self.state.supermercados)
                            }
                            //Actualizo los pesos y precios totales.
                            self.actualizarPesosYPreciosTotales()
                        }
                    )
                }
            }
        )
    }
    
    private func updateProducto(active: Bool, producto: Productos.Producto) -> Void {
        caseUses.updateProducto.updateProducto(active: active, producto: producto).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if(dataState?.data != nil){
                    //Actualizo la lista
                    self.actualizarLista()
                }
            })
    }
    
    private func aplicarFiltro(filter: FilterEnum) -> Void{
        //Elimino los productos calculados hasta ahora
        deleteListaCompra()
        
        print("Aplicando filtrado de tipo: ")
        print(filter.name)
        //Calculo los productos con el nuevo filtro
        calcularCompra(
            filter: filter,
            supermercados: KotlinMutableSet<SupermercadosEnum>(array: [
                SupermercadosEnum.carrefour,
                SupermercadosEnum.dia,
                SupermercadosEnum.mercadona
            ])
        )
        
    }
    
    private func calcularCompra(
        filter: FilterEnum = FilterEnum.baratos,
        supermercados: KotlinMutableSet<SupermercadosEnum> = KotlinMutableSet<SupermercadosEnum>(array: [SupermercadosEnum.carrefour])
    ) -> Void {
        var productos: [Productos.Producto] = []
        var alimentosNecesarios: [Alimento] = []
        
        //Primero calculo los mejores productos. Obtengo a la vez los alimentos necesarios
        caseUses.calcularProductos.calcularProductos(
            id_cestaCompra: self.state.id_cestaCompra,
            filter: filter,
            supermercados: supermercados 
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    productos = dataState?.data?.second as! [Productos.Producto]
                    alimentosNecesarios = dataState?.data?.first as! [Alimento]
                    print("Productos de la compra de la lista con id " + String(self.id_listaRecetas) + ": "
                          + String(productos.count))
                    dump(productos)
                    //Guardo los productos encontrados en caché.
                    self.guardarProductosCache(productos: productos)
                    
                    //Guardo los productos no encontrados en caché.
                    self.calcularAlimentosNoEncontrados(alimentosNecesarios: alimentosNecesarios,productos: productos)
                    self.guardarProductosNoEncontradosCache()
                    
                    //Lo anterior guarda los productos no encontrados y actualiza la lista y los pesos y precios totales.
                }
            })
    }
    
    private func actualizarSupermercados(productos: [Productos.Producto]) -> Void{
        let supermercados_actuales = KotlinMutableSet<SupermercadosEnum>()
        let iterator = SupermercadosEnum.values().iterator()
        while(iterator.hasNext()){
            let supermercado = iterator.next_()
            if(productos.contains(where: { producto in
                producto.supermercado.isEqual(supermercado)
            })){
                //Añado el supermercado a los supermercados actuales.
                supermercados_actuales.add(supermercado as! SupermercadosEnum)
            }
        }
        self.state = self.state.doCopy(
            listaProductos: self.state.listaProductos,
            id_cestaCompra: self.state.id_cestaCompra,
            precio_total: self.state.precio_total,
            peso_total: self.state.peso_total,
            alimentos_cesta: self.state.alimentos_cesta,
            alimentos_no_encontrados: self.state.alimentos_no_encontrados,
            supermercados: supermercados_actuales
        )
    }
    
    private func actualizarPesosYPreciosTotales() -> Void{
        
        //Actualizo los supermercados
        self.actualizarSupermercados(productos: self.state.listaProductos)
        
        //Creo las variable necesarias
        var precio_total_carrefour: Float = 0
        var precio_total_dia: Float = 0
        var precio_total_mercadona: Float = 0

        var peso_total_carrefour: Float = 0
        var peso_total_dia: Float = 0
        var peso_total_mercadona: Float = 0
        
        print("Antes de calcular los pesos totales")
        dump(self.state.listaProductos)
        //Calculo los precios totales y pesos totales de cada supermercado
        self.state.listaProductos.forEach{ producto in
            switch(producto.supermercado){
                case SupermercadosEnum.carrefour:
                    precio_total_carrefour += Float(producto.cantidad) * producto.precio_numero
                    if((producto.tipoUnidad != nil && !(producto.tipoUnidad!.name == "UNIDADES"))){
                        peso_total_carrefour += Float(producto.cantidad) * producto.peso
                    }
                case SupermercadosEnum.mercadona:
                    precio_total_mercadona += Float(producto.cantidad) * producto.precio_numero
                    if((producto.tipoUnidad != nil && !(producto.tipoUnidad!.name == "UNIDADES"))){
                        peso_total_mercadona += Float(producto.cantidad) * producto.peso
                    }
                case SupermercadosEnum.dia:
                    precio_total_dia += Float(producto.cantidad) * producto.precio_numero
                    if((producto.tipoUnidad != nil && !(producto.tipoUnidad!.name == "UNIDADES"))){
                        peso_total_dia += Float(producto.cantidad) * producto.peso
                    }
                default:
                    print("SUpermercado no filtrado correctamente en cantidades y precios")
            }
        }
        
        let dictionary_precio = KotlinMutableDictionary<SupermercadosEnum, KotlinFloat>()
        dictionary_precio[SupermercadosEnum.carrefour] = precio_total_carrefour
        dictionary_precio[SupermercadosEnum.mercadona] = precio_total_mercadona
        dictionary_precio[SupermercadosEnum.dia] = precio_total_dia
        
        let dictionary_peso = KotlinMutableDictionary<SupermercadosEnum, KotlinFloat>()
        dictionary_peso[SupermercadosEnum.carrefour] = peso_total_carrefour
        dictionary_peso[SupermercadosEnum.mercadona] = peso_total_mercadona
        dictionary_peso[SupermercadosEnum.dia] = peso_total_dia
        
        //Actualizo el estado con los productos y el nuevo precio y peso total.
        let currentState: ListaCompraState = self.state.copy() as! ListaCompraState
        self.state = self.state.doCopy(
            listaProductos: currentState.listaProductos,
            id_cestaCompra: currentState.id_cestaCompra,
            precio_total: dictionary_precio ,
            peso_total: dictionary_peso ,
            alimentos_cesta: currentState.alimentos_cesta,
            alimentos_no_encontrados: currentState.alimentos_no_encontrados,
            supermercados: currentState.supermercados
        )
    }
    
    private func calcularAlimentosNoEncontrados(alimentosNecesarios: [Alimento], productos: [Productos.Producto]) -> Void {
        var alimentosNoEncontrados: [Alimento] = []
        alimentosNecesarios.forEach{ alimento in
            if(!productos.contains(
                where: { producto in
                    producto.query.lowercased() == alimento.nombre.lowercased()
                }
            )){
                //Añado el alimento a la lista de alimentos no encontrados.
                alimentosNoEncontrados.append(alimento)
            }
        }
        //Guardo los alimentos no encontrados en el estado.
        let currentState: ListaCompraState = self.state.copy() as! ListaCompraState
        self.state = self.state.doCopy(
            listaProductos: currentState.listaProductos,
            id_cestaCompra: currentState.id_cestaCompra,
            precio_total: currentState.precio_total,
            peso_total: currentState.peso_total,
            alimentos_cesta: currentState.alimentos_cesta,
            alimentos_no_encontrados: alimentosNoEncontrados,
            supermercados: currentState.supermercados
        )
    }
    
    private func guardarProductosCache(productos: [Productos.Producto]) -> Void {
        caseUses.saveListaCompra.saveProductos(
            id_cestaCompra: self.state.id_cestaCompra,
            productos: productos
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                //Productos guardados
            })
    }
    
    private func guardarProductosNoEncontradosCache() -> Void {
        caseUses.saveListaCompra.saveAlimentosNoEncontrados(
            id_cestaCompra: self.state.id_cestaCompra,
            productosNoEncontrados: state.alimentos_no_encontrados.toProductos()
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                //Alimentos no encontrados guardados.
                self.actualizarLista()
            }
        )
    }
    
    private func deleteListaCompra() -> Void {
        caseUses.deleteProductos.deleteProductos().collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //Productos guardados eliminados.
                    self.state = self.state.doCopy(
                        listaProductos: [],
                        id_cestaCompra: Int32(self.id_listaRecetas),
                        precio_total: KotlinMutableDictionary<SupermercadosEnum, KotlinFloat>(),
                        peso_total: KotlinMutableDictionary<SupermercadosEnum, KotlinFloat>() ,
                        alimentos_cesta: [],
                        alimentos_no_encontrados: [],
                        supermercados: KotlinMutableSet<SupermercadosEnum>()
                    )
                }
            }
        )
    }
}
