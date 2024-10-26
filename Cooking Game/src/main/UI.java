package main;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

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

        // DRAW TIMER
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
        g2.drawString("Time: " + Time.getTimer(), gp.tileSize* 15, 65);

        if (gp.gameState == gp.playState) {
            playUI();
        }
        if (gp.gameState == gp.pauseState) {
            pauseUI();
        }
        if (gp.gameState == gp.optionState) {
           optionsUI();
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

    // UI STATES
    private void playUI(){};
    private void pauseUI() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    private void optionsUI() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    }

}