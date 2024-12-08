package ui;

import game.Score;
import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.io.*;

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
        g2.setFont(balooBold);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "TIMES UP!";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.screenHeight / 2;
        drawLetterBorder(g2, text, Color.BLACK, 3, x + shakeEffect(1), y + shakeEffect(2));
        g2.setColor(Color.RED);
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
        g2.setFont(luckiestGuy);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
        String text = "LEADERBOARDS";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.tileSize * 3;

        // LETTERING: WHITE
        g2.setColor(primary);
        g2.drawString(text, x + 12, y + 12);

        // SHADOW TEXT COLOR
        g2.setColor(primaryAccent);
        g2.drawString(text, x + 8, y + 8);

        drawLetterBorder(g2, text, Color.BLACK, 3, x, y);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        // FINAL SCORE
        String score = "Final Score: " + gp.score.getTotalScore();
        g2.setFont(fredokaSemiBold);
        g2.setFont(g2.getFont().deriveFont(30F));
        x = Utility.Aligner.centerTextOnScreen(score, gp, g2);
        y = gp.screenHeight/2 - 30;
        g2.setColor(Color.WHITE);
        g2.drawString(score, x, y);

        // PAST GAME SCORE
        score = "Past Score: " + gp.score.getPastScore();
        g2.setFont(g2.getFont().deriveFont(30F));
        x = Utility.Aligner.centerTextOnScreen(score, gp, g2);
        y += gp.tileSize;
        g2.setColor(Color.WHITE);
        g2.drawString(score, x, y);

        // OVERRIDE THE HIGHEST SCORE
        gp.score.updateHighestScore(gp.score.getTotalScore());

        // CURRENT HIGHEST SCORE
        score = "HIGHEST SCORE: " + gp.score.retrieveCRUDScore();
        g2.setFont(luckiestGuy);
        g2.setFont(g2.getFont().deriveFont(37F));
        x = Utility.Aligner.centerTextOnScreen(score, gp, g2);
        y += gp.tileSize + 13;
        g2.setColor(primary);
        g2.drawString(score, x, y);


        // RETURN TO HOME SCREEN
        g2.setFont(fredokaSemiBold);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
        g2.setColor(Color.WHITE);
        text = "Press Enter to Continue...";
        x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        y = gp.tileSize * 10;
        g2.drawString(text, x, y);
    }



}
