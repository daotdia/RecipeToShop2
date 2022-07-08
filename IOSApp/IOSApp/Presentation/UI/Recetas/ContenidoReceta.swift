//
//  ContenidoReceta.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 5/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ContenidoReceta: View {
    private let receta: Receta
    private let caseUses: UseCases
    
    @ObservedObject var viewModel: RecetaViewModel
    
    init(
        receta: Receta,
        caseUses: UseCases
    ){
        self.receta = receta
        self.caseUses = caseUses
        
        self.viewModel = RecetaViewModel(
            caseUses: caseUses,
            receta: receta
        )
    }
    
    @State var openDialog: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                //Imagen
                ZStack(alignment: .center){
                    Color(white: 0.90)
                        .frame(maxWidth: .infinity, minHeight: 240)
                    Image(systemName: "camera")
                        .resizable()
                        .frame(width: 64, height: 64, alignment: .center)
                        .zIndex(1)
                    //TODO: Permitir añadir imágenes locales.
                }
                
                Text("Ingredientes de la receta")
                    .font(.headline)
                    .padding(4)
                
                //Botón para añadir ingredientes.
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
                
                //Lista de ingredientes.
                //Es una lista vertical de tres columnas fijas
                ScrollView{
                    LazyVGrid(
                        columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                        spacing: 8
                    ){
                        ForEach(
                            viewModel.state.ingredientes, id: \.self.id_alimento
                        ){ alimento in
                            DespensaCard(
                                alimento: alimento,
                                deleteAlimento: { alimento in
                                    viewModel.onTriggerEvent(
                                        event: RecetaEventos.onDeleteIngrediente(
                                            id_ingrediente: alimento.id_alimento as! Int32
                                        )
                                    )
                                }
                            )
                        }
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .offset(y:24)
                    .padding(6)
                }
            }
        }
        .navigationTitle(receta.nombre)
        .sheet(isPresented: $openDialog, content: {
            NewAlimentoDialog(
                caseUses: self.caseUses,
                openDialog: $openDialog,
                addAlimento: { nombre, peso, tipoUnidad -> Void in
                    viewModel.onTriggerEvent(
                        event: RecetaEventos.onaAddIngrediente(
                            nombre: nombre,
                            cantidad: Int32(peso) ?? 1,
                            tipoUnidad: ComprobarTipoUnidad(tipoUnidad: tipoUnidad).returnTipo()
                        )
                    )
                }
            )
        })
    }
}
