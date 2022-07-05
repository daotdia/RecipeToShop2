//
//  SearchBar.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SearchBar: View {
    
    private let diccionario: [String]
    private let updateSearch: (String) -> Void
        
    init(
        diccionario: [String],
        updateSearch: @escaping (String) -> Void
    ){
        self.diccionario = diccionario
        self.updateSearch = updateSearch
    }
    
    @State var input: String = ""
    
    var body: some View {
        HStack{
            ZStack{
                TextField("Nombre Receta...", text: $input)
                    .textFieldStyle(.roundedBorder)
                    .padding(4)
                    .onChange(of: input) { newValue in
                        //Actualizo la búsqueda
                        updateSearch($input.wrappedValue)
                    }
                    .textFieldStyle(MyTextFieldStyle())
            }
            .overlay(
                //Imagen que es un icono de lupa si no hay nada buscado o una X si ya hay algo buscado.
                Image(systemName: $input.wrappedValue.isEmpty ? "magnifyingglass" : "xmark.circle")
                    .onTapGesture {
                        //En el caso de que no esté vacía y se clica X, se vacía la búsqueda.
                        if !$input.wrappedValue.isEmpty{
                            $input.wrappedValue = ""
                        }
                    },
                alignment: .trailing
            )
        }
    }
}


