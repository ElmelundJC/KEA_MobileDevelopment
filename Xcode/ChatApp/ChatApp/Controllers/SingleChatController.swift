//
//  SingleChatController.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit

class SingleChatController: UIViewController {
    
    @IBOutlet weak var singleChatTable: UITableView!
    @IBOutlet weak var messageTextfield: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

    @IBAction func sendPressed(_ sender: UIButton) {
    }
}
