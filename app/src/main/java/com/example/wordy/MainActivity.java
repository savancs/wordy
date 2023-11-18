package com.example.wordy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout LL1, LL2, LL3, LL4, LL5, LL6;
    LinkedList wordsList;
    EditText box11, box12, box13, box14, box15, box21, box22, box23, box24, box25,
    box31, box32, box33, box34, box35, box41, box42, box43, box44, box45,
    box51, box52, box53, box54, box55, box61, box62, box63, box64, box65;

    Button addWButton;
    Button submitButton;
    Button restartButton; //gives new word and clears
    Button clearButton; //clears with same word

    DatabaseReference ref;
    FirebaseDatabase wordDB;
    String randomWord;
    HashMap<String, Object> wordsMap;
    int numPlays = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Words");
        wordDB = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_main);
        box11 = findViewById(R.id.box11ET);
        box12 = findViewById(R.id.box12ET);
        box13 = findViewById(R.id.box13ET);
        box14 = findViewById(R.id.box14ET);
        box15 = findViewById(R.id.box15ET);

        box21 = findViewById(R.id.box21ET);
        box22 = findViewById(R.id.box22ET);
        box23 = findViewById(R.id.box23ET);
        box24 = findViewById(R.id.box24ET);
        box25 = findViewById(R.id.box25ET);

        box31 = findViewById(R.id.box31ET);
        box32 = findViewById(R.id.box32ET);
        box33 = findViewById(R.id.box33ET);
        box34 = findViewById(R.id.box34ET);
        box35 = findViewById(R.id.box35ET);

        box41 = findViewById(R.id.box41ET);
        box42 = findViewById(R.id.box42ET);
        box43 = findViewById(R.id.box43ET);
        box44 = findViewById(R.id.box44ET);
        box45 = findViewById(R.id.box45ET);

        box51 = findViewById(R.id.box51ET);
        box52 = findViewById(R.id.box52ET);
        box53 = findViewById(R.id.box53ET);
        box54 = findViewById(R.id.box54ET);
        box55 = findViewById(R.id.box55ET);

        box61 = findViewById(R.id.box61ET);
        box62 = findViewById(R.id.box62ET);
        box63 = findViewById(R.id.box63ET);
        box64 = findViewById(R.id.box64ET);
        box65 = findViewById(R.id.box65ET);

        LL1 = findViewById(R.id.horizLL1);
        LL2 = findViewById(R.id.horizLL2);
        LL3 = findViewById(R.id.horizLL3);
        LL4 = findViewById(R.id.horizLL4);
        LL5 = findViewById(R.id.horizLL5);
        LL6 = findViewById(R.id.horizLL6);

        LL2.setVisibility(View.INVISIBLE);
        LL3.setVisibility(View.INVISIBLE);
        LL4.setVisibility(View.INVISIBLE);
        LL5.setVisibility(View.INVISIBLE);
        LL6.setVisibility(View.INVISIBLE);

        addWButton = findViewById(R.id.addButton);
        submitButton = findViewById(R.id.submitButton);
        restartButton = findViewById(R.id.restartButton);
        clearButton = findViewById(R.id.clearButton);

        newRandomWord();

        addWButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), createActivity.class);
                startActivity(i);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRow(numPlays);
                numPlays++;
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

    }

    public void newRandomWord(){
        //need to grab word from firebase randomly
        ref = wordDB.getReference("Words");
        wordsList = new LinkedList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Please add words before playing!", Toast.LENGTH_LONG).show();
                    randomWord = "kitty";
                    Toast.makeText(getApplicationContext(), "The word is " + randomWord, Toast.LENGTH_LONG).show();
                    return;
                }
                wordsMap = (HashMap<String, Object>) snapshot.getValue();
                ArrayList<String> keys = new ArrayList<>(wordsMap.keySet());
                if (keys.size() == 1) {
                    randomWord = wordsMap.get(keys.get(0)).toString();
                } else{
                    Random rand = new Random();
                    String secretKey = keys.get(rand.nextInt(keys.size()));
                    randomWord = wordsMap.get(secretKey).toString();
                }
                //keep for debugging
                //Toast.makeText(getApplicationContext(), "The word is " + randomWord, Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validateRow(int numPlays){
        switch(numPlays){
            case 1:
                String edt1Text =  box11.getText().toString();
                String edt2Text =  box12.getText().toString();
                String edt3Text =  box13.getText().toString();
                String edt4Text =  box14.getText().toString();
                String edt5Text =  box15.getText().toString();
                String userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box11.setEnabled(false);
                box12.setEnabled(false);
                box13.setEnabled(false);
                box14.setEnabled(false);
                box15.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                } else {
                    colorBoxes(box11, box12, box13, box14, box15);
                    LL2.setVisibility(View.VISIBLE);
                    box21.setEnabled(true);
                    box22.setEnabled(true);
                    box23.setEnabled(true);
                    box24.setEnabled(true);
                    box25.setEnabled(true);
                }
                //numPlays = 2;
                break;
            case 2:
                edt1Text =  box21.getText().toString();
                edt2Text =  box22.getText().toString();
                edt3Text =  box23.getText().toString();
                edt4Text =  box24.getText().toString();
                edt5Text =  box25.getText().toString();
                userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box21.setEnabled(false);
                box22.setEnabled(false);
                box23.setEnabled(false);
                box24.setEnabled(false);
                box25.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }else {
                    colorBoxes(box21, box22, box23, box24, box25);
                    LL3.setVisibility(View.VISIBLE);
                    box31.setEnabled(true);
                    box32.setEnabled(true);
                    box33.setEnabled(true);
                    box34.setEnabled(true);
                    box35.setEnabled(true);
                }
                //numPlays = 3;
                break;
            case 3:
                edt1Text =  box31.getText().toString();
                edt2Text =  box32.getText().toString();
                edt3Text =  box33.getText().toString();
                edt4Text =  box34.getText().toString();
                edt5Text =  box35.getText().toString();
                userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box31.setEnabled(false);
                box32.setEnabled(false);
                box33.setEnabled(false);
                box34.setEnabled(false);
                box35.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }else {
                    colorBoxes(box31, box32, box33, box34, box35);
                    LL4.setVisibility(View.VISIBLE);
                    box41.setEnabled(true);
                    box42.setEnabled(true);
                    box43.setEnabled(true);
                    box44.setEnabled(true);
                    box45.setEnabled(true);
                }
                //numPlays = 3;
                break;
            case 4:
                edt1Text =  box41.getText().toString();
                edt2Text =  box42.getText().toString();
                edt3Text =  box43.getText().toString();
                edt4Text =  box44.getText().toString();
                edt5Text =  box45.getText().toString();
                userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box41.setEnabled(false);
                box42.setEnabled(false);
                box43.setEnabled(false);
                box44.setEnabled(false);
                box45.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }else {
                    colorBoxes(box41, box42, box43, box44, box45);
                    LL5.setVisibility(View.VISIBLE);
                    box51.setEnabled(true);
                    box52.setEnabled(true);
                    box53.setEnabled(true);
                    box54.setEnabled(true);
                    box55.setEnabled(true);
                }
                //numPlays = 3;
                break;
            case 5:
                edt1Text =  box51.getText().toString();
                edt2Text =  box52.getText().toString();
                edt3Text =  box53.getText().toString();
                edt4Text =  box54.getText().toString();
                edt5Text =  box55.getText().toString();
                userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box51.setEnabled(false);
                box52.setEnabled(false);
                box53.setEnabled(false);
                box54.setEnabled(false);
                box55.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }else {
                    colorBoxes(box51, box52, box53, box54, box55);
                    LL6.setVisibility(View.VISIBLE);
                    box61.setEnabled(true);
                    box62.setEnabled(true);
                    box63.setEnabled(true);
                    box64.setEnabled(true);
                    box65.setEnabled(true);
                }
                //numPlays = 3;
                break;
            case 6:
                edt1Text =  box61.getText().toString();
                edt2Text =  box62.getText().toString();
                edt3Text =  box63.getText().toString();
                edt4Text =  box64.getText().toString();
                edt5Text =  box65.getText().toString();
                userguess = edt1Text + edt2Text + edt3Text + edt4Text + edt5Text;

                box61.setEnabled(false);
                box62.setEnabled(false);
                box63.setEnabled(false);
                box64.setEnabled(false);
                box65.setEnabled(false);
                if(userguess.equals(randomWord)){
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }else {
                    colorBoxes(box61, box62, box63, box64, box65);
                    Toast.makeText(this, "You didn't win. The word was " + randomWord, Toast.LENGTH_LONG).show();
                }
                //numPlays = 3;
                break;


        }

        }

        public void clearGame(){
            //remove text from every edittext
            box11.setText("");
            box12.setText("");
            box13.setText("");
            box14.setText("");
            box15.setText("");

            box21.setText("");
            box22.setText("");
            box23.setText("");
            box24.setText("");
            box25.setText("");

            box31.setText("");
            box32.setText("");
            box33.setText("");
            box34.setText("");
            box35.setText("");

            box41.setText("");
            box42.setText("");
            box43.setText("");
            box44.setText("");
            box45.setText("");

            box51.setText("");
            box52.setText("");
            box53.setText("");
            box54.setText("");
            box55.setText("");

            box61.setText("");
            box62.setText("");
            box63.setText("");
            box64.setText("");
            box65.setText("");

            //remove color
            box11.setBackgroundColor(Color.WHITE);
            box12.setBackgroundColor(Color.WHITE);
            box13.setBackgroundColor(Color.WHITE);
            box14.setBackgroundColor(Color.WHITE);
            box15.setBackgroundColor(Color.WHITE);

            box21.setBackgroundColor(Color.WHITE);
            box22.setBackgroundColor(Color.WHITE);
            box23.setBackgroundColor(Color.WHITE);
            box24.setBackgroundColor(Color.WHITE);
            box25.setBackgroundColor(Color.WHITE);

            box31.setBackgroundColor(Color.WHITE);
            box32.setBackgroundColor(Color.WHITE);
            box33.setBackgroundColor(Color.WHITE);
            box34.setBackgroundColor(Color.WHITE);
            box35.setBackgroundColor(Color.WHITE);

            box41.setBackgroundColor(Color.WHITE);
            box42.setBackgroundColor(Color.WHITE);
            box43.setBackgroundColor(Color.WHITE);
            box44.setBackgroundColor(Color.WHITE);
            box45.setBackgroundColor(Color.WHITE);

            box51.setBackgroundColor(Color.WHITE);
            box52.setBackgroundColor(Color.WHITE);
            box53.setBackgroundColor(Color.WHITE);
            box54.setBackgroundColor(Color.WHITE);
            box55.setBackgroundColor(Color.WHITE);

            box61.setBackgroundColor(Color.WHITE);
            box62.setBackgroundColor(Color.WHITE);
            box63.setBackgroundColor(Color.WHITE);
            box64.setBackgroundColor(Color.WHITE);
            box65.setBackgroundColor(Color.WHITE);



            //make all rows invisible except 1
            LL1.setVisibility(View.VISIBLE);
            LL2.setVisibility(View.INVISIBLE);
            LL3.setVisibility(View.INVISIBLE);
            LL4.setVisibility(View.INVISIBLE);
            LL5.setVisibility(View.INVISIBLE);
            LL6.setVisibility(View.INVISIBLE);

            box11.setEnabled(true);
            box12.setEnabled(true);
            box13.setEnabled(true);
            box14.setEnabled(true);
            box15.setEnabled(true);
            //set guess to 1
            numPlays = 1;
        }
        public void restartGame(){
            clearGame();
            newRandomWord();
        }
    public void colorBoxes(EditText et1, EditText et2, EditText et3, EditText et4, EditText et5){
        randomWord = randomWord.toLowerCase();
        String[] wordArr = new String[5];
        wordArr[0] = randomWord.substring(0,1);
        wordArr[1] = randomWord.substring(1,2);
        wordArr[2] = randomWord.substring(2,3);
        wordArr[3] = randomWord.substring(3,4);
        wordArr[4] = randomWord.substring(4);

        List<String> wordArrList = Arrays.asList(wordArr);

        String et1Text = et1.getText().toString().toLowerCase();
        String et2Text = et2.getText().toString().toLowerCase();
        String et3Text = et3.getText().toString().toLowerCase();
        String et4Text = et4.getText().toString().toLowerCase();
        String et5Text = et5.getText().toString().toLowerCase();

        if(wordArr[0].equals(et1Text)){
            et1.setBackgroundColor(Color.GREEN);
        }
        if(wordArr[1].equals(et2Text)){
            et2.setBackgroundColor(Color.GREEN);
        }
        if(wordArr[2].equals(et3Text)){
            et3.setBackgroundColor(Color.GREEN);
        }
        if(wordArr[3].equals(et4Text)){
            et4.setBackgroundColor(Color.GREEN);
        }
        if(wordArr[4].equals(et5Text)){
            et5.setBackgroundColor(Color.GREEN);
        }

        if(!wordArr[0].equals(et1Text) && wordArrList.contains(et1Text)){
            et1.setBackgroundColor(Color.YELLOW);
        }
        if(!wordArr[1].equals(et2Text) && wordArrList.contains(et2Text)){
            et2.setBackgroundColor(Color.YELLOW);
        }
        if(!wordArr[2].equals(et3Text) && wordArrList.contains(et3Text)){
            et3.setBackgroundColor(Color.YELLOW);
        }
        if(!wordArr[3].equals(et4Text) && wordArrList.contains(et4Text)){
            et4.setBackgroundColor(Color.YELLOW);
        }
        if(!wordArr[4].equals(et5Text) && wordArrList.contains(et5Text)){
            et5.setBackgroundColor(Color.YELLOW);
        }

        if(!wordArr[0].equals(et1Text) && !wordArrList.contains(et1Text)){
            et1.setBackgroundColor(Color.GRAY);
        }
        if(!wordArr[1].equals(et2Text) && !wordArrList.contains(et2Text)){
            et2.setBackgroundColor(Color.GRAY);
        }
        if(!wordArr[2].equals(et3Text) && !wordArrList.contains(et3Text)){
            et3.setBackgroundColor(Color.GRAY);
        }
        if(!wordArr[3].equals(et4Text) && !wordArrList.contains(et4Text)){
            et4.setBackgroundColor(Color.GRAY);
        }
        if(!wordArr[4].equals(et5Text) && !wordArrList.contains(et5Text)){
            et5.setBackgroundColor(Color.GRAY);
        }

    }

    }


    //then make game

