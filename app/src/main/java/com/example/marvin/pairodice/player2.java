package com.example.marvin.pairodice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class player2 extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll, hold;
    private int score, checkScore=0;
    private int scoreFinal =0;
    private TextView roundScore, p1score, p2score;
    private String totalScore;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.player2);

        //Dice
        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);
        //Textview elements
        roundScore = (TextView) findViewById(R.id.round);
        p1score = (TextView) findViewById(R.id.p1score);
        p2score = (TextView) findViewById(R.id.p2score);
        //Button to roll dice
        roll = (Button) findViewById(R.id.button);
        //Press hold to save your current score.
        hold = (Button) findViewById(R.id.hold);

        //This is the score coming
        Intent intent = getIntent();
        String p1 = intent.getStringExtra("score1");
        String p2 = intent.getStringExtra("score2");
        p1score.setText(p1);
        p2score.setText(p2);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();

            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stateToSave = p1score.getText().toString();
                String stateToSave2 = p2score.getText().toString();
                String p2 = roundScore.getText().toString();

                if (checkScore > 99) {
                    AlertDialog alertDialog = new AlertDialog.Builder(player2.this).create();
                    alertDialog.setTitle("Game Over!");
                    alertDialog.setMessage("Congratulations you Won!");
                    alertDialog.setButton("New Game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        }
                    });
                    alertDialog.setButton2("Give up!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });
                    alertDialog.show();
                } else if (stateToSave2 == "" || stateToSave2 == null) {
                    //call the score from the top
                    scoreFinal = Integer.parseInt(p2) + 0;
                    Intent intent = new Intent(player2.this, MainActivity.class);
                    intent.putExtra("score1", stateToSave);
                    intent.putExtra("score2", String.valueOf(scoreFinal));
                    startActivity(intent);
                } else {
                    scoreFinal = Integer.parseInt(p2);
                    Intent intent = new Intent(player2.this, MainActivity.class);
                    intent.putExtra("score1", stateToSave);
                    intent.putExtra("score2", String.valueOf(scoreFinal));
                    startActivity(intent);
                }

            }
        });


    }


    //Get two Random Numbers change the dice to have the appropriate image
    public void rollDice() {

        int roll1 = 1 + (int) (6 * Math.random());
        int roll2 = 1 + (int) (6 * Math.random());

        //Check if a die gives you, reset score. Otherwise accumulate the score.
        if (roll1 == 1 || roll2 == 1) {
            score = 0;
            String stateToSave1 = p2score.getText().toString();
            String stateToSave2 = p2score.getText().toString();
            Intent intent = new Intent(player2.this, MainActivity.class);
            intent.putExtra("score1", stateToSave1);
            intent.putExtra("score2", stateToSave2);
            startActivity(intent);
        } else {
            score += roll1 + roll2;
            roundScore.setText(String.valueOf(score));
        }

        String stateToSave = p1score.getText().toString();
        String stateToSave2 = p2score.getText().toString();
        if(stateToSave2==null|| stateToSave2=="0")
        {
            checkScore = score + 0;
        } else checkScore = score;


        setDie1(roll1, die1);
        setDie1(roll2, die2);
    }

    //Set the appropriate image to the Frameview for an int
    public void setDie1(int value, FrameLayout die) {
        Drawable pick = null;

        switch (value) {
            case 1:
                pick = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pick = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pick = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pick = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pick = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pick = getResources().getDrawable(R.drawable.die_face_6);
                break;

        }
        die.setBackground(pick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        //    finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
