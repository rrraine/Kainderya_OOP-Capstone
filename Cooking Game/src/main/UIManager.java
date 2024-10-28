package main;

import game.Time;
import interfaces.Drawable;


import java.awt.*;

public class UIManager implements Drawable {

    // ~ FIELDS -------------------------------------------------------------
    private static UIManager instance;

    private final GamePanel gp;
    private final UI ui;

    private final UI.HomeUI homeUI;
    private final UI.PlayUI playUI;
    private final UI.PauseUI pauseUI;
    private final UI.OptionsUI optionsUI;

    // CONSTRUCTOR ------------------------------------------------------------
    private UIManager (GamePanel gp, Time time) {
        this.gp = gp;
        ui = UI.instantiate(gp, time);

        homeUI = ui.getHomeUI();
        playUI = ui.getPlayUI();
        pauseUI = ui.getPauseUI();
        optionsUI = ui.getOptionsUI();
    }
    // SINGLETON INSTANTIATOR --------------------------------------------------
    public static UIManager instantiate(GamePanel gp, Time time) {
        if (instance == null) {
            instance = new UIManager(gp, time);
        }
        return instance;
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------
    @Override
    public void update() {}
    @Override
    public void draw(Graphics2D g2) {

        ui.draw(g2);

        switch (gp.gameState) {
            case HOME:
                homeUI.draw();
                break;

            case PLAY:
                playUI.draw();
                break;

            case PAUSE:
                pauseUI.draw();
                break;

            case OPTIONS:
                optionsUI.draw();
                break;
        }
    }

    // GETTERS ----------------------------------------------------------------
    public UI.OptionsUI getOptionsUI() {
        return optionsUI;
    }
    public UI.PauseUI getPauseUI() {
        return pauseUI;
    }
    public UI.PlayUI getPlayUI() {
        return playUI;
    }
    public UI.HomeUI getHomeUI() {
        return homeUI;
    }

    // COMMAND GETTERS & SETTERS
    public int getCommand() {
        return ui.getCommand();
    }
    public void setCommand(int command) {
        ui.setCommand(command);
    }

}