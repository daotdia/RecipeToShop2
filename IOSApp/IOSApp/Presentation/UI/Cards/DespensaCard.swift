//
//  DespensaCard.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct DespensaCard: View {
    let width_card: CGFloat = 186
    let height_card: CGFloat = 64
    
    private let alimento: Alimento
    
    init(
        alimento: Alimento
    ){
        self.alimento = alimento
    }
    
    var body: some View {
        //La card
        VStack(alignment: .center){
            //El titulo del alimento de fondo verde redondeado.
            HStack(alignment: .center){
                //El nombre del alimento en blanco
                Text(alimento.nombre)
                    .foregroundColor(Color.white)
                    .padding()
            }
            .background(Color.green)
            .clipShape(Capsule())
            .frame(width: width_card, height: height_card/4)
            
            Spacer()
            
            //La cantidad y tipo de unidad del alimento.
            HStack(alignment: .center){
                Text(
                    String(alimento.cantidad) +
                     "    " +
                     AbrevTipoUnidad(tipoUnidad: alimento.tipoUnidad)
                    .parseAbrev()
                )
                    .foregroundColor(Color.gray)
                    .padding()
            }
        }
        .frame(width: width_card, height: height_card)
        .padding(6)
    }
}

