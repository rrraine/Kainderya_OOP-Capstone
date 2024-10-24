package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    // ~ FIELDS

    // ENTITY COORDINATES IN WORLD
    public int worldX, worldY;
    public int speed;

    // SPRITE
    public BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int standCounter = 0;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // COLLISION ASPECTS
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}