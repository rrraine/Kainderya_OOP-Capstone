package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();

        // lets window close when user clicks "x" button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // disables resizable window
        window.setResizable(false);
        window.setTitle("Cooking Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // sets the window's size as specified in the GamePanel
        window.pack();

        // set window location at the screen center
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // LOAD OBJ IN WORLD
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}