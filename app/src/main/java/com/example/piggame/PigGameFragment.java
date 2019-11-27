package com.example.piggame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Fragment;

import com.example.piggame.R;

public class PigGameFragment extends Fragment {
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
    public Pig pig;

    private SharedPreferences prefs;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);
//        pig = new Pig();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pig_game, container, false);

        this.player1Label = view.findViewById(R.id.player1Label);
        this.player2Label = view.findViewById(R.id.player2Label);
        this.player1EditText = view.findViewById(R.id.player1EditText);
        this.player2EditText = view.findViewById(R.id.player2EditText);
        this.player1ScoreTV = view.findViewById(R.id.player1ScoreTV);
        this.player2ScoreTV = view.findViewById(R.id.player2ScoreTV);
        this.turnLabel = view.findViewById(R.id.turnLabel);
        this.dieImage = view.findViewById(R.id.dieImage);
        this.pointsThisTurnTV = view.findViewById(R.id.pointsThisTurnTV);
        this.rollButton = view.findViewById(R.id.rollButton);
        this.endTurnButton = view.findViewById(R.id.endTurnButton);
        this.backButton = view.findViewById(R.id.backButton);

//        this.rollButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity.Roll();
//            }
//        });

//        this.endTurnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EndTurn();
//            }
//        });

        player1ScoreTV.setText("0");
        player2ScoreTV.setText("0");
        pointsThisTurnTV.setText("0");

//        this.backButton.setOnClickListener(this);
        return view;
    }
//
//    @Override
//    public void onClick(View v) {
//        switch(v.getId()) {
//            case R.id.backButton:
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_new_game, new PigGameFragment())
//                        .commit();
//                break;
//        }
//    }
}
