package com.celmelund.listview21spring.adapter.repo;

import android.graphics.Bitmap;

import com.celmelund.listview21spring.TaskListener;
import com.celmelund.listview21spring.Updateable;
import com.celmelund.listview21spring.model.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {

    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public List<Note> notes = new ArrayList<>(); // you could use Note, instead of String
    private final String NOTES = "notes";
    private Updateable activity;


    public static Repo r() {
        return repo;
    }

    public void setup(Updateable a, List<Note> list) {
        activity = a;
        notes = list;
        startListener();
    }

    public Note getNoteWith(String id){
        for (Note note : notes) {
            if (note.getId().equals(id)){
                return note;
            }
        }
        return null;
    }

    public void startListener(){
        db.collection(NOTES).addSnapshotListener((values, error) -> {
            notes.clear();
            for (DocumentSnapshot snap: values.getDocuments()){
                Object title = snap.get("title");
                if(title != null) {
                    notes.add(new Note(snap.getId(), title.toString()));
                }
                System.out.println("Snap: " + snap.toString());
            }
            // update the listview
            // have a reference to MainActivity, and call a update()
            activity.update(null);
        });
    }

    public void addNote(String text) {
        // insert a new note with "text"
        DocumentReference ref = db.collection(NOTES ).document();
        // Note note = new Note(ref.getId(), text); // create new document, to get the ID (doesnt matter now that we use the ref)
        Map<String,String> map = new HashMap<>();
        map.put("title", text);
        ref.set(map); // will replace any previous value. (inserting and updating at the same time)
        // db.collection("notes").add(map); // short version of how to add a note to the database
        System.out.println("Done inserting new document " + ref.getId());
    }

    public void updateNote(Note note) {
        // insert a new note with "text"
        DocumentReference ref = db.collection(NOTES ).document(note.getId());
        Map<String,String> map = new HashMap<>();
        map.put("title", note.getText());
        ref.set(map); // will replace any previous value. (inserting and updating at the same time)
        // db.collection("notes").add(map); // short version of how to add a note to the database
        // ref.update("key","value"); // for updating single values, instead of the whole document.
        System.out.println("Done updating document " + ref.getId());
    }

    public void uploadNoteAndImage(Note note, Bitmap bitmap) {
        updateNote(note);
        System.out.println("uploadBitmap called " + bitmap.getByteCount());
        StorageReference ref = storage.getReference(note.getId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ref.putBytes(baos.toByteArray()).addOnCompleteListener(snap -> {
            System.out.println("OK to upload " + snap);
        }).addOnFailureListener(exception -> {
            System.out.println("failure to upload " + exception);
        });
    }

    public void downloadBitmap(String fileName, TaskListener taskListener) { // when to call this method?
        StorageReference ref = storage.getReference(fileName);
        int max = 1024 * 1024; // you are free to set the limit here
        ref.getBytes(max).addOnSuccessListener(bytes -> {
            taskListener.receive(bytes); // god linie!
            System.out.println("Download OK ");
        }).addOnFailureListener(e -> {
            System.out.println("error in download " + e);
        });
    }
}
