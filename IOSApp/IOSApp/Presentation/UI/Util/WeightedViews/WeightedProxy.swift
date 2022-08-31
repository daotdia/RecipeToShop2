//
//  WeightedProxy.swift
//  IOSApp
//
//  Created by David Otero Diaz on 26/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Foundation

class WeightedProxy {
  let kind: Kind // differentiates between HStack and VStack
  var geo: GeometryProxy? = nil // wrapped GeometryProxy
  private(set) var totalWeight: CGFloat = 0

  init(kind: Kind) {
    self.kind = kind
  }

  func register(with weight: CGFloat) {
    totalWeight += weight
  }

  func dimensionForRelative(weight: CGFloat) -> CGFloat {
    guard let geo = geo,
          totalWeight > 0
    else {
      return 0
    }
    let dimension = (kind == .vertical) ? geo.size.height : geo.size.width
    return dimension * weight / totalWeight
  }

  enum Kind {
    case vertical, horizontal
  }
}
