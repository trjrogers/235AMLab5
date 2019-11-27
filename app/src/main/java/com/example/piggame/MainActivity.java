package com.example.piggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//     UI Instance variables
    private TextView player1Label;
    private TextView player2Label;
    private EditText player1EditText;
    private EditText player2EditText;
    private TextView player1ScoreTV;
    private TextView player2ScoreTV;
    private TextView turnLabel;
    private ImageView dieImage;
    private TextView pointsThisTurnTV;
    private Button rollButton;
    private Button endTurnButton;
    private Button backButton;
    private Button newGameButton;

    private Pig pig;

    private SharedPreferences savedValues;
    private SharedPreferences prefs;
    private boolean ai = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.player1Label = findViewById(R.id.player1Label);
        this.player2Label = findViewById(R.id.player2Label);
        this.player1EditText = findViewById(R.id.player1EditText);
        this.player2EditText = findViewById(R.id.player2EditText);
        this.player1ScoreTV = findViewById(R.id.player1ScoreTV);
        this.player2ScoreTV = findViewById(R.id.player2ScoreTV);
        this.turnLabel = findViewById(R.id.turnLabel);
        this.dieImage = findViewById(R.id.dieImage);
        this.pointsThisTurnTV = findViewById(R.id.pointsThisTurnTV);
        this.rollButton = findViewById(R.id.rollButton);
        this.endTurnButton = findViewById(R.id.endTurnButton);
        this.newGameButton = findViewById(R.id.newGameButton);

        this.newGameButton.setOnClickListener(this);
        this.rollButton.setOnClickListener(this);
        this.endTurnButton.setOnClickListener(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        DisableRollButton();
        DisableEndTurnButton();

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // Save instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("player1Label", String.valueOf(player1Label.getText()));
        editor.putInt("player1Score", Integer.valueOf(String.valueOf(pig.GetPlayer1Score())));
        editor.putString("player2Label", String.valueOf(player2Label.getText()));
        editor.putInt("player2Score", Integer.valueOf(String.valueOf(pig.GetPlayer2Score())));
        editor.putInt("currentTurnPoints", pig.GetPointsThisTurn());
        editor.putInt("whoseTurn", pig.WhoseTurn());
        editor.putBoolean("endGame", pig.GetEndGame());
        editor.putBoolean("gameOver", pig.IsGameOver());
        editor.putString("turnLabel", String.valueOf(turnLabel.getText()));
        editor.putInt("lastRolledNumber", pig.GetLastRolled());
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        pig = new Pig("Human", "Computer", 6);

        player1Label.setText(savedValues.getString("player1Label", ""));

        pig.SetPlayer1Values(savedValues.getString("player1Label", "Player 1"), savedValues.getInt("player1Score", 0));
        player1ScoreTV.setText(String.valueOf(pig.GetPlayer1Score()));
        player2Label.setText(savedValues.getString("player2Label", ""));
        pig.SetPlayer2Values(savedValues.getString("player2Label", "Player 2"), savedValues.getInt("player2Score", 0));
        player2ScoreTV.setText(String.valueOf(pig.GetPlayer2Score()));
        pointsThisTurnTV.setText(String.valueOf(savedValues.getInt("currentTurnPoints", 0)));
        pig.SetWhoseTurn(savedValues.getInt("whoseTurn", 1));
        if(pig.WhoseTurn() == 1) {
            turnLabel.setText(pig.GetPlayer1Name() + "'s turn");
        } else {
            turnLabel.setText(pig.GetPlayer2Name() + "'s turn");
        }

        pig.SetPointsThisTurn(savedValues.getInt("currentTurnPoints", 0));
        pig.SetEndGame(savedValues.getBoolean("gameOver", false));
        UpdateImage(savedValues.getInt("lastRolledNumber", 1));

        ai = prefs.getBoolean("pref_ai", false);
        if(ai){
            pig.EnableAI();
        } else {
            pig.DisableAI();
        }
        pig.SetMaxTurnPoints(Integer.parseInt(prefs.getString("pref_max_score", "100")));
        pig.SetMaxRolls(Integer.parseInt(prefs.getString("pref_roll_number", "1")));
        if(!pig.IsGameOver()) {
            EnableRollButton();
            EnableEndTurnButton();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pig_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.newGameButton:
                String p1name = String.valueOf(player1EditText.getText());
                String p2name = String.valueOf(player2EditText.getText());
                if(p1name.equals("")) {
                    p1name = "Player 1";
                }
                if(p2name.equals("")) {
                    p2name = "Player 2";
                }
                pig = new Pig(p1name, p2name, 6);
                ai = prefs.getBoolean("pref_ai", false);
                if(ai){
                    pig.EnableAI();
                } else {
                    pig.DisableAI();
                }
                pig.SetMaxTurnPoints(Integer.parseInt(prefs.getString("pref_max_score", "100")));
                pig.SetMaxRolls(Integer.parseInt(prefs.getString("pref_roll_number", "1")));
                player1Label.setText(pig.GetPlayerName(1));
                player2Label.setText(pig.GetPlayerName(2));
                turnLabel.setText(pig.GetPlayerName(pig.WhoseTurn()));
                player1ScoreTV.setText("0");
                player2ScoreTV.setText("0");
                pointsThisTurnTV.setText("0");
                EnableRollButton();
                EnableEndTurnButton();
                break;
            case R.id.rollButton:
                ai = prefs.getBoolean("pref_ai", false);
                if(ai) {
                    pig.EnableAI();
                } else {
                    pig.DisableAI();
                }
                pig.SetMaxRolls(Integer.parseInt(prefs.getString("pref_roll_number", "1")));
                pig.SetMaxTurnPoints(Integer.parseInt(prefs.getString("pref_max_score", "100")));

                DisableRollButton();
                int rolled = pig.RollAndCalculate();
                UpdateImage(rolled);
                if(rolled == 1) {
                    pig.SetTurn();
                    pointsThisTurnTV.setText(String.valueOf(pig.GetPointsThisTurn()));
                    if(pig.WhoseTurn() == 1) {
                        turnLabel.setText(String.valueOf(pig.GetPlayer1Name()));
                    } else {
                        turnLabel.setText(String.valueOf(pig.GetPlayer2Name()));
                    }
                    EnableRollButton();
                } else {
                    pointsThisTurnTV.setText(String.valueOf(pig.GetPointsThisTurn()));
                    EnableRollButton();
                }
                break;
            case R.id.endTurnButton:
                DisableEndTurnButton();
                int turn;
                if(pig.WhoseTurn() == 1){
                    turn = 1;
                } else {
                    turn = 2;
                }
                pig.EndTurn();
                if(turn == 1) {
                    player1ScoreTV.setText(String.valueOf(pig.GetPlayer1Score()));
                } else {
                    player2ScoreTV.setText(String.valueOf(pig.GetPlayer2Score()));
                }
                if(pig.WhoseTurn() == 1) {
                    turnLabel.setText(String.valueOf(pig.GetPlayer1Name()));
                } else {
                    turnLabel.setText(String.valueOf(pig.GetPlayer2Name()));
                }
                EnableRollButton();
                EnableEndTurnButton();

                if (pig.IsGameOver()) {
                    DisableRollButton();
                    DisableEndTurnButton();
                    if(pig.DidPlayer1Win()) {
                        Toast.makeText(this, pig.GetPlayer1Name() + " wins!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this,pig.GetPlayer2Name() + " wins!", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void UpdateImage(int rolled) {
        int ref = 0;
        switch(rolled){
            case 1: {
                ref = R.drawable.die1;
//                dieNumber = 1;
                break;
            }
            case 2: {
                ref = R.drawable.die2;
//                dieNumber = 2;
                break;
            }
            case 3: {
                ref = R.drawable.die3;
//                dieNumber = 3;
                break;
            }
            case 4: {
                ref = R.drawable.die4;
//                dieNumber = 4;
                break;
            }
            case 5: {
                ref = R.drawable.die5;
//                dieNumber = 5;
                break;
            }
            case 6: {
                ref = R.drawable.die6;
//                dieNumber = 6;
                break;
            }default: {
                ref = -1;
                break;
            }
        }
        try {
            Drawable drawable = getResources().getDrawable(ref);
            this.dieImage.setImageDrawable(drawable);
        } catch (Exception e) {
            ref = R.drawable.die1;
            Drawable drawable = getResources().getDrawable(ref);
            this.dieImage.setImageDrawable(drawable);
        }
    }

    private void DisableRollButton() {
        this.rollButton.setEnabled(false);
    }

    private void DisableEndTurnButton() {
        this.endTurnButton.setEnabled(false);
    }

    private void EnableEndTurnButton() {
        this.endTurnButton.setEnabled(true);
    }

    private void EnableRollButton() {
        this.rollButton.setEnabled(true);
    }

}
