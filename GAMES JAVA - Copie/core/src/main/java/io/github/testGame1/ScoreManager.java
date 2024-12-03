package io.github.testGame1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {
    private int score;
    private int highScore;
    private String username;
    private DatabaseManager databaseManager;

    public ScoreManager(String username) {
        this.username = username;
        this.databaseManager = new DatabaseManager();
        this.highScore = databaseManager.getHighScore(username);
        this.score = 0;
    }

    //Met à jour le meilleur score localement
    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
        }
    }

    //Sauvegarde le score actuel dans la base de données
    public void saveCurrentScore() {
        databaseManager.saveScore(username, score);
    }

    public void resetScore() {
        score = 0;
    }

    public int getCurrentScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getScoreDisplay() {
        return "Score: " + score + " | Best: " + highScore;
    }
    //Sauvegarde dans la base si le score a changé
    public void saveHighScore() {
        databaseManager.saveScore(username, highScore);
    }
}
