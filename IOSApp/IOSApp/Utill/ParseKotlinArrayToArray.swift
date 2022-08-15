//
//  ParseKotlinArrayToArray.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 11/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedApp
import ImageIO

class ParseKoltinArrayToArray{
    func parseFilters(kotlin_array: KotlinArray<FilterEnum>) -> [FilterEnum]{
        let iterator = kotlin_array.iterator()
        var array: [FilterEnum] = []
        while(iterator.hasNext()){
            array.append(iterator.next_() as! FilterEnum)
        }
        return array
    }
}
