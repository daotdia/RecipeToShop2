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
    //Estado para guardar el nombre del alimento seleccionado.
    @State var nombreAlimento: String = ""
    
    var body: some View {
        ZStack{
            //Lista de alimentos de la despensa.
            if !viewModel.state.allAlimentos.isEmpty{
                ScrollView{
                    //Es una lista vertical de tres columnas fijas
                    LazyVGrid(
                        columns: [GridItem(.fixed(3))],
                        spacing: 8
                    ){
                        ForEach(
                            viewModel.state.allAlimentos, id: \.self.id_alimento
                        ){ alimento in
                            DespensaCard(alimento: alimento)
                        }
                    }
                }
            }
            //El floating button.
            FloatingButton(openDialog: $openDialog)
                .navigationTitle("Despensa")
            //Dialogo
            if $openDialog.wrappedValue{
                ZStack() {
                    Color(white: 0.9)
                    VStack {
                        //Botones de aceptar y cancelar del dialogo
                        Text("Nombre del alimento").font(.headline)
                        //Nommbre del alimento.
                        AutocompleteTextField(caseUses: caseUses)
                            .ignoresSafeArea()
                        Spacer()
                        Divider()
                        HStack {
                            Spacer()
                            Button(
                                action: {
                                    UIApplication.shared.windows[0].rootViewController?.dismiss(
                                        animated: true, completion: {}
                                    )
                                    $openDialog.wrappedValue = false
                                }
                            ){ Text("Done") }
                            Spacer()
                            Divider()
                            Spacer()
                            Button(
                                action: {
                                    UIApplication.shared.windows[0].rootViewController?.dismiss(
                                        animated: true, completion: {}
                                    )
                                    $openDialog.wrappedValue = false
                                }
                            ){ Text("Cancel") }
                            Spacer()
                    }
                    .padding(0)
                }
                .frame(width: 300, height: 180,alignment: .center)
                .cornerRadius(20)
                .shadow(radius: 20)
            }
//            DespensaCard(alimento: Alimento(
//                id_cestaCompra: -1, id_receta: -1, id_alimento: -1, nombre: "Prueba", cantidad: 1 , tipoUnidad:TipoUnidad.gramos,
//                    active: true
//            ))
        }
    }
}
}
