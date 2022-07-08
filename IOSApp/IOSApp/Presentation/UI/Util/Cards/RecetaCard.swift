//
//  RecetaCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 29/6/22.
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
    private let id_listaRecetas: Int
    private let caseUses: UseCases
    
    @Binding var isBusqueda: Bool
    
    init(
        receta: Receta,
        isBusqueda: Binding<Bool>,
        eliminarCard: @escaping (Receta) -> Void,
        activarReceta: @escaping (Receta, Bool) -> Void,
        addReceta: @escaping (Receta) -> Void,
        addRecetaExistente: @escaping (Receta) -> Void,
        id_listaRecetas: Int,
        caseUses: UseCases
    ){
        self.receta = receta
        self._isBusqueda = isBusqueda
        self.eliminarCard = eliminarCard
        self.activarReceta = activarReceta
        self.addReceta = addReceta
        self.id_listaRecetas = id_listaRecetas
        self.addRecetaExistente = addRecetaExistente
        self.caseUses = caseUses
    }
    
    @State var openDeleteReceta: Bool = false
    @State var onRecetaContent: Bool = false
    
    var body: some View {
        VStack{
            ZStack{
                //Si no estamos en la busqueda y la receta no es activa el fondo es grisaceo.
                Color(white: !isBusqueda && (!receta.active || receta.id_cestaCompra != id_listaRecetas) ?
                      0.85 : 1)
                //Icono de añadir imagen
                Image(systemName: "camera")
                    .resizable()
                    .frame(width: 24, height: 24, alignment: .center)
                    .opacity(0.75)
                    .foregroundColor(.gray)
                    .offset(y:-64)
                
                //Nombre de la receta
                Text(receta.nombre)
                    .font(.headline)
                    .frame(alignment: .center)
                
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
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding([.leading, .trailing], 8)

                //Símbolo de eliminar receta
                if openDeleteReceta {
                    ZStack(alignment: .topTrailing){
                        //Necesario para hacer el frame igual de grande que el padre.
                        Color.clear
                        Image(systemName: "trash.fill")
                            .foregroundColor(.red)
                            .onTapGesture {
                                //Si la receta está activa se desactiva.
                                if receta.active{
                                    activarReceta(receta, false)
                                } else {
                                    //Si ya está desactivada se elimina
                                    eliminarCard(receta)
                                }
                            }
                    }
                }
                
                //La navegación al contenido de la receta
                if onRecetaContent{
                    ZStack{
                        NavigationLink(
                            destination: ContenidoReceta(
                                receta: receta,
                                caseUses: caseUses
                            ),
                            isActive: $onRecetaContent
                        ){
                                EmptyView()
                            }
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
                
            }
            .padding(0.1)
            .onTapGesture {
                //Escondo el símbolo de basura.
                if openDeleteReceta{
                    openDeleteReceta = false
                }
                
                //Si la card se encuentra en la lista de recetas y está desactivada; se activa.
                if !isBusqueda{
                    //En el caso de que la receta no pertenezca a la lista se añade la receta a la lista (activada predeterminadamente).
                    if(receta.id_cestaCompra != id_listaRecetas){
                        addRecetaExistente(self.receta)
                    } else if !receta.active{
                        //En el caso de que ya pertenezca a la lista se activa
                        activarReceta(self.receta, true)
                    } else if receta.active{
                        onRecetaContent = true
                    }
                    
                } else {
                    //Añado la receta completa a la lista de recetas
                    addReceta(self.receta)
                }
                
            }
            .onLongPressGesture(perform: {
                if !isBusqueda && (receta.id_cestaCompra == id_listaRecetas || !receta.active) {
                    openDeleteReceta = true
                }
            })
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
