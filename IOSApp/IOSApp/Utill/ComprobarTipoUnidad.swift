//
//  ComprobarTipoUnidad.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class ComprobarTipoUnidad{
    private let tipoUnidad: String?
    init(
        tipoUnidad: String?
    ){
        self.tipoUnidad = tipoUnidad
    }
    
    func check() -> Bool{
        if(self.tipoUnidad == nil){
            return false
        }
        if(
            self.tipoUnidad == "Gr"
            || self.tipoUnidad == "Ml"
            || self.tipoUnidad == "Ud"
        ){
            return true
        }
        else{
            return false
        }
    }
    
    func returnTipo() -> TipoUnidad{
        if(self.tipoUnidad) == nil {
            return TipoUnidad.gramos
        }
        switch self.tipoUnidad {
            case "Gr":
                return TipoUnidad.gramos
            case "Ml":
                return TipoUnidad.ml
            case "Ud":
                return TipoUnidad.unidades
            default:
                return TipoUnidad.gramos
        }
    }
    
}
