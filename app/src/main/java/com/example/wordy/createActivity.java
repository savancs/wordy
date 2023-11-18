package com.example.wordy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createActivity extends AppCompatActivity {
    Button submitButton;
    Button cancelButton;

    EditText newWordET;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Words");
        setContentView(R.layout.activity_create);
        newWordET = findViewById(R.id.insertTV);

        submitButton = findViewById(R.id.addWButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newWord = newWordET.getText().toString();
                if(newWord.length() == 5){//check for a-z and not in database
                    newWordET.setBackgroundColor(Color.WHITE);
                    addWord(newWord);
                    Toast.makeText(createActivity.this, "Word successfully added", Toast.LENGTH_SHORT).show();
                } else{
                    newWordET.setBackgroundColor(Color.MAGENTA);
                    Toast.makeText(createActivity.this, "Word needs to be 5 characters long", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void addWord(String word){
        final String newWord = newWordET.getText().toString();
        ref.child(ref.push().getKey()).setValue(newWord);

        newWordET.getText().clear();
    }

    public String getWord(String word){

        return null;
    }

}