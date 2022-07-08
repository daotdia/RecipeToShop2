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
            //La pila de la pantalla
            ScrollView{
                LazyVStack{
                    //Recuadro de cada supermercado. TODO: Pendiente hacer para varios.
                    ForEach(1..<1){ _ in
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
                                        .aspectRatio(contentMode: .fit)
                                        .frame(maxWidth: 300, maxHeight: 180)
                                case .failure:
                                    Image(systemName: "photo")
                                        .frame(width: 84, height: 84, alignment: .center)
                                @unknown default:
                                    EmptyView()
                            }
                        }
                        
                        //Lista de productos del supermercado
                        
                    }
                    .shadow(color: Color.green, radius: 4)
                    .cornerRadius(14)
                }
            }
            VStack{
                
                
            }
        }
    }
}


