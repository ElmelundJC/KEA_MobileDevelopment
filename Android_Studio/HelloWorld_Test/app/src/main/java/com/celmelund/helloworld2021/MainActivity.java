package com.celmelund.helloworld2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // R.id.textView => R -> R klasse er en javaklasse der bliver compiled hele tiden. så når man laver en ny button, vil R klassen re-compile. så hver gang man opretter noget kan android finde elementen(button, textfield
        // gennem R klassen, hvor elementerne får tildelt et id.
        textView = findViewById(R.id.textView1);
        editText1 = findViewById(R.id.editText1);
    }

    public void savePressed(View view) {
        // her connecter vi textViewer med editText feltet via save knappen
        textView.setText(editText1.getText());
    }

    public void aboutPressed(View view) {
        // Intent er sådan vi opretter navigation mellem knap og pages. parametre (this -> objektet selv, AboutActivity.class -> når man skriver .class får man klassen selv som objekt.)
        // sout(AboutActivity.class)
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}