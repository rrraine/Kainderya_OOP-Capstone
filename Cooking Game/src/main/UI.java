package main;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class UI implements Drawable, Importable {

    // ~ FIELDS -----------------------------------------------------------------
    private static UI instance;

    private final GamePanel gp;
    private final Time time;
    private Graphics2D g2;

    // FONTS & TIME FORMATS
    private final Font fredokaSemiBold;
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

        // OPTIONS UI
        command = 0;

        // SHAKE EFFECT
        random = new Random();
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
    private void drawCursor(String text, int x, int y, boolean singleArrow) {

        List<Integer> coord = Utility.Aligner.centerCursor(text, x, y, gp, g2);

        if (singleArrow) {
            g2.drawString(">", coord.get(0) + gp.tileSize - 25, coord.get(1));
            g2.setStroke(new BasicStroke(1.2F));
            g2.drawLine(coord.get(4), coord.get(5), coord.get(6), coord.get(7));
            return;
        }

        g2.drawString(">", coord.get(0), coord.get(1));
        g2.drawString("<", coord.get(2), coord.get(3));
        g2.setStroke(new BasicStroke(1.2F));
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

        Color color = new Color(0,0,0, 220);
        g2.setColor(color);

        // DRAW WINDOW
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255,255,255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
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
        public enum substate { TITLE, SELECTION }

        public HomeUI() {
            homeState = substate.TITLE;
        }
        public void draw() {

            // BACKGROUND
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            switch (homeState) {

                case TITLE:
                    homeTITLE();
                    break;

                case SELECTION:
                    homeSELECTION();
                    break;
            }
        }

        // SUB-STATES
        private void homeTITLE() {

            // TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "vj's merienda";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 3;

            // SHADOW TEXT COLOR
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN TEXT COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // ICON
            x = gp.screenWidth / 2 - (gp.tileSize*2)/2 - 20;
            y += gp.tileSize * 2 - 20;
            g2.drawImage(gp.npc.get(0).getIdle1(), x, y, gp.tileSize * 3, gp.tileSize * 3, null);

            // NEW GAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize * 4 + 30;
            g2.drawString(text, x, y);
            if (command == 0) {
                drawCursor(text, x, y, false);
            }

            // CUSTOMIZE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "CREDITS";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                drawCursor(text, x, y, false);
            }

            // QUIT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                drawCursor(text, x, y, false);
            }
        }
        private void homeSELECTION() {

            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select Avatar";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Boy 1";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (command == 0) {
                drawCursor(text, x, y, false);
            }

            text = "Boy 2";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                drawCursor(text, x, y, false);
            }

            text = "Girl 1";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                drawCursor(text, x, y, false);
            }

            text = "Girl 2";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 3) {
                drawCursor(text, x, y, false);
            }
        }
    }
    public class PlayUI  {

        public PlayUI() {

        }
        public void draw() {

            // DRAW TIMER
            if (gp.gameState != GamePanel.state.HOME) {

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
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
                g2.drawString("Stamina: " + gp.player.getStamina(), gp.tileSize * 16 + 5, 100);
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

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));

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
            g2.drawString(label, textX, textY);

            // FULL SCREEN ON/OFF
            label = "Full Screen";
            textX = frameX + gp.tileSize;
            textY += gp.tileSize * 2;
            g2.drawString(label, textX, textY);
            if (command == 0) {
                drawCursor(label, textX, textY, true);

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
            }

            // MULTIPLAYER
            label = "Multiplayer";
            textY += gp.tileSize;
            g2.drawString(label, textX, textY);
            if (command == 1) {
                drawCursor(label, textX, textY, true);
            }

            // VOLUME
            label = "Volume";
            textY += gp.tileSize;
            g2.drawString(label, textX, textY);
            if (command == 2) {
                drawCursor(label, textX, textY, true);
            }

            // QUIT
            label = "Quit";
            textY += gp.tileSize;
            g2.drawString(label , textX, textY);
            if (command == 3) {
                drawCursor(label, textX, textY, true);
                if (gp.keyB.isEnterPressed()) {
                    optionsState = substate.QUIT;
                    command = 0;
                }
            }

            // RESUME
            label = "Resume";
            textY += gp.tileSize * 2;
            g2.drawString(label, textX, textY);
            if (command == 4) {
                drawCursor(label, textX, textY, true);
                if (gp.keyB.isEnterPressed()) {
                    gp.gameState = GamePanel.state.PLAY;
                    command = 0;
                }
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
                drawCursor("Back", textX, textY, true);
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
                g2.drawString(line, textX, textY);
                textY += 40;
            }

            // YES
            String text = "Yes";
            textX = Utility.Aligner.centerText(text, gp, g2);
            textY += gp.tileSize * 3;
            g2.drawString(text, textX, textY);
            if (command == 0) {
                drawCursor(text, textX, textY, true);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    optionsUI.optionsState = OptionsUI.substate.START;
                    homeUI.homeState = HomeUI.substate.TITLE;
                    gp.gameState = GamePanel.state.HOME;
                }
            }

            // NO
            text = "No";
            textX = Utility.Aligner.centerText(text, gp, g2);
            textY += gp.tileSize;
            g2.drawString(text, textX, textY);
            if (command == 1) {
                drawCursor(text, textX, textY, true);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    getOptionsUI().optionsState = OptionsUI.substate.START;
                    command = 0;
                }
            }
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