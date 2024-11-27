package tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    // ~ FIELDS --------------------------------------------------------------
    public BufferedImage image;

    // COLLISION ASPECTS
    Rectangle solidArea;
    int solidAreaDefaultX;
    int solidAreaDefaultY;
    boolean collision;

    public Tile(int tileSize) {

        collision = false;
        solidArea = new Rectangle(0, 0, tileSize, tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    void setManualCollisions(int width, int height) {
        solidArea.width = width;
        solidArea.height = height;
    }

    public boolean isCollision() {
        return collision;
    }
}