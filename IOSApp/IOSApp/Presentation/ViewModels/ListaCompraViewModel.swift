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
        self.caseUses = caseUses
        self.id_listaRecetas = id_listaRecetas
    }
    
    func onTriggerEvent(event: ListaCompraEvents) -> Void {
        switch event {
            default:
                print("La lista de la compra no está preparada para el evento")
        }
    }
}
