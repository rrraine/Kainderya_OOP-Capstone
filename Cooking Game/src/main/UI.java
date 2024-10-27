package main;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
// TODO ORGANIZE UI INTO SUBCLASSES AND CREATE METHODS TO SET UI STUFF POSITIONS
public class UI implements Drawable, Importable {

    // ~ FIELDS -----------------------------------------------------------------

    GamePanel gp;
    Time time;
    Graphics2D g2;

    // FONTS & TIME FORMATS
    Font fredokaSemiBold;
    DecimalFormat timeFormat;

    //ICONS & TIME
    BufferedImage icon;

    // NOTIFICATIONS
    public String notif;
    public boolean notifOn;
    int notifDuration;

    // GAME STATE
    public boolean gameFinished;


    // UI SUBSTATES
    private UIstate substate;
    public enum UIstate { SETTINGS, FULLSCREEN, MUSIC, VOLUME, QUIT, RESUME }
    public int homeUISubstate = 0;



    private int command;


    // ~ METHODS -----------------------------------------------------------------

    // CONSTRUCTOR -----------------------------------------------------------------
    public UI(GamePanel gp, Time time) {

        this.gp = gp;
        this.time = time;

        notifDuration = 0;
        notifOn = false;
        notif = "";
        gameFinished = false;
        timeFormat = new DecimalFormat("#0:00");

        // FONT SETUP
        fredokaSemiBold = importFont("Fredoka-SemiBold");

        // ICON SETUP
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;

        // OPTIONS UI
        command = 0;
        substate = UIstate.SETTINGS;
        String c;
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------------
    @Override
    public void update() {} // USELESS
    @Override
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(fredokaSemiBold);
        g2.setColor(Color.white);


        // HOME STATE
        if (gp.state == GamePanel.gameState.HOME) {
            homeUI();

        }
        else {
            // DRAW TIMER
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
            g2.drawString("Time: " + Time.getTimer(), gp.tileSize * 16, 65);

            switch (gp.state) {
                case PLAY:
                    playUI();
                    break;

                case PAUSE:
                    pauseUI();
                    break;

                case OPTIONS:
                    optionsUI();
                    break;
            }
        }

    }

    // FROM THIS CLASS -------------------------------------------------------------
    public void showNotification(String text) {
    // CONTROLS DISPLAY OF NOTIFS

        notif = text;
        notifOn = true;
    }
    private int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
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
    private void options_top(int frameX, int frameY) {

        int textX, textY;

        // TITLE
        String text = "OPTIONS";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (command == 0) {
            g2.drawString(">", textX - 25, textY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(textX, textY + 5, textX + 153, textY + 5);
        }

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (command == 1) {
            g2.drawString(">", textX - 25, textY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(textX, textY + 5, textX + 153, textY + 5);
        }

        // VOLUME
        textY += gp.tileSize;
        g2.drawString("Volume", textX, textY);
        if (command == 2) {
            g2.drawString(">", textX - 25, textY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(textX, textY + 5, textX + 153, textY + 5);
        }

        // QUIT
        textY += gp.tileSize;
        g2.drawString("Quit", textX, textY);
        if (command == 3) {
            g2.drawString(">", textX - 25, textY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(textX, textY + 5, textX + 153, textY + 5);
        }

        // RESUME
        textY += gp.tileSize * 2;
        g2.drawString("Resume", textX, textY);
        if (command == 4) {
            g2.drawString(">", textX - 25, textY);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawLine(textX, textY + 5, textX + 153, textY + 5);
        }
    }

    // UI STATES
    private void homeUI() {

        // BG
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (homeUISubstate == 0) {
            // TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Kusina ni VJ";
            int x = getXForCenteredText(text);
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
            g2.drawImage(gp.getNpc().get(0).getIdle(), x, y, gp.tileSize * 3, gp.tileSize * 3, null);

            // NEW GAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize * 4 + 30;
            g2.drawString(text, x, y);
            if (command == 0) {
                g2.drawString(">", x - gp.tileSize, y);
                g2.drawString("<", x + (gp.tileSize * 6) - 5, y);
                g2.setStroke(new BasicStroke(1.2F));
                g2.drawLine(x, y + 5, x + 265, y + 5);
            }

            // CUSTOMIZE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "CREDITS";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                g2.drawString(">", x - gp.tileSize, y);
                g2.drawString("<", x + (gp.tileSize * 5) - 20, y);
                g2.setStroke(new BasicStroke(1.2F));
                g2.drawLine(x, y + 5, x + (gp.tileSize * 4), y + 5);
            }

            // QUIT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                g2.drawString(">", x - gp.tileSize, y);
                g2.drawString("<", x + (gp.tileSize * 2) + 45, y);
                g2.setStroke(new BasicStroke(1.2F));
                g2.drawLine(x, y + 5, x + (gp.tileSize * 2) + 15, y + 5);
            }
        }
        else if (homeUISubstate == 1) {

            // BG
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select Avatar";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Boy 1";
            x = getXForCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (command == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Boy 2";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Girl 1";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Girl 2";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (command == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }

    }
    private void playUI(){}

    private void pauseUI() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    private void optionsUI() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (substate) {
            case SETTINGS:
                options_top(frameX, frameY);
                break;

            case FULLSCREEN:
                break;

            case MUSIC:
                break;

            case VOLUME:
                break;

            case QUIT:
                break;

            case RESUME:
                break;
        }
    }

    // GETTERS & SETTERS
    public int getCommand() {
        return command;
    }
    public void setCommand(int command) {
        this.command = command;
    }
    public UIstate getSubstate() {
        return substate;
    }
}