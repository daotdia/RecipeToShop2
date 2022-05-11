//
//  MainViewModel.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 9/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//
import SwiftUI
import sharedApp

class DespensaViewModel: ObservableObject{
    //Casos de uso necesarios.
    let useCases: UseCases
    
    //He tenido que modificar el estado en commonmain debido a que Swift no reconoce los valores predeterminados en las clases.
    @Published var state: ListaAlimentosState = ListaAlimentosState()
    
    init(
        useCases: UseCases
    ){
        //Casos de uso necesarios, CAMBIAR POR SOLO LOS NECESARIOS.
        self.useCases = useCases
        //Hacer lo que debe de hacer el viewmodel de despensa
        
    }
    
    func onTriggerEvent(stateEvent: DespensaEventos){
        switch stateEvent {
        case is DespensaEventos.onAddAlimento:
            addAlimento(
                nombre: (stateEvent as! DespensaEventos.onAddAlimento).nombre,
                cantidad: Int((stateEvent as! DespensaEventos.onAddAlimento).cantidad)!,
                tipoUnidad:  (stateEvent as! DespensaEventos.onAddAlimento).tipo
            )
        default:
            print("Evento inesperado en la despensa: " + stateEvent.description)
        }
    }
    
    //Función para añadir un nuevo alimento a la despensa.
    private func addAlimento(nombre: String, cantidad: Int, tipoUnidad: String){
        //Creo el alimento nuevo.
        let alimento: Alimento = createAlimento(
            nombre: nombre,
            cantidad: cantidad,
            tipoUnidad: tipoUnidad
        )
        //Inserto el alimento en el cache.
        useCases.insertNewAlimento.insertAlimento(
            alimento: alimento
        ).collectFlow(
            coroutineScope: nil,
            callback: { datastate in
                //Cuando se haya insertado el alimento con éxito llamo a actualizar despensa
                if datastate != nil {
                    self.reprintDespensa()
                }
            }
        )
    }
    
    //Funcion para crear un alimento dados su nombre, cantidad y tipo de unidad.
    private func createAlimento(nombre: String, cantidad: Int, tipoUnidad: String) -> Alimento
    {
        //Tengo que hacer un iterador porque hay problemas con las listas de Kotlin.
        let tipos = TipoUnidad.values()
        let iterator = tipos.iterator()
        var tipoFinal: TipoUnidad = TipoUnidad.gramos
        while(iterator.hasNext()){
            let tipoAux = iterator.next_() as! TipoUnidad
            if(tipoAux.name == tipoUnidad){
                tipoFinal = tipoAux
            }
        }
        //Devuelvo el alimento.
        return Alimento(
            id_cestaCompra: nil,
            id_receta: nil,
            id_alimento: nil,
            nombre: nombre,
            cantidad: Int32(cantidad),
            tipoUnidad: tipoFinal,
            active: true
        )
    }
    
    //Función para actualizar la lista de alimentos en la despensa.
    private func reprintDespensa(){
        let currentState = self.state.copy() as! ListaAlimentosState
        //Obtengo la lista de alimentos de despensa nueva.
        useCases.getAlimentos.getAlimentos().collectFlow(
            coroutineScope: nil,
            callback: {datastate in
                //En el caso de que no sea null actualizo la lista de alimentos.
                if datastate != nil {
                    self.state = self.state.doCopy(
                        allAlimentos: datastate?.data as! [Alimento],
                        alimentosActivos: currentState.alimentosActivos,
                        alimentosInactivos: currentState.alimentosInactivos,
                        queueError: currentState.queueError,
                        resultadoAutoCompletado: currentState.resultadoAutoCompletado,
                        queryAutoComplete: currentState.queryAutoComplete)
                }
            }
        )
    }
}


