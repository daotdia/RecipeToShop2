//
//  ParserProductosAlimentos.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

extension Array where Element == Alimento {
    func toProductos() -> [Productos.Producto]{
        var productos: [Productos.Producto] = []
        
        self.forEach{ alimento in
            productos.append(
                Productos.Producto(
                    imagen_src: "",
                    nombre: alimento.nombre,
                    oferta: "",
                    precio_texto: "",
                    precio_peso: "",
                    query: alimento.nombre,
                    cantidad: 0,
                    peso: Float(alimento.cantidad),
                    tipoUnidad: alimento.tipoUnidad,
                    precio_numero: 0,
                    noEncontrado: true
                )
            )
        }
        
        return productos
    }
}


