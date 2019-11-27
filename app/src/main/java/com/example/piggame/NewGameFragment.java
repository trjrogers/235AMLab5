package com.example.piggame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Fragment;

public class NewGameFragment extends Fragment {
    private TextView player1Label;
    private TextView player2Label;
    private EditText player1EditText;
    private EditText player2EditText;
    private TextView gameInstructionsTextView;
    private Button newGameButton;

    private SharedPreferences savedValues;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_game, container, false);

        this.player1Label = view.findViewById(R.id.player1Label);
        this.player2Label = view.findViewById(R.id.player2Label);
        this.player1EditText = view.findViewById(R.id.player1EditText);
        this.player2EditText = view.findViewById(R.id.player2EditText);
        this.gameInstructionsTextView = view.findViewById(R.id.gameInstructionsTextView);
        this.newGameButton = view.findViewById(R.id.newGameButton);

//        this.newGameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_new_game, new PigGameFragment())
//                        .commit();
//
//
//            }
//        });
//        this.newGameButton.setOnClickListener(this);
        return view;
    }

//    @Override
//    public void onClick(View v) {
//        switch(v.getId()) {
//            case R.id.newGameButton:
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_new_game, new PigGameFragment())
//                        .commit();
//                PigGameFragment.
//                break;
//        }
//    }
    // for new game method
    //            player1ScoreTV.setText("0");
//        player2ScoreTV.setText("0");
//        pointsThisTurnTV.setText("0");
}
