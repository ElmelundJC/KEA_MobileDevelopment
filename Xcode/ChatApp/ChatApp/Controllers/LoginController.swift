//
//  LoginController.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit
import Firebase

class LoginController: UIViewController {
    
    @IBOutlet weak var emailTextfield: UITextField!
    @IBOutlet weak var passwordTextfield: UITextField!
    
    
    @IBAction func loginPressed(_ sender: UIButton) {
        
        // optional binding -->
        // if emailTextField.text & passwordTextfield.text is not nil, then ...
        if let email = emailTextfield.text, let password = passwordTextfield.text {
        
        
            // once the closure is complete we end up with an authResult and an error if there is one
            Auth.auth().signIn(withEmail: email, password: password) { [weak self] authResult, error in
                if let e = error {
                    print(e)
                } else {
                    // navigate to the ListChatController // if it does not work with the chat list -> change the code back so there is no chatListController and thereby change the id of the segue
                    
                    self?.performSegue(withIdentifier: "LoginToChatList", sender: self)
                }
            }
        }
    }
}
