package ui;

import game.Time;
import interfaces.Drawable;
import main.GamePanel;


import java.awt.*;

public class UIFactory implements Drawable {

    // ~ FIELDS -------------------------------------------------------------
    private static UIFactory instance;

    private final GamePanel gp;

    private final HomeUI homeUI;
    private final PlayUI playUI;
    private final PauseUI pauseUI;
    private final OptionsUI optionsUI;
    private final DialogueUI dialogueUI;
    private final TerminalUI terminalUI;

    // CONSTRUCTOR ------------------------------------------------------------
    private UIFactory(GamePanel gp, Time time) {
        this.gp = gp;

        homeUI = new HomeUI(gp, time);
        playUI = new PlayUI(gp, time);
        pauseUI = new PauseUI(gp, time);
        optionsUI = new OptionsUI(gp, time);
        dialogueUI = new DialogueUI(gp, time);
        terminalUI = new TerminalUI(gp, time);
    }
    // SINGLETON INSTANTIATOR --------------------------------------------------
    public static UIFactory instantiate(GamePanel gp, Time time) {
        if (instance == null) {
            instance = new UIFactory(gp, time);
        }
        return instance;
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------
    @Override
    public void update() {}
    @Override
    public void draw(Graphics2D g2) {

        switch (gp.gameState) {
            case HOME:
                homeUI.draw(g2);
                break;

            case PLAY:
                playUI.draw(g2);
                break;

            case PAUSE:
                pauseUI.draw(g2);
                break;

            case OPTIONS:
                optionsUI.draw(g2);
                break;

            case DIALOGUE:
                dialogueUI.draw(g2);
                break;

            case TERMINAL:
                terminalUI.draw(g2);
                break;
        }
    }

    // UI GETTERS ----------------------------------------------------------------
    public OptionsUI getOptionsUI() { return optionsUI; }
    public PauseUI getPauseUI() { return pauseUI; }
    public PlayUI getPlayUI() { return playUI; }
    public HomeUI getHomeUI() { return homeUI; }
    public TerminalUI getTerminalUI() { return terminalUI; }

    // COMMAND GETTERS & SETTERS
    public int getCommand() { return UI.getCommand(); }
    public void setCommand(int command) { UI.setCommand(command); }

    // OTHER GETTERS & SETTERS
    public void setNotif(String notif) { UI.setNotif(notif); }

    public void resetParams() {
        playUI.resetParams();
        instance = null;
    }
}