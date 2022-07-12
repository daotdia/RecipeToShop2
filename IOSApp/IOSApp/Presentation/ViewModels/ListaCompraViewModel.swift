//
//  ListaCompraViewModel.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 8/7/22.
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
            self.calcularCompra()
        }
    }
    
    func onTriggerEvent(event: ListaCompraEvents) -> Void {
        switch event {
            default:
                print("La lista de la compra no está preparada para el evento")
        }
    }
    
    private func calcularCompra() -> Void {
        var productos: [Productos.Producto] = []
        var alimentosNecesarios: [Alimento] = []
        
        //Primero calculo los mejores productos. Obtengo a la vez los alimentos necesarios
        caseUses.calcularProductos.calcularProductos(id_cestaCompra: Int32(self.id_listaRecetas)).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    productos = dataState?.data?.second as! [Productos.Producto]
                    alimentosNecesarios = dataState?.data?.first as! [Alimento]
                    print("Productos de la compra de la lista con id " + String(self.id_listaRecetas) + ": "
                          + String(productos.count))
                    dump(productos)
                    //Calculo el peso y precio total de la lista de la compra.
                    self.actualizarPesoYPrecioTotal(productos: productos)
                    //Determino cuales de los alimentos necesarios no se han encontrado.
                    self.calcularAlimentosNoEncontrados(alimentosNecesarios: alimentosNecesarios)
                    //Guardo los productos encontrados en caché.
                    self.guardarProductosCache()
                    //Guardo los productos no encontrados en caché.
                    self.guardarProductosNoEncontradosCache()
                }
            })
    }
    
    private func actualizarPesoYPrecioTotal(productos: [Productos.Producto]) -> Void{
        var precio_total: Float = 0
        var peso_total: Float = 0
        
        productos.forEach{ producto in
            //Actualizo el precio total.
            precio_total += producto.precio_numero
            //Actualizo el peso total en el caso de que exista tipo de unidad y no sea de tipo unidades.
            if(producto.tipoUnidad != nil && producto.tipoUnidad!.name != "UNIDADES"){
                peso_total += producto.peso
            }
        }
        
        //Actualizo el estado con los productos y el nuevo precio y peso total.
        let currentState: ListaCompraState = self.state.copy() as! ListaCompraState
        self.state = self.state.doCopy(
            listaProductos: productos,
            id_cestaCompra: currentState.id_cestaCompra,
            precio_total: precio_total,
            peso_total: peso_total,
            alimentos_cesta: currentState.alimentos_cesta,
            alimentos_no_encontrados: currentState.alimentos_no_encontrados
        )
    }
    
    private func calcularAlimentosNoEncontrados(alimentosNecesarios: [Alimento]) -> Void {
        var alimentosNoEncontrados: [Alimento] = []
        alimentosNecesarios.forEach{ alimento in
            if(!self.state.listaProductos.contains(
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
            alimentos_no_encontrados: alimentosNoEncontrados
        )
    }
    
    private func guardarProductosCache() -> Void {
        caseUses.saveListaCompra.saveProductos(
            id_cestaCompra: self.state.id_cestaCompra,
            productos: self.state.listaProductos
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                //Productos guardados
            })
    }
    
    private func guardarProductosNoEncontradosCache() -> Void {
        caseUses.saveListaCompra.saveAlimentosNoEncontrados(
            id_cestaCompra: state.id_cestaCompra,
            productosNoEncontrados: state.alimentos_no_encontrados.toProductos()
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                //Alimentos no encontrados guardados.
            }
        )
    }
    
    private func deleteListaCompra() -> Void {
        caseUses.deleteProductos.deleteProductos().collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //Productos guardados eliminados.
                    self.state = ListaCompraState()
                }

            }
        )
    }
}
