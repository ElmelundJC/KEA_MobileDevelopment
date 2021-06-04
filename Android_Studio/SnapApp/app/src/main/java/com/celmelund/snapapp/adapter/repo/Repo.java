package com.celmelund.snapapp.adapter.repo;

import android.graphics.Bitmap;

import com.celmelund.snapapp.TaskListener;
import com.celmelund.snapapp.Updateable;
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

public class Repo  {

    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private final String SNAP = "snapcollection";
    public List<String> snaps = new ArrayList<>();
    private Updateable activity;

    public static Repo r() {
        return repo;
    }

    public void setup(Updateable a, List<String> snaps) {
        activity = a;
        this.snaps = snaps;
        startListener();
    }


    public void startListener(){
        db.collection(SNAP).addSnapshotListener((values, error) -> {
           snaps.clear();
           for (DocumentSnapshot snapshot: values.getDocuments()) {
               Object title = snapshot.get("title");

               if (title != null) {
                   snaps.add(snapshot.getId());
               }
               System.out.println("Snapshot: " + toString());
           }
           //update the listview
            // have a reference to MainActivity, and call a update()
            activity.update(null);
        });
    }


    public void uploadBitmap(String snapText, Bitmap bitmap) {
        // insert a new note with "text" into firebase firestore which creates a new document with ID
        DocumentReference doc = db.collection(SNAP).document();
        Map<String,String> map = new HashMap<>();
        map.put("title", snapText);
        doc.set(map); // will replace any previous value. (inserting and updating at the same time)
        // db.collection("notes").add(map); // short version of how to add a note to the database
        // ref.update("key","value"); // for updating single values, instead of the whole document.
        System.out.println("Done updating document " + doc.getId());

        // bitmap is uploaded to firebase Storage in a seperate folder with the name set to the document ID from text/title in firestore
        System.out.println("uploadBitmap called " + bitmap.getByteCount());
        StorageReference ref = storage.getReference(SNAP + "/" + doc.getId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ref.putBytes(baos.toByteArray()).addOnCompleteListener(snap -> {
            System.out.println("OK to upload " + snap);
        }).addOnFailureListener(exception -> {
            System.out.println("failure to upload " + exception);
        });
    }

    public void downloadBitmap(String id, TaskListener taskListener) {
        StorageReference ref = storage.getReference(SNAP + "/" + id);
        int max = 1024 * 1024;
        ref.getBytes(max).addOnSuccessListener(taskListener::receive)
                .addOnFailureListener(ex -> System.out.println("Error in download" +ex));

        // ref.getBytes(max).addOnSuccessListener(bytes -> {
        // taskListener.recieve(bytes);
        // sout("Download OK");
        // }).addOnFailureListener(ex -> {
        // sout("error in download" + ex);
        // })
    }

    public void deleteSnap(String id) {
        db.collection(SNAP).document(id).delete();
        storage.getReference(SNAP + "/" + id).delete();
    }


}
