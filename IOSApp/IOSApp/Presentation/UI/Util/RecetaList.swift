//
//  RecetaList.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 29/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct RecetaList: View {
    
    private let addNewReceta: (String) -> Void
    private let addReceta: (Receta) -> Void
    private let eliminarReceta: (Receta) -> Void
    private let activarReceta: (Receta, Bool) -> Void
    private let id_listaRecetas: Int
    private let addRecetaExistente: (Receta) -> Void
    private let caseUses: UseCases
    private let saveImage: (UIImage, Receta) -> Void
    
    @State var isBusqueda: Bool
    
    @Binding var onRecetaContent: Bool
    
    @ObservedObject var viewModel: ListaRecetasViewModel
        
    init(
        viewModel: ListaRecetasViewModel,
        isBusqueda: Bool,
        addReceta: @escaping (Receta) -> Void,
        addNewReceta: @escaping (String) -> Void,
        activarReceta: @escaping (Receta, Bool) -> Void,
        eliminarReceta: @escaping (Receta) -> Void,
        addRecetaExistente: @escaping (Receta) -> Void,
        saveImage: @escaping (UIImage, Receta) -> Void,
        id_listaRecetas: Int,
        caseUses: UseCases,
        onRecetaContent: Binding<Bool>
    ){
        self.viewModel = viewModel
        self.isBusqueda = isBusqueda
        self.addReceta = addReceta
        self.addNewReceta = addNewReceta
        self.activarReceta = activarReceta
        self.eliminarReceta = eliminarReceta
        self.id_listaRecetas = id_listaRecetas
        self.addRecetaExistente = addRecetaExistente
        self.saveImage = saveImage
        self.caseUses = caseUses
        self._onRecetaContent = onRecetaContent
    }
    
    @State var nombreReceta: String = ""
    @State var nombreValido: Bool = false
    @State var inAddReceta: Bool = false
    @State var openDialog: Bool = false
    
    var body: some View {
        ZStack{
            VStack(alignment: .center){
                //Botón para añadir recetas nuevas en el caso de que no estemos en la búsqueda.
                if !isBusqueda{
                    Text("Recetas activas")
                        .offset(y:16)
                        .padding(4)
                    
                    Image(systemName: "plus.circle.fill")
                        .resizable()
                        .frame(width: 64, height: 64, alignment: .center)
                        .opacity(0.85)
                        .shadow(
                            color: Color.black.opacity(0.3),
                            radius: 3,
                            x: 3,
                            y: 3
                        )
                        .onTapGesture {
                            openDialog = true
                        }
                        .offset(y:16)
                        .zIndex(1)
                    
                    ScrollView{
                        LazyVGrid(
                            columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                            spacing: 8
                        ){
                            //La lista de recetas de la lista activas.
                            ForEach(viewModel.state.recetasActive, id: \.self.id_Receta){ receta in
                                RecetaCard(
                                    receta: receta,
                                    isBusqueda: $isBusqueda,
                                    eliminarCard: { receta in
                                        eliminarReceta(receta)
                                    },
                                    activarReceta: { receta, active in
                                        activarReceta(receta, active)
                                    },
                                    addReceta: { receta in
                                        addReceta(receta)
                                    },
                                    addRecetaExistente: { receta in
                                        addRecetaExistente(receta)
                                    },
                                    saveImage: { image in
                                        self.saveImage(image,receta)
                                    },
                                    id_listaRecetas: id_listaRecetas,
                                    caseUses: caseUses,
                                    onRecetaContent: $onRecetaContent,
                                    sumarReceta: { receta in
                                        viewModel.onTriggerEvent(event: CestaCompraEventos.onAumentarCantidadReceta(
                                            receta: receta
                                        ))
                                    },
                                    restarRecera: { receta in
                                        viewModel.onTriggerEvent(event: CestaCompraEventos.onReducirCantidadReceta(
                                            receta: receta
                                        ))
                                    }
                                )
                            }
                            .frame(minWidth: 100, minHeight: 100)
                            .border(.green, width: 1.5)
                            .cornerRadius(18)
                        }
                    }
                    .frame(maxHeight: 115)
                    
                    Text("Recetas inactivas")
                    
                    ScrollView{
                        //Lista de recetas inactivas.
                        LazyVGrid(
                            columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                            spacing: 8
                        ){
                            //La lista de recetas de la lista activas.
                            ForEach(viewModel.state.recetasInactive, id: \.self){ receta in
                                RecetaCard(
                                    receta: receta,
                                    isBusqueda: $isBusqueda,
                                    eliminarCard: { receta in
                                        eliminarReceta(receta)
                                    },
                                    activarReceta: { receta, active in
                                        activarReceta(receta, active)
                                    },
                                    addReceta: { receta in
                                        addReceta(receta)
                                    },
                                    addRecetaExistente: { receta in
                                        print("He llegado hasta aquí")
                                        addRecetaExistente(receta)
                                    },
                                    saveImage: { image in
                                        self.saveImage(image,receta)
                                    },
                                    id_listaRecetas: id_listaRecetas,
                                    caseUses: caseUses,
                                    onRecetaContent: $onRecetaContent,
                                    sumarReceta: { receta in },
                                    restarRecera: { receta in }
                                )
                            }
                            .frame(minWidth: 100, minHeight: 100)
                            .border(.green, width: 1.5)
                            .cornerRadius(12)
                        }
                    }
                    .frame(maxHeight: 115)
                } else {
                    ScrollView{
                        //Lista de recetas inactivas.
                        LazyVGrid(
                            columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                            spacing: 8
                        ){
                            //La lista de recetas de la lista activas.
                            ForEach(viewModel.state.recetasBuscadas, id: \.self){ receta in
                                RecetaCard(
                                    receta: receta,
                                    isBusqueda: $isBusqueda,
                                    eliminarCard: { receta in
                                        eliminarReceta(receta)
                                    },
                                    activarReceta: { receta, active in
                                        activarReceta(receta, active)
                                    },
                                    addReceta: { receta in
                                        addReceta(receta)
                                    },
                                    addRecetaExistente: { receta in
                                        print("He llegado hasta aquí")
                                        addRecetaExistente(receta)
                                    },
                                    saveImage: { image in
                                        self.saveImage(image,receta)
                                    },
                                    id_listaRecetas: id_listaRecetas,
                                    caseUses: caseUses,
                                    onRecetaContent: $onRecetaContent,
                                    sumarReceta: { receta in },
                                    restarRecera: { receta in }
                                )
                            }
                            .frame(minWidth: 100, minHeight: 100)
                            .border(.green, width: 1.5)
                            .cornerRadius(12)
                        }
                    }
                    .offset(y:24)
                }
            }.padding([.leading,.trailing], 8)
                
            //En el caso de que se esté creando una receta aparece diálogo
            if openDialog{
                ZStack{
                    Color.clear
                    GeneralDialog(
                        siFunc: {
                            if nombreValido {
                                //Añado la receta.
                                addNewReceta(nombreReceta)
                            } else {
                                inAddReceta = true
                            }
                        },
                        noFunc: {},
                        siFlag: nombreValido ? $openDialog : Binding.constant(true),
                        noFlag: $openDialog,
                        dialogContent: {
                            Text("Nombre de la receta")
                            TextField("Nombre", text: $nombreReceta)
                                .multilineTextAlignment(.center)
                                .onChange(of: nombreReceta, perform: { newValue in
                                    self.nombreReceta = newValue
                                    //EN el caso de que el nombre no sea vacío y no exista una receta con un nombre igual, el nombre es válido.
                                    if (
                                        !self.nombreReceta.isEmpty
                                        && !self.viewModel.state.recetasActive.contains(where: { receta in
                                            receta.nombre == self.nombreReceta
                                        })
                                        && !self.viewModel.state.recetasInactive.contains(where: { receta in
                                            receta.nombre == self.nombreReceta
                                        })
                                    ){
                                        nombreValido = true
                                    }
                                })
                                .frame(maxWidth: .infinity, maxHeight: 240)
                                .padding([.top], 8)
                                .textFieldStyle(MyTextFieldStyle())
                                //En el caso de que esté vacío aparece el borde de error.
                                .overlay(
                                    !nombreValido ? ErrorBorder(error: $inAddReceta): nil
                                )
                        },
                        siButtonContent: {
                            Text("Añadir")
                        },
                        noButtonContent: {
                            Text("Cancelar")
                        })
                }
                .ignoresSafeArea()
            }
        }
    }
}

