package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class NPC extends Entity {

    protected int actionInterval = 0;


    public NPC (GamePanel gp, int speed, String direction) {
        super(gp, speed, direction);
    }

    public abstract void setNPCAction();
    public void update() {

        setNPCAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkNPC2Player(this);

        // IF COLLISION IS FALSE, NPC CAN MOVE
        if (!collisionOn) {

            switch (direction) {

                case "up":
                    worldY -= speed;
                    break;

                case "down":
                    worldY += speed;
                    break;

                case "left":
                    worldX -= speed;
                    break;

                case "right":
                    worldX += speed;
                    break;

                case "idle":
                    standCounter++;

                    if (standCounter == 20) {
                        spriteNum = 3;
                        standCounter = 0;
                    }
                    break;
            }

            // ALTERNATE SPRITE MOVE POSES EVERY 12 FRAMES
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1 || spriteNum == 3) {
                    spriteNum = 2;
                } else if (spriteNum == 2 || spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public static class StudentFemale extends NPC {

        public StudentFemale (GamePanel gp) {
            super(gp, 1, "idle");
            getAvatarImage();
        }

        @Override
        public void getAvatarImage() {

            idle = setUpAvatar("npc","studentFemale", "idle");
            up1 = setUpAvatar("npc","studentFemale", "up1");
            up2 = setUpAvatar("npc","studentFemale", "up2");
            down1 = setUpAvatar("npc","studentFemale", "down1");
            down2 = setUpAvatar("npc","studentFemale", "down2");
            left1 = setUpAvatar("npc","studentFemale", "left1");
            left2 = setUpAvatar("npc","studentFemale", "left2");
            right1 = setUpAvatar("npc","studentFemale", "right1");
            right2 = setUpAvatar("npc","studentFemale", "right2");
        }

        @Override
        public void setNPCAction() {
            // NPC RANDOM BEHAVIOR

            actionInterval++;

            if (actionInterval == 120) {

                Random random = new Random();
                int i = random.nextInt(125) +1;

                if (i <= 25) {
                    direction = "up";
                }
                else if (i <= 50) {
                    direction = "down";
                }
                else if (i <= 75) {
                    direction = "left";
                }
                else if (i <= 100){
                    direction = "right";
                }
                else {
                    direction = "idle";
                }

                actionInterval = 0;
            }
        }


    }
}
