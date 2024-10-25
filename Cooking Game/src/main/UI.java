package main;

import interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI implements Drawable {

    // ~ FIELDS -----------------------------------------------------------------

    GamePanel gp;
    Graphics2D g2;

    // FONTS & TIME FORMATS
    Font arial_40, arial_80B;
    DecimalFormat timeFormat;

    //ICONS & TIME
    BufferedImage icon;
    double playTime;

    // NOTIFICATIONS
    public String notif;
    public boolean notifOn;
    int notifDuration;

    // GAME STATE
    public boolean gameFinished;


    // ~ METHODS -----------------------------------------------------------------

    // CONSTRUCTOR -----------------------------------------------------------------
    public UI(GamePanel gp) {

        this.gp = gp;
        notifDuration = 0;
        notifOn = false;
        notif = "";
        gameFinished = false;
        timeFormat = new DecimalFormat("#0");

        // FONT SETUP
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // ICON SETUP
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------------
    @Override
    public void update() {} // USELESS
    @Override
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            // TODO PLAYSTATE
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
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
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

}