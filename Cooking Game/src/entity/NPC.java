package entity;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.util.Random;

public abstract class NPC extends Entity {

    // ~ FIELDS ---------------------------------------------------
    protected int actionInterval = 0;

    // ~ METHODS -------------------------------------------------

    // CONSTRUCTOR ----------------------------------------------
    public NPC (GamePanel gp, int speed, String direction) {
        super(gp, speed, direction);
        lastDirection = lastRecordedDirection.DOWN;

        // COLLISION DIMENSIONS
        solidArea = new Rectangle(18, 38, 28, 23);

        // DEFAULT COLLISION
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
    }

    // FROM CLASS: ENTITY ------------------------------------------
    @Override
    public void update() {

        setNPCAction();
        collisionOn = false;
        Utility.CollisionChecker.entityHitsTile(this, gp);
        int objIndex = Utility.CollisionChecker.entityHitsSuperObject(this, gp.getAssetPool());
        Utility.CollisionChecker.entityHitsNPC(this, gp.getNpc());
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

            idle1 = setAvatar("npc","studentFemale", "idle");
            idle2 = setAvatar("npc","studentFemale", "idleUp");
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
                    lastDirection = lastRecordedDirection.UP;
                }
                else if (i <= 50) {
                    direction = "down";
                    lastDirection = lastRecordedDirection.DOWN;
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

    // TODO actionInterval for other inner classes
    // todo fix keybindings and actioninterval
    public static class StudentMale extends NPC {

        public StudentMale (GamePanel gp) {
            super(gp, 1, "idle");
            getAvatar();
        }

        // FROM CLASS: ENTITY ----------------------------------------
        @Override
        void getAvatar() {

            idle1 = setAvatar("npc","studentMale", "idle");
            idle2 = setAvatar("npc","studentMale", "idleUp");
            up1 = setAvatar("npc","studentMale", "up1");
            up2 = setAvatar("npc","studentMale", "up2");
            down1 = setAvatar("npc","studentMale", "down1");
            down2 = setAvatar("npc","studentMale", "down2");
            left1 = setAvatar("npc","studentMale", "left1");
            left2 = setAvatar("npc","studentMale", "left2");
            right1 = setAvatar("npc","studentMale", "right1");
            right2 = setAvatar("npc","studentMale", "right2");
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
                    lastDirection = lastRecordedDirection.UP;
                }
                else if (i <= 50) {
                    direction = "down";
                    lastDirection = lastRecordedDirection.DOWN;
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

    public static class Tambay1 extends NPC {

        public Tambay1 (GamePanel gp) {
            super(gp, 1, "idle");
            getAvatar();
        }

        // FROM CLASS: ENTITY ----------------------------------------
        @Override
        void getAvatar() {

            idle1 = setAvatar("npc","tambay1", "idle");
            idle2 = setAvatar("npc","tambay1", "idleUp");
            up1 = setAvatar("npc","tambay1", "up1");
            up2 = setAvatar("npc","tambay1", "up2");
            down1 = setAvatar("npc","tambay1", "down1");
            down2 = setAvatar("npc","tambay1", "down2");
            left1 = setAvatar("npc","tambay1", "left1");
            left2 = setAvatar("npc","tambay1", "left2");
            right1 = setAvatar("npc","tambay1", "right1");
            right2 = setAvatar("npc","tambay1", "right2");
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
                    lastDirection = lastRecordedDirection.UP;
                }
                else if (i <= 50) {
                    direction = "down";
                    lastDirection = lastRecordedDirection.DOWN;
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

    public static class civilianFemale1 extends NPC {

        public civilianFemale1 (GamePanel gp) {
            super(gp, 1, "idle");
            getAvatar();
        }

        // FROM CLASS: ENTITY ----------------------------------------
        @Override
        void getAvatar() {

            idle1 = setAvatar("npc","civilianfem1", "idle");
            idle2 = setAvatar("npc","civilianfem1", "idleUp");
            up1 = setAvatar("npc","civilianfem1", "up1");
            up2 = setAvatar("npc","civilianfem1", "up2");
            down1 = setAvatar("npc","civilianfem1", "down1");
            down2 = setAvatar("npc","civilianfem1", "down2");
            left1 = setAvatar("npc","civilianfem1", "left1");
            left2 = setAvatar("npc","civilianfem1", "left2");
            right1 = setAvatar("npc","civilianfem1", "right1");
            right2 = setAvatar("npc","civilianfem1", "right2");
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
                    lastDirection = lastRecordedDirection.UP;
                }
                else if (i <= 50) {
                    direction = "down";
                    lastDirection = lastRecordedDirection.DOWN;
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

    //@Override
    public int getDefaultX() {
        Random rand = new Random();
        return rand.nextInt(24);  // Generate a random X position within a valid range
    }

    // @Override
    public int getDefaultY() {
        Random rand = new Random();
        return rand.nextInt(14);  // Generate a random Y position within a valid range
    }
}
