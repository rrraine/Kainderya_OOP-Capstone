package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;

public class TerminalUI extends UI implements Drawable {

    public substate terminalState;
    public enum substate { TIMESUP, LEADERBOARD }

    Utility.Regulator ut = new Utility.Regulator();

    public TerminalUI(GamePanel gp, Time time) {
        super(gp, time);
        terminalState = substate.TIMESUP;
    }
    @Override
    public void draw(Graphics2D g2) {

        switch (terminalState) {

            case TIMESUP:
                terminalTIMESUP(g2);
                break;

            case LEADERBOARD:
                terminalLEADERBOARD(g2);
                break;
        }
    }
    private void terminalTIMESUP(Graphics2D g2) {

        // TIMES UP TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        g2.setColor(Color.RED);
        String text = "TIMES UP!";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x + shakeEffect(1), y + shakeEffect(2));

        // block for 2 secs then proceed
        if (ut.block(2)) {
            terminalState = substate.LEADERBOARD;
        }

    }
    private void terminalLEADERBOARD(Graphics2D g2) {

        // SUB-WINDOW
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 18;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(g2, frameX, frameY, frameWidth, frameHeight);

        // LEADERBOARDS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
        g2.setColor(Color.WHITE);
        String text = "LEADERBOARDS";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        // RETURN TO HOME SCREEN
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
        g2.setColor(Color.WHITE);
        text = "Press Enter to Continue...";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y = gp.tileSize * 10;
        g2.drawString(text, x, y);
    }
}
