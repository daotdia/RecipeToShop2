//
//  FilterRow.swift
//  IOSApp
//
//  Created by David Otero Diaz on 11/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedApp

struct FilterRow: View {
    
    private let filter: FilterEnum
    
    init(
        filter: FilterEnum
    ){
        self.filter = filter
    }
    
    var body: some View {
        Text(filter.name)
        Divider()
    }
}

