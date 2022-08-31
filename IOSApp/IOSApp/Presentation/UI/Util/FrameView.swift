//
//  FrameView.swift
//  IOSApp
//
//  Created by David Otero Diaz on 11/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FrameView: View {
    
    var image: CGImage?
    private let label = Text("Camera feed")

    var body: some View {
        // 1
        if let image = image {
          // 2
          GeometryReader { geometry in
            // 3
            Image(image, scale: 1.0, orientation: .up, label: label)
              .resizable()
              .scaledToFill()
              .frame(
                width: geometry.size.width,
                height: geometry.size.height,
                alignment: .center)
              .clipped()
          }
        } else {
          // 4
          Color.black
        }
    }
}

