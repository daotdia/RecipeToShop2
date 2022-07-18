//
//  DropDown.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct DropDown: View {
    var options: [String]
    var onOptionSelected: ((_ option: String) -> Void)?
    var body: some View {
        ScrollView {
            VStack(alignment: .center, spacing: 0) {
                ForEach(self.options, id: \.self) { option in
                    DropDownRow(option: option, onOptionSelected: self.onOptionSelected)
                        .frame(alignment: .center)
                    Divider()
                }
            }
        }
        .frame(height: 100, alignment: .center)
        .padding(.vertical, 5)
        .background(Color.white)
        .cornerRadius(5)
        .overlay(
            RoundedRectangle(cornerRadius: 5)
                .stroke(Color.gray, lineWidth: 1)
        )
    }
}
