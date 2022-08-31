//
//  ListaRecetasViewModel.swift
//  IOSApp
//
//  Created by David Otero Diaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

class ListasRecetasViewModel: ObservableObject {
    //Casos de uso necesarios.
    let caseUses: UseCases
    
    @Published var state: ListaCestasCompraState = ListaCestasCompraState()
    
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
        
        //Lo primero es pintar las listas de recetas ya guardadas.
        self.rePrintListasRecetas()
    }
    
    func onTriggerEvent(stateEvent: ListaCestasCompraEventos){
        switch stateEvent {
            case is ListaCestasCompraEventos.onAddListaRecetaEventos:
                addListaRecetas(
                    nombre:  (stateEvent as! ListaCestasCompraEventos.onAddListaRecetaEventos).nombre
                )
            case is ListaCestasCompraEventos.onDeleteCestaCompra:
                deleteListaRecetas(
                    id_listaRecetas: Int((
                        stateEvent as! ListaCestasCompraEventos.onDeleteCestaCompra
                    ).id_cestaCompra)
                )
            case is ListaCestasCompraEventos.onAddPicture:
                if((stateEvent as! ListaCestasCompraEventos.onAddPicture).picture != nil){
                    updatePicture(
                        picture: (stateEvent as! ListaCestasCompraEventos.onAddPicture).picture!,
                        id_cestaCompra: Int((stateEvent as! ListaCestasCompraEventos.onAddPicture).id_cestaCompra)
                    )
                }
            default:
                print("Evento no reconocido")
        }
    }
    
    private func updatePicture(picture: String, id_cestaCompra: Int) -> Void {
        print("Las listas de la compra antes")
        dump(self.state.listaCestasCompra)
        self.caseUses.addPictureCestaCompra.addPictureCestaCompra(
            id_cestaCompra: Int32(id_cestaCompra),
            picture: picture
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if(dataState?.data != nil){
                    //Vuelvo a pintar las recetas.
                    self.rePrintListasRecetas()
                }
            }
        )
    }
    
    private func addListaRecetas(nombre: String)-> Void{
        let listaRecetas = CestaCompra(
            id_cestaCompra: nil,
            imagen: "",
            nombre: nombre
        )
        
        let currentState = self.state.copy() as! ListaCestasCompraState

        caseUses.addNewCestaCompra.addCestaCompra(cestaCompra: listaRecetas).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil{
                    //Obtengo el id de la lista de de recetas debido a que lo necesito para navegar automáticamente al contenido de la misma.
                    self.state = self.state.doCopy(
                        listaCestasCompra: currentState.listaCestasCompra,
                        id_cestaCompraActual: dataState?.data as! Int32
                    )
                    //Cuando añado la nueva lista de recetas reprinto las listas de recetas.
                    self.rePrintListasRecetas()
                }
            }
        )
    }
    
    private func deleteListaRecetas(id_listaRecetas: Int) -> Void {
        //TODO: Crear caso de uso para poder eliminar la lista de recetas.
    }
    
    private func rePrintListasRecetas() -> Void {
        
        let currentState = self.state.copy() as! ListaCestasCompraState

        caseUses.printListaCestasCompra.printListaCestasCompra().collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    print("Las listas de la compra después: ")
                    dump(dataState?.data as! [CestaCompra])
                    //Obtengo las listas de recetas y las añado al estado actual.
                    self.state = self.state.doCopy(
                        listaCestasCompra: dataState?.data as! [CestaCompra],
                        id_cestaCompraActual: currentState.id_cestaCompraActual
                    )
                }
            }
        )
    }
}
