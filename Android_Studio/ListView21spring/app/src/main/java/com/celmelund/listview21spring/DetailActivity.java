package com.celmelund.listview21spring;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.celmelund.listview21spring.adapter.repo.Repo;
import com.celmelund.listview21spring.model.Note;

public class DetailActivity extends AppCompatActivity implements TaskListener {

    private ImageView imageView;
    private Bitmap currentBitmap;
    private Note currentNote;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        editText = findViewById(R.id.editText1);
        // here we catch the intent given from the mainActivity class and set it in the plainText
        String noteID = getIntent().getStringExtra("noteid");
        currentNote = Repo.r().getNoteWith(noteID);
        editText.setText(currentNote.getText());
        imageView = findViewById(R.id.imageView2);
    }


    public void save(View view) {
        // must be called AFTER onCreate() has finished. Because otherwise the image is not there yet.
        imageView.buildDrawingCache(true);
        currentBitmap = Bitmap.createBitmap(imageView.getDrawingCache(true));
        currentNote.setText(editText.getText().toString());
        Repo.r().uploadNoteAndImage(currentNote, currentBitmap);
        System.out.println("you pressed save");
        System.out.println("The bitmap size: " + currentBitmap.getByteCount());
    }

    @Override
    public void receive(byte[] bytes) {
        // figure out, how to get the byte array to an image, and from there to the imageView
    }
}