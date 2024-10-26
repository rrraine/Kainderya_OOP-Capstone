package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // ~ FIELDS -----------------------------------------------------------------
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // ~ METHODS -----------------------------------------------------------------
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

   // FROM INTERFACE: KEYLISTENER ------------------------------------------------
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // HOME STATE
        if (gp.state == GamePanel.gameState.HOME) {

            if (code == KeyEvent.VK_W) {
               gp.ui.setCommand(gp.ui.getCommand() -1);
               if (gp.ui.getCommand() < 0) {
                   gp.ui.setCommand(2);
               }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.setCommand(gp.ui.getCommand() +1);
                if (gp.ui.getCommand() > 2) {
                    gp.ui.setCommand(0);
                }
            }

            // KEY EXECUTION
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.getCommand() == 0) {
                    // NEW GAME
                    gp.state = GamePanel.gameState.PLAY;
                    gp.music.playSound();
                }
                else if (gp.ui.getCommand() == 1) {
                    // TODO CUSTOMIZE
                }
                else if (gp.ui.getCommand() == 2) {
                    // QUIT
                    System.exit(0);
                }
            }
        }

        // PLAY STATE
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            optionState(code);
        }
        if (code == KeyEvent.VK_P) {
            if (gp.state == GamePanel.gameState.PLAY) {
                gp.state = GamePanel.gameState.PAUSE;
            }
            else if (gp.state == GamePanel.gameState.PAUSE) {
                gp.state = GamePanel.gameState.PLAY;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // RECORDS IF USER RELEASES KEY TILE
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
    }

    @Override
    public void keyTyped(KeyEvent e) {} // USELESS

    // GAME STATE CONTROLS
    public void optionState(int code) {

        if (gp.state == GamePanel.gameState.PLAY) {
            gp.state = GamePanel.gameState.OPTIONS;
        }
        else if (gp.state == GamePanel.gameState.OPTIONS) {
            gp.state = GamePanel.gameState.PLAY;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommand = 0;
        switch (gp.ui.getSubstate()) {
            case UI.UIstate.SETTINGS:
                maxCommand = 4;
        }
        System.out.println(code);
        if (code == KeyEvent.VK_W) {
            System.out.println("HELLO");
            gp.ui.setCommand(gp.ui.getCommand()-1);
            gp.playSFX(2);
            if (gp.ui.getCommand() < 0) {
                gp.ui.setCommand(maxCommand);
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.setCommand(gp.ui.getCommand()+1);
            gp.playSFX(2);
            if (gp.ui.getCommand() > maxCommand) {
                gp.ui.setCommand(0);
            }
        }


    }

}