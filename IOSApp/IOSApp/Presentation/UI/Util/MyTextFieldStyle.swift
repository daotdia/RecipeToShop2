//
//  EditTextStyle.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 25/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct MyTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
        .padding(18)
        .background(
            RoundedRectangle(cornerRadius: 14, style: .continuous)
                .stroke(Color.green, lineWidth: 3)
        ).padding()
    }
}
