//
//  ChatViewController.swift
//  Flash Chat iOS13
//
//  Created by Angela Yu on 21/10/2019.
//  Copyright © 2019 Angela Yu. All rights reserved.
//

import UIKit
import Firebase

class ChatViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var messageTextfield: UITextField!
    
    let db = Firestore.firestore()
    
    var messages: [Message] = [
        Message(sender: "1@2.com", body: "Hey!"),
        Message(sender: "a@b.com", body: "Hello!"),
        Message(sender: "1@2.com", body: "What's up?")
    ]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.dataSource = self
        title = K.appName // refering to the static constant
        navigationItem.hidesBackButton = true
        
        tableView.register(UINib(nibName: K.cellNibName, bundle: nil), forCellReuseIdentifier: K.cellIdentifier)
        
        loadMessages()
    }
    
    func loadMessages() {
        // .order --> orders the messages by datefield in ascending order
        // addSnapshotListener listens for real time updates and runs the code once again
        db.collection(K.FStore.collectionName)
            .order(by: K.FStore.dateField)
            .addSnapshotListener { (querySnapshot, error) in
            
                // inside the closure we empty the array, so that we wont get all the messages again. Also while working inside a closure we have to define the 'self' in front of messages array.
            self.messages = []
            
            if let e = error {
                print("There was an issue retrieving data from Firestore. \(e)")
            } else {
                if let snapshotDocuments = querySnapshot?.documents {
                    for doc in snapshotDocuments {
                        let data = doc.data()
                        if let messageSender = data[K.FStore.senderField] as? String, let messageBody =
                            data[K.FStore.bodyField] as? String {
                            let newMessage = Message(sender: messageSender, body: messageBody)
                            self.messages.append(newMessage)
                            
                            
                            // TIP: Whenever your trying to manipulate the userinterface (in this case we update the tableview) & we are inside a closure (lambda function) --> a good idea is to get a hold of the main queue like this.
                            // this just ensures that, since this process fetching documents happens in a closure, it means that it is happening in the background, and when we are ready to update the tableview with the new messages, we have to fetch the main thread, which is the process happening in the foreground. It is on the main thread that we update our data.
                            DispatchQueue.main.async {
                                self.tableView.reloadData() // this call reloads the tableview.
                                let indexPath = IndexPath(row: self.messages.count - 1, section: 0)
                                self.tableView.scrollToRow(at: indexPath, at: .top, animated: true)
                            }
                        }
                    }
                }
            }
        }
    }
    
    @IBAction func sendPressed(_ sender: UIButton) {
        if let messageBody = messageTextfield.text, let messageSender = Auth.auth().currentUser?.email {
            // Adds the message from textfield + the user/sender to firestore under the collection messages
            db.collection(K.FStore.collectionName).addDocument(data: [
                                                                K.FStore.senderField: messageSender,
                                                                K.FStore.bodyField: messageBody,
                                                                K.FStore.dateField: Date().timeIntervalSince1970 // Creates a timestamp inside the messages collection
            ]) { (error) in
                if let e = error {
                    print("There was an issue saving data to firestore, \(e)")
                } else {
                    print("Successfully saved data.")
                    
                    DispatchQueue.main.async {
                        self.messageTextfield.text = ""
                    }
                }
            }
        }
    }
    

    @IBAction func logOutPressed(_ sender: UIBarButtonItem) {
    do {
      try Auth.auth().signOut()
        navigationController?.popToRootViewController(animated: true)
    } catch let signOutError as NSError {
      print ("Error signing out: %@", signOutError)
    }
      
    }
}


// 2 methods: DataSource protocol: is the one responsible for populating the tableView, how many cells and which to put into the tableview
// 2 methods: Delegate protocol: response from the tableview, when the user interacts with the tableview.. each cells clickable... do something

// extension makes that app make a request for data upon startup
extension ChatViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return messages.count // returns the number of messages in the array
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let message = messages[indexPath.row]
        
        let cell = tableView.dequeueReusableCell(withIdentifier: K.cellIdentifier, for: indexPath) as! MessageCell // as! => typecasting
        cell.label.text = message.body
        
        // This is a message from the current user. (ME)
        if message.sender == Auth.auth().currentUser?.email {
            cell.leftImageView.isHidden = true
            cell.rightImageView.isHidden = false
            cell.messageBubble.backgroundColor = UIColor(named: K.BrandColors.lightPurple)
            cell.label.textColor = UIColor(named: K.BrandColors.purple)
        } else {
            // This is a message from another sender
            cell.leftImageView.isHidden = false
            cell.rightImageView.isHidden = true
            cell.messageBubble.backgroundColor = UIColor(named: K.BrandColors.lighBlue)
            cell.label.textColor = UIColor(named: K.BrandColors.blue)
        }
        
        return cell
    }
}

// Delegate methods not needed since we dont want the user to interact with each cell ... Its a chat god dammit!

