//
//  RegisterController.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit
import Firebase

class RegisterController: UIViewController {

    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    
//    override func viewDidLoad() {
//        super.viewDidLoad()
//
//        // Do any additional setup after loading the view.
//    }
    
    @IBAction func registerPressed(_ sender: UIButton) {
        
        // if emailTextField.text & passwordTextfield.text is not nil, then ...
        if let email = emailTextField.text, let password = passwordTextField.text {
        
            Auth.auth().createUser(withEmail: email, password: password) { authResult, error in
                if let e = error {
                    print(e)// e.localizedDiscription --> language in the users own language
                    // // TODO: Add popup warning if user types wrong password or mail
                } else {
                    // navigate to the ListChatController // if it does not work with the chat list -> change the code back so there is no chatListController and thereby change the id of the segue
                    self.performSegue(withIdentifier: "RegisterToChatList", sender: self)
                }
            }
        }
    }
    
    

}
