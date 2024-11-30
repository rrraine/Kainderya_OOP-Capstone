package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;

public class DialogueUI extends UI implements Drawable {

    public DialogueUI(GamePanel gp, Time time) {
        super(gp, time);
    }

    @Override
    public void draw(Graphics2D g2) {
        // TODO POP-UP ORDER CLOUD ABOVE NPC
    }
}
