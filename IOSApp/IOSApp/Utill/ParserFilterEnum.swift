//
//  ParserFilterEnum.swift
//  IOSApp
//
//  Created by David Otero Diaz on 9/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class ParserFilterEnum {
    
    func parseStringToFilter(nombre_filtro: String) -> FilterEnum {
        switch(nombre_filtro.trimmingCharacters(in: .whitespacesAndNewlines).lowercased()){
            case "baratos", "más baratos":
                return FilterEnum.baratos
            case "ligeros", "más ligeros":
                return FilterEnum.ligeros
            case "más baratos ajustados", "ajustados":
                return FilterEnum.ajustados
            case "supermercado más barato":
                return FilterEnum.unicobarato
            case "supermercado más ligero":
                return FilterEnum.unicoligero
            case "supermercado más ajustado":
                return FilterEnum.unicoajustado
            default:
                return FilterEnum.baratos
        }
    }
}
