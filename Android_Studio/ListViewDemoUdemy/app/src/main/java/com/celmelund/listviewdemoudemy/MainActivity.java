package com.celmelund.listviewdemoudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView avengersListView = findViewById(R.id.avengersListView);

        final ArrayList<String> avengerList = new ArrayList<String>(asList("Spiderman", "Captain America", "Iron Man", "Buckey Barnes"));

//        avengerList.add("Spiderman");
//        avengerList.add("Captain America");
//        avengerList.add("Iron Man");
//        avengerList.add("Thor");
//        avengerList.add("Hulk");
//        avengerList.add("Buckey Barnes");
//        avengerList.add("Black Widow");
//        avengerList.add("Doctor Strange");
//        avengerList.add("Ant Man");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, avengerList);

        avengersListView.setAdapter(arrayAdapter);

        avengersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Avengers selected: ", avengerList.get(i));

                Toast.makeText(getApplicationContext(), "Hello " + avengerList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}