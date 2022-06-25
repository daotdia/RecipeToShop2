//
//  Despensa.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 9/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct Despensa: View {
    private let caseUses: UseCases
    
    
    //El viewModel al que observar.
    @ObservedObject var viewModel: DespensaViewModel
    
    //Obtengo los casos de uso e instancio el viwmodel con los mismos.
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
        self.viewModel = DespensaViewModel(
            useCases: self.caseUses
        )
    }
    
    //Estado para controlar el dialogo de creación de alimento.
    @State var openDialog: Bool = false
    @State var openDeleteIcon: Bool = false
    
    var body: some View {
        ZStack{
            //Lista de alimentos de la despensa.
            if !viewModel.state.allAlimentos.isEmpty{
                ScrollView{
                    //Botón para eliminar toda la despensa.
                    Button(action: {
                        openDeleteIcon = true
                        }
                    ){
                        Image(systemName: openDeleteIcon ? "trash.fill" : "ellipsis")
                            .font(Font.system(size: 18, weight: .medium))
                            .foregroundColor(openDeleteIcon ? Color.red: Color.black)
                    }
                    .frame(
                        minWidth:0, maxWidth: .infinity, minHeight: 0, maxHeight: 6, alignment: .trailing
                    )
                    .padding(.trailing, 24)

                    //Es una lista vertical de tres columnas fijas
                    LazyVGrid(
                        columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                        spacing: 8
                    ){
                        ForEach(
                            viewModel.state.allAlimentos, id: \.self.id_alimento
                        ){ alimento in
                            DespensaCard(alimento: alimento)
                        }
                    }
                    .offset(y:24)
                    .padding(6)
                }
            }
            //El floating button.
            FloatingButton(openDialog: $openDialog)
                .navigationTitle("Despensa")
            //Dialogo
                if $openDialog.wrappedValue{
                    NewAlimentoDialog(
                        caseUses: self.caseUses,
                        openDialog: $openDialog,
                        addAlimento: { nombre, peso, tipoUnidad -> Void in
                            viewModel.onTriggerEvent(
                                stateEvent: DespensaEventos.onAddAlimento(
                                    nombre: nombre,
                                    tipo: tipoUnidad,
                                    cantidad: peso
                                )
                            )
                        }
                    )
                }//            DespensaCard(alimento: Alimento(
//                id_cestaCompra: -1, id_receta: -1, id_alimento: -1, nombre: "Prueba", cantidad: 1 , tipoUnidad:TipoUnidad.gramos,
//                    active: true
//            ))
        }
    }
}
