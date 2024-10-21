package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

// works as the game screen
// The Runnable Interface implements functions that are to be executed by a Thread
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 5; // scale to screen res
    public final int tileSize = originalTileSize * scale; // ACTUAL TILE: 80 x 80 pixels

    // ASPECT RATIO: 16:9
    public final int maxScreenCol = 16; // 16 tiles per row
    public final int maxScreenRow = 9; // 9 tiles per column

    // TOTAL SCREEN RESOLUTION: 1536 x 864 pixels
    public final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 720 pixels

    // GAME VARIABLES
    Thread gameThread; // CONCEPT OF TIME FOR PROGRAM TASKS EXECUTION
    KeyHandler keyH = new KeyHandler(); // KEYBOARD INPUTS
    TileManager tileM = new TileManager(this);
    final int FPS = 60;

    // PLAYER OBJECT
    Player player = new Player(this, keyH);


    public GamePanel() {

        // sets the size of the game panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        this.setDoubleBuffered(true); // not sure what this does
        this.addKeyListener(keyH); // listens for key inputs
        this.setFocusable(true); // GamePanel can be "focused" to receive key inputs
    }

    public void startGameThread() {

        gameThread = new Thread(this); // pass the entire class to the gameThread
        gameThread.start(); // automatically calls the run method of Runnable Interface
    }
    @Override
    public void run() {

        // DELTA ACCUMULATOR METHOD
        double drawInterval = (double) 1000000000 / FPS; // draws the screen 60 times every 0.016 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        // GAME LOOP: Core of the game
        while (gameThread != null) {

            currentTime = System.nanoTime(); // gets current time in nanoseconds: 1 billion ns = 1 sec
            delta += (currentTime - lastTime) / drawInterval;

            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1) {
                update(); // 1) UPDATE: update info like character positions
                repaint(); // 2) DRAW: draw the screen with updated info thru paintComponent()
                delta--;

                drawCount++;
            }

            // DEBUGGING PURPOSES ONLY: Show FPS
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        player.update();
    }
    public void paintComponent(Graphics g) {

        // this method is a built-in from JComponent Class
        // Graphics Class: contains functions to draw objects on screen
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // typecast Graphics Class to provide better 2D attributes

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose(); // good practice to dispose this graphics to release any system resources it was using and save memory
    }
}