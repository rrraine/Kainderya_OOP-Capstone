package main;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class UI implements Drawable, Importable {

    // ~ FIELDS -----------------------------------------------------------------
    private static UI instance;

    private final GamePanel gp;
    private final Time time;
    private Graphics2D g2;

    // FONTS
    private final Font fredokaSemiBold;
    private final Font luckiestGuy;
    private final Font balooMedium;
    private final Font paytoneOne;

    // TIME FORMATS
    private final DecimalFormat timeFormat;

    // NOTIFICATIONS
    private static String notif;
    private static boolean notifOn;
    private static int notifDuration;

    // GAME TERMINAL STATE
    private final boolean isTerminal;

    // UI SUBSTATES
    private int command;

    // INNER UI VERSIONS
    private final HomeUI homeUI;
    private final PlayUI playUI;
    private final PauseUI pauseUI;
    private final OptionsUI optionsUI;
    private final DialogueUI dialogueUI;
    private final TerminalUI terminalUI;

    // SHAKE EFFECT
    private final Random random;

    // COLORS
    private final Color transBlack;
    private final Color transWhite;
    private final Color primary;
    private final Color primaryAccent;
    private final Color secondary;
    private Color secondaryAccent;
    private final Color blue;
    private final Color orange;


    // CONSTRUCTOR -----------------------------------------------------------------
    private UI(GamePanel gp, Time time) {

        this.gp = gp;
        this.time = time;

        homeUI = new HomeUI();
        playUI = new PlayUI();
        pauseUI = new PauseUI();
        optionsUI = new OptionsUI();
        dialogueUI = new DialogueUI();
        terminalUI = new TerminalUI();

        notifDuration = 0;
        notifOn = false;
        notif = "";
        isTerminal = false;
        timeFormat = new DecimalFormat("#0:00");

        // FONT SETUP
        fredokaSemiBold = importFont("Fredoka-SemiBold");
        luckiestGuy = importFont("LuckiestGuy-Regular");
        balooMedium = importFont("Baloo2-Medium");
        paytoneOne = importFont("PaytoneOne-Regular");

        // OPTIONS UI
        command = 0;

        // SHAKE EFFECT
        random = new Random();

        // COLOR PALLETTE
        transBlack = new Color(0,0,0, 175);
        transWhite = new Color(255, 255, 255, 185);
        primary = new Color( 255, 219, 75);
        primaryAccent = new Color(65, 52, 18);
        secondary = new Color(255, 75, 81);
        blue = new Color(75, 165, 255);
        orange = new Color(255, 171, 75);
    }
    public static UI instantiate(GamePanel gp, Time time) {
        if (instance == null) {
            instance = new UI(gp, time);
        }
        return instance;
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------------
    @Override
    public void update() {} // USELESS
    @Override
    public void draw(Graphics2D g2) {

        // COMMON UI
        this.g2 = g2;

        g2.setFont(fredokaSemiBold);
        g2.setColor(Color.white);

    }

    // FROM THIS CLASS -------------------------------------------------------------
    private void drawCursor(String text, int x, int y, boolean singleArrow, boolean underline) {

        List<Integer> coord = Utility.Aligner.centerCursor(text, x, y, gp, g2);

        if (singleArrow) { // SINGLE ARROW
            drawLetterBorder(">", Color.BLACK, 3, coord.get(0) + gp.tileSize - 25, coord.get(1));
            g2.setColor(primary);
            g2.drawString(">", coord.get(0) + gp.tileSize - 25, coord.get(1));
            g2.setStroke(new BasicStroke(1.2F));
        }
        else { // DOUBLE ARROW
            drawLetterBorder(">", Color.BLACK, 3, coord.get(0) + gp.tileSize/9, coord.get(1));
            drawLetterBorder("<", Color.BLACK, 3, coord.get(2), coord.get(3));
            g2.setColor(primary);
            g2.drawString(">", coord.get(0) + gp.tileSize/9, coord.get(1));
            g2.drawString("<", coord.get(2), coord.get(3));
            g2.setStroke(new BasicStroke(1.2F));
        }
        if (underline) // UNDERLINE
            g2.drawLine(coord.get(4), coord.get(5), coord.get(6), coord.get(7));
    }
    private void drawElement(String text, int x, int y) {}
    public static void showNotification(String text) {
        notif = text;
        notifOn = true;
    }
    public static boolean freeze(int sec) {
        if (notifOn) {
            notifDuration++;

            if (notifDuration > (GamePanel.FPS * sec)) {
                notifDuration = 0;
                notifOn = false;
                return false;
            }
            return true;
        }
        return false;
    }
    private int shakeEffect(int intensity) {
        return random.nextInt(intensity * 2 + 1) - intensity;
    }
    private void drawPopUpWindow(int x, int y, int width, int height) {

        g2.setColor(transBlack);

        // DRAW WINDOW
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    private void drawPopUpWindow(int x, int y, int width, int height, Color window, Color border) {

        g2.setColor(window);

        // DRAW WINDOW
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(border);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    private void drawCircle(int x, int y, int width, int height, Color bg, Color border) {

        g2.setColor(bg);

        // DRAW OVAL
        g2.fillOval(x, y, width, height);

        g2.setColor(border);
        g2.setStroke(new BasicStroke(5));
        g2.drawOval(x+5, y+5, width -10, height -10);
    }
    private void drawLetterBorder(String text, Color color, int thickness, int x, int y) {

        g2.setColor(color);
        for (int i = thickness *-1; i <= thickness; i++) {
            for (int j = thickness *-1; j <= thickness; j++) {
                if (i != 0 || j != 0) {
                    g2.drawString(text, x + i, y + j);
                }
            }
        }
    }

    // COMMAND GETTERS & SETTERS
    public int getCommand() { return command; }
    public void setCommand(int command) { this.command = command; }

    // OTHER GETTERS & SETTERS
    public void setNotif(String notif) {
        this.notif = notif;
    }

    // UI STATE GETTERS -----------------------------------------------
    public HomeUI getHomeUI() { return homeUI; }
    public PlayUI getPlayUI() { return playUI; }
    public PauseUI getPauseUI() { return pauseUI; }
    public OptionsUI getOptionsUI() { return optionsUI; }
    public DialogueUI getDialogueUI() { return dialogueUI; }
    public TerminalUI getTerminalUI() { return terminalUI; }


    // INNER CLASS UI STATES ---------------------------------------------------------
    public class HomeUI {

        public substate homeState;
        public enum substate { TITLE, SELECTION, CREDITS }

        private BufferedImage wallpaper;
        private BufferedImage wallpaperFront;

        // ANIMATED WALLPAPER
        UIAnimated girl;
        UIAnimated boy;
        UIAnimated civilian;
        UIAnimated cook1, cook2, cook3, cook4;

        public HomeUI() {
            homeState = substate.TITLE;
            wallpaper = importWallpaper("wallpapers", "homeUI", "homeUI_Back");
            wallpaperFront = importWallpaper("wallpapers", "homeUI", "homeUI_Front");

            // ANIMATED WALLPAPER
            girl = new UIAnimated(gp, "npc", "studentFemale",  30,true);
            girl.reposition((gp.tileSize * 8) + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);

            boy = new UIAnimated(gp, "npc","studentMale", 16,false);
            boy.reposition(gp.tileSize + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);

            civilian = new UIAnimated(gp, "npc","civilianfem1", 20, true);
            civilian.reposition((gp.tileSize * 11) + gp.tileSize * 5, (gp.tileSize *3+20) + gp.tileSize * 5);

            // TODO UPDATE IMAGE ARGUMENT
            cook1 = new UIAnimated(gp, "player", "cook1",  12,true);
            cook2 = new UIAnimated(gp, "player", "cook2",  12,true);
            cook3 = new UIAnimated(gp, "player", "cook1",  12,true);
            cook4 = new UIAnimated(gp, "player", "cook4",  12,true);

        }
        public void draw() {

            // HOME BACKGROUND BACK
            g2.drawImage(wallpaper, 0, 0, gp.screenWidth, gp.screenHeight, null);

            switch (homeState) {

                case TITLE:
                    homeTITLE();
                    break;

                case SELECTION:
                    homeSELECTION();
                    break;

                case CREDITS:
                    homeCREDITS();
                    break;
            }
        }

        // SUB-STATES
        private void homeTITLE() {

            g2.setFont(paytoneOne);

            // ANIMATED WALLPAPER
            girl.drawSideViewMoving(g2);
            boy.drawSideViewMoving(g2);
            civilian.drawSideViewMoving(g2);

            // HOME BACKGROUND FRONT
            g2.drawImage(wallpaperFront, 0, 0, gp.screenWidth, gp.screenHeight, null);

            // TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 135F));
            String text = "KAiNDERYA";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 4 - 40;

            // LETTERING: WHITE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + 16, y + 16);

            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 14, y + 14);

            // BORDERING
            drawLetterBorder(text, Color.BLACK, 6, x, y);

            // MAIN TEXT COLOR
            if (Utility.Regulator.flipSwitch(2))
                g2.setColor(primary);
            else
                g2.setColor(secondary);

            if (Utility.Regulator.flipSwitch(2))
                g2.drawString(text, x , y);
            else
                g2.drawString(text, x + shakeEffect(1), y);


            // NEW GAME
            g2.setFont(g2.getFont().deriveFont(48F));
            text = "NEW GAME";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize * 3 -5;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x +7, y +5);
            // BORDERING
            drawLetterBorder(text, Color.BLACK, 3, x, y);
            // MAIN TEXT COLOR
            if (command == 0) {
                drawCursor(text, x, y, false, false);
            }
            else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, x, y);


            // CREDITS
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "CREDITS";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x +7, y +5);
            // BORDERING
            drawLetterBorder(text, Color.BLACK, 3, x, y);
            // MAIN TEXT COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            if (command == 1) {
                drawCursor(text, x, y, false, false);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, x, y);

            // QUIT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x +7, y +5);
            // BORDERING
            drawLetterBorder(text, Color.BLACK, 3, x, y);
            // MAIN TEXT COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            if (command == 2) {
                drawCursor(text, x, y, false, false);
                g2.setColor(secondary);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, x, y);
        }
        private void homeSELECTION() {

            // SUB-WINDOW
            int frameX = gp.tileSize;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 18;
            int frameHeight = gp.tileSize * 10;
            drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

            g2.setFont(luckiestGuy);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select Avatar";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 2 + 25;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 6, y);
            // MAIN TEXT COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // SELECTION HERE
            int gridX = gp.tileSize * 3 + 24, gridY = gp.tileSize * 4 -20;
            int gridWidth = gp.tileSize * 3;
            int gridHeight = gp.tileSize * 4;

            cook1.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

            g2.setFont(g2.getFont().deriveFont(30F));

            // GRID 1
            text = "Miguel";
            x = gridX + gp.tileSize - 6;
            y = gridY * 2 + gp.tileSize;
            // BORDER
            drawLetterBorder(text, Color.BLACK, 3, x, y);
            // MAIN TEXT COLOR

            // DRAW NAME
            if (command == 0) {
                g2.setColor(primary);
                drawCursor(text, x, y, true, false);
            } else
                g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // DRAW GRID BOX
            if (command == 0) {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
                cook1.drawFrontViewMoving(g2);
            } else {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
                cook1.drawFrontViewStatic(g2);
            }

            // GRID 2
            gridX += (gp.tileSize * 3) + 30;
            drawPopUpWindow(gridX, gridY, gridWidth, gridHeight);

            cook2.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

            text = "Gina";
            x = gridX + gp.tileSize - 4;
            y = gridY * 2 + gp.tileSize;
            // BORDER
            drawLetterBorder(text, Color.BLACK, 3, x, y);

            // DRAW NAME
            if (command == 1) {
                g2.setColor(primary);
                drawCursor(text, x, y, true, false);
            } else
                g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // DRAW GRID BOX
            if (command == 1) {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
                cook2.drawFrontViewMoving(g2);
            } else {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
                cook2.drawFrontViewStatic(g2);
            }


            // GRID 3
            gridX += (gp.tileSize * 3) + 30;
            drawPopUpWindow(gridX, gridY, gridWidth, gridHeight);

            cook3.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

            text = "Gabriel";
            x = gridX + gp.tileSize - 26;
            y = gridY * 2 + gp.tileSize;
            // BORDER
            drawLetterBorder(text, Color.BLACK, 3, x, y);

            // DRAW NAME
            if (command == 2) {
                g2.setColor(primary);
                drawCursor(text, x, y, true, false);
            } else
                g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // DRAW GRID BOX
            if (command == 2) {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
                cook3.drawFrontViewMoving(g2);
            } else {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
                cook3.drawFrontViewStatic(g2);
            }


            // GRID 4
            gridX += (gp.tileSize * 3) + 30;
            drawPopUpWindow(gridX, gridY, gridWidth, gridHeight);

            cook4.reposition(gridX + gp.tileSize - 18, gridY + gp.tileSize + 20);

            text = "Sofia";
            x = gridX + gp.tileSize - 8;
            y = gridY * 2 + gp.tileSize;
            // BORDER
            drawLetterBorder(text, Color.BLACK, 3, x, y);

            // DRAW NAME
            if (command == 3) {
                g2.setColor(primary);
                drawCursor(text, x, y, true, false);
            } else
                g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // DRAW GRID BOX
            if (command == 3) {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, primaryAccent, primary);
                cook4.drawFrontViewMoving(g2);
            } else {
                drawPopUpWindow(gridX, gridY, gridWidth, gridHeight, transWhite, Color.BLACK);
                cook4.drawFrontViewStatic(g2);
            }

            text = "Name: ";
            x = Utility.Aligner.centerText(text, gp, g2) - (gp.tileSize * 4);
            y += gp.tileSize * 2 - gp.tileSize /2;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 6, y);
            // BORDER
            drawLetterBorder(text, Color.BLACK, 3, x, y);

            // DRAW TEXT
            if (command == 4) {
                g2.setColor(primary);
                drawCursor(text, x, y, true, false);
            } else
                g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // NAME FIELD
            // TODO IMPLEMENT TEXT FIELD WITH LISTENER
            x += gp.tileSize * 3;
            y -= 40;
            int width = gp.tileSize * 7;
            int height = gp.tileSize - 10;
            drawPopUpWindow(x, y, width, height, transWhite, Color.BLACK);
        }
        private void homeCREDITS() {

            // SUB-WINDOW
            int frameX = gp.tileSize;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 18;
            int frameHeight = gp.tileSize * 10;
            drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

            g2.setFont(luckiestGuy);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "CREDITS <3";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 2 + 25;
            // SHADOW TEXT COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 6, y);
            // MAIN TEXT COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // TEAM
            g2.setFont(g2.getFont().deriveFont(30F));
            text = "Procramming   |   2024";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize + 32;

            // LETTERING: WHITE
            g2.drawString(text, x + 7, y + 4);
            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 5, y + 2);
            // BORDER
            drawLetterBorder(text, Color.BLACK, 2, x, y);
            // MAIN
            g2.setColor(orange);
            g2.drawString(text, x, y);

            // OUR BELOVED NAMES
            g2.setFont(g2.getFont().deriveFont(42F));
            // GK
            text = "Carreon       Gianna Katrin";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize + 38;

            // LETTERING: WHITE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + 14, y + 6);
            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 11, y + 4);

            drawLetterBorder(text, Color.BLACK, 3, x, y);
            g2.setColor(blue);
            g2.drawString(text, x, y);

            // RON
            text = "Jatayna       Aaron Dei";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;

            // LETTERING: WHITE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + 14, y + 6);
            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 11, y + 4);

            drawLetterBorder(text, Color.BLACK, 3, x, y);
            g2.setColor(secondary);
            g2.drawString(text, x, y);

            // VJ
            text = "Juarez       Venice Jonah";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;

            // LETTERING: WHITE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + 14, y + 6);
            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 11, y + 4);

            drawLetterBorder(text, Color.BLACK, 3, x, y);
            g2.setColor(blue);
            g2.drawString(text, x, y);

            // LORI
            text = "Quezada       Lorraine";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;

            // LETTERING: WHITE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + 14, y + 6);
            // SHADOW TEXT COLOR
            g2.setColor(primaryAccent);
            g2.drawString(text, x + 11, y + 4);

            drawLetterBorder(text, Color.BLACK, 3, x, y);
            g2.setColor(secondary);
            g2.drawString(text, x, y);

            // BACK
            g2.setFont(g2.getFont().deriveFont(30F));
            text = "Back";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize + 48;
            drawLetterBorder(text, Color.BLACK, 2, x, y);
            g2.setColor(primary);
            g2.drawString(text, x, y);
            drawCursor(text, x, y, false, true);
        }

    }
    public class PlayUI  {

        public PlayUI() {

        }
        public void draw() {

            // DRAW TIMER
            if (gp.gameState != GamePanel.state.HOME) {

                // ORDER PANE
                int frameX = -10;
                int frameY = gp.tileSize * 10 + 8;
                int frameWidth = gp.tileSize * 20 + gp.tileSize/3;
                int frameHeight = gp.tileSize * 2 + 3;
                drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

                // CIRCLE FOR MENU ICON
                drawCircle(gp.tileSize/2, frameY - gp.tileSize/2 , gp.tileSize*2 + 6, gp.tileSize*2, Color.WHITE, Color.BLACK);

                g2.setColor(Color.WHITE);
                
                if (Time.rushTime()) {
                    g2.setColor(Color.RED);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
                    g2.drawString("Time: " + Time.getTimer(), gp.tileSize * 16 + shakeEffect(1), 65 + shakeEffect(1));
                }
                else {
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
                    g2.drawString("Time: " + Time.getTimer(), gp.tileSize * 16, 65);
                }

                // stamina
                if (gp.player.getStamina() >= 0) {
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
                    g2.drawString("Stamina: " + gp.player.getStamina(), gp.tileSize * 16 + 5, 100);
                }
                else {
                    g2.setColor(Color.RED);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
                    g2.drawString("Stamina: " + gp.player.getStamina(), gp.tileSize * 16 + 5, 100);
                }
            }
        }
    }
    public class PauseUI  {

        private PauseUI() {

        }
        public void draw() {

            // PAUSE TEXT
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
            String text = "PAUSED";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.screenHeight / 2;

            g2.drawString(text, x, y);
        }

    }
    public class OptionsUI  {

        public substate optionsState;
        public enum substate {START, FULLSCREEN, SOUND, MULTIPLAYER, QUIT, RESUME }

        public OptionsUI() {
            command = 0;
            optionsState = substate.START;
        }


        public void draw() {

            // SUB WINDOW
            int frameX = gp.tileSize * 6;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 10;
            drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

           // g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
            g2.setFont(fredokaSemiBold.deriveFont(30F));

            // TODO OPTIONS STATE
            switch (optionsState) {

                case START:
                    optionsSTART(frameX, frameY);
                    break;

                case FULLSCREEN:
                    optionsFULLSCREEN(frameX, frameY);
                    break;

                case SOUND:
                    break;

                case MULTIPLAYER:
                    break;

                case QUIT:
                    optionsQUIT(frameX, frameY);
                    break;

                case RESUME:
                    break;
            }

            gp.keyB.setEnterPressed(false);
        }


        // SUBSTATES
        private void optionsSTART(int frameX, int frameY) {

            int textX, textY;

            // ~ TEXT DISPLAY ------------

            // TITLE
            String label = "OPTIONS";
            textX = Utility.Aligner.centerText(label, gp, g2);
            textY = frameY + gp.tileSize;
            drawLetterBorder(label, Color.BLACK, 3, textX, textY);
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);

            // FULL SCREEN ON/OFF
            label = "Full Screen";
            textX = frameX + gp.tileSize;
            textY += gp.tileSize * 2;

            drawLetterBorder(label, Color.BLACK, 2, textX, textY);

            if (command == 0) {
                g2.setColor(primary);
                g2.drawString(label, textX, textY);
                drawCursor(label, textX, textY, true, true);

                // IF PRESSED
                if (gp.keyB.isEnterPressed()) {
                    // IF FULL SCREEN
                    if (gp.fullScreenOn) {
                        gp.fullScreenOn = false;
                    }
                    else {
                        gp.fullScreenOn = true;
                    }
                    optionsState = substate.FULLSCREEN;
                    command = 0;
                }
            }else{
                g2.setColor(Color.WHITE);
                g2.drawString(label, textX, textY);
            }

            // MULTIPLAYER
            label = "Multiplayer";
            textY += gp.tileSize;
            drawLetterBorder(label, Color.BLACK, 2, textX, textY);
            if (command == 1) {
                g2.setColor(primary);
                g2.drawString(label, textX, textY);
                drawCursor(label, textX, textY, true, true);
            }else{
                g2.setColor(Color.WHITE);
                g2.drawString(label, textX, textY);
            }

            // VOLUME
            label = "Volume";
            textY += gp.tileSize;
            drawLetterBorder(label, Color.BLACK, 2, textX, textY);
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);
            if (command == 2) {
                g2.setColor(primary);
                g2.drawString(label, textX, textY);
                drawCursor(label, textX, textY, true, true);
            }else{
                g2.setColor(Color.WHITE);
                g2.drawString(label, textX, textY);
            }

            // QUIT
            label = "Quit";
            textY += gp.tileSize;
            drawLetterBorder(label, Color.BLACK, 2, textX, textY);
            if (command == 3) {
                g2.setColor(primary);
                g2.drawString(label , textX, textY);
                drawCursor(label, textX, textY, true, true);
                if (gp.keyB.isEnterPressed()) {
                    optionsState = substate.QUIT;
                    command = 0;
                }
            }else{
                g2.setColor(Color.WHITE);
                g2.drawString(label , textX, textY);
            }

            // RESUME
            label = "Resume";
            textY += gp.tileSize * 2;
            drawLetterBorder(label, Color.BLACK, 2, textX, textY);
            if (command == 4) {
                g2.setColor(primary);
                g2.drawString(label, textX, textY);
                drawCursor(label, textX, textY, true, true);
                if (gp.keyB.isEnterPressed()) {
                    gp.gameState = GamePanel.state.PLAY;
                    command = 0;
                }
            }else{
                g2.setColor(Color.WHITE);
                g2.drawString(label, textX, textY);
            }

            // ~ BOX DISPLAY

            // FULL SCREEN BOX
            textX = frameX + gp.tileSize * 5;
            textY = frameY + gp.tileSize * 2 + 24;
            g2.setStroke(new BasicStroke((3)));
            g2.drawRect(textX, textY, 24, 24);
            // TOGGLE
            if (gp.fullScreenOn) {
                g2.fillRect(textX, textY, 24, 24);
            }

            // VOLUME
            textY += gp.tileSize * 2;
            g2.drawRect(textX, textY, 120, 24);
        }
        void optionsFULLSCREEN(int frameX, int frameY) {

            int textX = frameX + gp.tileSize;
            int textY = frameY + gp.tileSize * 3;

            notif = "The change will \ntake effect after \nrestarting the \ngame.";

            for (String line : notif.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 40;
            }

            // BACK
            textY += gp.tileSize * 2;
            g2.drawString("Back", textX, textY);
            if (command == 0) {
                drawCursor("Back", textX, textY, true, true);
                if (gp.keyB.isEnterPressed()){
                    getOptionsUI().optionsState = OptionsUI.substate.START;
                    command = 0;
                }
            }

        }
        private void optionsQUIT(int frameX, int frameY) {

            int textX = frameX + gp.tileSize;
            int textY = frameY + gp.tileSize * 3;

            notif = "Quit game and return \nto the home screen?";
            for (String line : notif.split("\n")) {
                drawLetterBorder(line, Color.BLACK, 2, textX, textY);
                g2.setColor(Color.WHITE);
                g2.drawString(line, textX, textY);
                textY += 40;
            }

            // YES
            String text = "Yes";
            textX = Utility.Aligner.centerText(text, gp, g2);
            textY += gp.tileSize * 3;
            drawLetterBorder(text, Color.BLACK, 2, textX, textY);
            if (command == 0) {
                g2.setColor(primary);
                drawCursor(text, textX, textY, true, true);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    optionsUI.optionsState = OptionsUI.substate.START;
                    homeUI.homeState = HomeUI.substate.TITLE;
                    gp.gameState = GamePanel.state.HOME;
                }
            }
            else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, textX, textY);

            // NO
            text = "No";
            textX = Utility.Aligner.centerText(text, gp, g2);
            textY += gp.tileSize;
            drawLetterBorder(text, Color.BLACK, 2, textX, textY);
            if (command == 1) {
                g2.setColor(primary);
                drawCursor(text, textX, textY, true, true);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    getOptionsUI().optionsState = OptionsUI.substate.START;
                    command = 0;
                }
            }
            else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, textX, textY);
        }
    }
    public class DialogueUI {

        public DialogueUI() {

        }

        public void draw() {
            // TODO POP-UP ORDER CLOUD ABOVE NPC
        }
    }
    public class TerminalUI {

        public substate terminalState;
        public enum substate { TIMESUP, LEADERBOARD }

        public TerminalUI() {
            terminalState = substate.TIMESUP;
        }
        public void draw() {

            switch (terminalState) {

                case TIMESUP:
                    terminalTIMESUP();
                    break;

                case LEADERBOARD:
                    terminalLEADERBOARD();
                    break;
            }
        }
        private void terminalTIMESUP() {

            // TIMES UP TEXT
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
            g2.setColor(Color.RED);
            String text = "TIMES UP!";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.screenHeight / 2;

            g2.drawString(text, x + shakeEffect(1), y + shakeEffect(2));

            // block for 2 secs then proceed
            if (Utility.Regulator.block(2)) {
                terminalState = substate.LEADERBOARD;
            }

        }
        private void terminalLEADERBOARD() {

            // SUB-WINDOW
            int frameX = gp.tileSize;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 18;
            int frameHeight = gp.tileSize * 10;
            drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

            // LEADERBOARDS
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
            g2.setColor(Color.WHITE);
            String text = "LEADERBOARDS";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            // RETURN TO HOME SCREEN
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
            g2.setColor(Color.WHITE);
            text = "Press Enter to Continue...";
            x = Utility.Aligner.centerText(text, gp, g2);
            y = gp.tileSize * 10;
            g2.drawString(text, x, y);
        }
    }

}