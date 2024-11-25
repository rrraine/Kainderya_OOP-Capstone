package main;

import entity.Entity;
import entity.NPC;
import interfaces.Drawable;
import interfaces.Importable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIAnimated implements Importable, Drawable {

    GamePanel gp;

    // ANIMATED WALLPAPER
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int animatedX;
    private int animatedY;
    private boolean movingLeft;

    BufferedImage left1, left2, right1, right2;

    public UIAnimated(GamePanel gp, String avatar, int x, int y, boolean movingLeft) {
        this.gp = gp;
        this.movingLeft = movingLeft;

        renderAvatar(avatar);
        initAnimation(x, y, gp.tileSize);
    }

    private void renderAvatar(String avatar) {
        left1 = importImage("avatar", "npc", avatar, "left1", gp.tileSize);
        left2 = importImage("avatar", "npc", avatar, "left2", gp.tileSize);
        right1 = importImage("avatar", "npc", avatar, "right1", gp.tileSize);
        right2 = importImage("avatar", "npc", avatar, "right2", gp.tileSize);
    }
    private void initAnimation(int x, int y, int tileSize) {
        animatedX = x + tileSize * 5;
        animatedY = y + tileSize * 5;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2) {
        int speed = 1;

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

        // DRAW
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

        // ALTERNATE SPRITE MOVE POSES EVERY 12 FRAMES
        spriteCounter++;
        if (spriteCounter > 24) {
            if (spriteNum == 1 || spriteNum == 3) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}