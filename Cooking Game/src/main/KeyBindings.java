package main;

import ui.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBindings implements KeyListener {

    // ~ FIELDS -----------------------------------------------------------------
    private static KeyBindings instance;

    GamePanel gp;
    UIFactory uiM;

    private boolean player1UpPressed;
    private boolean player1DownPressed;
    private boolean player1LeftPressed;
    private boolean player1RightPressed;
    private boolean player1EnterPressed;
    private boolean player1ShiftPressed;
    private boolean player1FPressed;

    private boolean player2UpPressed;
    private boolean player2DownPressed;
    private boolean player2LeftPressed;
    private boolean player2RightPressed;
    private boolean player2EnterPressed;
    private boolean player2ShiftPressed;
    private boolean player2CtrlPressed;


    private int lastCommand = 0;

    // CONSTRUCTOR -----------------------------------------------------------------
    private KeyBindings(GamePanel gp, UIFactory uiM) {
        this.gp = gp;
        this.uiM = uiM;
    }
    // SINGLETON INSTANTIATE -------------------------------------------------
    public static KeyBindings instantiate(GamePanel gp, UIFactory uiM) {
        if (instance == null) {
            instance = new KeyBindings(gp, uiM);
        }
        return instance;
    }

   // FROM INTERFACE: KEYLISTENER ------------------------------------------------
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        switch (gp.gameState) {

            case HOME:
                homeBindings(code);
                break;

            case PLAY:
                playBindings(code);
                break;

            case PAUSE:
                pauseBindings(code);
                break;

            case OPTIONS:
                optionsBindings(code);
                break;

            case TERMINAL:
                terminalBindings(code);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // PLAYER 1 CONTROLS
        if (code == KeyEvent.VK_W) {
            player1UpPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            player1DownPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            player1LeftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            player1RightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            player1ShiftPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            player1EnterPressed = false;
        }

        // PLAYER 2 CONTROLS
        if (code == KeyEvent.VK_UP) {
            player2UpPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            player2DownPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            player2LeftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            player2RightPressed = false;
        }
        if (code == KeyEvent.VK_CONTROL) {
            player2ShiftPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            player2EnterPressed = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {} // USELESS


    // GAME STATE BINDINGS --------------------------------------------------------
    private void homeBindings(int code) {

        // HOME STATE -> TITLE SUB-STATE
        if (uiM.getHomeUI().homeState == HomeUI.substate.TITLE) {

            // W & S
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() -1);
                if (uiM.getCommand() < 0) {
                    uiM.setCommand(2);
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() +1);
                if (uiM.getCommand() > 2) {
                    uiM.setCommand(0);
                }
            }
            // EXECUTE
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {
                gp.playSFX(2);
                // NEW GAME
                if (uiM.getCommand() == 0) {
                    gp.playSFX(2);
                    uiM.getHomeUI().homeState = HomeUI.substate.MULTIPLAYER;
                    // NEW GAME
                    gp.music.playSound();
                }
                // CREDITS
                else if (uiM.getCommand() == 1) {
                    // TODO CUSTOMIZE
                    gp.playSFX(2);
                    uiM.getHomeUI().homeState = HomeUI.substate.CREDITS;
                }
                // QUIT
                else if (uiM.getCommand() == 2) {
                    gp.playSFX(2);
                    System.exit(0);
                }
            }
        }

        // HOME STATE -> SELECTION SUB-STATE
        else if (uiM.getHomeUI().homeState == HomeUI.substate.SELECTION) {

            // A & D
            if (uiM.getCommand() != 4) {
                if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    gp.playSFX(2);
                    uiM.setCommand(uiM.getCommand() -1);
                    if (uiM.getCommand() < 0) {
                        uiM.setCommand(3);
                    }
                }
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    gp.playSFX(2);
                    uiM.setCommand(uiM.getCommand() +1);
                    if (uiM.getCommand() > 3) {
                        uiM.setCommand(0);
                    }
                }
                lastCommand = uiM.getCommand();
            }

            // W & S
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.playSFX(2);

                if (uiM.getCommand() < 4) {
                    uiM.setCommand(4);
                } else {
                    uiM.setCommand(lastCommand);
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.playSFX(2);
                if (uiM.getCommand() < 4) {
                    uiM.setCommand(4);
                } else {
                    uiM.setCommand(lastCommand);
                }
            }

            // EXECUTE
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {
                gp.playSFX(2);

                String playerAvatar = "", playerName = "";

                if (uiM.getCommand() == 0) {
                    playerAvatar = "Cook1";
                    playerName = "MIGUEL";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 1) {
                    playerAvatar = "Cook2";
                    playerName = "GINA";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 2) {
                    playerAvatar = "Cook3";
                    playerName = "JAVIER";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 3) {
                    playerAvatar = "Cook4";
                    playerName = "SOFIA";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 4) {
                    // NAME FIELD
                    playerName = "Aaron";
                    gp.playSFX(2);
                }

                try {
                    if (playerName.isBlank() || playerAvatar.isBlank()) {
                        throw new Exception("Unexpected Character Selection Error");
                    }
                    gp.selectCharacter(playerAvatar, playerName);
                    PlayUI.playState = PlayUI.substate.LOADING;
                    gp.gameState = GamePanel.state.PLAY;
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }

            }
        }

        // HOME STATE -> CREDITS SUB-STATE
        else if (uiM.getHomeUI().homeState == HomeUI.substate.CREDITS) {

            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {
                gp.playSFX(2);

                uiM.getHomeUI().homeState = HomeUI.substate.TITLE;
            }
        }

        // HOME STATE -> MULTIPLAYER SUB-STATE
        else if (uiM.getHomeUI().homeState == HomeUI.substate.MULTIPLAYER) {

            // A & D
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() -1);
                if (uiM.getCommand() < 0) {
                    uiM.setCommand(1);
                }
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() +1);
                if (uiM.getCommand() > 1) {
                    uiM.setCommand(0);
                }
            }

            // EXECUTE
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {

                gp.playSFX(2);

                if (uiM.getCommand() == 0) {
                    gp.setMultiplayer(false);
                }
                else if (uiM.getCommand() == 1) {
                    gp.setMultiplayer(true);
                }

                uiM.getHomeUI().homeState = HomeUI.substate.SELECTION;
            }
        }
    }
    private void playBindings(int code) {

        // player 1 movement
        if (code == KeyEvent.VK_W) { player1UpPressed = true; }
        if (code == KeyEvent.VK_S) { player1DownPressed = true; }
        if (code == KeyEvent.VK_A) { player1LeftPressed = true; }
        if (code == KeyEvent.VK_D) { player1RightPressed = true; }
        if (code == KeyEvent.VK_F) { player1EnterPressed = true; }
        if (code == KeyEvent.VK_SHIFT) { player1ShiftPressed = true; }

        // player 2 movement
        if (code == KeyEvent.VK_UP) { player2UpPressed = true; }
        if (code == KeyEvent.VK_DOWN) { player2DownPressed = true; }
        if (code == KeyEvent.VK_LEFT) { player2LeftPressed = true; }
        if (code == KeyEvent.VK_RIGHT) { player2RightPressed = true; }
        if (code == KeyEvent.VK_ENTER) { player2EnterPressed = true; }
        if (code == KeyEvent.VK_CONTROL) { player2ShiftPressed = true; }

        // options
        if (code == KeyEvent.VK_ESCAPE) {
            gp.playSFX(2);
            uiM.setCommand(0);
            uiM.getOptionsUI().optionsState = OptionsUI.substate.START;
            gp.gameState = GamePanel.state.OPTIONS;
        }
        // pause
        if (code == KeyEvent.VK_P) {
            gp.playSFX(2);
            gp.gameState = GamePanel.state.PAUSE;
        }
        // terminal
        if (code == KeyEvent.VK_BACK_SPACE) {
            gp.playSFX(3);
            uiM.getTerminalUI().terminalState = TerminalUI.substate.TIMESUP;
            gp.gameState = GamePanel.state.TERMINAL;
        }
    }
    private void pauseBindings(int code) {

        if (code == KeyEvent.VK_P) {
            gp.gameState = GamePanel.state.PLAY;
        }
    }
    private void optionsBindings(int code) {

        // SPECIAL BINDINGS
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GamePanel.state.PLAY;
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {
            player1EnterPressed = true;
            player2EnterPressed = true;
        }

        // BINDINGS
        int maxCommand = 0;

        switch (uiM.getOptionsUI().optionsState) {
            case START:
                maxCommand = 4;
                break;
            case QUIT:
                maxCommand = 1;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            uiM.setCommand(uiM.getCommand()-1);
            gp.playSFX(2);
            if (uiM.getCommand() < 0) {
                uiM.setCommand(maxCommand);
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            uiM.setCommand(uiM.getCommand()+1);
            gp.playSFX(2);
            if (uiM.getCommand() > maxCommand) {
                uiM.setCommand(0);
            }
        }
    }
    private void terminalBindings(int code) {

        // TERMINAL STATE -> LEADERBOARDS SUB-STATE
        if (uiM.getTerminalUI().terminalState == TerminalUI.substate.LEADERBOARD) {

            if (code == KeyEvent.VK_ENTER) {
                uiM.getHomeUI().homeState = HomeUI.substate.TITLE;
                gp.gameState = GamePanel.state.HOME;
                uiM.getTerminalUI().terminalState = TerminalUI.substate.TIMESUP;
            }
        }
    }

    // GETTERS & SETTERS ---------------------------------------------------------
    public boolean isPlayer1UpPressed() {
        return player1UpPressed;
    }
    public boolean isPlayer1DownPressed() {
        return player1DownPressed;
    }
    public boolean isLeftPressed() {
        return player1LeftPressed;
    }
    public boolean isPlayer1RightPressed() {
        return player1RightPressed;
    }
    public boolean isPlayer1EnterPressed() {
        return player1EnterPressed;
    }
    public boolean isPlayer1ShiftPressed() {
        return player1ShiftPressed;
    }


    public void setPlayer1UpPressed(boolean player1UpPressed) {
        this.player1UpPressed = player1UpPressed;
    }
    public void setPlayer1DownPressed(boolean player1DownPressed) {
        this.player1DownPressed = player1DownPressed;
    }
    public void setLeftPressed(boolean leftPressed) {
        this.player1LeftPressed = leftPressed;
    }
    public void setPlayer1RightPressed(boolean player1RightPressed) {
        this.player1RightPressed = player1RightPressed;
    }
    public void setPlayer1EnterPressed(boolean player1EnterPressed) {
        this.player1EnterPressed = player1EnterPressed;
    }
}