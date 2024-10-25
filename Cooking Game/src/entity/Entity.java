package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    // ~ FIELDS
    GamePanel gp;

    // ENTITY COORDINATES IN WORLD
    public int worldX, worldY;
    public int speed;

    // SPRITE
    public BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int standCounter;
    public int spriteCounter;
    public int spriteNum;

    // COLLISION ASPECTS
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn;


    // ~ METHODS
    public Entity(GamePanel gp, int speed, String direction) {

        this.gp = gp;

        this.speed = speed;
        this.direction = direction;

        standCounter = spriteCounter = 0;
        spriteNum = 1;
        solidArea = new Rectangle(0, 0, 48, 48);
        collisionOn = false;
    }

    public abstract void update();

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        try {

            // DRAW ITEM AT SET LOCATION IN WORLD
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // IMPROVED RENDERING: Only draw tiles player can see in screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {


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

    public abstract void getAvatarImage();
    public BufferedImage setUpAvatar(String type, String avatar, String imageName) {

        BufferedImage image = null;

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + type + "/" + avatar + "/" + imageName + ".png")));
            image = Utility.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            System.err.println("Trouble finding avatar path: " + e.getMessage());
        }
        return image;
    }
}