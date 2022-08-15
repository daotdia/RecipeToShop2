//
//  SupermercadoCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

@available(iOS 15.0, *)
struct SupermercadoCard: View {
    
    private let productos: [Productos.Producto]
    private let onClickProducto: (Productos.Producto) -> Void
    private let imagen_super: String
    private let peso_super: Float
    private let precio_super: Float
    
    init(
        productos: [Productos.Producto],
        imagen_super: String,
        peso_super: Float,
        precio_super: Float,
        onClickProducto: @escaping (Productos.Producto) -> Void
    ){
        self.productos = productos
        self.imagen_super = imagen_super
        self.precio_super = precio_super
        self.peso_super = peso_super
        self.onClickProducto = onClickProducto
    }
    
    var body: some View {
        VStack{
            //Imagen del supermercado
            AsyncImage(
                url: URL(
                    string: imagen_super
                )
            ){ phase in
                switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image.resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(maxWidth: .infinity, maxHeight: 80)
                    case .failure:
                        Image(systemName: "photo")
                            .frame(width: 84, height: 84, alignment: .center)
                    @unknown default:
                        EmptyView()
                }
            }
            
            //Lista de productos del supermercado.
            LazyVGrid(
                columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                spacing: 8
            ){
                ForEach(self.productos, id: \.self.query){ producto in
                    ProductoCard(
                        producto: producto,
                        onClickProducto: self.onClickProducto
                    )
                        .frame(maxWidth: 150, maxHeight: 150)
                        .offset(y: 24)
                }
            }
            
            //Aquí se encuentra el peso total y el precio total del supermercado.
            HStack{
                //Peso total de la compra.
                Text(
                    self.peso_super > 1000 ?
                    String(
                        String(Float(peso_super/1000)) + " Kg"
                    )
                    :
                    String(
                        String(Float(peso_super)) + " Gr"
                    )
                )
                    .foregroundColor(Color.green)
                
                Spacer()
                
                //Precio total de la compra.
                Text(String(precio_super) + " €")
                    .foregroundColor(Color.green)
            }
            .padding([.leading, .trailing], 8)
            .offset(y: 24)
        }
    }
}

