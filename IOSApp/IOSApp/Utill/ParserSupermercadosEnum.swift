//
//  ParserSupermercadosEnum.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp

class ParserSupermercadosEnum{
    func getImage(supermercado: SupermercadosEnum) -> String {
        switch(supermercado){
            case SupermercadosEnum.carrefour:
                return "https://vams-loyalia-storage.s3.eu-west-1.amazonaws.com/images/deals/_720x495/carrefour.jpg"
            case SupermercadosEnum.dia:
                return "https://thefoodtech.com/wp-content/uploads/2020/12/supermercados-dia-ofrece-espectaculo-para-consumidores.jpg"
            case SupermercadosEnum.mercadona:
                return "https://1000marcas.net/wp-content/uploads/2021/09/Mercadona-Logo.png"
            default:
                return ""
        }
    }
}
