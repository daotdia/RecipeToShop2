//
//  ListaRecetas.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ListaRecetas: View {
    
    private let caseUses: UseCases
    private let id_listaRecetas: Int
    
    @Binding var tabSelection: Int
    
    @ObservedObject var viewModel: ListaRecetasViewModel
    
    init(
        caseUses: UseCases,
        tabSelection: Binding<Int>,
        id_listaRecetas: Int
    ){
        self.caseUses = caseUses
        self._tabSelection = tabSelection
        self.id_listaRecetas = id_listaRecetas
        
        self.viewModel = ListaRecetasViewModel(
            caseUses: self.caseUses,
            id_listaRecetas: self.id_listaRecetas
        )
    }
    
    @State var calculateListaCompra: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                //TODO: Hacer barra buscadora de recetas por nombre.
                SearchBar(diccionario: [], updateSearch: { query in
                    print()
                })
                //TODO: Hacer ScrollView con todas las recetas de la lista.
                
                Divider()
                
                //TODO: Hacer Scrollview con todas las recetas realziadas hasta el momento, con fondo girs para indicar que no son las activas.
                
                //TODO: Hacer Scrollview horizontal con los alimentos sueltos de la lista de recetas.
            }
            //TODO: Hacer FloatingButton pero con diferente símbolo.
            
        }
    }
}


