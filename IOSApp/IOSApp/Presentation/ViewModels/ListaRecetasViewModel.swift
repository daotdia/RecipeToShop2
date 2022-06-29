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
    
    @Published var state: CestaCompraState = CestaCompraState()
    
    init(
        caseUses: UseCases,
        id_listaRecetas: Int
    ){
        self.caseUses = caseUses
        self.id_listaRecetas = id_listaRecetas
        
        self.rePrintRecetas()
    }
    
    
    private func rePrintRecetas()-> Void{
        let currentState: CestaCompraState = self.state.copy() as! CestaCompraState
        
        self.caseUses.updateRecetasLista.updateRecetas(id_listaRecetas: Int32(id_listaRecetas)).collectFlow(
            coroutineScope: nil,
            callback: { dataState in
                if dataState?.data != nil {
                    let recetasListaNoActivas: [Receta] = dataState?.data?["recetasListaNoActivas"] as! [Receta]
                    let recetasOutActivas: [Receta] = dataState?.data?["recetasOutActivas"] as! [Receta]
                    let recetasOutNoActivas: [Receta] = dataState?.data?["recetasOutNoActivas"] as! [Receta]
                    
                    let recetasInactivas: [Receta] = recetasListaNoActivas + recetasOutActivas + recetasOutNoActivas

                    self.state = self.state.doCopy(
                        id_cestaCompra_actual: currentState.id_cestaCompra_actual,
                        id_receta_actual: currentState.id_receta_actual,
                        nombre: currentState.nombre,
                        alimentosActive: dataState?.data?["alimentosListaActivos"] as! [Alimento],
                        alimentosInactive: dataState?.data?["alimentosListaNoActivos"] as! [Alimento],
                        allAlimentos: dataState?.data?["alimentosLista"] as! [Alimento],
                        recetasActive: dataState?.data?["recetasListaActivas"] as! [Receta],
                        recetasInactive: recetasInactivas,
                        allrecetas: dataState?.data?["allRecetas"] as! [Receta],
                        listaCestasCompra: currentState.listaCestasCompra,
                        onNewReceta: currentState.onNewReceta,
                        queueError: currentState.queueError,
                        resultadosAutoCompleteAlimentos: currentState.resultadosAutoCompleteAlimentos,
                        recetasFavoritas: currentState.recetasFavoritas
                    )
                }
            })
    }
}

