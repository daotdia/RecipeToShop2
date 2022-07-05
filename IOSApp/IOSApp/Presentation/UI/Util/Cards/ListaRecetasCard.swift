//
//  ListaRecetasCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ListaRecetasCard: View {
    private let nombre: String
    private let id_listaRecetas: Int
    private let eliminarCard: (Int) -> Void
    private let caseUses: UseCases
    
    @Binding var tabSelection: Int
        
    init(
        caseUses: UseCases,
        nombre: String,
        id_listaRecetas: Int,
        eliminarCard: @escaping (Int) -> Void,
        tabSelection: Binding<Int>
    ){
        self.caseUses = caseUses
        self.nombre = nombre
        self.id_listaRecetas = id_listaRecetas
        self.eliminarCard = eliminarCard
        
        self._tabSelection = tabSelection
    }
    
    @State var openDeleteListaRecetasCard: Bool = false

    var body: some View {
        VStack{
            ZStack{
                //Icono de añadir imagen
                Image(systemName: "camera")
                    .resizable()
                    .frame(width: 24, height: 24, alignment: .center)
                    .opacity(0.75)
                    .foregroundColor(.gray)
                    .offset(y:-46)
                
                NavigationLink(
                    nombre,
                    destination: {
                        ListaRecetas(
                            caseUses: caseUses,
                            id_listaRecetas: id_listaRecetas,
                            nombre: nombre,
                            tabSelection: $tabSelection
                        )
                        .navigationTitle(nombre)
                    }
                )
                .multilineTextAlignment(.center)
                
                if openDeleteListaRecetasCard {
                    ZStack(alignment: .topTrailing){
                        //Necesario para hacer el frame igual de grande que el padre.
                        Color.clear
                        Image(systemName: "trash.fill")
                            .foregroundColor(.red)
                            .onTapGesture {
                                eliminarCard(self.id_listaRecetas)
                            }
                    }
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .padding()
            .onTapGesture {
                if openDeleteListaRecetasCard {
                    //EN el caso de que se clique la lista pero aparezca el símbolo de eliminar, lo esconde
                    openDeleteListaRecetasCard = false
                }
            }
            .onLongPressGesture(perform: {
                openDeleteListaRecetasCard = true
            })
        }
    }
}
