//
//  WeightedHStack.swift
//  IOSApp
//
//  Created by David Otero Diaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Foundation

struct WeightedHStack<Content>: View where Content : View {
  private let proxy = WeightedProxy(kind: .horizontal)
  @State private var initialized = false
  @ViewBuilder let content: (WeightedProxy) -> Content

  var body: some View {
    GeometryReader { geo in
      HStack(spacing: 0) {
        if initialized {
          content(proxy)
        } else {
          Color.clear.onAppear {
            proxy.geo = geo
            initialized.toggle()
          }
        }
      }
    }
  }
}
