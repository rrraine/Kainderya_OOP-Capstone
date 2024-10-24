package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    // ~ FIELDS
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    //BufferedImage keyImage;
    int messageCounter = 0;

    public boolean messageOn = false;
    public String message = "";

    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0");

    // ~ METHODS

    public UI(GamePanel gp) {
        this.gp = gp;

        // SET UP FONT NAME, STYLE, SIZE
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // INSTANTIATE KEY TO DISPLAY AS UI
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;
    }

    // CONTROLS DISPLAY OF MESSAGE NOTIFS
    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    // DRAW THE UI CALLED BY GAMEPANEL
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
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    private int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
