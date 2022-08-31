//
//  ListaRecetasViewModel.swift
//  IOSApp
//
//  Created by David Otero Diaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp
import SwiftUI

class ListaRecetasViewModel: ObservableObject{
    
    private let caseUses: UseCases
    private let id_listaRecetas: Int
    private let nombre: String
    
    @Published var state: CestaCompraState = CestaCompraState()
    
    init(
        caseUses: UseCases,
        id_listaRecetas: Int,
        nombre: String
    ){
        self.caseUses = caseUses
        self.id_listaRecetas = id_listaRecetas
        self.nombre = nombre
        
        self.state = self.state.doCopy(
            id_cestaCompra_actual: (KotlinInt(integerLiteral: id_listaRecetas)),
            id_receta_actual: state.id_receta_actual,
            nombre: state.nombre,
            alimentosActive: state.alimentosActive,
            alimentosInactive: state.alimentosInactive,
            allAlimentos: state.allAlimentos,
            recetasActive: state.recetasActive,
            recetasInactive: state.recetasInactive,
            allrecetas: state.allrecetas,
            listaCestasCompra: state.listaCestasCompra,
            onNewReceta: state.onNewReceta,
            queueError: state.queueError,
            resultadosAutoCompleteAlimentos: state.resultadosAutoCompleteAlimentos,
            recetasFavoritas: state.recetasFavoritas,
            recetasBuscadas: state.recetasBuscadas
        )
        
        self.rePrintListaRecetas()
    }
    
    func onTriggerEvent(event: CestaCompraEventos) -> Void{
        switch event{
            case is CestaCompraEventos.onSearchRecetas:
                self.searchRecetas(query: (event as! CestaCompraEventos.onSearchRecetas).query)
                
            case is CestaCompraEventos.onAddReceta:
                let receta = Receta(
                    id_cestaCompra: self.state.id_cestaCompra_actual as! Int32,
                    id_Receta: nil,
                    nombre: (event as! CestaCompraEventos.onAddReceta).nombre,
                    cantidad: (event as! CestaCompraEventos.onAddReceta).cantidad,
                    user: true,
                    active: true,
                    imagenSource: nil,
                    ingredientes: [],
                    rating: nil,
                    isFavorita: false)
                addReceta(receta: receta)
                
            case is CestaCompraEventos.onAddRecetaExistente:
                addReceta(receta: (event as! CestaCompraEventos.onAddRecetaExistente).receta)
                
            case is CestaCompraEventos.onUpdateRecetaActive:
                updateReceta(
                    receta: (event as! CestaCompraEventos.onUpdateRecetaActive).receta,
                    active: (event as! CestaCompraEventos.onUpdateRecetaActive).active,
                    cantidad: nil
                )
                
            case is CestaCompraEventos.onDeleteReceta:
                deleteReceta(receta: (event as! CestaCompraEventos.onDeleteReceta).receta)
                
            case is CestaCompraEventos.onAddAlimento:
                addAlimento(
                    nombre: (event as! CestaCompraEventos.onAddAlimento).nombre,
                    cantidad: (event as! CestaCompraEventos.onAddAlimento).cantidad,
                    tipoUnidad: (event as! CestaCompraEventos.onAddAlimento).tipoUnidad
                )
            
            case is CestaCompraEventos.onDeleteAlimento:
                deleteAlimento(alimento: (event as! CestaCompraEventos.onDeleteAlimento).alimento)
            
            case is CestaCompraEventos.onUpdatePicture:
                //Actualizo la receta compiando en sus propiedades la nueva picture.
                self.updateReceta(
                    receta: (event as! CestaCompraEventos.onUpdatePicture).receta.doCopy(
                        id_cestaCompra: (event as! CestaCompraEventos.onUpdatePicture).receta.id_cestaCompra,
                        id_Receta: (event as! CestaCompraEventos.onUpdatePicture).receta.id_Receta,
                        nombre: (event as! CestaCompraEventos.onUpdatePicture).receta.nombre,
                        cantidad: (event as! CestaCompraEventos.onUpdatePicture).receta.cantidad,
                        user: (event as! CestaCompraEventos.onUpdatePicture).receta.user,
                        active: (event as! CestaCompraEventos.onUpdatePicture).receta.active,
                        imagenSource: (event as! CestaCompraEventos.onUpdatePicture).picture,
                        ingredientes: (event as! CestaCompraEventos.onUpdatePicture).receta.ingredientes,
                        rating: (event as! CestaCompraEventos.onUpdatePicture).receta.rating,
                        isFavorita: (event as! CestaCompraEventos.onUpdatePicture).receta.isFavorita
                    ),
                    active: (event as! CestaCompraEventos.onUpdatePicture).receta.active,
                    cantidad: nil
                )
            case is CestaCompraEventos.onAumentarCantidadReceta:
                self.updateReceta(
                    receta: (event as! CestaCompraEventos.onAumentarCantidadReceta).receta,
                    active: (event as! CestaCompraEventos.onAumentarCantidadReceta).receta.active,
                    cantidad: (event as! CestaCompraEventos.onAumentarCantidadReceta).receta.cantidad + 1
                )
            case is CestaCompraEventos.onReducirCantidadReceta:
                self.updateReceta(
                    receta: (event as! CestaCompraEventos.onReducirCantidadReceta).receta,
                    active: (event as! CestaCompraEventos.onReducirCantidadReceta).receta.active,
                    cantidad: (event as! CestaCompraEventos.onReducirCantidadReceta).receta.cantidad - 1
                )
            default:
                print("Evento no tenido en cuenta")
        }
    }
    
    private func addReceta(receta: Receta){
        print("La receta a añadir tiene los ingredientes: ")
        dump(receta.ingredientes)
        
        self.caseUses.addRecetaCestaCompra.addRecetaCestaCompra(receta: receta).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    let id_receta = dataState?.data
                    self.rePrintListaRecetas()
                    //Añado los ingredientes a la receta.
                    self.caseUses.addRecetaCestaCompra.addIngredientesReceta(
                        receta: receta,
                        id_receta: id_receta as! Int32
                    ).collectFlow(
                        coroutineScope: nil,
                        callback: { dataState in
                            if dataState?.data != nil {
                                //CUando se inserte vuelvo a pintar las recetas.
                                self.rePrintListaRecetas()
                            }
                        }
                    )
                }
            }
        )
    }
    
    private func deleteReceta(receta: Receta) -> Void {
        self.caseUses.deleteRecetaCestaCompra.deleteRecetaCestaCompra(receta: receta).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    self.rePrintListaRecetas()
                }
            }
        )
    }
    
    private  func updateReceta(receta: Receta, active: Bool, cantidad: Int32?) -> Void{
        print("Llego a intentar actualizar la receta: " + active.description)
        print("Los ingredientes a copiar son: ")
        dump(receta.ingredientes)
        print("La cantidad es: ")
        print(cantidad ?? 0)
        if(cantidad == nil || cantidad! > 0) {
            self.caseUses.updateRecetaCestaCompra.updateRecetaCestaCompra(
                receta: receta,
                active: active,
                //Tiene que ser null para que el caso de uso sepa que quiero actualizar active y no la cantidad
                cantidad: cantidad != nil ? KotlinInt(int: cantidad!) : nil
            ).collectFlow(
                coroutineScope: nil,
                callback: { dataState in
                    if dataState?.data != nil {
                        //Consigo que la receta se actualice pero debido a la conversión de unidades de Kotlin considera que el id es distinto y crea una nueva, por lo que primero tengo que obtener los ingredientes de la antigua, añadirlos a la nueva y eliminar la antigua.
                        let id_nuevo: Int32 = dataState?.data as! Int32
                        print("El nuevo id de la receta es: " )
                        print(id_nuevo)
                        self.rePrintListaRecetas()
                    }
                }
            )
        }
    }
    
    private func addIngredientesReceta(receta: Receta, id_nuevo: Int) -> Void {
        caseUses.addRecetaCestaCompra.addIngredientesReceta(receta: receta, id_receta: Int32(id_nuevo)).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //Elimino los ingredientes de la receta,
                    self.caseUses.deleteIngredienteReceta.deleteIngredientesReceta(
                        id_receta: receta.id_Receta as! Int32
                    ).collectFlow(
                        coroutineScope: nil,
                        callback: {_ in
                        }
                    )
                }
            }
        )
    }
    
    private func addAlimento(nombre: String, cantidad: Int32, tipoUnidad: TipoUnidad) -> Void{
        self.caseUses.addAlimentoCestaCompra.insertAlimentosCestaCompra(
            alimento: Alimento(
                id_cestaCompra: KotlinInt(integerLiteral: self.id_listaRecetas),
                id_receta: nil,
                id_alimento: nil,
                nombre: nombre,
                cantidad: cantidad,
                tipoUnidad: tipoUnidad,
                active: true
            )
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //Actualizo la lista de recetas.
                    self.rePrintListaRecetas()
                }
            }
        )
    }
    
    private func deleteAlimento(alimento: Alimento) -> Void{
        self.caseUses.deleteAlimento.deleteAlimento(alimento: alimento).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //TODO: No elimina el alimento por culpa de backend.
                    print("Intento eliminar el alimento con id: " +
                    String(Int(truncating: alimento.id_alimento!)))
                    self.rePrintListaRecetas()
                }
            }
        )
    }
    
    private func searchRecetas(query: String) -> Void {
        let currentState: CestaCompraState = self.state.copy() as! CestaCompraState
        
        self.caseUses.searchRecetasCache.searchRecetasCache(query: query).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //Obtengo de las recetas resultado de la búsqueda aquellas que no se encuentren ya entre las recetas de la lista activas.
                    var resultado_busqueda = (dataState?.data as! [Receta]).filter { receta in
                        self.state.recetasActive.contains(where: { it in
                            it.nombre != receta.nombre
                        })
                    }
                    
                    //Obtengo solo las recetas que pertenezcan a esta lista de recetas..
                    resultado_busqueda = resultado_busqueda.filter{ receta in
                        receta.id_cestaCompra == self.id_listaRecetas
                    }
                    
                    if(query == ""){
                        resultado_busqueda = []
                    }
                    
                    print("El resultado de la busqueda es:")
                    //dump(resultado_busqueda)
                    
                    self.state = self.state.doCopy(
                        id_cestaCompra_actual: currentState.id_cestaCompra_actual,
                        id_receta_actual: currentState.id_receta_actual,
                        nombre: currentState.nombre,
                        alimentosActive: currentState.alimentosActive,
                        alimentosInactive: currentState.alimentosInactive,
                        allAlimentos: currentState.allAlimentos,
                        recetasActive: currentState.recetasActive,
                        recetasInactive: currentState.recetasInactive,
                        allrecetas: currentState.allrecetas,
                        listaCestasCompra: currentState.listaCestasCompra,
                        onNewReceta: currentState.onNewReceta,
                        queueError: currentState.queueError,
                        resultadosAutoCompleteAlimentos: currentState.resultadosAutoCompleteAlimentos,
                        recetasFavoritas: currentState.recetasFavoritas,
                        recetasBuscadas: resultado_busqueda
                    )
                }
            })
    }
    
    
    private func rePrintListaRecetas()-> Void{
        let currentState: CestaCompraState = self.state.copy() as! CestaCompraState
        
        self.caseUses.updateRecetasLista.updateRecetas(id_listaRecetas: Int32(id_listaRecetas)).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                
                if dataState?.data != nil {
                    
                    let allRecetas: [Receta] = dataState?.data?["allRecetas"] as! [Receta]
                    let recetasListaNoActivas: [Receta] = dataState?.data?["recetasListaNoActivas"] as! [Receta]
                    //let recetasOutActivas: [Receta] = dataState?.data?["recetasOutActivas"] as! [Receta]
                    //let recetasOutNoActivas: [Receta] = dataState?.data?["recetasOutNoActivas"] as! [Receta]
                    let recetasActivas: [Receta] = dataState?.data?["recetasListaActivas"] as! [Receta]
                    let alimentos: [Alimento] = dataState?.data?["alimentosLista"] as! [Alimento]
            
                    let recetasInactivas: [Receta] = recetasListaNoActivas
                    
                    //Elimino de las inactivas las recetas con nombres iguales o repetidas, respetando el orden.
                    //recetasInactivas = recetasInactivas.unique{
                    //    $0.nombre
                    //}.filter{ receta in
                    //    !recetasActivas.contains(where: { it in
                    //        it.nombre == receta.nombre
                    //    })
                    //}
                    
                    print("Todas las recetas son:")
                    //dump(allRecetas)
                    
                    print("Las recetas actuales de la lista " + self.nombre + " son: ")
                    //dump(recetasInactivas)
                    
                    print("Las recetas activas son: ")
                    recetasActivas.forEach{ it in
                        print(it.id_Receta as! Int)
                        print(it.cantidad)
                    }
                    
                    print("Los alimentos son: ")
                    //dump(alimentos)
                    
                    print("La id de la lista de recetas es: " + String(Int(truncating: self.state.id_cestaCompra_actual!)))

                    
                    self.state = self.state.doCopy(
                        id_cestaCompra_actual: currentState.id_cestaCompra_actual,
                        id_receta_actual: currentState.id_receta_actual,
                        nombre: currentState.nombre,
                        alimentosActive: dataState?.data?["alimentosListaActivos"] as! [Alimento],
                        alimentosInactive: dataState?.data?["alimentosListaNoActivos"] as! [Alimento],
                        allAlimentos: alimentos,
                        recetasActive: recetasActivas,
                        recetasInactive: recetasInactivas,
                        allrecetas: allRecetas,
                        listaCestasCompra: currentState.listaCestasCompra,
                        onNewReceta: currentState.onNewReceta,
                        queueError: currentState.queueError,
                        resultadosAutoCompleteAlimentos: currentState.resultadosAutoCompleteAlimentos,
                        recetasFavoritas: currentState.recetasFavoritas,
                        recetasBuscadas: []
                    )
                }
            })
    }
}

