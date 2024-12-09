package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;

public class PlayUI extends UI implements Drawable {

    public static substate playState;
    public enum substate { LOADING, GAME }

    UIElement staminaBar, loadingBar;

    private int loadTime;
    private static int randomNum;

    public PlayUI(GamePanel gp, Time time) {
        super(gp, time);
        staminaBar = new UIElement(gp, "staminaBar", "staminaBar", 0, false);
        loadingBar = new UIElement(gp, "staminaBar", "staminaBar", 0, false);
        playState = substate.LOADING;

        loadTime = 60 * 6;
    }
    @Override
    public void draw(Graphics2D g2) {

        switch (playState) {

            case LOADING:
                playLOADING(g2);
                break;
            case GAME:
                playGAME(g2);
                break;
        }
    }

    private void playGAME(Graphics2D g2) {

        if (gp.gameState != GamePanel.state.HOME) {

            // INITIALIZE
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

            // TIMER
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

            // STAMINA
            if (gp.player.getStamina() >= 0) {
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
                g2.drawString("Stamina: " + gp.player.getStamina(), gp.tileSize * 16 + 5, 100);
            }
            else {
                g2.setColor(Color.RED);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
                g2.drawString("Stamina: " + gp.player.getStamina(), gp.tileSize * 16 + 5, 100);
            }

            // SCORE
            String score = Integer.toString(gp.score.getTotalScore());
            g2.setFont(g2.getFont().deriveFont(24F));
            x = gp.tileSize * 18 + 5;
            y = gp.screenHeight/2;
            drawLetterBorder(g2, "Score: " + score, Color.BLACK, 3, x, y);
            g2.setColor(Color.WHITE);
            g2.drawString("Score: " + score, x, y);

        }
    }
    private void playLOADING(Graphics2D g2) {

        g2.setColor(primary);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (loadTime <= 0) {
            loadTime = 0;
            playState = substate.GAME;
        }

        // tips
        g2.setColor(Color.BLACK);
        g2.setFont(balooBold);
        g2.setFont(g2.getFont().deriveFont(24F));

        String text = null;

        switch (randomNum) {
            case 1:
                text = "COMBINE INGREDIENTS TO MAKE TASTY DISHES.";
                break;
            case 2:
                text = "COLA IS NOW AVAILABLE! CHECK THE FRIDGE OUTSIDE.";
                break;
            case 3:
                text = "WATCH YOUR STAMINA. KITCHEN ACCIDENTS ARE COMMON.";
                break;
            default:
                text = "CUSTOMERS CAN BE PRETTY IMPATIENT.";
                break;
        }
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        g2.drawString(text, x, gp.screenHeight/2 + gp.tileSize);

        loadTime--;
        loadingBar.drawLoadingBar(g2, loadTime);
    }


    public static void generateRandomNum() {
        randomNum = random.nextInt(4) +1;
    }
}
