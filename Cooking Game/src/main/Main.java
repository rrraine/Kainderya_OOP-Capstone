package main;

import javax.swing.*;

public class Main {

    static JFrame window;

    public static void main(String[] args) {

        // ~ FIELDS
        GamePanel gamePanel;


        window = new JFrame(); // WINDOW FRAME ON WHICH GAME PANEL RUNS
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ENABLES WINDOW TO BE CLOSED
        window.setResizable(false); // DISABLES RESIZABLE WINDOW
        window.setTitle("Cooking Game");
        window.setUndecorated(true);

        gamePanel = GamePanel.initialize();


        window.add(gamePanel); // RUN GAME PANEL ON WINDOW
        window.pack(); // SETS WINDOW SIZE
        window.setLocationRelativeTo(null); // SET WINDOW AT CENTER OF SCREEN
        window.setVisible(true);


        gamePanel.setUpGame(); // PRELOAD NECESSARY STUFF IN WORLD BEFORE GAME STARTS
        gamePanel.startGameThread(); // START GAME

        // DEBUGGING PURPOSES ------------------------------------
    }
}