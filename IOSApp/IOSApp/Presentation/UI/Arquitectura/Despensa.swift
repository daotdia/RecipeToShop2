//
//  Despensa.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 9/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct Despensa: View {
    private let caseUses: UseCases
    
    
    //El viewModel al que observar.
    @ObservedObject var viewModel: DespensaViewModel
    
    //Obtengo los casos de uso e instancio el viwmodel con los mismos.
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
        self.viewModel = DespensaViewModel(
            useCases: self.caseUses
        )
    }

    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
    }
}

