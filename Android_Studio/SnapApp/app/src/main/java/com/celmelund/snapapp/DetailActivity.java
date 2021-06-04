package com.celmelund.snapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.celmelund.snapapp.adapter.repo.Repo;
import com.celmelund.snapapp.model.Snap;

public class DetailActivity extends AppCompatActivity implements TaskListener{


    String currentSnap;
    ImageView imageView;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        currentSnap = getIntent().getStringExtra("snapid");

        // using the method downloadBitmap to get the image from firebase storage into this class
        Repo.r().downloadBitmap(currentSnap, DetailActivity.this);

    }


    @Override
    public void receive(byte[] bytes) {
        imageView = findViewById(R.id.snapView);
        bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageView.setImageBitmap(bitmap);

    }

    // when user tabs the return button, the snap gets deleted.
    @Override
    public void onBackPressed() {
        Repo.r().deleteSnap(currentSnap);
        finish();
    }
}