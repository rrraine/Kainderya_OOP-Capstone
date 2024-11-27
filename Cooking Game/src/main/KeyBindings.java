package main;

import object.Item;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBindings implements KeyListener {

    // ~ FIELDS -----------------------------------------------------------------
    private static KeyBindings instance;

    GamePanel gp;
    UIControl uiM;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private boolean shiftPressed;

    private int lastCommand = 0;


    // CONSTRUCTOR -----------------------------------------------------------------
    private KeyBindings(GamePanel gp, UIControl uiM) {
        this.gp = gp;
        this.uiM = uiM;
    }
    // SINGLETON INSTANTIATE -------------------------------------------------
    public static KeyBindings instantiate(GamePanel gp, UIControl uiM) {
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

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {} // USELESS


    // GAME STATE BINDINGS --------------------------------------------------------
    private void homeBindings(int code) {

        // HOME STATE -> TITLE SUB-STATE
        if (uiM.getHomeUI().homeState == UI.HomeUI.substate.TITLE) {

            // W & S
            if (code == KeyEvent.VK_W) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() -1);
                if (uiM.getCommand() < 0) {
                    uiM.setCommand(2);
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.playSFX(2);
                uiM.setCommand(uiM.getCommand() +1);
                if (uiM.getCommand() > 2) {
                    uiM.setCommand(0);
                }
            }
            // EXECUTE
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F) {
                gp.playSFX(2);
                if (uiM.getCommand() == 0) {
                    gp.playSFX(2);
                    uiM.getHomeUI().homeState = UI.HomeUI.substate.SELECTION;
                    // NEW GAME
                    gp.music.playSound();
                }
                else if (uiM.getCommand() == 1) {
                    // TODO CUSTOMIZE
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 2) {
                    // QUIT
                    gp.playSFX(2);
                    System.exit(0);
                }
            }
        }

        // HOME STATE -> SELECTION SUB-STATE
        else if (uiM.getHomeUI().homeState == UI.HomeUI.substate.SELECTION) {

            // A & D
            if (uiM.getCommand() != 4) {
                if (code == KeyEvent.VK_A) {
                    gp.playSFX(2);
                    uiM.setCommand(uiM.getCommand() -1);
                    if (uiM.getCommand() < 0) {
                        uiM.setCommand(3);
                    }
                }
                if (code == KeyEvent.VK_D) {
                    gp.playSFX(2);
                    uiM.setCommand(uiM.getCommand() +1);
                    if (uiM.getCommand() > 3) {
                        uiM.setCommand(0);
                    }
                }
                lastCommand = uiM.getCommand();
            }

            // TODO AVATAR & NAME
            // W & S
            if (code == KeyEvent.VK_W) {
                gp.playSFX(2);

                if (uiM.getCommand() < 4) {
                    uiM.setCommand(4);
                } else {
                    uiM.setCommand(lastCommand);
                }
            }
            if (code == KeyEvent.VK_S) {
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
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 1) {
                    playerAvatar = "Cook2";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 2) {
                    playerAvatar = "Cook1";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 3) {
                    playerAvatar = "Cook4";
                    gp.playSFX(2);
                }
                else if (uiM.getCommand() == 4) {
                    // NAME FIELD
                    playerName = "Aaron";
                    gp.playSFX(2);
                }

                // TODO IMPLEMENT EXCEPTION HANDLING IF BOTH PLAYERAVATAR AND PLAYERNAME ARE EMPTY & USE WHILE LOOP TO REPEAT THE PROMPT
                gp.selectCharacter(playerAvatar, playerName);

                // TODO CANNOT PROCEED IF NAME EMPTY
                gp.gameState = GamePanel.state.PLAY;
            }
        }
    }
    private void playBindings(int code) {

        // movement
        if (code == KeyEvent.VK_W) { upPressed = true; }
        if (code == KeyEvent.VK_S) { downPressed = true; }
        if (code == KeyEvent.VK_A) { leftPressed = true; }
        if (code == KeyEvent.VK_D) { rightPressed = true; }
        if (code == KeyEvent.VK_SHIFT) { shiftPressed = true; }

        // options
        if (code == KeyEvent.VK_ESCAPE) {
            gp.playSFX(2);
            uiM.setCommand(0);
            uiM.getOptionsUI().optionsState = UI.OptionsUI.substate.START;
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
            uiM.getTerminalUI().terminalState = UI.TerminalUI.substate.TIMESUP;
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
            enterPressed = true;
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
        if (code == KeyEvent.VK_W) {
            uiM.setCommand(uiM.getCommand()-1);
            gp.playSFX(2);
            if (uiM.getCommand() < 0) {
                uiM.setCommand(maxCommand);
            }
        }
        if (code == KeyEvent.VK_S) {
            uiM.setCommand(uiM.getCommand()+1);
            gp.playSFX(2);
            if (uiM.getCommand() > maxCommand) {
                uiM.setCommand(0);
            }
        }
    }
    private void terminalBindings(int code) {

        // TERMINAL STATE -> LEADERBOARDS SUB-STATE
        if (uiM.getTerminalUI().terminalState == UI.TerminalUI.substate.LEADERBOARD) {

            if (code == KeyEvent.VK_ENTER) {
                uiM.getHomeUI().homeState = UI.HomeUI.substate.TITLE;
                gp.gameState = GamePanel.state.HOME;
                uiM.getTerminalUI().terminalState = UI.TerminalUI.substate.TIMESUP;
            }
        }
    }

    // GETTERS & SETTERS ---------------------------------------------------------
    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isEnterPressed() {
        return enterPressed;
    }
    public boolean isShiftPressed() {
        return shiftPressed;
    }


    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }
    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }
}