//
//  AlimentosList.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 4/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct AlimentosList: View {
    private let addAlimento: (String, String, String) -> Void
    private let removeAlimento: (Alimento) -> Void
    private let caseUses: UseCases
    
    @ObservedObject var viewModel: ListaRecetasViewModel
    
    init(
        viewModel: ListaRecetasViewModel,
        addAlimento: @escaping (String, String, String) -> Void,
        removeAlimento: @escaping (Alimento) -> Void,
        caseUses: UseCases
    ){
        self.viewModel = viewModel
        self.addAlimento = addAlimento
        self.removeAlimento = removeAlimento
        self.caseUses = caseUses
    }
    
    @State var openDialog: Bool = false
    @State var inEditAlimento: Bool = false
    @State var alimento_actual: Alimento = Alimento(
        id_cestaCompra: nil,
        id_receta: nil,
        id_alimento: nil,
        nombre: "",
        cantidad: 0,
        tipoUnidad: TipoUnidad.gramos,
        active: false
    )
    
    var body: some View {
        Text("Alimentos sueltos")
        ScrollView{
            LazyHStack(
                alignment: .center,
                spacing: 8,
                pinnedViews: [.sectionHeaders]
            ){
                //El circulo fijo para añadir nuevos alimentos
                Section(header: Image(systemName: "plus.circle.fill")
                    .resizable()
                    .frame(width: 64, height: 64, alignment: .center)
                    .opacity(0.85)
                    .shadow(
                        color: Color.black.opacity(0.3),
                        radius: 3,
                        x: 3,
                        y: 3
                    )
                    .onTapGesture {
                        openDialog = true
                    }
                    .offset(y: 12)
                ){
                    ForEach(viewModel.state.allAlimentos, id: \.self.id_alimento){ alimento in
                        //La card de cada alimento:
                        DespensaCard(
                            alimento: alimento,
                            deleteAlimento: { alimento in
                                removeAlimento(alimento)
                            },
                           onClickAlimento: { alimento in
                                //Seteo le alimento actual de la despensa clicado
                                alimento_actual = alimento
                                
                                //Abro el diálogo del nuevo alimento setado con la información del alimento actual
                                inEditAlimento = true
                                openDialog = true
                                
                            }
                        )
                    }
                    .offset(y: 38)
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: 115)
        .padding([.leading,.trailing], 8)
        .sheet(isPresented: $openDialog, content: {
            NewAlimentoDialog(
                caseUses: self.caseUses,
                alimento: alimento_actual,
                openDialog: $openDialog,
                inEditAlimento: $inEditAlimento,
                addAlimento: addAlimento,
                editAlimento: { alimento in
                    viewModel.onTriggerEvent(event: CestaCompraEventos.onAlimentoClick(
                        alimento: alimento, active: alimento.active
                    ))
                }
            )
        })
    }
}
