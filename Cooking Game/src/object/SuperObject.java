package object;

import interfaces.Drawable;
import interfaces.Observable;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject implements Drawable, Observable {

    // ~ FIELDS
    GamePanel gp;

    BufferedImage image;
    String name;
    boolean collision = false;

    // ABSOLUTE POS IN MAP
    int worldX;
    int worldY;

    // COLLISION DIMENSIONS
    Rectangle solidArea;

    // DEFAULT COLLISION
    int solidAreaDefaultX;
    int solidAreaDefaultY;

    // ~ METHODS

    // CONSTRUCTOR
    public SuperObject(GamePanel gp, String name) {

        this.gp = gp;
        this.name = name;

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    // FROM INTERFACE: DRAWABLE
    @Override
    public void update() {}
    @Override
    public void draw(Graphics2D g2) {

        // DRAW ITEM AT SET LOCATION IN WORLD
        int screenX = worldX - gp.player.getWorldX() + gp.player.getPlayerCenteredScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getPlayerCenteredScreenY();

        // IMPROVED RENDERING
        if (inView(gp.tileSize, gp.player, worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // FOR DEBUGGING PURPOSES
    @Override
    public String toString() {
        return "SuperObject{" +
                "name='" + name + '\'' +
                '}';
    }

    // GETTERS & SETTERS
    public boolean isCollision() {
        return collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }
}
