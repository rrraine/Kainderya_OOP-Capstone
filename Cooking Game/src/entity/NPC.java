package entity;

import main.GamePanel;
import main.Utility;

import java.util.Random;

public abstract class NPC extends Entity {

    // ~ FIELDS ---------------------------------------------------
    protected int actionInterval = 0;

    // ~ METHODS -------------------------------------------------

    // CONSTRUCTOR ----------------------------------------------
    public NPC (GamePanel gp, int speed, String direction) {
        super(gp, speed, direction);
    }

    // FROM CLASS: ENTITY ------------------------------------------
    @Override
    public void update() {

        setNPCAction();
        collisionOn = false;
        Utility.CollisionChecker.entityHitsTile(this, gp);
        int objIndex = Utility.CollisionChecker.entityHitsSuperObject(this, gp.getObj());
        Utility.CollisionChecker.NPCHitsPlayer(this, gp.player);

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
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    // FROM THIS CLASS ----------------------------------------------
    public abstract void setNPCAction();

    // INNER STATIC CLASSES ----------------------------------------

    public static class StudentFemale extends NPC {

        public StudentFemale (GamePanel gp) {
            super(gp, 1, "idle");
            getAvatar();
        }

        // FROM CLASS: ENTITY ----------------------------------------
        @Override
        void getAvatar() {

            idle = setAvatar("npc","studentFemale", "idle");
            up1 = setAvatar("npc","studentFemale", "up1");
            up2 = setAvatar("npc","studentFemale", "up2");
            down1 = setAvatar("npc","studentFemale", "down1");
            down2 = setAvatar("npc","studentFemale", "down2");
            left1 = setAvatar("npc","studentFemale", "left1");
            left2 = setAvatar("npc","studentFemale", "left2");
            right1 = setAvatar("npc","studentFemale", "right1");
            right2 = setAvatar("npc","studentFemale", "right2");
        }

        // FROM CLASS: NPC ----------------------------------------
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
