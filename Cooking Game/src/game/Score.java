package game;

import main.GamePanel;

public class Score {

    private static Score instance;

    GamePanel gp;
    private int totalScore;

    private Score(GamePanel gp) {
        this.gp = gp;
        totalScore = 0; // set score to 100 for test
    }
    public static Score instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new Score(gp);
        }
        return instance;
    }

    public void addScore(int points) {
        totalScore += points;
        System.out.println("Score updated: " + totalScore);
    }

    public void deductScore(int points) {
        totalScore -= points;
        if (totalScore <= 0) {
            totalScore = 0;
        }
        // totalScore -= (totalScore <= 0 ? 0 : points);
        System.out.println("Score updated: " + totalScore);
    }

    public int getTotalScore() {
        return totalScore;
    }

}
