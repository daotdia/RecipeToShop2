//
//  RecetaViewModel.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 6/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class RecetaViewModel: ObservableObject {
    
    private let caseUses: UseCases
    private let receta: Receta
    
    @Published var state: RecetaState = RecetaState()
    
    init(
        caseUses: UseCases,
        receta: Receta
    ){
        self.caseUses = caseUses
        self.receta = receta
        
        self.reprintReceta()
    }
    
    func onTriggerEvent(event: RecetaEventos) -> Void {
        switch event{
            case is RecetaEventos.onaAddIngrediente:
                print("Intentando añadir ingrediente " +
                      (event as! RecetaEventos.onaAddIngrediente).nombre +
                      "de la receta " + String(Int(self.state.cestaCompra_id)) +
                      "de la lista de recetas: " + String(Int(self.state.receta_id))
                )
                addIngrediente(
                    ingrediente: Alimento(
                        id_cestaCompra: KotlinInt(integerLiteral: Int(self.state.cestaCompra_id)),
                        id_receta: KotlinInt(integerLiteral: Int(self.state.receta_id)),
                        id_alimento: nil,
                        nombre: (event as! RecetaEventos.onaAddIngrediente).nombre,
                        cantidad: (event as! RecetaEventos.onaAddIngrediente).cantidad,
                        tipoUnidad: (event as! RecetaEventos.onaAddIngrediente).tipoUnidad,
                        active: true
                    )
                )
            case is RecetaEventos.onDeleteIngrediente:
                deleteIngrediente(id_ingrediente: (event as! RecetaEventos.onDeleteIngrediente).id_ingrediente)
                
            default:
                print("Evento de receta no conocido")
        }
    }
    
    private func addIngrediente(ingrediente: Alimento) -> Void {
        caseUses.addIngredienteReceta.addIngredienteReceta(
            id_cestaCompra: Int32(truncating: ingrediente.id_cestaCompra!),
            id_receta: Int32(truncating: ingrediente.id_receta!),
            nombre: ingrediente.nombre,
            cantidad: ingrediente.cantidad,
            tipoUnidad: ingrediente.tipoUnidad
        ).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    self.reprintReceta()
                }
            }
        )
    }
    
    private func deleteIngrediente(id_ingrediente: Int32) -> Void {
        caseUses.deleteIngredienteReceta.deleteIngredientereceta(id_ingrediente: id_ingrediente).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    self.reprintReceta()
                }
            }
        )
    }
    
    private func reprintReceta() -> Void {
        let currentState:RecetaState = self.state.copy() as! RecetaState
        if(receta.id_cestaCompra != -1 || receta.id_Receta != -1){
            print("El id de la receta es: " + String(Int(truncating: self.receta.id_Receta!)))
            caseUses.getDatosReceta.getDatosReceta(id_receta: self.receta.id_Receta as! Int32).collectFlow(
                coroutineScope: nil,
                callback: { dataState in
                    if dataState?.data != nil {
                        self.state = self.state.doCopy(
                            cestaCompra_id: self.receta.id_cestaCompra,
                            receta_id: dataState?.data?.id_Receta as! Int32,
                            nombre: self.receta.nombre,
                            cantidad: String(Int(self.receta.cantidad)),
                            imagen: dataState?.data?.imagenSource ?? "",
                            ingredientes: dataState?.data?.ingredientes ?? [],
                            queueError: currentState.queueError,
                            resultadoAutoComplete: currentState.resultadoAutoComplete
                        )
                    }
                }
            )
        }
    }
}
