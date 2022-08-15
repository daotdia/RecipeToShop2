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
    private let deleteAlimento: (Alimento) -> Void
    private var onClickAlimento: (Alimento) -> Void = { alimento in }
    
    init(
        alimento: Alimento,
        deleteAlimento: @escaping (Alimento) -> Void,
        onClickAlimento: @escaping (Alimento) -> Void
    ){
        self.deleteAlimento = deleteAlimento
        self.alimento = alimento
        self.onClickAlimento = onClickAlimento
    }
    
    @State var isLongPressed: Bool = false
    
    var body: some View {
        //La card
        VStack(alignment: .center){
            //El titulo del alimento de fondo verde redondeado.
            HStack(){
                //El nombre del alimento en blanco
                Text(alimento.nombre)
                    .foregroundColor(Color.white)
                    .padding()
                    .lineLimit(3)
                    //.scaledToFill()
                    .minimumScaleFactor(0.6)
                    .multilineTextAlignment(.center)
                //Botón de eliminar en el caso de que se haya presionado long.
                if $isLongPressed.wrappedValue {
                    Image(systemName: "trash.fill")
                        .frame(width: 11, height: 11)
                        .padding([.trailing],6)
                        .foregroundColor(.red)
                        .onTapGesture {
                            deleteAlimento(alimento)
                        }
                }
            }
            .background(Color.green)
            .clipShape(Capsule())
            .frame(minWidth: width_card/5, maxWidth: width_card, minHeight: (height_card/5), maxHeight:(height_card*8)/5)
            
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
        .onTapGesture(perform: {
            if(!isLongPressed){
                onClickAlimento(alimento)
            }
            $isLongPressed.wrappedValue = false
        })
        .onLongPressGesture(perform: {
            isLongPressed = true
        })
    }
}

