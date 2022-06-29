//
//  GeneralDialog.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

//Es un poderoso diálogo general, en el que se puede customizar los pesos de las vistas de contenido y botnes, el contenido del diálogo, el contenido de cada botón, las flags al apretar un botón u otro y las funciones a llamar en caso de que se aprete un botón u otro.
struct GeneralDialog<Content:View, siContent:View, noContent:View> : View {
    
    private let siFunc: () -> Void
    private let noFunc: () -> Void
    
    //private let weights: (contentWeight: CGFloat, buttonWeight: CGFloat)
    
    //El contenido del dialogo
    @ViewBuilder var dialogContent: Content
    @ViewBuilder var siButtonContent: siContent
    @ViewBuilder var noButtonContent: noContent
    
    @Binding var siFlag: Bool
    @Binding var noFlag: Bool
    
    @inlinable init(
        //weights: (contentWeight: CGFloat, buttonWeight: CGFloat),
        siFunc: @escaping () -> Void,
        noFunc: @escaping () -> Void,
        siFlag: Binding<Bool>,
        noFlag: Binding<Bool>,
        @ViewBuilder dialogContent: @escaping () -> Content,
        @ViewBuilder siButtonContent: @escaping () -> siContent,
        @ViewBuilder noButtonContent: @escaping () -> noContent
    ){
        //self.weights = weights
        
        self.siFunc = siFunc
        self.noFunc = noFunc
        
        self._siFlag = siFlag
        self._noFlag = noFlag
        
        self.dialogContent = dialogContent()
        self.siButtonContent = siButtonContent()
        self.noButtonContent = noButtonContent()
    }
    
    var body: some View {
        Color(white: 0.95)
        VStack(){
            dialogContent
                .padding(4)
                .frame(alignment:.center)
                //.weighted(weights.contentWeight, proxy: proxy)
            Spacer()
            Divider()
            //Botones de sí o no.
            HStack{
                Spacer()
                //Botón de sí.
                Button(action: {
                    //En el caso de que se aprete a sí se realiza la función de SI.
                    siFunc()
                    //Se invierte el flag booleano de sí.
                    siFlag = !siFlag
                }){
                    siButtonContent
                }
                Spacer()
                Divider()
                Spacer()
                //Botón de no.
                Button(action: {
                    //la función del no
                    noFunc()
                    //Se invierte el flag booleano de no.
                    noFlag = !noFlag
                }){
                    noButtonContent
                }
                Spacer()
            }
            //.weighted(weights.buttonWeight, proxy: proxy)
            .padding(4)
            .frame(maxHeight: 46)
        }
        .padding(6)
    }
}

