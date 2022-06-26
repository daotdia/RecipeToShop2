//
//  Weighted.swift
//  IOSApp
//
//  Created by Irene Otero Díaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Foundation

struct Weighted: ViewModifier {
  private let weight: CGFloat
  private let proxy: WeightedProxy

  init(_ weight: CGFloat, proxy: WeightedProxy) {
    self.weight = weight
    self.proxy = proxy
    proxy.register(with: weight)
  }

  @ViewBuilder func body(content: Content) -> some View {
    if proxy.kind == .vertical {
      content.frame(height: proxy.dimensionForRelative(weight: weight))
    } else {
      content.frame(width: proxy.dimensionForRelative(weight: weight))
    }
  }
}

extension View {
  func weighted(_ weight: CGFloat, proxy: WeightedProxy) -> some View {
    self.modifier(Weighted(weight, proxy: proxy))
  }
}
