//
//  ListaRecetasScreen.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ListaRecetasScreen: View {
    
    private let caseUses: UseCases
    
    @Binding var openDialog: Bool
    @Binding var tabSelection: Int
    
    @ObservedObject var viewModel: ListasRecetasViewModel
    
    init(
        caseUses: UseCases,
        openDialog: Binding<Bool>,
        tabSelection: Binding<Int>
    ){
        self.caseUses = caseUses
        self._openDialog = openDialog
        self._tabSelection = tabSelection
        
        self.viewModel = ListasRecetasViewModel(caseUses: self.caseUses)
    }
    
    @State var nombreNuevaListaRecetas: String = ""
    @State var openListaRecetas: Bool = false
    @State var id_listaRecetasSeleccionada: Int = -1
    
    var body: some View {
        ZStack{
            //Lista de listas de recetas
            ScrollView{
                LazyVGrid(
                    columns: Array(repeating: .init(.adaptive(minimum: 240)), count: 2),
                    spacing: 12
                ){
                    ForEach(
                        viewModel.state.listaCestasCompra, id: \.self.id_cestaCompra
                    ){ listaRecetas in
                        ListaRecetasCard(
                            nombre: listaRecetas.nombre,
                            id_listaRecetas: listaRecetas.id_cestaCompra as! Int,
                            eliminarCard: { id in
                                viewModel.onTriggerEvent(stateEvent: ListaCestasCompraEventos.onDeleteCestaCompra(
                                    id_cestaCompra: Int32(id)
                                    )
                                )
                            },
                            openListaRecetasModal: $openListaRecetas,
                            id_listaRecetasSeleccionada: $id_listaRecetasSeleccionada
                        )
                    }
                    .frame(minWidth: 164, minHeight: 164)
                    .border(.green, width: 1.5)
                    .cornerRadius(16)
                }
                .offset(y:24)
                .padding(6)
            }
            
            //Boton Floating oara añadir una nueva lista de recetas.
            FloatingButton(openDialog: $openDialog)
            
            //Dialogo de nuevo alimento
            if $openDialog.wrappedValue{
                GeneralDialog(
                    siFunc: {
                        //Funcion para añadir la lista de recetas utilizo el nombre guardado en esta view.
                        viewModel.onTriggerEvent(stateEvent: ListaCestasCompraEventos.onAddListaRecetaEventos(
                            nombre: $nombreNuevaListaRecetas.wrappedValue
                        ))
                        //Lanzo el modal con l alista de recetas.
                        openListaRecetas = true
                    },
                    noFunc: {},
                    siFlag: $openDialog,
                    noFlag: $openDialog,
                    dialogContent: {
                        VStack{
                            Text("Nombre de la lista de recetas")
                                .font(.headline)
                                .padding([.top,.bottom], 8)
                            TextField("Nombre", text: $nombreNuevaListaRecetas)
                                .multilineTextAlignment(.center)
                                .onChange(of: nombreNuevaListaRecetas, perform: { newValue in
                                    self.nombreNuevaListaRecetas = newValue
                                })
                                .padding([.top], 8)
                                .textFieldStyle(MyTextFieldStyle())
                        }
                    },
                    siButtonContent: {
                        Text("Crear")
                    },
                    noButtonContent: {
                        Text("Cancelar")
                    }
                )
                .frame(height: 186, alignment: .top)
                .padding([.leading,.trailing],24)
                .cornerRadius(46)
                .opacity(0.9)
                .offset(y:-48)
            }
        }
        .sheet(isPresented: $openListaRecetas, content: {
            //TODO: Crear pantalla de lista de recetas.
            ListaRecetas(
                caseUses: self.caseUses,
                tabSelection: $tabSelection,
                id_listaRecetas: id_listaRecetasSeleccionada
            )
        })
    }
}

