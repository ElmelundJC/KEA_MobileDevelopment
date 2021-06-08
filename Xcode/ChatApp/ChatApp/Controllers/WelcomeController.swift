//
//  WelcomeController.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit
import CLTypingLabel

class WelcomeController: UIViewController {

    @IBOutlet weak var titleLabel: CLTypingLabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = "Exam Chat App"
    }

}
