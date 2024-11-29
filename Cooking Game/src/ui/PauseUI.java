package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;
import main.Utility;

import java.awt.*;

public class PauseUI extends UI implements Drawable {

    PauseUI(GamePanel gp, Time time) {
        super(gp, time);
    }

    @Override
    public void draw(Graphics2D g2) {

        // PAUSE TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = Utility.Aligner.centerTextOnScreen(text, gp, g2);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

}
