package entity;

import interfaces.Drawable;
import interfaces.Observable;
import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity implements Drawable, Observable {

    // ~ FIELDS ---------------------------------------------------
    GamePanel gp;

    // ABSOLUTE POS IN MAP
    int worldX;
    int worldY;
    int speed;

    // SPRITE
    BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;
    String direction;
    int standCounter;
    int spriteCounter;
    int spriteNum;

    // COLLISION ASPECTS
    Rectangle solidArea;
    int solidAreaDefaultX;
    int solidAreaDefaultY;
    boolean collisionOn;


    // ~ METHODS ---------------------------------------------------

    // CONSTRUCTOR ---------------------------------------------------
    public Entity(GamePanel gp, int speed, String direction) {

        this.gp = gp;

        this.speed = speed;
        this.direction = direction;

        standCounter = spriteCounter = 0;
        spriteNum = 1;
        solidArea = new Rectangle(0, 0, 48, 48);
        collisionOn = false;


    }

    // FROM INTERFACE: DRAWABLE ---------------------------------------------------
    @Override
    public abstract void update();
    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        try {

            // DRAW ITEM AT SET LOCATION IN WORLD
            int screenX = worldX - gp.player.worldX + gp.player.getPlayerCenteredScreenX();
            int screenY = worldY - gp.player.worldY + gp.player.getPlayerCenteredScreenY();

            // IMPROVED RENDERING
            if (inView(gp.tileSize, gp.player, worldX, worldY)) {

                switch (direction) {

                    // MOVEMENT
                    case "up":
                        if (spriteNum == 1) {
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
                        break;

                    case "left":
                        if (spriteNum == 1) {
                            image = left1;
                        }
                        if (spriteNum == 2) {
                            image = left2;
                        }
                        break;

                    case "right":
                        if (spriteNum == 1) {
                            image = right1;
                        }
                        if (spriteNum == 2) {
                            image = right2;
                        }
                        break;

                    case "idle":
                        image = idle;
                        break;
                }

                try {
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                } catch (Exception e) {
                    System.err.println("Cannot draw NPC");
                }

            }

    } catch (Exception e) {
        System.out.println("NPC placement causing camera issues: " + e.getMessage());
    }
}

    // FROM THIS CLASS ---------------------------------------------------
    abstract void getAvatarImage();
    BufferedImage setUpAvatar(String type, String avatar, String imageName) {

        BufferedImage image = null;

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + type + "/" + avatar + "/" + imageName + ".png")));
            image = Utility.Scaler.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            System.err.println("Trouble finding avatar path: " + e.getMessage());
        }
        return image;
    }

    // GETTERS & SETTERS ---------------------------------------------------
    public int getSpeed() {
        return speed;
    }
    public String getDirection() {
        return direction;
    }
    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public Rectangle getSolidArea() {
        return solidArea;
    }
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }
    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public BufferedImage getIdle() {
        return idle;
    }
}