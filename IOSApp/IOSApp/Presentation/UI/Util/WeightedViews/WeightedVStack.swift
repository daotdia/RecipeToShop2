//
//  WeightedVStack.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct WeightedVStack<Content>: View where Content : View {
  private let proxy = WeightedProxy(kind: .vertical)
  @State private var initialized = false
  @ViewBuilder let content: (WeightedProxy) -> Content

  var body: some View {
    GeometryReader { geo in
      VStack(spacing: 0) {
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
