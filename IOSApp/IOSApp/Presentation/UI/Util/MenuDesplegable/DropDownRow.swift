//
//  DropDown.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct DropDownRow: View {
    var option: String
    var onOptionSelected: ((_ option: String) -> Void)?

    //TODO: Poner los tipos de unidades en el centro.
    
    var body: some View {
        Button(action: {
            if let onOptionSelected = self.onOptionSelected {
                onOptionSelected(self.option)
            }
        }) {
            HStack {
                Text(self.option)
                    .font(.system(size: 22))
                    .foregroundColor(Color.black)
                Spacer()
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 5)
        .frame(alignment: .center)
        }
}
