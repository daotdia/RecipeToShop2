//
//  AutocompleteTextField.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 12/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct AutocompleteTextField: View {
    private let caseUses: UseCases
    
    @ObservedObject var autocompleteViewModel: AutoCompleteViewModel
    
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
        self.autocompleteViewModel = AutoCompleteViewModel(useCases: self.caseUses)
    }
    
    @State var input: String = ""

    var body: some View {
        //El textfield donde se indica el texto a autocompletar.
        VStack {
            TextField("", text: $input)
                .textFieldStyle(.roundedBorder)
                .padding()
                .onChange(of: input) { newValue in
                    autocompleteViewModel.actualizarAutoComplete(input: $input.wrappedValue)
                }
        }
        //Lista de opciones según se haya indicado en el field anterior.
        List(autocompleteViewModel.state.resultado, id: \.self) { suggestion in
            VStack {
                Text(suggestion)
            }
            .onTapGesture {
                input = suggestion
            }
        }
    }
}


