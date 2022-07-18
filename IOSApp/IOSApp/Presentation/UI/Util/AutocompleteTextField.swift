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
    
    @Binding var nombre: String
    @Binding var nombreValido: Bool
    
    init(
        caseUses: UseCases,
        nombre: Binding<String>,
        nombreValido: Binding<Bool>
    ){
        self.caseUses = caseUses
        self._nombre = nombre
        self._nombreValido = nombreValido
        self.input = ""
        
        self.autocompleteViewModel = AutoCompleteViewModel(useCases: self.caseUses)
    }
    
    @State var input: String

    var body: some View {
        //El textfield donde se indica el texto a autocompletar.
        TextField("Nombre", text: $input)
            .textFieldStyle(.roundedBorder)
            .padding(4)
            .onChange(of: input) { newValue in
                autocompleteViewModel.actualizarAutoComplete(input: $input.wrappedValue)
                if !autocompleteViewModel.comprobarExistencia(input: $input.wrappedValue){
                    self.$nombre.wrappedValue = $input.wrappedValue
                    print("El alimento " + $input.wrappedValue + " no existe")
                    nombreValido = false
                } else {
                    self.$nombre.wrappedValue = $input.wrappedValue
                    autocompleteViewModel.clearResultado()
                    nombreValido = true
                }
            }.textFieldStyle(MyTextFieldStyle())
        if !autocompleteViewModel.state.resultado.isEmpty{
            //Lista de opciones según se haya indicado en el field anterior.
            List(autocompleteViewModel.state.resultado, id: \.self) { suggestion in
            Text(suggestion)
                .onTapGesture {
                    input = suggestion
                    //self.nombre= $inputeAlimento.wrappedValue
                    autocompleteViewModel.clearResultado()
                    self.$nombre.wrappedValue = $input.wrappedValue
                    nombreValido = true
                }
            }// .frame(minHeight: 164)
            .listStyle(.inset)
            .frame(
                width: UIScreen.main.bounds.width - 80,
                height: CGFloat(autocompleteViewModel.state.resultado.count * 45)
            )
            .cornerRadius(8)
        }
    }
}



