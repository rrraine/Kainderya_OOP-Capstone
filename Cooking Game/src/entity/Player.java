package entity;

import main.GamePanel;
import main.KeyBindings;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    // ~ FIELDS ---------------------------------------------------
    private final KeyBindings keyB;

    // PLAYER SCREEN COORDINATES ALWAYS IN CENTER
    private final int playerCenteredScreenX;
    private final int playerCenteredScreenY;

    // STAMINA SYSTEM
    private int stamina;
    private int maxStamina;

    // PLAYER PREFERENCES
    private final String playerAvatar;
    private String playerName;

    // ~ METHODS ---------------------------------------------------

    // CONSTRUCTOR ---------------------------------------------------
    public Player(GamePanel gp, KeyBindings keyB, String playerAvatar, String playerName) {

        super(gp, 4, "down");

        this.keyB = keyB;
        this.playerAvatar = playerAvatar;
        this.playerName = playerName;

        // PLAYER CENTERED ON SCREEN
        playerCenteredScreenX = gp.screenWidth / 2 - (gp.tileSize /2);
        playerCenteredScreenY = gp.screenHeight / 2 - (gp.tileSize /2);

        // COLLISION DIMENSIONS
        this.solidArea = new Rectangle(15, 20, 18, 26);

        // DEFAULT COLLISION
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        setDefaultPlayerValues();
        getAvatar();
    }


    // FROM CLASS: ENTITY ---------------------------------------------------
    @Override
    public void update() {

        // if moving
        if (keyB.isUpPressed() || keyB.isDownPressed() || keyB.isLeftPressed() || keyB.isRightPressed() ||
            keyB.isEnterPressed()) {

            // update movements
            if (keyB.isUpPressed()) { direction = "up"; }
            else if (keyB.isDownPressed()) { direction = "down"; }
            else if (keyB.isLeftPressed()) { direction = "left"; }
            else { direction = "right"; }

            // sprint
            if (sprint()) {
                speed = 8;
                stamina--;

                // when exhausted, takes 5 second cooldown to sprint again
                if (stamina <= 0) {
                    stamina = -6 * GamePanel.FPS;
                }
            }
            else {
                speed = 4;
                stamina++;

                if (stamina > maxStamina) stamina = maxStamina;
            }

            // speed penalty if exhausted
            if (stamina < 1) {
                speed = 1;
            }

            // check tile collision
            collisionOn = false;
            Utility.CollisionChecker.entityHitsTile(this, gp);
            Utility.CollisionChecker.entityHitsTile(this, gp);

            // check super-object collision
            int objIndex = Utility.CollisionChecker.entityHitsSuperObject(this, gp.getObj());
            interactSuperObject(objIndex);

            // check npc collision
            int npcIndex = Utility.CollisionChecker.entityHitsNPC(this, gp.getNpc());
            interactNPC(npcIndex);

            // check event collision
            Utility.CollisionChecker.entityHitsEvent(this, gp);

            // if no collision, player can move
            if (!collisionOn && !keyB.isEnterPressed()) {

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

            keyB.setEnterPressed(false);

            // alternate sprite poses every 12 frames
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
        // else idle
        else {
            stamina++;
            if (stamina > maxStamina) stamina = maxStamina;

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

            // sprite images
            case "up":
                if (spriteNum == 1) { image = up1; }
                if (spriteNum == 2) { image = up2; }
                if (spriteNum == 3) { image = idle2; }
                break;

            case "down":
                if (spriteNum == 1) { image = down1; }
                if (spriteNum == 2) { image = down2; }
                if (spriteNum == 3) { image = idle1; }
                break;

            case "left":
                if (spriteNum == 1 || spriteNum == 3) { image = left1; }
                if (spriteNum == 2) { image = left2; }
                break;

            case "right":
                if (spriteNum == 1 || spriteNum == 3) { image = right1; }
                if (spriteNum == 2) { image = right2; }
                break;
        }
        g2.drawImage(image, playerCenteredScreenX, playerCenteredScreenY, null);
    }
    @Override
    void getAvatar() {

        idle1 = setAvatar("player", playerAvatar, "idle");
        idle2 = setAvatar("player", playerAvatar, "idleUp");
        up1 = setAvatar("player", playerAvatar, "up1");
        up2 = setAvatar("player", playerAvatar, "up2");
        down1 = setAvatar("player", playerAvatar, "down1");
        down2 = setAvatar("player", playerAvatar, "down2");
        left1 = setAvatar("player", playerAvatar, "left1");
        left2 = setAvatar("player", playerAvatar, "left2");
        right1 = setAvatar("player", playerAvatar, "right1");
        right2 = setAvatar("player", playerAvatar, "right2");
    }

    // FROM THIS CLASS ---------------------------------------------------
    private void setDefaultPlayerValues() {

        // STARTING POSITION
        this.worldX = gp.tileSize * 23;
        this.worldY = gp.tileSize * 21;

        int seconds = 5;
        maxStamina = seconds * GamePanel.FPS;
        stamina = maxStamina;
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

    private boolean sprint() {

        return keyB.isShiftPressed() && stamina >= 0 && (keyB.isUpPressed() || keyB.isDownPressed() || keyB.isLeftPressed() || keyB.isRightPressed());
    }

    // GETTERS & SETTERS ---------------------------------------------------
    public int getPlayerCenteredScreenX() {
        return playerCenteredScreenX;
    }
    public int getPlayerCenteredScreenY() {
        return playerCenteredScreenY;
    }
    public int getStamina() {
        return stamina / GamePanel.FPS;
    }
    public String getPlayerName() {
        return playerName;
    }
}