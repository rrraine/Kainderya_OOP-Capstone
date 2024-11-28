package main;

import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIAnimated implements Importable {

    GamePanel gp;

    // ANIMATED WALLPAPER
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int x;
    private int y;
    private boolean movingLeft;

    private int frameInterval;

    private BufferedImage idle, left1, left2, right1, right2, down1, down2;

    public UIAnimated(GamePanel gp, String type, String image, int frameInterval, boolean movingLeft) {
        this.gp = gp;
        this.frameInterval = frameInterval;
        this.movingLeft = movingLeft;

        switch (type) {
            case "npc":
            case "player":
                renderAvatar(type, image);
                break;
            case "ui":
                renderIcon(image);
                break;
        }
    }

    private void renderAvatar(String type, String image) {
        idle = importImage("/avatar/" + type + "/" + image + "/idle", gp.tileSize);
        left1 = importImage("/avatar/" + type + "/" + image + "/left1", gp.tileSize);
        left2 = importImage("/avatar/" + type + "/" + image + "/left2", gp.tileSize);
        right1 = importImage("/avatar/" + type + "/" + image + "/right1", gp.tileSize);
        right2 = importImage("/avatar/" + type + "/" + image + "/right2", gp.tileSize);
        down1 = importImage("/avatar/" + type + "/" + image + "/down1", gp.tileSize);
        down2 = importImage("/avatar/" + type + "/" + image + "/down2", gp.tileSize);

        /*
        idle = importImage("avatar", type, image, "idle", gp.tileSize);
        left1 = importImage("avatar", type, image, "left1", gp.tileSize);
        left2 = importImage("avatar", type, image, "left2", gp.tileSize);
        right1 = importImage("avatar", type, image, "right1", gp.tileSize);
        right2 = importImage("avatar", type, image, "right2", gp.tileSize);
        down1 = importImage("avatar", type, image, "down1", gp.tileSize);
        down2 = importImage("avatar", type, image, "down2", gp.tileSize);

         */
    }
    private void renderIcon(String image) {
        idle = importImage("/ui/staminaBar/staminaBar_full", gp.tileSize);
        left1 = importImage("/ui/staminaBar/staminaBar_3", gp.tileSize);
        left2 = importImage("/ui/staminaBar/staminaBar_2", gp.tileSize);
        right1 = importImage("/ui/staminaBar/staminaBar_1", gp.tileSize);
        right2 = importImage("/ui/staminaBar/staminaBar_emptyBlack", gp.tileSize);
        down1 = importImage("/ui/staminaBar/staminaBar_emptyRed", gp.tileSize);
    }


    public void reposition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void drawSideViewMoving(Graphics2D g2) {
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
                    movingLeft ? left1 : right1,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    movingLeft ? left2 : right2,
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
    public void drawFrontViewMoving(Graphics2D g2) {

        // DRAW SPRITE
        if (spriteNum == 1) {
            g2.drawImage(
                    down1,
                    x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    down2,
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
    public void drawFrontViewStatic(Graphics2D g2) {

        g2.drawImage(idle, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
    }
    public void drawStamina(Graphics2D g2, int phase) {

        switch (phase) {
            case 1:
                g2.drawImage(idle, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                break;
            case 2:
                g2.drawImage(left1, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                break;
            case 3:
                g2.drawImage(left2, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                break;
            case 4:
                g2.drawImage(right1, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                break;
            case 5:
                g2.drawImage(right2, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                break;
            case 6:
                if (Utility.Regulator.flipSwitch(1)) {
                    g2.drawImage(right2, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                }
                else {
                    g2.drawImage(down1, x, y, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
                }

                break;
        }
    }
}