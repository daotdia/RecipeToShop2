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
                    id_producto: -1,
                    id_cestaCompra: alimento.id_cestaCompra as! Int32,
                    imagen_src: "",
                    nombre: alimento.nombre,
                    oferta:"",
                    precio_texto: "",
                    precio_peso: "",
                    query: alimento.nombre,
                    cantidad: 0,
                    peso: Float(alimento.cantidad),
                    tipoUnidad: alimento.tipoUnidad,
                    precio_numero: 0,
                    noEncontrado: true,
                    supermercado: SupermercadosEnum.carrefour,
                    active: true
                )
            )
        }
        
        return productos
    }
}


