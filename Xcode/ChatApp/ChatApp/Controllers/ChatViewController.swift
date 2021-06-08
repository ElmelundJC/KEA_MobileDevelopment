//
//  ChatViewController.swift
//  ChatApp
//
//  Created by cElmelund on 07/06/2021.
//

import UIKit
import Firebase

class ChatViewController: UIViewController {
    

    @IBOutlet weak var messageTextfield: UITextField!
    @IBOutlet weak var tableView: UITableView!
    
    let db = Firestore.firestore()
    
    var messages: [Message] = []
//        Message(sender: "1@2.com", body: "Hey!"),
//        Message(sender: "a@b.com", body: "Hello!"),
//        Message(sender: "1@2.com", body: "What's up?")
//    ]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.dataSource = self
        // hides the "Back" button in the left corner of the controller
        navigationItem.hidesBackButton = true
        title = "Chat App"
        
        tableView.register(UINib(nibName: "MessageCell", bundle: nil), forCellReuseIdentifier: "ReusableCell")
        
        loadMessages()
    }
    
    func loadMessages() {
        db.collection("chatroom")
            .order(by: "date")
            .addSnapshotListener { (querySnapshot, error) in
            
            self.messages = []
            
            if let e = error {
                print("There was an issue retrieving data from Firestore. \(e)")
            } else {
                if let snapshotDocuments = querySnapshot?.documents {
                    for doc in snapshotDocuments {
                        let data = doc.data()
                        if let messageSender = data["sender"] as? String, let messageBody = data["body"] as? String {
                            let newMessage = Message(sender: messageSender, body: messageBody)
                            self.messages.append(newMessage)
                            
                            DispatchQueue.main.async{
                                self.tableView.reloadData()
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
            db.collection("chatroom").addDocument(data: ["sender": messageSender,
                                                         "body": messageBody,
                                                         "date": Date().timeIntervalSince1970
            ]) { (error) in
                if let e = error {
                    print("There was an issue saving data to firestor, \(e)")
                } else {
                    print("Data Saved to database")
                    
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
            print("Error signing out: %@", signOutError)
        }
    }
}

extension ChatViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return messages.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let message = messages[indexPath.row]
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "ReusableCell", for: indexPath) as! MessageCell
        cell.label.text = message.body
        
        // here we want to show messages from the currentuser
        if message.sender == Auth.auth().currentUser?.email {
            cell.leftImageView.isHidden = true
            cell.rightImageView.isHidden = false
            cell.messageBubble.backgroundColor = UIColor.systemBlue
            cell.label.textColor = UIColor.white
        } else {
            cell.leftImageView.isHidden = false
            cell.rightImageView.isHidden = true
            cell.messageBubble.backgroundColor = UIColor.systemRed
            cell.label.textColor = UIColor.white
        }
        
        
        return cell
    }
    
    
}

// extension Delegate to delete a message by long tap? maybe is enough time
