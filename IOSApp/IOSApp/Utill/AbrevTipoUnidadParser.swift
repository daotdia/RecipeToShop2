//
//  AbrevTipoUnidadParser.swift
//  IOSApp
//
//  Created by David Otero Diaz on 11/5/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class AbrevTipoUnidad{
    private let tipoUnidad: TipoUnidad
    
    init(
        tipoUnidad: TipoUnidad
    ){
        self.tipoUnidad = tipoUnidad
    }
    
    func parseAbrev() -> String{
        switch(tipoUnidad.name){
            case "GRAMOS":
                return "Gr"
            case "ML":
                return "Ml"
            case "UNIDADES":
                return "Ud"
            default:
                return "Gr"
        }
    }
    
}
