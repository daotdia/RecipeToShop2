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
    
    var body: some View {
        VStack {
            Spacer()
            HStack {
                Spacer()
                Button(
                    action: {
                        //Ordena la apertura del dialogo
                        $openDialog.wrappedValue = true
                    },
                    label: {
                        Text("+")
                            .font(.system(.largeTitle))
                            .frame(width: 64, height: 58)
                            .foregroundColor(Color.white)
                            .padding(.bottom, 7)
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
}
