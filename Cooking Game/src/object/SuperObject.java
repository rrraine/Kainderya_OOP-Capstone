package object;

import interfaces.Drawable;
import interfaces.Interactable;
import interfaces.Observable;
import main.Asset;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject extends Asset implements Drawable, Observable, Interactable {

    // ~ FIELDS --------------------------------------------------------------
    protected BufferedImage image;
    String name;
    boolean collision;

    protected int screenX, screenY;


    // CONSTRUCTOR --------------------------------------------------------------
    public SuperObject(GamePanel gp, String name) {

        super(gp);
        this.name = name;

        // DEFAULT OBJECT COLLISION
        collision = false;
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    // MANUALLY SET COLLISION
    protected void setDefaultCollisions(boolean collision, int solidAreaDefaultX, int solidAreaDefaultY, int width, int height) {

        this.collision = collision;
        // COLLISION DIMENSIONS
        solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, width, height);

        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
    }


    // FROM INTERFACE: DRAWABLE --------------------------------------------------------------
    @Override
    public void update() {}
    @Override
    public void draw(Graphics2D g2) {

        // DRAW ITEM AT SET LOCATION IN WORLD
        screenX = worldX - gp.player.getWorldX() + gp.player.getPlayerCenteredScreenX();
        screenY = worldY - gp.player.getWorldY() + gp.player.getPlayerCenteredScreenY();

        // IMPROVED RENDERING
        if (inView(gp.tileSize, gp.player, worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }


    // FOR DEBUGGING PURPOSES --------------------------------------------------------------
    @Override
    public String toString() {
        return "SuperObject{" +
                "name='" + name + '\'' +
                '}';
    }


    // GETTERS & SETTERS --------------------------------------------------------------
    public boolean isCollision() { return collision; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; }
    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; }
    public Rectangle getSolidArea() { return solidArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }

    public String getName() {
        return name;
    }
}
