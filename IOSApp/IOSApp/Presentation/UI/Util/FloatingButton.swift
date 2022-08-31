//
//  FloatingButton.swift
//  IOSApp
//
//  Created by David Otero Diaz on 11/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FloatingButton: View {
    
  
    @Binding var openDialog: Bool
    @State var simboloSys: String
    
    private let finalizarCompra: () -> Void
    private let determinarListaCompra: () -> Void
    
    private let isCalcular: Bool
    private let isFinish: Bool
    
    @Binding var tabSelection: Int
    
    init(
        openDialog: Binding<Bool>,
        simbolsys: String,
        isCalcular: Bool = false,
        isFinish: Bool = false,
        tabSelection: Binding<Int> = Binding.constant(-1),
        finalizarCompra: @escaping () -> Void = { },
        determinarListaCompra: @escaping () -> Void = {}
    ){
        self._openDialog = openDialog
        self.simboloSys = simbolsys
        self.isCalcular = isCalcular
        self.isFinish = isFinish
        self._tabSelection = tabSelection
        self.finalizarCompra = finalizarCompra
        self.determinarListaCompra = determinarListaCompra
    }
    
    var body: some View {
        ZStack(alignment: .bottomTrailing){
            Color.clear
            Button(
                action: {
                    if isCalcular  {
                        if tabSelection != -1 {
                            determinarListaCompra()
                            print("Llego a intentar cambiar de pantalla")
                            tabSelection = 3
                        }
                    }
                    if isFinish {
                        if tabSelection != -1 {
                            finalizarCompra()
                            tabSelection = 1
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

