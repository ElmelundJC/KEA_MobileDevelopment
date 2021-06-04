//
//  WelcomeViewController.swift
//  Flash Chat iOS13
//
//  Created by Angela Yu on 21/10/2019.
//  Copyright © 2019 Angela Yu. All rights reserved.
//

import UIKit

class WelcomeViewController: UIViewController {

    @IBOutlet weak var titleLabel: UILabel!
    
    // if we override a lifecycle method from the viewController like: viewWillAppear, viewWillDisappear or viewDidLoad, we must call super at some point in our implementation.. that means the parent (in this case UIViewController gets an opportunity to run its own code inside viewWillAppear and disappear) and then we run our code.
    // viewWillAppear and Disappear is removing the navigationbar in the viewController, but will show again when the next screen loads.
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.isNavigationBarHidden = true
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        navigationController?.isNavigationBarHidden = false
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = ""
        var charIndex = 0.0
        let titleText = K.appName
        for letter in titleText {
//            print("-")
//            print(0.1 * charIndex)
//            print(letter)
            Timer.scheduledTimer(withTimeInterval: 0.1 * charIndex, repeats: false) { (timer) in
                self.titleLabel.text?.append(letter)
            }
            charIndex += 1

        }
        
        //titleLabel.text = "⚡️FlashChat" // supposed to be setup with CLTypingLabel 3 party library --> install dependency in Podfile
        
    }
    

}
