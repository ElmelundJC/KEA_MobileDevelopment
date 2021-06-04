package com.celmelund.snapapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.celmelund.snapapp.adapter.MyAdapter;

import com.celmelund.snapapp.adapter.repo.Repo;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Updateable{

    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int REQUEST_IMAGE_PICK = 1;
    List<String> snaps = new ArrayList<>();
    MyAdapter myAdapter;
    ListView listView;
    Bitmap imageBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListView();

        Repo.r().setup(this, snaps);
    }



    public void snapBtn(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, 0);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    public void photoBtn(View view) {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK);
        try {
            choosePictureIntent.setType("image/*");
            choosePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(choosePictureIntent, 1);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When user selects the snapBtn
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

        // when user selects the foto from gallary button
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageBitmap = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setTextOnImageAlert();
    }


    // This method creates an alert for the user to set the text that that should be placed on the bitmap.
    public void setTextOnImageAlert() {
        AlertDialog.Builder textAlert = new AlertDialog.Builder(MainActivity.this);
        textAlert.setTitle("Set Text on image");

        // Textfield for the user to input text
        final EditText textfield = new EditText(MainActivity.this);
        textfield.setInputType(InputType.TYPE_CLASS_TEXT);
        textAlert.setView(textfield);

        // positive button
        textAlert.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Your text has been inserted", Toast.LENGTH_SHORT).show();
                drawTextToBitmap(textfield.getText().toString()); // calling the function to make a canvas upon the image/bitmap, to set text..
            }
        });

        // negative button
        textAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "TextBox has been cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        textAlert.show();
    }


    /*
    This Method lets is important since one of the requirements for the mandatory exercise is to place text
     upon the picture/image. So what happens here is that we first grab the configurations of the bitmap and if none
     set a defaultvalue to ARGB_8888 wich is the resoulution of 4 bits per pixel. Next we converts the bitmap so it
     becomes mutable. We then places a canvas on top of the image which is where the text in "drawn/placed" upon while it
     saved in the variable imageBitmap. With the Paint object we set the size, color, shadow of the text and draws it
     onto the canvas which is placed on top of the image. In the end we call the uploadBitmap function which takes 2
     parameters for both firebase storage and firebase firestore.
     */

    public void drawTextToBitmap(String gText) {
        // getting the configurations for the imageBitmap => our image. (TODO:LOOK UP!)
        Bitmap.Config bitmapConfig = imageBitmap.getConfig();
        // setting the default bitmap configurations if there is none.
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // Since resource bitmaps are immutable
        // we need to convert it to a mutable one
        imageBitmap = imageBitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(imageBitmap);

        // While working with Paint class OBS! sizes and other is manipulated in pixels.
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.MAGENTA);
        paint.setTextSize((int) 15); // size set in pixels
        paint.setShadowLayer(1f,0f,1f, Color.BLACK);

        canvas.drawText(gText, 10, 100, paint);

        // Next we upload the both the picture and the text to firebase firestore and storage.
        // through our repository updateBitmap method.
        Repo.r().uploadBitmap(gText, imageBitmap);
    }


    /*
    * setupListView is great method aswell since this method, as the name explaines, sets up our listview and listens for any
    * new list items that may be in our database for us to watch.
    * In the method we have also added a clickListener on every listItem which transfer the data from the selected
    * intent in the mainActivity class into the DetailActivity class. 
    * */
    private void setupListView() {
        listView = findViewById(R.id.snapList);
        myAdapter = new MyAdapter(snaps, this); // each activity can also be seen as a context ->
        // Go up the hierarchy context is part of the highest point/classes
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("snapid", snaps.get(position));
            startActivity(intent);
        });
    }

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }
}