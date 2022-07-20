//
//  NewAlimentoDialog.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp
import Combine

struct NewAlimentoDialog: View {
    
    private let caseUses: UseCases
    private var tipoUnidadOptions: [String] = []
    private let addAlimento: (String, String, String) -> Void
    @Binding var openDialog: Bool
    
    init(
        caseUses: UseCases,
        openDialog:  Binding<Bool>,
        addAlimento: @escaping (String, String, String) -> Void
    ) {
        self.caseUses = caseUses
        self._openDialog = openDialog
        self.addAlimento = addAlimento
        //Obtengo los tipos, lo he tendido que hacer manualmente debido a problemas con la compatibilidad de los Enums en Kotlin y en Swift al usarlo dentro de un ForEach; necesita que sea una colección con randomacces.
        tipoUnidadOptions.append("Gr")
        tipoUnidadOptions.append("Ml")
        tipoUnidadOptions.append("Ud")
                
    }
    
    @State var peso: String = ""
    @State var tipoUnidad: String? = ""
    @State var nombre: String = ""
    @State var incompleto: Bool = false
    @State var nombreValido: Bool = false
    @State var pesoValido: Bool = false
    @State var tipoValido: Bool = false
    
    var body: some View {
        ZStack{
            Color(white: 0.985).ignoresSafeArea()
            VStack{
                Spacer()
                //Nombre del alimento.
                Text("Nombre del alimento").font(.headline)
                    .padding(6)
                    
                //Nommbre del alimento, campo de edición de autocompletado.
                VStack{
                    AutocompleteTextField(
                        caseUses: caseUses,
                        nombre: $nombre,
                        nombreValido: $nombreValido
                    )
                    .overlay(
                        //En el caso de que el nombre no perntenezca a las opciones válidas y se haya intentando añadir el alimento; aparece borde rojo.
                        !nombreValido ? ErrorBorder(error: $incompleto): nil
                    )
                }
                .padding(6)
                .offset(y: 18)
                Spacer()
                //Peso del alimento.
                HStack{
                    Text("Peso del alimento")
                        .font(.headline)
                        .padding(8)
                    
                    TextField("Peso", text: $peso)
                        .keyboardType(.numberPad)
                        .onChange(of: peso){ newValue in
                            //En el caso de que el peso tenga un valor válido.
                            if(Int($peso.wrappedValue) != nil && Int($peso.wrappedValue)! >= 0){
                                pesoValido = true
                            } else{
                                pesoValido = false
                            }
                        }
                        .overlay(
                            //En el caso de que el peso no sea válido y se haya intentado añadir el alimento; sale borde rojo.
                            !pesoValido ? ErrorBorder(error: $incompleto): nil
                        )
                }
                Spacer()
                //Unidad de medida del alimento.
                HStack{
                    Text("Unidad de medida")
                        .font(.headline)
                        .padding(8)
                    
                    //Menu desplegable con las opciones de unidades posibles.
                    DropDownSelector(
                        options: self.tipoUnidadOptions,
                        placeholder: "Tipo de Unidad",
                        selectedOption: $tipoUnidad
                    )
                    //Cuadno cambie la vista cmopruebo que el tipo sea válido o no.
                    .onChange(of: tipoUnidad){ newValue in
                        ComprobarTipoUnidad(tipoUnidad: $tipoUnidad.wrappedValue).check() ?
                            (tipoValido = true) : (tipoValido = false)
                    }
                    //En el caso de que no sea válido, si se ha intentado agregar el alimento; borde rojo.
                    .overlay(
                        !tipoValido ? ErrorBorder(error: $incompleto) : nil
                    )
                }.offset(y: -18)
                Spacer()
                Divider()
                HStack {
                    Spacer()
                    Button(
                        action: {
                            UIApplication.shared.windows[0].rootViewController?.dismiss(
                                animated: true, completion: {}
                            )
                            //Si los datos son válidos.
                            if nombreValido && pesoValido && tipoValido {
                                //Llamo a añadir el alimento nuevo a la despensa
                                self.addAlimento(
                                    //OJO!: MUY IMPORTANTE RESPETAR EL ORDEN.
                                    $nombre.wrappedValue,
                                    $peso.wrappedValue,
                                    $tipoUnidad.wrappedValue!
                                )
                                //Cierro el diálogo
                                $openDialog.wrappedValue = false
                            }
                            else{
                                //Se ha intentado añadir un alimento con errores o información incompleta
                                incompleto = true
                            }
                        }
                    ){ Text("Done") }
                    Spacer()
                    Divider()
                    
                    Spacer()
                    Button(
                        action: {
                            UIApplication.shared.windows[0].rootViewController?.dismiss(
                                animated: true, completion: {}
                            )
                            $openDialog.wrappedValue = false
                        }
                    ){ Text("Cancel") }
                    Spacer()
            }
            .padding(0)
            .frame(maxHeight: 86)
        }
        }
    }
}