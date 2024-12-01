package entity;

import animation.AnimationFactory;
import animation.AnimationState;
import interfaces.Pickupable;
import main.GamePanel;
import main.KeyBindings;
import main.Utility;
import object.Item;
import object.RefillStation;
import object.SuperObject;
import object.WorkStation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Ref;
import java.util.List;

public class Player extends Entity {

    // TODO HANDLE FOOD INTERACTION HERE

    // ~ FIELDS ---------------------------------------------------

    AnimationFactory animF;

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
    public int staminaMeter() {

        if (stamina / GamePanel.FPS >= 7) {
            return 1;
        }
        else if (stamina / GamePanel.FPS >= 5) {
            return 2;
        }
        else if (stamina / GamePanel.FPS >= 3) {
            return 3;
        }
        else if (stamina / GamePanel.FPS >= 1) {
            return 4;
        }
        else if (stamina / GamePanel.FPS == 0) {
            return 5;
        }

        return 6;
    }

    // CONSTRUCTOR ---------------------------------------------------
    public Player(GamePanel gp, KeyBindings keyB, String playerAvatar, String playerName) {

        super(gp, 3, "down");

        this.keyB = keyB;
        this.playerAvatar = playerAvatar;
        this.playerName = playerName;

        // ANIMATION FACTORY
        animF = AnimationFactory.instantiate(gp, playerAvatar.toLowerCase());
        animF.switchState(AnimationState.BASE);

        // PLAYER CENTERED ON SCREEN
        playerCenteredScreenX = gp.screenWidth / 2 - (gp.tileSize /2);
        playerCenteredScreenY = gp.screenHeight / 2 - (gp.tileSize /2);

        // COLLISION DIMENSIONS
        this.solidArea = new Rectangle(18, 38, 28, 23);

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
        if (keyB.isPlayer1UpPressed() || keyB.isPlayer1DownPressed() || keyB.isLeftPressed() || keyB.isPlayer1RightPressed() ||
            keyB.isPlayer1EnterPressed()) {

            // update movements
            if (keyB.isPlayer1UpPressed()) { direction = "up"; }
            else if (keyB.isPlayer1DownPressed()) { direction = "down"; }
            else if (keyB.isLeftPressed()) { direction = "left"; }
            else { direction = "right"; }

            // sprint
            if (sprint()) {
                speed = 6;
                stamina--;

                // when exhausted, takes 5 second cooldown to sprint again
                if (stamina <= 0) {
                    stamina = -6 * GamePanel.FPS;
                }
            }
            else {
                speed = 3;
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
            int objIndex = Utility.CollisionChecker.entityHitsSuperObject(this, gp.getAssetPool());
            interactSuperObject(objIndex);

            if (objIndex != 999) {
                System.out.println("YO ARE COLLIDING AGAINST: " + gp.getAssetPool().get(objIndex));

                if (gp.getAssetPool().get(objIndex) instanceof Item) {
                    System.out.println("ITEM");
                }
                else if (gp.getAssetPool().get(objIndex) instanceof WorkStation) {
                    System.out.println("WorkStation");
                }
                if (gp.getAssetPool().get(objIndex) instanceof RefillStation) {
                    System.out.println("RefillStation");
                }
            }


            // check npc collision
            int npcIndex = Utility.CollisionChecker.entityHitsNPC(this, gp.getNpc());
            interactNPC(npcIndex);

            // check event collision
            Utility.CollisionChecker.entityHitsEvent(this, gp);

            // if no collision, player can move
            if (!collisionOn && !keyB.isPlayer1EnterPressed()) {

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

            keyB.setPlayer1EnterPressed(false);

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

        // UPDATE SPRITE IMAGES
        updateSprite();
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

    void updateSprite() {

        List<BufferedImage> list = animF.getSpriteArray();

        idle1 = list.get(0);
        idle2 = list.get(1);
        up1 = list.get(2);
        up2 = list.get(3);
        down1 = list.get(4);
        down2 = list.get(5);
        left1 = list.get(6);
        left2 = list.get(7);
        right1 = list.get(8);
        right2 = list.get(9);
    }

    // FROM THIS CLASS ---------------------------------------------------
    private void setDefaultPlayerValues() {

        // STARTING POSITION
        this.worldX = gp.tileSize * 5; // 23
        this.worldY = gp.tileSize * 7; // 21

        int seconds = 8;
        maxStamina = seconds * GamePanel.FPS;
        stamina = maxStamina;
    }
    private void interactSuperObject(int i) {

        if (i != 999) {
            // TODO
            // USE .INTERACT HERE
            // CHECK WHAT INSTANCE
            // THEN DEFINE THE INTERACT METHOD IN THE ABSTRACT CLASS

            SuperObject obj = (SuperObject) gp.getAssetPool().get(i);

            // IF INTERACT EXECUTED
            if (keyB.isPlayer1EnterPressed() && obj != null ) {


                if (obj instanceof Pickupable) {

                    if (((Pickupable) obj).isPickingUp(animF.getCurrentState())) {
                        obj.interact(this, animF);
                        gp.getAssetPool().remove(i);
                    }
                }
                else {
                    obj.interact(this, animF);
                }
            }

        }

    }
    private void interactNPC(int i) {

        if (i != 999) {
            // TODO SOON
        }
    }

    private boolean sprint() {

        return keyB.isPlayer1ShiftPressed() && stamina >= 0 && (keyB.isPlayer1UpPressed() || keyB.isPlayer1DownPressed() || keyB.isLeftPressed() || keyB.isPlayer1RightPressed());
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