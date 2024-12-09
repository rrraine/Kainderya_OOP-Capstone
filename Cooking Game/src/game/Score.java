package game;

import main.GamePanel;

import java.io.*;

public class Score {

    private static Score instance;

    GamePanel gp;
    private int totalScore;
    private int pastScore;

    private Score(GamePanel gp) {
        this.gp = gp;
        totalScore = 0; // set score to 100 for test

        pastScore = retrieveCRUDScore();
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
    public int getPastScore() { return pastScore; }

    // CRUD READERS
    public void updateHighestScore(int finalScore) {

        String folderPath = "CRUD";
        String fileName = folderPath + "/highestScore.txt";

        // CREATE CRUD FOLDER IF NOT EXISTS
        new File(folderPath).mkdir();

        int highestScore = 0;

        // READ EXISTING HIGHEST SCORE
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                if (line != null) {
                    highestScore = Integer.parseInt(line); // Parse score from file
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error reading highest score: " + e.getMessage());
            }
        }

        // COMPARE SCORES AND UPDATE IF FINAL SCORE IS HIGHER
        if (finalScore > highestScore) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName), 1024)) {
                bw.write(Integer.toString(finalScore)); // Write the new highest score
                System.out.println("Updated highest score to: " + finalScore);
            } catch (IOException e) {
                System.err.println("File writing error: " + e.getMessage());
            }
        } else {
            System.out.println("Final score is not higher than the current highest score.");
        }
    }
    public int retrieveCRUDScore() {

        String fileName = "CRUD/highestScore.txt";
        int highestScore = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Read the first line
            if (line != null) {
                highestScore = Integer.parseInt(line); // Convert to int
            }
        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from file.");
        }

        return highestScore;
    }

    public void resetParams() {

        instance = null;
    }
}
