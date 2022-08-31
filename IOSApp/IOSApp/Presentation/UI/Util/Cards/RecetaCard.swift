//
//  RecetaCard.swift
//  IOSApp
//
//  Created by David Otero Diaz on 29/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//
import SwiftUI
import sharedApp

import SwiftUI

struct RecetaCard: View {
    private let receta: Receta
    private let eliminarCard: (Receta) -> Void
    private let activarReceta: (Receta, Bool) -> Void
    private let addReceta: (Receta) -> Void
    private let addRecetaExistente: (Receta) -> Void
    private let saveImage: (UIImage) -> Void
    private let id_listaRecetas: Int
    private let caseUses: UseCases
    private let sumarReceta: (Receta) -> Void
    private let restarReceta: (Receta) -> Void
    
    @Binding var isBusqueda: Bool
    @Binding var onRecetaContent: Bool

    
    init(
        receta: Receta,
        isBusqueda: Binding<Bool>,
        eliminarCard: @escaping (Receta) -> Void,
        activarReceta: @escaping (Receta, Bool) -> Void,
        addReceta: @escaping (Receta) -> Void,
        addRecetaExistente: @escaping (Receta) -> Void,
        saveImage: @escaping (UIImage) -> Void,
        id_listaRecetas: Int,
        caseUses: UseCases,
        onRecetaContent: Binding<Bool>,
        sumarReceta: @escaping (Receta) -> Void,
        restarRecera: @escaping (Receta) -> Void
    ){
        self.receta = receta
        self._isBusqueda = isBusqueda
        self.eliminarCard = eliminarCard
        self.activarReceta = activarReceta
        self.addReceta = addReceta
        self.id_listaRecetas = id_listaRecetas
        self.addRecetaExistente = addRecetaExistente
        self.saveImage = saveImage
        self.caseUses = caseUses
        self._onRecetaContent = onRecetaContent
        self.sumarReceta = sumarReceta
        self.restarReceta = restarRecera
    }
    
    @State var openDeleteReceta: Bool = false
  
    
    var body: some View {
        VStack{
            ZStack{
                //Si no estamos en la busqueda y la receta no es activa el fondo es grisaceo.
                Color(white: 0.85).opacity(!isBusqueda && (!receta.active || receta.id_cestaCompra != id_listaRecetas) ?
                                           1 : 0)
                //Imagen de la receta
                Image(
                    uiImage: UIImage(contentsOfFile: receta.imagenSource ?? "")?.image(
                        alpha: !isBusqueda && (!receta.active || receta.id_cestaCompra != id_listaRecetas) ?
                            0.5 : 0.75
                    )
                    ?? UIImage()
                )
                .resizable()
                .frame(maxHeight: 100)
                
                
                if(!isBusqueda){
                    //Nombre de la receta
                    NavigationLink(
                        receta.nombre,
                        destination: {
                            ContenidoReceta(
                            receta: receta,
                            caseUses: caseUses,
                            onRecetaContent: $onRecetaContent,
                            saveImage: saveImage
                            )
                            .navigationTitle(receta.nombre)
                        }
                    )
                    .foregroundColor(.black)
                    .zIndex(5)
                } else {
                    Text(receta.nombre)
                        .font(.headline)
                        .frame(alignment: .center)
                }
                
                
                //Componente para controlar la cantidad de recetas.
                ZStack(alignment: .bottom){
                    Color.clear
                    HStack{
                        //Botón de reducir en uno la cantidad de la receta.
                        ZStack(alignment: .leading){
                            Image(systemName: "plus.circle")
                                .resizable()
                                .frame(width: 24, height: 24, alignment: .center)
                                .padding(4)
                                .onTapGesture(perform: {
                                    sumarReceta(receta)
                                })
                                .zIndex(5)
                        }
                        Spacer()
                        //La cantidad de receta.
                        Text(String(receta.cantidad))
                            .multilineTextAlignment(.center)
                        Spacer()
                        //Botón de añadir en uno la cantidad de receta
                        ZStack(alignment: .trailing){
                            Image(systemName: "minus.circle")
                                .resizable()
                                .frame(width: 24, height: 24, alignment: .center)
                                .padding(4)
                                .onTapGesture(perform: {
                                    restarReceta(receta)
                                })
                                .zIndex(5)
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding([.leading, .trailing], 8)

                //Símbolo de eliminar receta
                ZStack(alignment: .topTrailing){
                    //Necesario para hacer el frame igual de grande que el padre.
                    Color.clear
                    Image(systemName: receta.active ? "minus.circle" : "plus.circle")
                        .foregroundColor(.darkGreen)
                        .onTapGesture {
                            //Si la receta está activa se desactiva.
                            if receta.active{
                                activarReceta(receta, false)
                            } else {
                                //Si ya está desactivada se activa
                                activarReceta(receta, true)
                            }
                        }
                        .padding(6)
                }
                if(!receta.active){
                    ZStack(alignment: .topLeading){
                        Color.clear
                        Image(systemName: "trash.fill")
                            .foregroundColor(.red)
                            .onTapGesture {
                                //Si la receta está activa se desactiva.
                                eliminarCard(receta)
                            }
                            .padding(6)
                    }
                }
                
                    
                
                
//                //La navegación al contenido de la receta
//                ZStack{
//                    NavigationLink(
//                        destination: ContenidoReceta(
//                            receta: receta,
//                            caseUses: caseUses,
//                            onRecetaContent: $onRecetaContent,
//                            saveImage: saveImage
//                        ),
//                        isActive: $onRecetaContent
//                    ){
//                        EmptyView()
//                    }
//                }
//                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
            .padding(0.1)
            .onTapGesture {
                
                
//                //Si la card se encuentra en la lista de recetas y está desactivada; se activa.
//                if !isBusqueda{
//                    //En el caso de que la receta no pertenezca a la lista se añade la receta a la lista (activada predeterminadamente).
//                    if(receta.id_cestaCompra != id_listaRecetas){
//                        addRecetaExistente(self.receta)
//                    } else if !receta.active{
//                        //En el caso de que ya pertenezca a la lista se activa
//                        activarReceta(self.receta, true)
//                    } else if receta.active{
//                        onRecetaContent = true
//                    }
//
//                } else {
//                    //Añado la receta completa a la lista de recetas
//                    addReceta(self.receta)
//                }
                
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
