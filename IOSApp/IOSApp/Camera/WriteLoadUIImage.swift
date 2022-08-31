//
//  WriteLoadUIImage.swift
//  IOSApp
//
//  Created by David Otero Diaz on 15/8/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

class WriteLoadIImage{
    func saveImageToDocumentDirectory(_ chosenImage: UIImage) -> String {
            let directoryPath =  NSHomeDirectory().appending("/Documents/")
            if !FileManager.default.fileExists(atPath: directoryPath) {
                do {
                    try FileManager.default.createDirectory(at: NSURL.fileURL(withPath: directoryPath), withIntermediateDirectories: true, attributes: nil)
                } catch {
                    print(error)
                }
            }

            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyyMMddhhmmss"

            let filename = dateFormatter.string(from: Date()).appending(".jpg")
            let filepath = directoryPath.appending(filename)
            let url = NSURL.fileURL(withPath: filepath)
            do {
                try chosenImage.jpegData(compressionQuality: 1.0)?.write(to: url, options: .atomic)
                return filepath

            } catch {
                print(error)
                print("file cant not be save at path \(filepath), with error : \(error)");
                return filepath
            }
        }

    func fileExists(at path: String) -> Bool {
        return FileManager.default.fileExists(atPath: path)
    }
}
