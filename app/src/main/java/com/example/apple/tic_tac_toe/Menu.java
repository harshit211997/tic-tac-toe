package com.example.apple.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends Activity {

    AutoCompleteTextView p1input;
    AutoCompleteTextView p2input;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = (Button) findViewById(R.id.playButton);

        p1input = (AutoCompleteTextView)findViewById(R.id.p1input);
        p2input = (AutoCompleteTextView)findViewById(R.id.p2input);

        MyDBHandler handler = new MyDBHandler(this, null, null, 1);

        String names[] = handler.names();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        p1input.setAdapter(adapter);
        p2input.setAdapter(adapter);

        p1input.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            p2input.requestFocus();
                            return true;
                        }
                        return false;
                    }
                }
        );
        p2input.setOnKeyListener(
                new View.OnKeyListener(){
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            playClicked(playButton);
                            return true;
                        }
                        return false;
                    }
                }
        );
    }
    public void playClicked(View view)
    {
        Intent i = new Intent(this, MainActivity.class);

        if(p1input.getText().toString().equals("")||p2input.getText().toString().equals(""))
        {
            Toast.makeText(this, "Player's name cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(p1input.getText().toString().equals(p2input.getText().toString()))
        {
            Toast.makeText(this, "Please enter different names for the two players", Toast.LENGTH_SHORT).show();
        }
        else
        {
            i.putExtra("player1", p1input.getText().toString());
            i.putExtra("player2", p2input.getText().toString());

            startActivity(i);
        }
    }
    public void highscoresClicked(View v)
    {
        Intent i = new Intent(this, Scores.class);
        startActivity(i);
    }
}
