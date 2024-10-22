package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    int messageCounter = 0;

    public boolean messageOn = false;
    public String message = "";

    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0");

    public UI(GamePanel gp) {
        this.gp = gp;

        // SET UP FONT NAME, STYLE, SIZE
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // INSTANTIATE KEY
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        // GAME TERMINATION
        if (gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            // GAME OVER MESSAGE
            String text = "You found the treasure!";
            int textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // LENGTH OF TEXT
            int x, y;

            // PRINTING COORDINATES
            x = gp.screenWidth / 2 - textLen / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            // TIME MESSAGE
            dFormat.applyPattern("#0.00");
            text = "Your Time is " + dFormat.format(playTime) + "!";
            textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            // PRINTING COORDINATES
            x = gp.screenWidth / 2 - textLen / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            // CONGRATULATIONS
            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLen = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            // PRINTING COORDINATES
            x = gp.screenWidth / 2 - textLen / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            // GAME END
            gp.gameThread = null;
        }
        else {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            // DRAW TO SCREEN: XY LOCATION ON SCREEN
            g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            // DRAW TIME
            playTime += (double) 1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 12, 65);

            // DRAW MESSAGE
            if (messageOn) {

                g2.setFont(g2.getFont().deriveFont(30f));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*3);

                messageCounter++;

                // 120 FRAMES / 60 FPS = 2 SECONDS
                if (messageCounter > 90) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }
}
