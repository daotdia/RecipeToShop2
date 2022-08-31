//
//  CestaCompra.swift
//  IOSApp
//
//  Created by David Otero Diaz on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

@available(iOS 15.0, *)
struct ListaCompra: View {
    
    private let caseUses: UseCases
    
    @Binding var id_listaCompra: Int
    @Binding var tabSelection: Int
    
    @ObservedObject var viewModel: ListaCompraViewModel
    
    init (
        caseUses: UseCases,
        id_listaCompra: Binding<Int>,
        tabSelection: Binding<Int>
    ){
        self.caseUses =  caseUses
        self._id_listaCompra = id_listaCompra
        self._tabSelection = tabSelection
        
        self.viewModel = ListaCompraViewModel(
            caseUses: self.caseUses,
            id_listaRecetas: self._id_listaCompra.wrappedValue
        )
    }
    
    @State var openFilters: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                //La pila de la pantalla
                ScrollView{
                    //Menu desplegable para filtrar por peso o por precio.
                    ZStack(alignment: .trailing){
                        Button(action: {
                            if openFilters == false{
                                openFilters = true
                            }
                            else{
                                openFilters = true
                            }
                        }){
                            //Menú desplegable con las opciones.
                            if openFilters{
                                VStack{
                                    ForEach(
                                        ParseKoltinArrayToArray().parseFilters(
                                            kotlin_array: FilterEnum.values()
                                        ),
                                        id: \.self)
                                    { item in
                                        FilterRow(filter: item)
                                            .onTapGesture(perform: {
                                                openFilters = false
                                                
                                                viewModel.onTriggerEvent(
                                                    event: ListaCompraEvents.onCLickFilter(
                                                        filter_nombre: item.name
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            else {
                                Image(systemName: "ellipsis")
                                    .font(Font.system(size: 18, weight: .medium))
                                    .foregroundColor(Color.black)
                            }
                        }
                    }
                    .frame(maxWidth: .infinity)
                    .zIndex(5)
                    LazyVStack{
                        //Recuadro de cada supermercado.
                        ForEach(
                            viewModel.state.supermercados.allObjects as! [SupermercadosEnum],
                            id: \.self
                        ){ supermercado in
                            SupermercadoCard(
                                productos: viewModel.state.listaProductos.filter{ it in
                                    it.supermercado.isEqual(supermercado)
                                },
                                imagen_super:
                                    ParserSupermercadosEnum().getImage(
                                        supermercado: supermercado
                                    ),
                                peso_super:
                                    viewModel.state.peso_total[supermercado] as? Float ?? 0,
                                precio_super:
                                    viewModel.state.precio_total[supermercado] as? Float ?? 0,
                                onClickProducto: { producto in
                                    viewModel.onTriggerEvent(event: ListaCompraEvents.onClickProducto(
                                        producto: producto
                                    ))
                                }
                            )
                        }
                        .padding()
                    }
                }
                .frame(minHeight: 400)
                
                //Aquí se encuentran todos los alimentos no encontrados.
                Text("Alimentos no encontrados")
                    .foregroundColor(Color.green)
                
                //Lista con todos los alimentos no encontrados.
                ZStack{
                    HStack{
                        ScrollView{
                            LazyHStack{
                                ForEach(
                                    viewModel.state.alimentos_no_encontrados,
                                    id: \.self.id_alimento
                                ){ alimento in
                                    //Cada alimento no encontrado.
                                    DespensaCard(
                                        alimento: alimento,
                                        deleteAlimento: { alimento in },
                                        onClickAlimento: {alimento in}
                                    )
                                }
                            }
                        }
                    }
                    //Boton Floating oara añadir una nueva lista de recetas.
                    FloatingButton(
                        openDialog: Binding.constant(true),
                        simbolsys: "cart.circle.fill",
                        isFinish: true,
                        tabSelection: $tabSelection,
                        finalizarCompra: {
                            viewModel.onTriggerEvent(event: ListaCompraEvents.onFinishCompra())
                        }
                    )
                }
            }
        }
    }
}


