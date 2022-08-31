//
//  ListaRecetasScreen.swift
//  IOSApp
//
//  Created by David Otero Diaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ListasRecetasScreen: View {
    
    private let caseUses: UseCases
    
    @Binding var openDialog: Bool
    @Binding var tabSelection: Int
    @Binding var id_listaCompra: Int
    
    @ObservedObject var viewModel: ListasRecetasViewModel
        
    init(
        caseUses: UseCases,
        openDialog: Binding<Bool>,
        tabSelection: Binding<Int>,
        id_listaCompra: Binding<Int>
    ){
        self.caseUses = caseUses
        self._openDialog = openDialog
        self._tabSelection = tabSelection
        self._id_listaCompra = id_listaCompra
        
        self.viewModel = ListasRecetasViewModel(caseUses: self.caseUses)
    }
    
    @State var nombreNuevaListaRecetas: String = ""
    @State var id_listaRecetasSeleccionada: Int = -1
    @State var nombreValido: Bool = false
    @State var addLista: Bool = false
    
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
                            caseUses: caseUses,
                            nombre: listaRecetas.nombre,
                            id_listaRecetas: listaRecetas.id_cestaCompra as! Int,
                            eliminarCard: { id in
                                viewModel.onTriggerEvent(stateEvent: ListaCestasCompraEventos.onDeleteCestaCompra(
                                    id_cestaCompra: Int32(id)
                                    )
                                )
                            },
                            saveImage: { image in
                                viewModel.onTriggerEvent(stateEvent: ListaCestasCompraEventos.onAddPicture(
                                    picture: WriteLoadIImage().saveImageToDocumentDirectory(image),
                                    id_cestaCompra: listaRecetas.id_cestaCompra as! Int32
                                ))
                            },
                            tabSelection: $tabSelection,
                            id_listaCompra: $id_listaCompra,
                            uiImage: UIImage(contentsOfFile: listaRecetas.imagen) ?? UIImage()
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
            FloatingButton(
                openDialog: $openDialog,
                simbolsys: "plus"
            )
            
            //Dialogo de nueva lista de recetas
            if $openDialog.wrappedValue{
                GeneralDialog(
                    siFunc: {
                        if nombreValido {
                            addLista = true
                            
                            //Funcion para añadir la lista de recetas utilizo el nombre guardado en esta view.
                            viewModel.onTriggerEvent(stateEvent: ListaCestasCompraEventos.onAddListaRecetaEventos(
                                nombre: $nombreNuevaListaRecetas.wrappedValue
                            ))
                            
                            nombreNuevaListaRecetas = ""
                            
                        } else{
                            addLista = true
                        }
                        
                    },
                    noFunc: {
                        addLista = false
                        nombreNuevaListaRecetas = ""
                    },
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
                                    if !self.nombreNuevaListaRecetas.isEmpty{
                                        nombreValido = true
                                    }
                                })
                                .padding([.top], 8)
                                .textFieldStyle(MyTextFieldStyle())
                            //En el caso de que esté vacío aparece el borde de error.
                                .overlay(
                                    !nombreValido ? ErrorBorder(error: $addLista) : nil
                                )
                        }
                    },
                    siButtonContent: {
                        Text("Crear")
                    },
                    noButtonContent: {
                        Text("Cancelar")
                    }
                )
                .frame(height: 240, alignment: .top)
                .padding([.leading,.trailing],24)
                .cornerRadius(46)
                .opacity(0.9)
                .offset(y:-48)
            }
        }
    }
}

