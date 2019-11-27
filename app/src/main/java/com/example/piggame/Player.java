package com.example.piggame;

public class Player {
    private Integer score;
    private String name;

    public Player(String playerName) {
        this.name = playerName;
        this.score = 0;
    }

    public String GetName() {
        return this.name;
    }

    public void SetName(String playerName) {
        this.name = playerName;
    }

    public int GetScore() {
        return this.score;
    }
//
    public void AddScore(int newScore) {
        this.score = score + newScore;
    }
}
