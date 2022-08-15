//
//  ProductCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

@available(iOS 15.0, *)
struct ProductoCard: View {
    
    private let producto: Productos.Producto
    private let onClickProducto: (Productos.Producto) -> Void
    
    init(
        producto: Productos.Producto,
        onClickProducto: @escaping (Productos.Producto) -> Void
    ){
        self.producto = producto
        self.onClickProducto = onClickProducto
    }
    
    var body: some View {
        VStack{
            //Imagen del supermercado
            ZStack{
                VStack{
                    AsyncImage(
                        url: URL(
                            string:
                                producto.imagen_src
                        )
                    ){ phase in
                        switch phase {
                            case .empty:
                                ProgressView()
                            case .success(let image):
                                image.resizable()
                                    .aspectRatio(contentMode: .fill)
                                    .frame(maxWidth: 100, maxHeight: 90)
                            case .failure:
                                Image(systemName: "photo")
                                    .frame(width: 84, height: 84, alignment: .center)
                            @unknown default:
                                EmptyView()
                        }
                    }
                    .shadow(color: Color.gray, radius: 2)
                    .cornerRadius(14)
                    
                    //Las unidades de producto centradas.
                    Text(String(producto.cantidad) + " Ud")
                    
                    //La descripción del producto
                    Text(producto.nombre)
                    
                    //Peso y precio
                    HStack{
                        //Peso total del producto
                        Text(
                            producto.tipoUnidad?.name == "GRAMOS" ?
                                String(producto.peso/1000) + " Kg"
                            :
                                String(producto.peso/1000) + " L"
                        )
                            .foregroundColor(Color.green)
                        
                        //Para poner el peso al inicio y el precio al final.
                        Spacer()
                        
                        //Precio total del producto
                        Text(String(Float(producto.precio_numero * Float(producto.cantidad))) + " €")
                            .foregroundColor(Color.green)
                    }
                }
                if(!producto.active){
                    Image(systemName: "cart.circle.fill")
                        .resizable()
                        .frame(maxWidth: 75, maxHeight: 75)
                        .foregroundColor(.darkGreen)
                    Color(white: 0.75)
                    .frame(maxWidth: 150, maxHeight: 150)
                    .opacity(0.5)
                }
            }
        }
        .onTapGesture(perform: {
            onClickProducto(self.producto)
        })
    }
}

