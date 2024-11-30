package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;

public class OptionsUI extends UI implements Drawable {

    public substate optionsState;
    public enum substate {START, FULLSCREEN, SOUND, MULTIPLAYER, QUIT, RESUME }

    public OptionsUI(GamePanel gp, Time time) {
        super(gp, time);
        command = 0;
        optionsState = substate.START;
    }

    @Override
    public void draw(Graphics2D g2) {

        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(g2, frameX, frameY, frameWidth, frameHeight);

       // g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
        g2.setFont(fredokaSemiBold.deriveFont(30F));

        // TODO OPTIONS STATE
        switch (optionsState) {

            case START:
                optionsSTART(frameX, frameY, g2);
                break;

            case FULLSCREEN:
                optionsFULLSCREEN(frameX, frameY, g2);
                break;

            case SOUND:
                break;

            case MULTIPLAYER:
                break;

            case QUIT:
                optionsQUIT(frameX, frameY, g2);
                break;

            case RESUME:
                break;
        }

        gp.getKeyB().setPlayer1EnterPressed(false);
    }


    // SUBSTATES
    private void optionsSTART(int frameX, int frameY, Graphics2D g2) {

        int textX, textY;

        // ~ TEXT DISPLAY ------------

        // TITLE
        String label = "OPTIONS";
        textX = Utility.Aligner.centerTextOnScreen(label, gp, g2);
        textY = frameY + gp.tileSize;
        drawLetterBorder(g2, label, Color.BLACK, 3, textX, textY);
        g2.setColor(Color.WHITE);
        g2.drawString(label, textX, textY);

        // FULL SCREEN ON/OFF
        label = "Full Screen";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;

        drawLetterBorder(g2, label, Color.BLACK, 2, textX, textY);

        if (command == 0) {
            g2.setColor(primary);
            g2.drawString(label, textX, textY);
            drawCursor(g2, label, textX, textY, true, true);

            // IF PRESSED
            if (gp.getKeyB().isPlayer1EnterPressed()) {
                // IF FULL SCREEN
                if (gp.isFullScreenOn()) {
                    gp.setFullScreenOn(false);
                }
                else {
                    gp.setFullScreenOn(true);
                }
                optionsState = substate.FULLSCREEN;
                command = 0;
            }
        }else{
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);
        }

        // MULTIPLAYER
        label = "Multiplayer";
        textY += gp.tileSize;
        drawLetterBorder(g2, label, Color.BLACK, 2, textX, textY);
        if (command == 1) {
            g2.setColor(primary);
            g2.drawString(label, textX, textY);
            drawCursor(g2, label, textX, textY, true, true);
        }else{
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);
        }

        // VOLUME
        label = "Volume";
        textY += gp.tileSize;
        drawLetterBorder(g2, label, Color.BLACK, 2, textX, textY);
        g2.setColor(Color.WHITE);
        g2.drawString(label, textX, textY);
        if (command == 2) {
            g2.setColor(primary);
            g2.drawString(label, textX, textY);
            drawCursor(g2, label, textX, textY, true, true);
        }else{
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);
        }

        // QUIT
        label = "Quit";
        textY += gp.tileSize;
        drawLetterBorder(g2, label, Color.BLACK, 2, textX, textY);
        if (command == 3) {
            g2.setColor(primary);
            g2.drawString(label , textX, textY);
            drawCursor(g2, label, textX, textY, true, true);
            if (gp.getKeyB().isPlayer1EnterPressed()) {
                optionsState = substate.QUIT;
                command = 0;
            }
        }else{
            g2.setColor(Color.WHITE);
            g2.drawString(label , textX, textY);
        }

        // RESUME
        label = "Resume";
        textY += gp.tileSize * 2;
        drawLetterBorder(g2, label, Color.BLACK, 2, textX, textY);
        if (command == 4) {
            g2.setColor(primary);
            g2.drawString(label, textX, textY);
            drawCursor(g2, label, textX, textY, true, true);
            if (gp.getKeyB().isPlayer1EnterPressed()) {
                gp.gameState = GamePanel.state.PLAY;
                command = 0;
            }
        }else{
            g2.setColor(Color.WHITE);
            g2.drawString(label, textX, textY);
        }

        // ~ BOX DISPLAY

        // FULL SCREEN BOX
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke((3)));
        g2.drawRect(textX, textY, 24, 24);
        // TOGGLE
        if (gp.isFullScreenOn()) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // VOLUME
        textY += gp.tileSize * 2;
        g2.drawRect(textX, textY, 120, 24);
    }
    void optionsFULLSCREEN(int frameX, int frameY, Graphics2D g2) {

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
            drawCursor(g2, "Back", textX, textY, true, true);
            if (gp.getKeyB().isPlayer1EnterPressed()){
                optionsState = substate.START;
                command = 0;
            }
        }

    }
    private void optionsQUIT(int frameX, int frameY, Graphics2D g2) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        notif = "Quit game and return \nto the home screen?";
        for (String line : notif.split("\n")) {
            drawLetterBorder(g2, line, Color.BLACK, 2, textX, textY);
            g2.setColor(Color.WHITE);
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String text = "Yes";
        textX = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        textY += gp.tileSize * 3;
        drawLetterBorder(g2, text, Color.BLACK, 2, textX, textY);
        if (command == 0) {
            g2.setColor(primary);
            drawCursor(g2, text, textX, textY, true, true);
            // QUIT
            if (gp.getKeyB().isPlayer1EnterPressed()) {
                optionsState = substate.START;
                HomeUI.homeState = HomeUI.substate.TITLE;
                gp.gameState = GamePanel.state.HOME;
            }
        }
        else {
            g2.setColor(Color.WHITE);
        }
        g2.drawString(text, textX, textY);

        // NO
        text = "No";
        textX = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        textY += gp.tileSize;
        drawLetterBorder(g2, text, Color.BLACK, 2, textX, textY);
        if (command == 1) {
            g2.setColor(primary);
            drawCursor(g2, text, textX, textY, true, true);
            // QUIT
            if (gp.getKeyB().isPlayer1EnterPressed()) {
                optionsState = substate.START;
                command = 0;
            }
        }
        else {
            g2.setColor(Color.WHITE);
        }
        g2.drawString(text, textX, textY);
    }
}
