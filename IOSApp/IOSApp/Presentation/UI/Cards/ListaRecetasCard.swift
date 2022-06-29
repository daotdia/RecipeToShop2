//
//  ListaRecetasCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ListaRecetasCard: View {
    private let nombre: String
    private let id_listaRecetas: Int
    private let eliminarCard: (Int) -> Void
    
    @Binding var openListaRecetasModal: Bool
    @Binding var id_listaRecetasSeleccionada: Int
    
    init(
        nombre: String,
        id_listaRecetas: Int,
        eliminarCard: @escaping (Int) -> Void,
        openListaRecetasModal: Binding<Bool>,
        id_listaRecetasSeleccionada: Binding<Int>
    ){
        self.nombre = nombre
        self.id_listaRecetas = id_listaRecetas
        self.eliminarCard = eliminarCard
        
        self._openListaRecetasModal = openListaRecetasModal
        self._id_listaRecetasSeleccionada = id_listaRecetasSeleccionada
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
                Text(nombre)
                    .font(.headline)
                    .frame(alignment: .center)
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
                } else {
                    //En otro caso se crea la sheet con la lista de recetas seleccionada.
                    id_listaRecetasSeleccionada = id_listaRecetas
                    openListaRecetasModal = true
                }
            }
            .onLongPressGesture(perform: {
                openDeleteListaRecetasCard = true
            })
        }
    }
}
