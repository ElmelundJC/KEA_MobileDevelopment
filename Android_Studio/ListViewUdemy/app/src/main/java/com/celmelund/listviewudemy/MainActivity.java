package com.celmelund.listviewudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);

        ArrayList<String> avengerList = new ArrayList<String>();

        avengerList.add("Spiderman");
        avengerList.add("Captain America");
        avengerList.add("Iron Man");
        avengerList.add("Thor");
        avengerList.add("Hulk");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, avengerList);

        myListView.setAdapter(arrayAdapter);
    }
}