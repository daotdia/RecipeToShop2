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
    let width_card: CGFloat = 100
    let height_card: CGFloat = 86
    
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
                    .lineLimit(1)
                    .scaledToFill()
                    .minimumScaleFactor(0.6)
            }
            .background(Color.green)
            .clipShape(Capsule())
            .frame(minWidth: width_card/5, maxWidth: width_card, minHeight: (height_card/5), maxHeight:(height_card*2)/5)
            
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
        .frame(minWidth: width_card/5, maxWidth: width_card, minHeight: height_card, maxHeight: height_card*2)
        .padding(6)
    }
}

