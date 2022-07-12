//
//  CestaCompra.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

@available(iOS 15.0, *)
struct ListaCompra: View {
    
    private let caseUses: UseCases
    
    @Binding var id_listaCompra: Int
    
    @ObservedObject var viewModel: ListaCompraViewModel
    
    init (
        caseUses: UseCases,
        id_listaCompra: Binding<Int>
    ){
        self.caseUses =  caseUses
        self._id_listaCompra = id_listaCompra
        
        self.viewModel = ListaCompraViewModel(
            caseUses: self.caseUses,
            id_listaRecetas: self._id_listaCompra.wrappedValue
        )
    }
    
    var body: some View {
        ZStack{
            VStack{
                //La pila de la pantalla
                ScrollView{
                    LazyVStack{
                        //Recuadro de cada supermercado. TODO: Pendiente hacer para varios.
                        ForEach(1..<2){ _ in
                            //Imagen del supermercado
                            AsyncImage(
                                url: URL(
                                    string: "https://vams-loyalia-storage.s3.eu-west-1.amazonaws.com/images/deals/_720x495/carrefour.jpg"
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
                            
                            //Lista de productos del supermercado. TODO: falta por añadir los productos para cada supermercado.
                            LazyVGrid(
                                columns: Array(repeating: .init(.adaptive(minimum: 100)), count: 3),
                                spacing: 8
                            ){
                                ForEach(self.viewModel.state.listaProductos, id: \.self.query){ producto in
                                    ProductoCard(producto: producto)
                                        .frame(maxWidth: 150, maxHeight: 150)
                                }
                            }
                        }
                        .padding()
                    }
                }
                .frame(minHeight: 400)
                //Aquí se encuentra el peso total y el precio total de la lista de la compra.
                HStack{
                    //Peso total de la compra.
                    Text(
                        viewModel.state.peso_total > 1000 ?
                        String(
                            String(Float(viewModel.state.peso_total/1000)) + " Kg"
                        )
                        :
                        String(
                            String(Float(viewModel.state.peso_total/1000)) + " Gr"
                        )
                    )
                        .foregroundColor(Color.green)
                    
                    Spacer()
                    
                    //Precio total de la compra.
                    Text(String(viewModel.state.precio_total) + " €")
                        .foregroundColor(Color.green)
                }
                .padding([.leading, .trailing], 18)
                
                //Aquí se encuentran todos los alimentos no encontrados.
                Text("Alimentos no encontrados")
                    .foregroundColor(Color.green)
                
                //Lista con todos los alimentos no encontrados.
                HStack{
                    ScrollView{
                        LazyHStack{
                            ForEach(
                                viewModel.state.alimentos_no_encontrados,
                                id: \.self.id_alimento
                            ){ alimento in
                                //Cada alimento no encontrado.
                                DespensaCard(
                                    alimento: alimento, deleteAlimento: { alimento in }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


