//
//  ListaRecetasViewModel.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

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
                    cantidad: (event as! CestaCompraEventos.onUpdateRecetaActive).receta.cantidad
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
            default:
                print("Evento no tenido en cuenta")
        }
    }
    
    private func addReceta(receta: Receta){
        self.caseUses.addRecetaCestaCompra.addRecetaCestaCompra(receta: receta).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    //CUando se inserte vuelvo a pintar las recetas.
                    self.rePrintListaRecetas()
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
    
    private  func updateReceta(receta: Receta, active: Bool, cantidad: Int32) -> Void{
        print("Llego a intentar actualizar la receta: " + active.description)
        self.caseUses.updateRecetaCestaCompra.updateRecetaCestaCompra(
            receta: receta,
            active: active,
            cantidad: KotlinInt(integerLiteral: Int(cantidad))
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    self.rePrintListaRecetas()
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
                    
                    //Elimino del resultado de búsqueda las recetas de nombre repetido respetando el orden.
                    resultado_busqueda = resultado_busqueda.unique(map: {
                        $0.nombre
                    })
                    
                    if(query == ""){
                        resultado_busqueda = []
                    }
                    
                    print("El resultado de la busqueda es:")
                    dump(resultado_busqueda)
                    
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
                    let recetasOutActivas: [Receta] = dataState?.data?["recetasOutActivas"] as! [Receta]
                    let recetasOutNoActivas: [Receta] = dataState?.data?["recetasOutNoActivas"] as! [Receta]
                    let recetasActivas: [Receta] = dataState?.data?["recetasListaActivas"] as! [Receta]
                    let alimentos: [Alimento] = dataState?.data?["alimentosLista"] as! [Alimento]
                    
                    var recetasInactivas: [Receta] = recetasListaNoActivas + recetasOutActivas + recetasOutNoActivas
                    
                    //Elimino de las inactivas las recetas con nombres iguales o repetidas, respetando el orden.
                    recetasInactivas = recetasInactivas.unique{
                        $0.nombre
                    }.filter{ receta in
                        !recetasActivas.contains(where: { it in
                            it.nombre == receta.nombre
                        })
                    }
                    print("Todas las recetas son:")
                    dump(allRecetas)
                    
                    print("Las recetas actuales de la lista " + self.nombre + " son: ")
                    dump(recetasInactivas)
                    
                    print("Las recetas activas son: ")
                    dump(recetasActivas)
                    
                    print("Los alimentos son: ")
                    dump(alimentos)
                    
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

