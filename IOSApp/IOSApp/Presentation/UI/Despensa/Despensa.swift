//
//  Despensa.swift
//  IOSApp
//
//  Created by David Otero Diaz on 9/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct Despensa: View {
    private let caseUses: UseCases
    
    
    //El viewModel al que observar.
    @ObservedObject var viewModel: DespensaViewModel
    
    @Binding var openDeleteIcon: Bool
    @Binding var openDialogDelete: Bool
    
    //Obtengo los casos de uso e instancio el viwmodel con los mismos.
    init(
        caseUses: UseCases,
        openDeleteIcon: Binding<Bool>,
        openDialogDelete: Binding<Bool>
    ){
        self.caseUses = caseUses
        self.viewModel = DespensaViewModel(
            useCases: self.caseUses
        )
        
        self._openDeleteIcon = openDeleteIcon
        self._openDialogDelete = openDialogDelete
    }
    
    //Estado para controlar el dialogo de creación de alimento.
    @State var openDialog: Bool = false
    @State var alimento_actual: Alimento = Alimento(
        id_cestaCompra: nil,
        id_receta: nil,
        id_alimento: nil,
        nombre: "",
        cantidad: 0,
        tipoUnidad: TipoUnidad.gramos,
        active: false
    )
    @State var inEditAlimento: Bool = false
    
    var body: some View {
        ZStack{
            //Lista de alimentos de la despensa.
            if !viewModel.state.allAlimentos.isEmpty{
                ScrollView{
                    //Botón para eliminar toda la despensa.
                    Button(action: {
                        if openDeleteIcon == false{
                            openDeleteIcon = true
                            print("he llegado a opendialog intentar abrir")
                        }
                        else{
                            print("He llegado a dialog general")
                            openDialogDelete = true
                        }
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
                            DespensaCard(
                                alimento: alimento,
                                deleteAlimento: { alimento in
                                    viewModel.onTriggerEvent(
                                        stateEvent: DespensaEventos.onAlimentoDelete(alimento: alimento)
                                    )
                                },
                                onClickAlimento: { alimento in
                                    //Seteo le alimento actual de la despensa clicado
                                    $alimento_actual.wrappedValue = alimento
                                    
                                    //Abro el diálogo del nuevo alimento setado con la información del alimento actual
                                    $inEditAlimento.wrappedValue = true
                                    $openDialog.wrappedValue = true
                                }
                            )
                        }
                    }
                    .offset(y:24)
                    .padding([.leading, .trailing], 8)
                }
            }
            //El floating button.
            FloatingButton(
                openDialog: $openDialog,
                simbolsys: "plus"
            )
    
            //Dialogo generl de eliminar despensa
            if $openDialogDelete.wrappedValue{
                GeneralDialog(
                    siFunc:{
                        viewModel.onTriggerEvent(
                        stateEvent: DespensaEventos.onSelectedNestedMenuItem(option: "deleteAll")
                        )
                        //Cierro el icon de delete.
                        openDeleteIcon = false
                    },
                    noFunc: {
                        //Cierro el Icono de delete.
                        openDeleteIcon = false
                    },
                    siFlag: $openDialogDelete,
                    noFlag: $openDialogDelete,
                    dialogContent: {
                        Text("¿Estás seguro de querer eliminar la despensa?")
                            .font(.headline)
                    },
                    siButtonContent: {
                        Text("Sí")
                            .foregroundColor(.red)
                    },
                    noButtonContent: {
                        Text("No")
                    }
                )
                .frame(height: 186, alignment: .top)
                .padding([.leading,.trailing],24)
                .cornerRadius(46)
                .opacity(0.8)
                .offset(y:-48)
                
            }
            
            //Dialogo de nuevo alimento
//            if $openDialog.wrappedValue{
//                NewAlimentoDialog(
//                    caseUses: self.caseUses,
//                    openDialog: $openDialog,
//                    addAlimento: { nombre, peso, tipoUnidad -> Void in
//                        viewModel.onTriggerEvent(
//                            stateEvent: DespensaEventos.onAddAlimento(
//                                nombre: nombre,
//                                tipo: tipoUnidad,
//                                cantidad: peso
//                            )
//                        )
//                    }
//                )
//            }
            //            DespensaCard(alimento: Alimento(
//                id_cestaCompra: -1, id_receta: -1, id_alimento: -1, nombre: "Prueba", cantidad: 1 , tipoUnidad:TipoUnidad.gramos,
//                    active: true
//            ))
        }.onAppear{
            viewModel.reprintDespensa()
        }
        .sheet(isPresented: $openDialog, content: {
            NewAlimentoDialog(
                caseUses: self.caseUses,
                alimento: alimento_actual,
                openDialog: $openDialog,
                inEditAlimento: $inEditAlimento,
                addAlimento: { nombre, peso, tipoUnidad -> Void in
                    viewModel.onTriggerEvent(
                        stateEvent: DespensaEventos.onAddAlimento(
                            nombre: nombre,
                            tipo: tipoUnidad,
                            cantidad: peso
                        )
                    )
                },
                editAlimento: { alimento in
                    viewModel.onTriggerEvent(stateEvent: DespensaEventos.onClickAlimento(
                        alimento: alimento, active: alimento.active
                    ))
                }
            )
        })
    }
}
