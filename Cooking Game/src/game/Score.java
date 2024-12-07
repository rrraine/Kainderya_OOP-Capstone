package game;

public class Score {
    private int totalScore;

    public Score() {
        totalScore = 0; // set score to 100 for test
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
