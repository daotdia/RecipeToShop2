//
//  GeneralDialog.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct GeneralDialog: View {
    
    private let text: String
    
    private let siFunc: () -> Void
    private let noFunc: () -> Void
    
    @Binding var siFlag: Bool
    @Binding var noFlag: Bool
    
    init(
        siFunc: @escaping () -> Void,
        noFunc: @escaping () -> Void,
        text: String,
        siFlag: Binding<Bool>,
        noFlag: Binding<Bool>
    ){
        self.text = text
        self.siFunc = siFunc
        self.noFunc = noFunc
        
        self._siFlag = siFlag
        self._noFlag = noFlag
    }
    
    var body: some View {
        
        Color(white: 0.95)
        WeightedVStack(){ proxy in
            Text(text)
                .font(.headline)
                .padding(4)
                .frame(alignment:.center)
                .weighted(5, proxy: proxy)
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
                    Text("Done")
                        .foregroundColor(.red)
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
                    Text("Cancel")
                    
                }
                Spacer()
            }
            .weighted(3, proxy: proxy)
            .padding(4)
        }
    }
}

