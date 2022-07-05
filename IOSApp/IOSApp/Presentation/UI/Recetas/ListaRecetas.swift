//
//  ListaRecetas.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 28/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct ListaRecetas: View {
    
    private let caseUses: UseCases
    private let id_listaRecetas: Int
    private let nombre: String
        
    @ObservedObject var viewModel: ListaRecetasViewModel
    
    @Binding var tabSelection: Int
    
    init(
        caseUses: UseCases,
        id_listaRecetas: Int,
        nombre: String,
        tabSelection: Binding<Int>
    ){
        self.caseUses = caseUses
        self.id_listaRecetas = id_listaRecetas
        self._tabSelection = tabSelection
        self.nombre = nombre
        
        self.viewModel = ListaRecetasViewModel(
            caseUses: self.caseUses,
            id_listaRecetas: self.id_listaRecetas,
            nombre: nombre
        )
    }
    
    @State var calculateListaCompra: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                //Barra buscadora de recetas por nombre.
                SearchBar(
                    diccionario: [],
                    updateSearch: { query in
                        viewModel.onTriggerEvent(event: CestaCompraEventos.onSearchRecetas(query: query))
                    }
                )
                //En el caso de que el resultado de la búsqueda de recetas esté vacío se presentan las recetas activas y seleccionadas en primer lugar y en segundoi lugar y tras un divisor el resto de recetas ordenadas por: recetas de la lista de recetas no activas, recetas fuera de la lista de recetas pero activas, recetas fuera de la lista de recetas no activas.
                if self.viewModel.state.recetasBuscadas.isEmpty{
                    RecetaList(
                        viewModel: viewModel,
                        isBusqueda: false,
                        addReceta: { receta in },
                        addNewReceta:{ nombre in
                            viewModel.onTriggerEvent(event: CestaCompraEventos.onAddReceta(
                                nombre: nombre,
                                cantidad: 1))
                        },
                        activarReceta:{ receta, active in
                            viewModel.onTriggerEvent(event: CestaCompraEventos.onUpdateRecetaActive(
                                receta: receta,
                                active: active
                            ))
                        },
                        eliminarReceta: { receta in
                            viewModel.onTriggerEvent(event: CestaCompraEventos.onDeleteReceta(receta: receta))
                        },
                        addRecetaExistente: { receta in
                            viewModel.onTriggerEvent(event: CestaCompraEventos.onAddRecetaExistente(
                                receta: receta.doCopy(
                                    id_cestaCompra: Int32(id_listaRecetas),
                                    id_Receta: nil,
                                    nombre: receta.nombre,
                                    cantidad: receta.cantidad,
                                    user: receta.user,
                                    active: receta.active,
                                    imagenSource: receta.imagenSource,
                                    ingredientes: receta.ingredientes,
                                    rating: receta.rating,
                                    isFavorita: receta.isFavorita
                                )
                            ))
                        },
                        id_listaRecetas: id_listaRecetas
                    )
                    .offset(y: -24)
                    
                    //TODO: Hacer Scrollview horizontal con los alimentos sueltos de la lista de recetas. Solo en el caso de que la búsqueda esté vacía
                    AlimentosList(
                        viewModel: viewModel,
                        addAlimento: { nombre, cantidad, tipoUnidad in
                            print("Intentando añadir alimento: " + nombre + tipoUnidad + cantidad)
                            viewModel.onTriggerEvent(
                                event:
                                    CestaCompraEventos.onAddAlimento(
                                        nombre: nombre,
                                        cantidad: Int32(cantidad) ?? 1,
                                        tipoUnidad: ComprobarTipoUnidad(tipoUnidad: tipoUnidad).returnTipo()
                                    )
                            )
                        },
                        removeAlimento: { alimento in
                            viewModel.onTriggerEvent(
                                event: CestaCompraEventos.onDeleteAlimento(alimento: alimento)
                            )
                        },
                        caseUses: caseUses
                    )
                }
                //En el caso de que el resultado de búsqueda de recetas no esté vacío aparecen las recetas que contengan la query en orden: primero las recetas activas de la lista de receta, luego las recetas inactivas de la lista de recetas, luego las recetas activas de otras listas de recetas y por último las recetas no activas de otras listas de recetas.
                else{
                    //Paso sólo las recetas encontradas cuyo identificador no esté ya entre las acctivas y de la lista de recetas actualmente.
                    RecetaList(
                        viewModel: viewModel,
                        isBusqueda: true,
                        addReceta:{ receta in
                            viewModel.onTriggerEvent(event: CestaCompraEventos.onAddRecetaExistente(
                                receta: receta.doCopy(
                                    id_cestaCompra: Int32(id_listaRecetas),
                                    id_Receta: nil,
                                    nombre: receta.nombre,
                                    cantidad: 1,
                                    user: receta.user,
                                    active: true,
                                    imagenSource: receta.imagenSource,
                                    ingredientes: receta.ingredientes,
                                    rating: receta.rating,
                                    isFavorita: receta.isFavorita
                                )
                            ))
                        },
                        addNewReceta:{ nombre in },
                        activarReceta:{ receta, active in },
                        eliminarReceta: { receta in },
                        addRecetaExistente: {receta in},
                        id_listaRecetas: id_listaRecetas
                    )
                }
            }
        }
        .overlay(
            FloatingButton(
                openDialog: $calculateListaCompra,
                simbolsys: "cart"
            )
            .offset(y: -28)
            ,
            alignment: .bottomTrailing
        )
    }
}


