//
//  ErrorBorder.swift
//  IOSApp
//
//  Created by David Otero Diaz on 24/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ErrorBorder: View {
    
    @Binding var error: Bool
    
    var body: some View {
        RoundedRectangle(cornerRadius: 1)
            .stroke(error ? Color.red : Color.black.opacity(0.0), lineWidth: 2)
    }
}
