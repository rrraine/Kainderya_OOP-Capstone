package main;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

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
    private String notif;
    private boolean notifOn;
    private final int notifDuration;

    // GAME TERMINAL STATE
    private final boolean isTerminal;

    // UI SUBSTATES
    private int command;

    // INNER UI VERSIONS
    public final HomeUI homeUI;
    public final PlayUI playUI;
    public final PauseUI pauseUI;
    public final OptionsUI optionsUI;


    // CONSTRUCTOR -----------------------------------------------------------------
    private UI(GamePanel gp, Time time) {

        this.gp = gp;
        this.time = time;

        homeUI = new HomeUI(this);
        playUI = new PlayUI(this);
        pauseUI = new PauseUI(this);
        optionsUI = new OptionsUI(this);

        notifDuration = 0;
        notifOn = false;
        notif = "";
        isTerminal = false;
        timeFormat = new DecimalFormat("#0:00");

        // FONT SETUP
        fredokaSemiBold = importFont("Fredoka-SemiBold");

        // OPTIONS UI
        command = 0;
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

        // INITIALIZE FONT FACE
        g2.setFont(fredokaSemiBold);
        g2.setColor(Color.white);

        // DRAW TIMER
        if (gp.gameState != GamePanel.state.HOME) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
            g2.drawString("Time: " + Time.getTimer(), gp.tileSize * 16, 65);
        }

    }

    // FROM THIS CLASS -------------------------------------------------------------
    private void drawCursor(String text, int x, int y, boolean singleArrow, Graphics2D g2) {
        this.g2 = g2;

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
    public void showNotification(String text) {

        notif = text;
        notifOn = true;
    }
    private void drawSubWindow(int x, int y, int width, int height) {

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
    public int getCommand() {
        return command;
    }
    public void setCommand(int command) {
        this.command = command;
    }

    // UI STATE GETTERS -----------------------------------------------
    public HomeUI getHomeUI() {
        return homeUI;
    }
    public PlayUI getPlayUI() {
        return playUI;
    }
    public PauseUI getPauseUI() {
        return pauseUI;
    }
    public OptionsUI getOptionsUI() {
        return optionsUI;
    }


    // INNER CLASS UI STATES ---------------------------------------------------------
    public class HomeUI {

        UI ui;
        Graphics2D g2;
        public substate homeState;
        public enum substate { TITLE, SELECTION }

        public HomeUI(UI ui) {
            this.ui = ui;
            homeState = substate.TITLE;
        }
        public void draw() {
            this.g2 = ui.g2;

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
            String text = "Kusina ni VJ";
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
            g2.drawImage(gp.getNpc().getFirst().getIdle(), x, y, gp.tileSize * 3, gp.tileSize * 3, null);

            // NEW GAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize * 4 + 30;
            g2.drawString(text, x, y);
            if (command == 0) {
                drawCursor(text, x, y, false, g2);
            }

            // CUSTOMIZE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "CREDITS";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                drawCursor(text, x, y, false, g2);
            }

            // QUIT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                drawCursor(text, x, y, false, g2);
            }
        }
        private void homeSELECTION() {

            // BACKGROUND
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
                drawCursor(text, x, y, false, g2);
            }

            text = "Boy 2";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                drawCursor(text, x, y, false, g2);
            }

            text = "Girl 1";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                drawCursor(text, x, y, false, g2);
            }

            text = "Girl 2";
            x = Utility.Aligner.centerText(text, gp, g2);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 3) {
                drawCursor(text, x, y, false, g2);
            }
        }
    }
    public class PlayUI  {

        Graphics2D g2;
        UI ui;
        private PlayUI(UI ui) {
            this.ui = ui;
        }
        public void draw() {
            this.g2 = ui.g2;
        }
    }
    public class PauseUI  {

        Graphics2D g2;
        UI ui;

        private PauseUI(UI ui) {
            this.ui = ui;
        }
        public void draw() {
            this.g2 = ui.g2;

            // PAUSE TEXT
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
            String text = "PAUSED";
            int x = Utility.Aligner.centerText(text, gp, g2);
            int y = gp.screenHeight / 2;

            g2.drawString(text, x, y);
        }

    }
    public class OptionsUI  {

        Graphics2D g2;
        UI ui;
        public substate optionsState;
        public enum substate {START, FULLSCREEN, SOUND, MULTIPLAYER, QUIT, RESUME }

        public OptionsUI(UI ui) {
            this.ui = ui;
            optionsState = substate.START;
        }

        public void draw() {
            this.g2 = ui.g2;

            // SUB WINDOW
            int frameX = gp.tileSize * 6;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 10;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);

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
                drawCursor(label, textX, textY, true, g2);

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
                drawCursor(label, textX, textY, true, g2);
            }

            // VOLUME
            label = "Volume";
            textY += gp.tileSize;
            g2.drawString(label, textX, textY);
            if (command == 2) {
                drawCursor(label, textX, textY, true, g2);
            }

            // QUIT
            label = "Quit";
            textY += gp.tileSize;
            g2.drawString(label , textX, textY);
            if (command == 3) {
                drawCursor(label, textX, textY, true, g2);
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
                drawCursor(label, textX, textY, true, g2);
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
                drawCursor("Back", textX, textY, true, g2);
                if (gp.keyB.isEnterPressed()){
                    optionsUI.optionsState = OptionsUI.substate.START;
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
                drawCursor(text, textX, textY, true, g2);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    optionsUI.optionsState = OptionsUI.substate.START;
                    gp.gameState = GamePanel.state.HOME;
                    homeUI.homeState = HomeUI.substate.TITLE;
                }
            }

            // NO
            text = "No";
            textX = Utility.Aligner.centerText(text, gp, g2);
            textY += gp.tileSize;
            g2.drawString(text, textX, textY);
            if (command == 1) {
                drawCursor(text, textX, textY, true, g2);
                // QUIT
                if (gp.keyB.isEnterPressed()) {
                    optionsUI.optionsState = OptionsUI.substate.START;
                    command = 0;
                }
            }
        }
    }

}