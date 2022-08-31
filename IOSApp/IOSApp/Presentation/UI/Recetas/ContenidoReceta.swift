//
//  ContenidoReceta.swift
//  IOSApp
//
//  Created by David Otero Diaz on 5/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ContenidoReceta: View {
    private let receta: Receta
    private let caseUses: UseCases
    private let saveImage: (UIImage) -> Void
    
    @ObservedObject var viewModel: RecetaViewModel
    
    @Binding var onRecetaContent: Bool
    
    init(
        receta: Receta,
        caseUses: UseCases,
        onRecetaContent: Binding<Bool>,
        image: UIImage = UIImage(),
        saveImage: @escaping (UIImage) -> Void
    ){
        self.receta = receta
        self.caseUses = caseUses
        self._onRecetaContent = onRecetaContent
        
        self.viewModel = RecetaViewModel(
            caseUses: caseUses,
            receta: receta
        )
        self.image = image
        self.saveImage = saveImage
        
    }
    
    @State var openDialog: Bool = false
    @State var inEditAlimento: Bool = false
    @State var alimento_actual: Alimento = Alimento(
        id_cestaCompra: nil,
        id_receta: nil,
        id_alimento: nil,
        nombre: "",
        cantidad: 0,
        tipoUnidad: TipoUnidad.gramos,
        active: false
    )
    @State private var image: UIImage
    @State private var showSheet = false
    
    var body: some View {
        ZStack{
            VStack{
                //Imagen
                ZStack(alignment: .center){
                    Image(uiImage: UIImage(contentsOfFile: receta.imagenSource ?? "") ?? UIImage())
                        .resizable()
                        .frame(maxHeight: 256)
                    
                    //Icono de añadir imagen
                    Image(systemName: "camera")
                        .resizable()
                        .frame(width: 24, height: 24, alignment: .topLeading)
                        .opacity(0.75)
                        .foregroundColor(.darkGreen)
                        .offset(y:-24)
                        .onTapGesture(perform: {
                            showSheet = true
                        })
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
                                },
                                onClickAlimento: { alimento in
                                    alimento_actual = alimento
                                    
                                    inEditAlimento = true
                                    openDialog = true
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
        .onDisappear{
            onRecetaContent = false
        }
        .sheet(isPresented: $openDialog, content: {
            NewAlimentoDialog(
                caseUses: self.caseUses,
                alimento: alimento_actual,
                openDialog: $openDialog,
                inEditAlimento: $inEditAlimento,
                addAlimento: { nombre, peso, tipoUnidad -> Void in
                    viewModel.onTriggerEvent(
                        event: RecetaEventos.onaAddIngrediente(
                            nombre: nombre,
                            cantidad: Int32(peso) ?? 1,
                            tipoUnidad: ComprobarTipoUnidad(tipoUnidad: tipoUnidad).returnTipo()
                        )
                    )
                },
                editAlimento: { alimento in
                    viewModel.onTriggerEvent(event: RecetaEventos.onEditIngrediente(
                        id_ingrediente: alimento.id_alimento as! Int32,
                        nombre: alimento.nombre,
                        cantidad: alimento.cantidad,
                        tipoUnidad: alimento.tipoUnidad.name
                    ))
                }
            )
        })
        .sheet(isPresented: $showSheet, content: {
            ImagePicker(
                sourceType: .camera,
                selectedImage: self.$image,
                saveImage: self.saveImage
            )
        })
    }
}
