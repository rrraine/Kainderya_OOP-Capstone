package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // ~ FIELDS
        JFrame window;
        GamePanel gamePanel;


        window = new JFrame(); // WINDOW FRAME ON WHICH GAMEPANEL RUNS
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ENABLES WINDOW TO BE CLOSED
        window.setResizable(false); // DISABLES RESIZABLE WINDOW
        window.setTitle("Cooking Game");

        gamePanel = new GamePanel();


        window.add(gamePanel); // RUN GAMEPANEL ON WINDOW
        window.pack(); // SETS WINDOW SIZE
        window.setLocationRelativeTo(null); // SET WINDOW AT CENTER OF SCREEN
        window.setVisible(true);


        gamePanel.setUpGame(); // PRELOAD NECESSARY STUFF IN WORLD BEFORE GAME STARTS
        gamePanel.startGameThread(); // START GAME

        // DEBUGGING PURPOSES ------------------------------------
        System.out.println("\nNEW FEATURE: Press ESC to Pause");
    }
}