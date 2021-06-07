//
//  ListChatController.swift
//  ChatApp
//
//  Created by cElmelund on 05/06/2021.
//

import UIKit
import Firebase

class ListChatController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var chatroomArray: [String] = ["Swift", "Java", "Python", "Javascript", "NodeJS"]
    let sharedSingleton = Singleton.sharedInstance
    
    @IBOutlet weak var chatroomTextfield: UITextField!
    @IBOutlet weak var chatTableView: UITableView!
    

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        chatTableView.dataSource = self
        chatTableView.delegate = self
        
        navigationItem.hidesBackButton = true // hides backButton
        navigationItem.title = "ChatRooms"
    }
    



    @IBAction func logOutPressed(_ sender: UIBarButtonItem) {
        
    do {
      try Auth.auth().signOut()
        navigationController?.popToRootViewController(animated: true) // jumps to the viewController at the root (WelcomeController)
    } catch let signOutError as NSError {
      print ("Error signing out: %@", signOutError)
    }
      
    }
    
    
    @IBAction func newChatRoomPressed(_ sender: UIButton) {
        if let txt = chatroomTextfield.text, txt.isEmpty == false { // need to make sure we have something here
            sharedSingleton.stringArray.append(txt) // store it in our data holder
        }
        chatroomTextfield.text = nil // clears the textfield
        self.chatTableView.reloadData()
    }
    
    
    
    // MARK: - TABLEVIEW DELEGATE & DATASOURCE
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sharedSingleton.stringArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "chatroomCell", for: indexPath)
        
        cell.textLabel!.text = sharedSingleton.stringArray[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("You selected row \(indexPath.row)")
        performSegue(withIdentifier: "cellRoom", sender: self)
    }
}
