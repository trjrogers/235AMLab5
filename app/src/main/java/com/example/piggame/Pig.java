package com.example.piggame;

public class Pig {
    private Player player1;
    private Player player2;
    private Die die;
    private Integer whoseTurn;
    private Integer pointsThisTurn;
    private Integer lastRolledNumber;
    private Boolean player1Win;
    private Boolean endGame;
    private Boolean gameOver;
    private Boolean ai = false;
    private int maxTurnPoints;
    private int maxRolls;

    public Pig(String player1Name, String player2Name, int dieSides) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.die = new Die(dieSides);
        this.whoseTurn = 1;
        this.pointsThisTurn = 0;
        this.lastRolledNumber = 0;
        this.player1Win = false;
        this.endGame = false;
        this.gameOver = false;
    }

    public String GetPlayerName(Integer playerNumber){
        if(playerNumber == 1) {
            return GetPlayer1Name();
        } else {
            return GetPlayer2Name();
        }
    }

    public int RollAndCalculate() {
        if (ai && whoseTurn == 2) {
            int lastRolled = 0;
            int timesRolled = 0;
            while ((pointsThisTurn <= maxTurnPoints) && (lastRolled != 1) && timesRolled < maxRolls) {
                int rolled = this.rollDie();
                if (rolled != 1) {
                    UpdatePoints(rolled);
                    lastRolledNumber = rolled;
                } else {
                    resetPointsThisTurn();
                    lastRolledNumber = rolled;
                    return rolled;
                }
            } return lastRolledNumber;
        } else {
            int rolled = this.rollDie();
            if (rolled != 1) {
                UpdatePoints(rolled);
                lastRolledNumber = rolled;
                return rolled;
            } else {
                resetPointsThisTurn();
                lastRolledNumber = rolled;
                return rolled;
            }
        }
    }

    private int rollDie() {
        return this.die.Roll();
    }

    private void resetPointsThisTurn() {
        this.pointsThisTurn = 0;
    }

    private void SwitchTurns() {
        if (this.whoseTurn == 1) {
//            turnLabel.setText(player2Label.getText() + "'s turn");
//            player1Turn = false;
        } else {
//            turnLabel.setText(player1Label.getText() + "'s turn");
//            player1Turn = true;
        }
        this.SetTurn();
        pointsThisTurn = 0;
        UpdatePoints(pointsThisTurn);
//        EnableRollButton();
    }

    private void UpdatePoints(int points) {
        pointsThisTurn += points;
//        this.pointsThisTurnTV.setText(String.valueOf(pointsThisTurn));
    }

    public void SetTurn() {
        if (whoseTurn == 1) {
            whoseTurn = 2;
        } else {
            whoseTurn = 1;
        }
    }

    public void EndTurn() {
        if (!endGame) {
            if (this.whoseTurn == 1) {
//                DisableRollButton();
                this.player1.AddScore(pointsThisTurn);
//                player1ScoreTV.setText(String.valueOf(pig.player1.score));
                CheckForWinner();
                SwitchTurns();
//                EnableRollButton();
            } else {
//                DisableRollButton();
//                this.player2.score += pointsThisTurn;
                this.player2.AddScore(pointsThisTurn);
//                player2ScoreTV.setText(String.valueOf(pig.player2.score));
                CheckForWinner();
                SwitchTurns();
//                EnableRollButton();
            }
        } else {
            if (this.player1.GetScore() > this.player2.GetScore()) {
                gameOver = true;
                player1Win = true;
//                Toast.makeText(getApplicationContext(),player1Label.getText() + " wins!",Toast.LENGTH_LONG).show();
            } else if (this.player2.GetScore() > this.player1.GetScore()) {
                gameOver = true;
                player1Win = false;
//                Toast.makeText(getApplicationContext(),player2Label.getText() + " wins!",Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getApplicationContext(),"Tie!",Toast.LENGTH_LONG).show();
            }
//            DisableRollButton();
//            DisableEndTurnButton();
        }
    }

    private void CheckForWinner() {
        if (this.player1.GetScore() >= 100) {
            endGame = true;
        } else if (this.player2.GetScore() >= 100) {
            endGame = true;
        } else {
            endGame = false;
        }
    }

    public String GetPlayer1Name() {
        return player1.GetName();
    }

    public String GetPlayer2Name() {
        return player2.GetName();
    }

    public int GetPlayer1Score() {
        return player1.GetScore();
    }

    public int GetPlayer2Score() {
        return player2.GetScore();
    }

    public int WhoseTurn() {
        return whoseTurn;
    }

    public int GetPointsThisTurn() {
        return pointsThisTurn;
    }

    public boolean IsGameOver() {
        if(gameOver) {
            return true;
        } else return false;
    }

    public boolean DidPlayer1Win() {
        if(player1Win) {
            return true;
        } else return false;
    }

    public Boolean GetEndGame() {
        return endGame;
    }

    public void SetPlayer1Values(String name, int score) {
        this.player1.SetName(name);
        this.player1.AddScore(score);
    }

    public void SetPlayer2Values(String name, int score) {
        this.player2.SetName(name);
        this.player2.AddScore(score);
    }

    public void SetPointsThisTurn(int points) {
        this.pointsThisTurn = points;
    }

    public void SetWhoseTurn(int who) {
        if(who == 1) {
            whoseTurn = 1;
        } else {
            whoseTurn = 2;
        }
    }

    public void SetEndGame(boolean e) {
        if(e) {
            endGame = true;
        } else endGame = false;
    }

    public int GetLastRolled() {
        return this.lastRolledNumber;
    }

    public void EnableAI() {
        ai = true;
    }

    public void DisableAI() {
        ai = false;
    }

    public void SetMaxRolls(int max) {
        this.maxRolls = max;
    }

    public void SetMaxTurnPoints(int max) {
        this.maxTurnPoints = max;
    }
}