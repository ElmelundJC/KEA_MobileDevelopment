//
//  Singleton.swift
//  ChatApp
//
//  Created by cElmelund on 06/06/2021.
//

import Foundation

class Singleton {
    static let sharedInstance = Singleton()
    // var stringArray = [String]() is a shorthand way to initialize an empty Array --> we define we define a variable and what type the array can hold by doing var stringArray = [String], by adding the parentheses we initialize the array, that lets us assign values to the array of type Strings. after this way "stringArray" actually has a value, which is a completely empty array that is allowed to contain Strings.
    var stringArray = [String]()
    private init() {
        print("Singleton initialized")
    } // private init method, makes every other instantiation of the singleton class a reference and not a new object. so it actually makes the singleton completely singleton xD
}
