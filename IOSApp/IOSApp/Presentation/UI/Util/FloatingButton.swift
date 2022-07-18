//
//  FloatingButton.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FloatingButton: View {
    
  
    @Binding var openDialog: Bool
    @State var simboloSys: String
    
    private let isCalcular: Bool
    
    @Binding var tabSelection: Int
    
    init(
        openDialog: Binding<Bool>,
        simbolsys: String,
        isCalcular: Bool = false,
        tabSelection: Binding<Int> = Binding.constant(-1)
    ){
        self._openDialog = openDialog
        self.simboloSys = simbolsys
        self.isCalcular = isCalcular
        self._tabSelection = tabSelection
    }
    
    var body: some View {
        ZStack(alignment: .bottomTrailing){
            Color.clear
            Button(
                action: {
                    if isCalcular  {
                        if tabSelection != -1 {
                            print("Llego a intentar cambiar de pantalla")
                            tabSelection = 3
                        }
                    }
                    //Ordena la apertura del dialogo
                    $openDialog.wrappedValue = true
                },
                label: {
                    Image(systemName: simboloSys)
                        .font(.system(.title))
                        .frame(width: 64, height: 64)
                        .foregroundColor(Color.white)
                    }
            )
            .background(Color.green)
            .cornerRadius(38.5)
            .padding()
            .shadow(
                color: Color.black.opacity(0.3),
                radius: 3,
                x: 3,
                y: 3
            )
            
        }
    }
}

