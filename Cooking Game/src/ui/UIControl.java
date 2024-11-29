package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;


import java.awt.*;

public class UIControl implements Drawable {

    // ~ FIELDS -------------------------------------------------------------
    private static UIControl instance;

    private final GamePanel gp;
    private final UI ui;

    private final UI.HomeUI homeUI;
    private final UI.PlayUI playUI;
    private final UI.PauseUI pauseUI;
    private final UI.OptionsUI optionsUI;
    private final UI.DialogueUI dialogueUI;
    private final UI.TerminalUI terminalUI;

    // CONSTRUCTOR ------------------------------------------------------------
    private UIControl(GamePanel gp, Time time) {
        this.gp = gp;
        ui = UI.instantiate(gp, time);

        homeUI = ui.getHomeUI();
        playUI = ui.getPlayUI();
        pauseUI = ui.getPauseUI();
        optionsUI = ui.getOptionsUI();
        dialogueUI = ui.getDialogueUI();
        terminalUI = ui.getTerminalUI();
    }
    // SINGLETON INSTANTIATOR --------------------------------------------------
    public static UIControl instantiate(GamePanel gp, Time time) {
        if (instance == null) {
            instance = new UIControl(gp, time);
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

            case DIALOGUE:
                dialogueUI.draw();
                break;

            case TERMINAL:
                terminalUI.draw();
                break;
        }
    }

    // UI GETTERS ----------------------------------------------------------------
    public UI.OptionsUI getOptionsUI() { return optionsUI; }
    public UI.PauseUI getPauseUI() { return pauseUI; }
    public UI.PlayUI getPlayUI() { return playUI; }
    public UI.HomeUI getHomeUI() { return homeUI; }
    public UI.TerminalUI getTerminalUI() { return terminalUI; }

    // COMMAND GETTERS & SETTERS
    public int getCommand() { return ui.getCommand(); }
    public void setCommand(int command) { ui.setCommand(command); }

    // OTHER GETTERS & SETTERS
    public void setNotif(String notif) { ui.setNotif(notif); }
}