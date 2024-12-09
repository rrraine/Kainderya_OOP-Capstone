package main;

import java.awt.*;

public abstract class Asset implements Comparable<Asset> {

    // REMARKS: The point of this class is to store all entities and superobjects and foods in an Asset List (declared in GamePanel)
    //          to be sorted, and then rendered in order based on worldY, ultimately removing overlapping images

    public GamePanel gp;

    // ABSOLUTE POS IN MAP * TILESIZE
    public int worldX;
    public int worldY;

    // XY COORDINATED BASED ON RAW TEXT FILE MAP
    public int textMapX, textMapY;

    // COLLISION BOX
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    protected Asset(GamePanel gp) {

        this.gp = gp;
    }

    @Override
    public int compareTo(Asset other) {
        return Integer.compare(this.worldY, other.worldY);
    }

    public void resetParams() {}
}
