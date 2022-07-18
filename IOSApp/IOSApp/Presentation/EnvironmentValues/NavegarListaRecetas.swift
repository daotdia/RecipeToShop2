//
//  NavegarListaRecetas.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 1/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

private struct NavegarListaRecetas: EnvironmentKey {
    
    static let defaultValue: Bool = false
    
}

extension EnvironmentValues {
    var navegarListaRecetas: Bool {
        get { self[NavegarListaRecetas.self]}
        set { self[NavegarListaRecetas.self] = newValue}
    }
}

extension View {
    func navegarListaRecetas(_ value: Bool) -> some View {
        environment(\.navegarListaRecetas, value)
    }
}
