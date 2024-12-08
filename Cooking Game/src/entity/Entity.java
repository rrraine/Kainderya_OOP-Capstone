package entity;

import interfaces.Drawable;
import interfaces.Importable;
import interfaces.Observable;
import main.Asset;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends Asset implements Drawable, Observable, Importable {

    // ~ FIELDS ---------------------------------------------------
    int speed;

    // SPRITE
    protected BufferedImage idle1, idle2, up1, up2, down1, down2, left1, left2, right1, right2, sitUp, sitSide;
    protected enum lastRecordedDirection { UP, DOWN, LEFT, RIGHT };
    protected lastRecordedDirection lastDirection;
    protected String direction;
    int standCounter;
    int spriteCounter;
    int spriteNum;

    private BufferedImage image;

    // XY Coordinates
    protected int screenX, screenY;

    // COLLISION ASPECTS
    boolean collisionOn;


    // ~ METHODS ---------------------------------------------------

    // CONSTRUCTOR ---------------------------------------------------
    public Entity(GamePanel gp, int speed, String direction) {

        super(gp);

        this.speed = speed;
        this.direction = direction;

        standCounter = spriteCounter = 0;
        spriteNum = 1;
        solidArea = new Rectangle(0, 0, 64, 64);
        collisionOn = false;
    }

    // FROM INTERFACE: DRAWABLE ---------------------------------------------------
    @Override
    public abstract void update();
    @Override
    public void draw(Graphics2D g2) {

        image = null;

        try {

            // DRAW ITEM AT SET LOCATION IN WORLD
            screenX = worldX - gp.player.worldX + gp.player.getPlayerCenteredScreenX();
            screenY = worldY - gp.player.worldY + gp.player.getPlayerCenteredScreenY();

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
                        if (lastDirection == lastRecordedDirection.UP) {
                            image = idle2;
                        }
                        else {
                            image = idle1;
                        }
                        break;

                    case "sitUp":
                        image = sitUp;
                        break;

                    case "sitSide":
                        image = sitSide;
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

    // FROM THIS CLASS ------------------------------------------------------

    abstract void getAvatar();
    BufferedImage setAvatar(String structure, String avatar, String image) {

        //return importImage("avatar", structure, avatar, image, gp.tileSize);
        return importImage("/avatar/" + structure + "/" + avatar +  "/" + image, gp.tileSize);
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

    public BufferedImage getIdle1() {
        return idle1;
    }
    public BufferedImage getLeft1() {
        return left1;
    }
    public BufferedImage getLeft2() {
        return left2;
    }
    public BufferedImage getRight1() {
        return right1;
    }
    public BufferedImage getRight2() {
        return right2;
    }
}