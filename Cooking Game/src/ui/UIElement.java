package ui;

import interfaces.Importable;
import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIElement implements Importable {

    GamePanel gp;

    // ANIMATED WALLPAPER
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int x;
    private int y;
    private boolean movingLeft;

    private int frameInterval;

    private BufferedImage image1, image2, image3, image4, image5, image6, image7;

    public UIElement(GamePanel gp, String type, String specific, int frameInterval, boolean movingLeft) {
        this.gp = gp;
        this.frameInterval = frameInterval;
        this.movingLeft = movingLeft;

        switch (type) {
            case "npc":
                renderAvatar("/avatar/npc/" + specific);
                break;
            case "player":
                renderAvatar("/avatar/player/" + specific);
                break;
            case "staminaBar":
                renderStaminaBar();
                break;
        }
    }

    private void renderAvatar(String path) {
        image1 = importImage(path + "/idle", gp.tileSize);
        image2 = importImage(path + "/left1", gp.tileSize);
        image3 = importImage(path + "/left2", gp.tileSize);
        image4 = importImage(path + "/right1", gp.tileSize);
        image5 = importImage(path + "/right2", gp.tileSize);
        image6 = importImage(path + "/down1", gp.tileSize);
        image7 = importImage(path + "/down2", gp.tileSize);
    }
    private void renderStaminaBar() {
        image1 = importImage("/ui/staminaBar/staminaBar_full", gp.tileSize);
        image2 = importImage("/ui/staminaBar/staminaBar_3", gp.tileSize);
        image3 = importImage("/ui/staminaBar/staminaBar_2", gp.tileSize);
        image4 = importImage("/ui/staminaBar/staminaBar_1", gp.tileSize);
        image5 = importImage("/ui/staminaBar/staminaBar_emptyBlack", gp.tileSize);
        image6 = importImage("/ui/staminaBar/staminaBar_emptyRed", gp.tileSize);
    }

    public void reposition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawAvatarSideMoving(Graphics2D g2) {
        int speed = 2;

        // MOVING DISPLACEMENT
        if (movingLeft) {
            x -= speed;

            if (x < -gp.tileSize * 2) {
                movingLeft = false;
            }
        } else {
            x += speed;

            if (x > gp.screenWidth) {
                movingLeft = true;
            }
        }

        // DRAW SPRITE
        if (spriteNum == 1) {
            g2.drawImage(
                    movingLeft ? image2 : image4,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    movingLeft ? image3 : image5,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        }

        // ALTERNATE SPRITE MOVE POSES
        spriteCounter++;
        if (spriteCounter > frameInterval) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 1;
        }
    }
    public void drawAvatarFrontMoving(Graphics2D g2) {

        // DRAW SPRITE
        if (spriteNum == 1) {
            g2.drawImage(
                    image6,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    image7,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        }

        // ALTERNATE SPRITE MOVE POSES
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 1;
        }
    }
    public void drawAvatarFrontStatic(Graphics2D g2) {

        g2.drawImage(image1, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
    }
    public void drawStaminaBar(Graphics2D g2, int phase) {

        switch (phase) {
            case 1:
                g2.drawImage(image1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                break;
            case 2:
                g2.drawImage(image2, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                break;
            case 3:
                g2.drawImage(image3, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                break;
            case 4:
                g2.drawImage(image4, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                break;
            case 5:
                g2.drawImage(image5, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                break;
            case 6:
                if (Utility.Regulator.flipSwitch(1)) {
                    g2.drawImage(image5, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                }
                else {
                    g2.drawImage(image6, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                }

                break;
        }
    }
}