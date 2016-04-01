package com.example.apple.tic_tac_toe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements Animation.AnimationListener{

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    TextView player;
    String p1, p2;
    ImageView icon;
    ImageView b[] = new ImageView[9];
    MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialising the database object
        handler = new MyDBHandler(this, null, null, 1);

        b[0] = (ImageView)findViewById(R.id.imageButton1);
        b[1] = (ImageView)findViewById(R.id.imageButton2);
        b[2] = (ImageView)findViewById(R.id.imageButton3);
        b[3] = (ImageView)findViewById(R.id.imageButton4);
        b[4] = (ImageView)findViewById(R.id.imageButton5);
        b[5] = (ImageView)findViewById(R.id.imageButton6);
        b[6] = (ImageView)findViewById(R.id.imageButton7);
        b[7] = (ImageView)findViewById(R.id.imageButton8);
        b[8] = (ImageView)findViewById(R.id.imageButton9);

        icon = (ImageView)findViewById(R.id.icon);

        //Setting tag of each element to clicked=false
        String tag = "nothing";
        for(int i=0;i<9;i++)
        {
            b[i].setTag(tag);
        }

        Bundle playerNames = getIntent().getExtras();
        if(playerNames == null)
        {
            return;
        }

        p1 = playerNames.getString("player1");
        p2 = playerNames.getString("player2");

        player = (TextView)findViewById(R.id.player);
        player.setText(p1);
        icon.setBackgroundResource(R.drawable.circle);
    }

    public void changePlayer()
    {
        if(player.getText().toString()==p1) {
            player.setText(p2);
            icon.setBackgroundResource(R.drawable.cross);
        }
        else
        {
            player.setText(p1);
            icon.setBackgroundResource(R.drawable.circle);
        }
    }

    public void tile1Clicked(View v)
    {
        tileClicked(R.id.imageButton1);
    }
    public void tile2Clicked(View v)
    {
        tileClicked(R.id.imageButton2);
    }
    public void tile3Clicked(View v)
    {
        tileClicked(R.id.imageButton3);
    }
    public void tile4Clicked(View v)
    {
        tileClicked(R.id.imageButton4);
    }
    public void tile5Clicked(View v)
    {
        tileClicked(R.id.imageButton5);
    }
    public void tile6Clicked(View v)
    {
        tileClicked(R.id.imageButton6);
    }
    public void tile7Clicked(View v)
    {
        tileClicked(R.id.imageButton7);
    }
    public void tile8Clicked(View v)
    {
        tileClicked(R.id.imageButton8);
    }
    public void tile9Clicked(View v)
    {
        tileClicked(R.id.imageButton9);
    }

    public void tileClicked(int ID)
    {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        ImageView tile = (ImageView)findViewById(ID);
        if((String)tile.getTag()=="nothing")
        {
            if(player.getText().toString()==p1)
            {
                tile.setBackgroundResource(R.drawable.circle);
                String tag = "circle";
                tile.setTag(tag);
            }
            else
            {
                tile.setBackgroundResource(R.drawable.cross);
                String tag = "cross";
                tile.setTag(tag);
            }
            tile.startAnimation(animation);
            //check if someone has won
            check();
            //changing the player name
            changePlayer();
        }
    }
    public void check()
    {
        String res[] = new String[9];
        for(int i=0;i<9;i++)
        {
            res[i] = (String)b[i].getTag();
        }
        if(res[0]==res[1]&&res[1]==res[2])
        {
            if(res[0]!="nothing")
                score(res[0]);
        }
        else if(res[3]==res[4]&&res[4]==res[5])
        {
            if(res[3]!="nothing")
                score(res[3]);
        }
        else if(res[6]==res[7]&&res[7]==res[8])
        {
            if(res[6]!="nothing")
                score(res[6]);
        }
        else if(res[0]==res[3]&&res[3]==res[6])
        {
            if(res[0]!="nothing")
                score(res[0]);
        }
        else if(res[1]==res[4]&&res[4]==res[7])
        {
            if(res[1]!="nothing")
                score(res[1]);
        }
        else if(res[2]==res[5]&&res[5]==res[8])
        {
            if(res[2]!="nothing")
                score(res[2]);
        }
        else if(res[0]==res[4]&&res[4]==res[8])
        {
            if(res[0]!="nothing")
                score(res[0]);
        }
        else if(res[2]==res[4]&&res[4]==res[6])
        {
            if(res[2]!="nothing")
                score(res[2]);
        }
        else if (isboardFull())
        {
            score("Draw");
        }
    }
    public void score(String p)
    {
        //Adding the scores data of the player into database
        Highscores player1;
        Highscores player2;
        String win = "";
        if(p=="circle")
        {
            player1 = new Highscores(1, p1);
            player2 = new Highscores(0, p2);
            win=p1 + " wins!!!";

        }
        else if(p=="cross")
        {
            player1 = new Highscores(0, p1);
            player2 = new Highscores(1, p2);
            win=p2 + " wins!!!";
        }
        else
        {
            player1 = new Highscores(0, p1);
            player2 = new Highscores(0, p2);
            win = "draw";
        }
        handler.addRow(player1);
        handler.addRow(player2);

        int score1 = handler.getScore(p1);
        int score2 = handler.getScore(p2);

        //Building a dialog box for showing of the scores of players
        Dialog dialog = new Dialog(this);

        //To make the background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.custom_dialog);
        TextView p1score = (TextView) dialog.findViewById(R.id.player1);
        TextView p2score = (TextView) dialog.findViewById(R.id.player2);
        TextView winner = (TextView) dialog.findViewById(R.id.winner);
        Button playAgain = (Button) dialog.findViewById(R.id.playAgain);
        Button mainMenu = (Button) dialog.findViewById(R.id.mainMenu);

        dialog.setCancelable(false);//So that the dialog box cannot be dismissed

        p1score.setText(p1 + " : " + score1);
        p2score.setText(p2 + " : " + score2);
        winner.setText(win);


        playAgain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);//Here we cannot write only this
                        i.putExtra("player1", p1);
                        i.putExtra("player2", p2);
                        MainActivity.this.finish();
                        startActivity(i);
                    }
                });
        mainMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.finish();
                    }
                }
        );
        dialog.show();
    }
    public void onBackPressed()
    {
        MainActivity.this.finish();
    }
    boolean isboardFull()
    {
        int count=0;
        for(int i=0;i<9;i++)
        {
            if(b[i].getTag()=="nothing")
                count++;
        }
        if(count==0)
            return true;
        return false;
    }
}
