package edu.nyu.cs101.android.rz1145;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class HangMan {
    public HangMan(){
        this("banana");
    }
    public HangMan(String fruit){
        puzzle = fruit.toLowerCase();
        puzzle_len = puzzle.length();
        status = new Vector<Boolean>(puzzle_len);
        status.setSize(puzzle_len);
        Collections.fill(status, false);
    }
    private final String puzzle;
    private final Integer puzzle_len;
    private Vector<Boolean> status;
    private boolean completed = false;

    public String toString() {
        boolean success = true;
        String result = "";
        for(int i=0; i<puzzle_len; i++) {
            char cur_char = '_';
            if (status.elementAt(i))
                cur_char = puzzle.charAt(i);
            else
                success = false;
            result += cur_char;
            result += " ";
        }
        if (success) {
            completed=true;
        }
        return result;
    }

    public boolean checkComplete() {
        this.toString();
        return completed;
    }
    public void update_status(char c) {
        for(int i=0; i<puzzle_len; i++) {
            if (puzzle.charAt(i) == c) {
                status.setElementAt(true, i);
            }
        }
    }
    public String peek(){
        return puzzle;
    }
}

public class MainActivity extends AppCompatActivity {
    Button button;
    Button bNew;
    EditText editText;
    TextView showText;
    char currentChoice;
    HangMan hangmanGame;
    List<String> fruit_dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateHangman();
        Log.v("HangmanGame", hangmanGame.peek());

        button = (Button) findViewById(R.id.button);
        bNew = (Button) findViewById(R.id.button_new);
        editText = (EditText) findViewById(R.id.editText);
        showText = (TextView) findViewById(R.id.showtext);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editted_text = editText.getText().toString();
                        Log.v("EditText", editted_text);
                        if (editted_text.length()==0) {
                            alertMe("Warning", "WriteSth");
                            return;
                        }

                        currentChoice = editText.getText().toString().charAt(0);
                        hangmanGame.update_status(currentChoice);
                        showText.setText(hangmanGame.toString());
                        Log.v("HangmanGame", hangmanGame.toString());
                        if (hangmanGame.checkComplete()) {
                            alertMe("Cong", "Smart Guess");
                            //startActivities(new Intent(MainActivity.this, new PopupWindow()));
                        }
                    }
                }
        );

        bNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initiateHangman();
                        showText.setText(hangmanGame.toString());
                    }
                }
        );
    }
    private void alertMe(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        alert.setTitle(title);
        alert.setMessage(message);
        alert.create().show();
    }
    private void initiateHangman(){
        List<String> fruit_dictionary = Arrays.asList(
                "apple", "orange", "pineapple",
                "watermelon", "durian", "banana",
                "blackberry", "cranberry", "strawberry",
                "kiwifruit", "papaya", "avocado");
        Random rand = new Random();
        String puzzle_word = fruit_dictionary.get(rand.nextInt(fruit_dictionary.size()));

        hangmanGame = new HangMan(puzzle_word);


    }
}
