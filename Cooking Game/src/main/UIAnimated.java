package main;

import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIAnimated implements Importable {

    GamePanel gp;

    // ANIMATED WALLPAPER
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int animatedX;
    private int animatedY;
    private boolean movingLeft;

    private int frameInterval;

    private BufferedImage idle, left1, left2, right1, right2, down1, down2;

    public UIAnimated(GamePanel gp, String type, String image, int frameInterval, boolean movingLeft) {
        this.gp = gp;
        this.frameInterval = frameInterval;
        this.movingLeft = movingLeft;

        renderAvatar(type, image);
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

    public void reposition(int x, int y) {
        animatedX = x;
        animatedY = y;
    }
    public void drawSideViewMoving(Graphics2D g2) {
        int speed = 2;

        // MOVING DISPLACEMENT
        if (movingLeft) {
            animatedX -= speed;

            if (animatedX < -gp.tileSize * 2) {
                movingLeft = false;
            }
        } else {
            animatedX += speed;

            if (animatedX > gp.screenWidth) {
                movingLeft = true;
            }
        }

        // DRAW SPRITE
        if (spriteNum == 1) {
            g2.drawImage(
                    movingLeft ? left1 : right1,
                    animatedX, animatedY, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    movingLeft ? left2 : right2,
                    animatedX, animatedY, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
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
                    animatedX, animatedY, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
            );
        } else if (spriteNum == 2) {
            g2.drawImage(
                    down2,
                    animatedX, animatedY, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null
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

        g2.drawImage(idle, animatedX, animatedY, gp.tileSize * 2 - 20, gp.tileSize * 2 - 20, null);
    }
}