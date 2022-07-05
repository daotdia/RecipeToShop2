//
//  ContenidoReceta.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 5/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ContenidoReceta: View {
    private let receta: Receta
    
    init(
        receta: Receta
    ){
        self.receta = receta
    }
    
    var body: some View {
        ZStack{
            VStack{
                
            }
        }
        .navigationTitle(receta.nombre)
    }
}
