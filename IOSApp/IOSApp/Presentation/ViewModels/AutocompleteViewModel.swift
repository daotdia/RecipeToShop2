//
//  AutocompleteViewModel.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 12/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp
class AutoCompleteViewModel: ObservableObject{
    //Casos de uso necesarios.
    let useCases: UseCases
    
    //Los alimentos disponibles a autocompletar
    @Published var state: AutoCompleteState = AutoCompleteState()
    
    init(
        useCases: UseCases
    ){
        //Casos de uso necesarios.
        self.useCases = useCases
    }
    
    func actualizarAutoComplete(input: String){
        //Obtengo los resultados filtrados del input indicado
        let resultado: [String] = useCases.actualizarAutoComplete.actualizarAutoComplete(query: input)

        //Los actualizo.
        self.state = self.state.doCopy(resultado: resultado)
        print("La lista es: " + self.state.resultado.joined(separator: ","))
    }
    
    func clearResultado(){
        let resultado: [String] = []
        self.state = self.state.doCopy(resultado: resultado)
    }
    
    func comprobarExistencia(input: String) -> Bool{
        if self.state.resultado.contains(input.lowercased()){
            return true
        }
        return false
    }
}
