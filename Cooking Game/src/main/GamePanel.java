package main;

import entity.NPC;
import entity.Player;
import game.Time;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // ~ FIELDS ---------------------------------------------------------------------------

    // FRAME RATE
    public static final int FPS = 60;

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale; // ACTUAL TILE: 48 x 48

    // ASPECT RATIO
    private final int maxScreenCol = 20; // (16) TILES PER ROW
    private final int maxScreenRow = 12; // (12) TILES PER COL

    // STANDARD SCREEN RESOLUTION: 1
    public final int screenWidth = tileSize * maxScreenCol; // 960
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FULL SCREEN RESOLUTION: 2
    private int fullScreenWidth = screenWidth;
    private int fullScrenHeight = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // GAME SETTINGS SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    Time time = new Time(this);
    UI ui = new UI(this, time);
    Thread gameThread;

    // OBJECTS AND ENTITY
    public Player player = new Player(this, keyH);
    private final List<NPC> npc = new ArrayList<>();
    private final List<SuperObject> obj = new ArrayList<>();

    // GAME STATE
    //public int gameState;
//    public final int playState = 1;
//    public final int pauseState = 2;
//    public final int optionState = 3;
    public gameState state;
    public enum gameState { HOME, PLAY, PAUSE, OPTIONS }

    // ~ FIELDS END HERE
    // ~ METHODS ---------------------------------------------------------------------------

    // CONSTRUCTOR ---------------------------------------------------------------------------
    public GamePanel() {

        // SET DIMENSIONS AND COLOR OF THE FRAME
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        // LISTEN FOR KEYSTROKES
        this.addKeyListener(keyH);
        // ALLOWS RECEIVING OF KEYSTROKES
        this.setFocusable(true);
    }


    // FROM INTERFACE: RUNNABLE ----------------------------------------------------------------
    @Override
    public void run() {
        // a method of Runnable interface

        // DELTA ACCUMULATOR METHOD
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        // GAME LOOP
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            timer += currentTime - lastTime;

            lastTime = currentTime;

            if (delta >= 1) {

                update();
                drawTempScreen();
                drawFullScreen();
                //repaint(); // CALLS PAINTCOMPONENT()

                delta--;

                drawCount++;
            }
        }
    }



    // FROM THIS CLASS ------------------------------------------------------------------------


    // CORE METHODS ------------------------------------------------------------------------

    // 1) PRELOAD STUFF
    public void setUpGame() {

        // 1. LOAD OBJECTS AND NPC
        Utility.AssetSetter.deploySuperObjectInMap(this, tileSize, obj);
        Utility.AssetSetter.deployNPCInMap(this, tileSize, getNpc());

        // 2. LOAD MUSIC
        //playMusic(0);
        //music.stopSound();

        // 3. LOAD GAME STATE
        state = gameState.HOME;

        // 4. LOAD FULL SCREEN
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        // 5. SET FULL SCREEN
        setFullScreen();
    }

    // 2) START THE GAME CALLED BY MAIN
    public void startGameThread() {

        // PASS GAMEPANEL AND CALLS RUN() OF RUNNABLE
        gameThread = new Thread(this);
        gameThread.start();
    }

    // 3) UPDATE: UPDATE INFO & MOVEMENTS
    private void update() {

        if (state == gameState.PLAY) {
            time.update();
            player.update();
            for (NPC n : getNpc()) {
                if (n != null) {
                    n.update();
                }
            }
        }
        if (state == gameState.PAUSE) {
            // TODO
        }
    }

    // 4.1) LOAD UPDATED INFO TO BUFFERED TEMP SCREEN
    private void drawTempScreen() {

        // HOME SCREEN
        if (state == gameState.HOME) {
            ui.draw(g2);
        }
        else {
            // 1. DRAW TILES
            tileM.draw(g2);

            // 2. DRAW SUPEROBJECTS : TODO CLEAN THIS
            try {
                for (SuperObject object : obj) {

                    object.draw(g2);
                }
            } catch (ConcurrentModificationException e) {
                System.err.println("Trouble attempting to draw: " + e.getMessage());
            }

            // 3. DRAW NPC
            for (NPC n : getNpc()) {
                if (n != null) {
                    n.draw(g2);
                }
            }

            // 4. DRAW PLAYER
            player.draw(g2);

            // 5. DRAW UI
            ui.draw(g2);
        }


    }
    // 4.2) LOAD TEMP SCREEN TO ACTUAL FULL SCREEN
    private void drawFullScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScrenHeight, null);
        g.dispose();
    }

    // 4.1) DRAW: DRAW FRAME WITH UPDATED INFO
//    public void paintComponent(Graphics g) {
//
//        super.paintComponent(g); // PROVIDES FUNCTIONS TO DRAW OBJECTS
//        Graphics2D g2 = (Graphics2D) g; // TYPECAST TO PROVIDE BETTER 2D
//
//        // 1. DRAW TILES
//        tileM.draw(g2);
//
//        // 2. DRAW SUPEROBJECTS : TODO CLEAN THIS
//        try {
//            for (SuperObject object : obj) {
//
//                object.draw(g2);
//            }
//        } catch (ConcurrentModificationException e) {
//            System.err.println("Trouble attempting to draw: " + e.getMessage());
//        }
//
//        // 3. DRAW NPC
//        for (NPC n : getNpc()) {
//            if (n != null) {
//                n.draw(g2);
//            }
//        }
//
//        // 4. DRAW PLAYER
//        player.draw(g2);
//
//        // 5. DRAW UI
//        ui.draw(g2);
//
//        // GOOD PRACTICE TO RELEASE USED RES
//        g2.dispose();
//    }



    // AUXILIARY METHODS ------------------------------------------------------------------------

    private void setFullScreen() {

        // GET LOCAL SCREEN DEVICE INFO
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH & HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScrenHeight = Main.window.getHeight();
    }

    // PLAY BG MUSIC
    private void playMusic(int i) {

        music.setSound(i);
        music.adjustSoundVolume(-18); // DECIBELS
        music.playSound();
        music.loopSound();
    }
    // PLAY SFX MUSIC ON EVENTS
    public void playSFX(int i) {

        sfx.setSound(i);
        sfx.playSound();
    }



    // GETTERS & SETTERS ------------------------------------------------------------------------
    public List<NPC> getNpc() {
        return npc;
    }
    public List<SuperObject> getObj() {
        return obj;
    }
}