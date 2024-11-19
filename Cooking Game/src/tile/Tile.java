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
    public boolean collision = false;

    void setTileCollision(int x, int y, int tileSize) {

    }

}