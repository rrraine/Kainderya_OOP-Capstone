package entity;

import main.GamePanel;
import main.KeyBindings;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    // ~ FIELDS ---------------------------------------------------
    private final KeyBindings keyH;

    // PLAYER SCREEN COORDINATES ALWAYS IN CENTER
    private final int playerCenteredScreenX;
    private final int playerCenteredScreenY;

    // ~ METHODS ---------------------------------------------------

    // CONSTRUCTOR ---------------------------------------------------
    public Player(GamePanel gp, KeyBindings keyH) {

        super(gp, 5, "down");

        this.keyH = keyH;

        // PLAYER CENTERED ON SCREEN
        playerCenteredScreenX = gp.screenWidth / 2 - (gp.tileSize /2);
        playerCenteredScreenY = gp.screenHeight / 2 - (gp.tileSize /2);

        // COLLISION DIMENSIONS
        this.solidArea = new Rectangle(15, 20, 18, 26);

        // DEFAULT COLLISION
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        setDefaultPlayerValues();
        getAvatarImage();
    }


    // FROM CLASS: ENTITY ---------------------------------------------------
    @Override
    public void update() {

        // DETECT DIRECTION BY KEYSTROKE TO UPDATE MOVING POSES
        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
                direction = "up";
            }
            else if (keyH.isDownPressed()) {
                direction = "down";
            }
            else if (keyH.isLeftPressed()) {
                direction = "left";
            }
            else {
                direction = "right";
            }


            // CHECK TILE COLLISION
            collisionOn = false;
            Utility.CollisionChecker.entityHitsTile(this, gp);
            Utility.CollisionChecker.entityHitsTile(this, gp);

            // CHECK OBJECT COLLISION
            int objIndex = Utility.CollisionChecker.entityHitsSuperObject(this, gp.getObj());
            interactSuperObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = Utility.CollisionChecker.entityHitsNPC(this, gp.getNpc());
            interactNPC(npcIndex);

            // CHECK EVENT COLLISION
            Utility.CollisionChecker.entityHitsEvent(this, gp);

            keyH.setEnterPressed(false);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
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
                }
            }


            // ALTERNATE SPRITE MOVE POSES EVERY 12 FRAMES
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1 || spriteNum == 3) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        // IDLE POSE
        else {

            standCounter++;

            if (standCounter == 20) {
                spriteNum = 3;
                standCounter = 0;
            }
        }
    }
    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {

            // MOVEMENT
            case "up":
                if (spriteNum == 1 || spriteNum == 3) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = idle;
                }
                break;

            case "left":
                if (spriteNum == 1 || spriteNum == 3) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNum == 1 || spriteNum == 3) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, playerCenteredScreenX, playerCenteredScreenY, null);
    }
    @Override
    void getAvatarImage() {

        idle = setUpAvatar("player","cook1", "idle");
        up1 = setUpAvatar("player","cook1", "up1");
        up2 = setUpAvatar("player","cook1", "up2");
        down1 = setUpAvatar("player","cook1", "down1");
        down2 = setUpAvatar("player","cook1", "down2");
        left1 = setUpAvatar("player","cook1", "left1");
        left2 = setUpAvatar("player","cook1", "left2");
        right1 = setUpAvatar("player","cook1", "right1");
        right2 = setUpAvatar("player","cook1", "right2");
    }

    // FROM THIS CLASS ---------------------------------------------------
    private void setDefaultPlayerValues() {

        // STARTING POSITION
        this.worldX = gp.tileSize * 23;
        this.worldY = gp.tileSize * 21;
    }
    private void interactSuperObject(int i) {

        if (i != 999) {
            // TODO
        }
    }
    private void interactNPC(int i) {

        if (i != 999) {
            // TODO SOON
        }
    }

    // GETTERS & SETTERS ---------------------------------------------------
    public int getPlayerCenteredScreenX() {
        return playerCenteredScreenX;
    }
    public int getPlayerCenteredScreenY() {
        return playerCenteredScreenY;
    }

}