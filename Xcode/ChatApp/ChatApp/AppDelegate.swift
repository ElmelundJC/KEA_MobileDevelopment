//
//  AppDelegate.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit
import Firebase
import IQKeyboardManagerSwift

@main
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        FirebaseApp.configure()
        let db = Firestore.firestore()
        
        print(db)
        
        // 3 party library that helps us helps the textfield to follow the keyboard move up when typing a text.
        // enableAutobar = false, removes the grey field for pressing "Done"
        // shouldResignOnTouchOutside = true, when clicked elsewhere than the keyboard, the keybords disapear.
        
        IQKeyboardManager.shared.enable = true // fixes the keyboard issues
        IQKeyboardManager.shared.enableAutoToolbar = false // removes the gray toolbar
        IQKeyboardManager.shared.shouldResignOnTouchOutside = true
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

