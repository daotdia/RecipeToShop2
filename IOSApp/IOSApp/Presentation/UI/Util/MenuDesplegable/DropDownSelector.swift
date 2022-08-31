//
//  DropDownSelector.swift
//  IOSApp
//
//  Created by David Otero Diaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct DropDownSelector: View {
    private let options: [String]

    @State private var shouldShowDropdown = false
    
    var placeholder: String
    var onOptionSelected: ((_ option: String) -> Void)?
    private let buttonHeight: CGFloat = 45
    
    @Binding private var selectedOption: String?
    
    init(
        options: [String],
        placeholder: String,
        selectedOption: Binding<String?>
    ){
        self.options = options
        self.placeholder = placeholder
        self._selectedOption = selectedOption
    }
        
    var body: some View {
        Button(action: {
            self.shouldShowDropdown.toggle()
        }) {
            HStack {
                Text(selectedOption == nil ? placeholder : selectedOption!)
                    .font(.system(size: 14))
                    .foregroundColor(selectedOption == nil ? Color.gray: Color.black)
                Spacer()
                Image(systemName: self.shouldShowDropdown ? "arrowtriangle.up.fill" : "arrowtriangle.down.fill")
                    .resizable()
                    .frame(width: 9, height: 5)
                    .font(Font.system(size: 9, weight: .medium))
                    .foregroundColor(Color.black)
                }
            }
            .padding(.horizontal)
            .cornerRadius(5)
            .frame(width: .infinity, height: self.buttonHeight)
            .overlay(
                RoundedRectangle(cornerRadius: 5)
                    .stroke(Color.gray, lineWidth: 1)
            )
            .overlay(
                VStack {
                    if self.shouldShowDropdown {
                        Spacer(minLength: buttonHeight + 10)
                        DropDown(options: self.options, onOptionSelected: { option in
                            shouldShowDropdown = false
                            selectedOption = option
                            self.onOptionSelected?(option)
                        })
                    }
                }, alignment: .topLeading
            )
            .background(
                RoundedRectangle(cornerRadius: 5).fill(Color.white)
            )
    }
}

