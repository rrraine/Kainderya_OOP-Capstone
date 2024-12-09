package main;

import entity.*;
import food.FoodBuilder;
import game.Score;
import game.Time;
import object.SuperObject;
import tile.TileManager;
import ui.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // DEBUG CONSOLE CAN BE DELETED
    String reset = "\u001B[0m";
    String debug = "\u001B[32m";

    // ~ FIELDS ---------------------------------------------------------------------------
    private static GamePanel instance;

    // FRAME RATE
    public static final int FPS = 60;

    // SCREEN SETTINGS
    private final int originalTileSize = 32; // 16 to 32
    private final int scale = 2;
    public final int tileSize = originalTileSize * scale; // ACTUAL TILE: 48 x 48, 64 x 64 na

    // ASPECT RATIO
    private final int maxScreenCol = 20; // (16) TILES PER ROW // old: 20
    private final int maxScreenRow = 12; // (12) TILES PER COL // old: 16

    // STANDARD SCREEN RESOLUTION: 1
    public final int screenWidth = tileSize * maxScreenCol; // 960
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 25; // 50
    public final int maxWorldRow = 15; // 50

    // FULL SCREEN RESOLUTION
    private int fullScreenWidth = screenWidth;
    private int fullScreenHeight = screenHeight;
    BufferedImage tempScreen;
    boolean fullScreenOn;

    // GAME SETTINGS SYSTEM
    Graphics2D g2;
    TileManager tileM = TileManager.instantiate(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    Time time = Time.instantiate(this);
    UIFactory uiM = UIFactory.instantiate(this, time);
    KeyBindings keyB = KeyBindings.instantiate(this, uiM);
    EventHandler eH = EventHandler.instantiate(this);
    Thread gameThread;
    public Score score = Score.instantiate(this);

    // OBJECTS AND ENTITY
    public Player player;
    final List<NPC> npc = new ArrayList<>();
    final List<SuperObject> obj = new ArrayList<>();
    private List<Asset> assetPool = new ArrayList<>();
    ShopManager shopManager = ShopManager.instantiate(this);
    public FoodBuilder fBuilder = FoodBuilder.instantiate(this);

    private final int maxCustomers = 9;
    private final int maxRoamers = 15;


    // GAME STATE
    public state gameState;
    public enum state { HOME, PLAY, PAUSE, OPTIONS, DIALOGUE, TERMINAL }
    private boolean newGame;
    private boolean multiplayer;

    // ~ FIELDS END HERE


    // CONSTRUCTOR ---------------------------------------------------------------------------
    private GamePanel() {

        // SET DIMENSIONS AND COLOR OF THE FRAME
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        // LISTEN FOR KEYSTROKES
        this.addKeyListener(getKeyB());
        // ALLOWS RECEIVING OF KEYSTROKES
        this.setFocusable(true);

        newGame = true;
    }
    // SINGLETON INITIALIZE
    public static GamePanel initialize() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
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
                delta--;

                drawCount++;
            }
        }
    }


    // CORE METHODS ------------------------------------------------------------------------

    // 1) PRELOAD STUFF
    public void setUpGame() {

        // LOAD OBJECTS AND NPC
        Utility.AssetSetter.deploySuperObjectInMap(this, tileSize, obj);
        System.out.println(debug + "Deploying Objects. Count: " + obj.size() + reset);
        setupNPCDeployment(tileSize);
        //Utility.AssetSetter.deployNPCInMap(this, tileSize, npc, shopManager);
        // Utility.AssetSetter.deployNPCInMap(this, tileSize, npc);
         System.out.println(debug + "Deploying NPCs. GAME PANEL Count: " + npc.size() + reset);

        assetPool.addAll(npc);
        assetPool.addAll(obj);

        // LOAD MUSIC
        playBGMusic(0);
        music.stopSound();

        // LOAD GAME STATE
        gameState = state.HOME;

        // LOAD FULL SCREEN
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        // SET FULL SCREEN
        setFullScreen();
    }

    // 2) START THE GAME CALLED BY MAIN
    public void startGameThread() {

        // PASS GAMEPANEL AND CALLS RUN() OF RUNNABLE
        gameThread = new Thread(this);
        gameThread.start();
    }

    // 3) UPDATE: UPDATE INFO & MOVEMENTS ------------
    private void update() {

        if (gameState == state.HOME) {
            if (!newGame)
                newGame();
        }
        if (gameState == state.PLAY) {
            newGame = false;

            time.update();
            player.update();
            // updateNPCs();
            for (NPC n : npc) {
                if (n != null) {
                    n.update();
                }
            }

            for (SuperObject o : obj) {
                if (o != null) {
                    o.update();
                }
            }

        }
    }

    // 4.1) RENDER UPDATED INFO TO BUFFERED TEMP SCREEN
    private void drawTempScreen() {
        // warning!!!! only attempt to draw within conditional blocks to avoid overlapping draws

        if (gameState == state.HOME) {
            // RID off map glitches
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, screenWidth, screenHeight);

            uiM.draw(g2);
        }
        else {
            // RID off map glitches
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, screenWidth, screenHeight);

            // 1. DRAW TILES
            tileM.draw(g2);

            // SORT ASSETS
            Collections.sort(assetPool);

            // 3. DRAW ASSETS / THE TRY CATCH IS DEBUGGING CAN BE REMOVED RA
            for (Asset a : assetPool) {
                if (a instanceof Entity) {
                    ((Entity) a).draw(g2);
                   // System.out.println(((Entity) a).getIdle1().toString());
                }
                else if (a instanceof SuperObject) {
                    ((SuperObject) a).draw(g2);
                }
            }

            // 5. DRAW UI
            uiM.draw(g2);
        }
    }
    // 4.2) LOAD TEMP SCREEN TO ACTUAL FULL SCREEN
    private void drawFullScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        g.dispose();
    }


    // NEW GAME INSTANCE RESET
    private void newGame() {
        newGame = true;

        keyB.resetParams();
        uiM.resetParams();
        score.resetParams();
        time.resetParams();
        fBuilder.resetParams();
        shopManager.resetParams();

        if (player != null) {
            player.resetParams();
            player = null;
        }

        // RESET ALL ASSET PARAMS
        for (Asset a : assetPool) {
            if (a instanceof SuperObject) {
                a.resetParams();
            }
        }

    }


    // AUXILIARY METHODS ------------------------------------------------------------------------
    private void setFullScreen() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fullScreenWidth = (int) width;
        fullScreenHeight = (int) height;

//
//        // GET LOCAL SCREEN DEVICE INFO
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        gd.setFullScreenWindow(Main.window);
//
//        // GET FULL SCREEN WIDTH & HEIGHT
//        fullScreenWidth = Main.window.getWidth();
//        fullScreenHeight = Main.window.getHeight();
    }
    // PLAY BG MUSIC
    private void playBGMusic(int i) {

        music.setSound(i);
        music.adjustSoundVolume(-20); // DECIBELS
        music.playSound();
        music.loopSound();
    }
    // PLAY SFX MUSIC ON EVENTS
    public void playSFX(int i) {

        sfx.setSound(i);
        sfx.playSound();
    }
    void selectCharacter(String playerAvatar, String playerName) {
        player = new Player(this, getKeyB(), playerAvatar, playerName);
        assetPool.add(player);
    }



    // GETTERS & SETTERS ------------------------------------------------------------------------
    public List<NPC> getNpc() {
        return npc;
    }
    public List<SuperObject> getObj() {
        return obj;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }
    public boolean isMultiplayer() {
        return multiplayer;
    }
    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public KeyBindings getKeyB() {
        return keyB;
    }

    public void setKeyB(KeyBindings keyB) {
        this.keyB = keyB;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public List<Asset> getAssetPool() {
        return assetPool;
    }

    public int getMaxCustomers() {
        return maxCustomers;
    }

    public int getMaxRoamers() {
        return maxRoamers;
    }

    public String getReset() {
        return reset;
    }

    public String getDebug() {
        return debug;
    }

    public void setupNPCDeployment(int tileSize) {

        // Generate NPCs
        shopManager.generateNPCs();
        //System.out.println("Generated NPCs in ShopManager: " + shopManager.getAllNPCs().size());
        System.out.println("Generated NPCs in ShopManager: " + npc.size());

        // Deploy NPCs to the map
        Utility.AssetSetter.deployNPCInMap(this, tileSize, this.npc, shopManager);

        // Confirm deployment
        System.out.println("Total NPCs deployed to map: " + this.npc.size());
    }


}