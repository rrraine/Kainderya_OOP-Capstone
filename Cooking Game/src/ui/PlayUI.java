package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;

public class PlayUI extends UI implements Drawable {

    UIElement staminaBar;

    public PlayUI(GamePanel gp, Time time) {
        super(gp, time);
        staminaBar = new UIElement(gp, "staminaBar", "staminaBar", 0, false);
    }
    @Override
    public void draw(Graphics2D g2) {

        // DRAW TIMER
        if (gp.gameState != GamePanel.state.HOME) {

            g2.setFont(fredokaSemiBold);
            g2.setFont(g2.getFont().deriveFont(18F));
            
            // PLAYER NAME
            int x = Utility.Aligner.centerTextOnAvatar(gp.player.getPlayerName(), gp, g2);
            int y = gp.player.getPlayerCenteredScreenY() - 10;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,18F));
            drawLetterBorder(g2, gp.player.getPlayerName(), Color.BLACK, 1, x, y);
            g2.setColor(player1);
            g2.drawString(gp.player.getPlayerName(), x, y);

            // ORDER PANE
            x = -10;
            y = gp.tileSize * 10 + 48;
            int frameWidth = gp.tileSize * 20 + gp.tileSize/3;
            int frameHeight = gp.tileSize + 26;
            drawPopUpWindow(g2, x, y, frameWidth, frameHeight);

            // CIRCLE FOR MENU ICON
            drawCircle(g2, gp.tileSize/2, y - gp.tileSize + 12, gp.tileSize + 48 + 6, gp.tileSize + 48, Color.WHITE, Color.BLACK);

            // STAMINA BAR
            x = gp.screenWidth - gp.tileSize *2 - 20;
            y -= gp.tileSize * 2;
            staminaBar.reposition(x, y);
            staminaBar.drawStaminaBar(g2, gp.player.staminaMeter());

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