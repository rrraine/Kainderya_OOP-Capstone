package tile;

import interfaces.Drawable;
import interfaces.Importable;
import interfaces.Observable;
import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager implements Importable, Drawable, Observable {

    // ~ FIELDS -------------------------------------------------
    private static TileManager instance;

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    // ~ METHODS -------------------------------------------------


    // CONSTRUCTOR -------------------------------------------------
    private TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[50]; // stores different types of tile
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // stores the map matrix

        tileSetup();
        loadMap("/maps/newMap.txt");
        // loadMap("/maps/finalMap.txt"); // uncomment to use the old map
    }
    // SINGLETON INSTANTIATE --------------------------------------
    public static TileManager instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new TileManager(gp);
        }
        return instance;
    }

    //this is the new implementation of importImage
        private void addTile(int index, String path, boolean collision){
            tile[index] = new Tile(gp.tileSize);
            tile[index].image = importImage(path, gp.tileSize);
            tile[index].collision = collision;
        }



    // FROM INTERFACE: DRAWABLE ---------------------------------------
    @Override
    public void update() {} // USELESS AS TILES ARE STATIONARY
    @Override
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        // CAMERA FUNCTION
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            // World XY: POSITION OF TILE ON THE MAP
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Screen XY: POSITION OF TILE ON THE SCREEN
            int screenX = worldX - gp.player.getWorldX() + gp.player.getPlayerCenteredScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getPlayerCenteredScreenY();

            // IMPROVED RENDERING
            if (inView(gp.tileSize, gp.player, worldX, worldY)) {
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // FROM THIS CLASS -------------------------------------------------

    // TILE INSTANTIATION, IMPORTING, AND COLLISION SETTING
    private void tileSetup() {
        
        // construction

        addTile(0, "/tiles/construction/tileFloor", false);
        addTile(1, "/tiles/construction/upperCornerLeft", true);
        addTile(2, "/tiles/construction/upperCornerRight", true);
        addTile(3, "/tiles/construction/lowerCornerLeft", true);
        addTile(4, "/tiles/construction/lowerCornerRight", true);
        addTile(5, "/tiles/construction/upperStraight", true);
        addTile(6, "/tiles/construction/middleOuterWall", true);
        addTile(7, "/tiles/construction/rightCornerWall", true);
        addTile(8, "/tiles/construction/rightWall", true);
        addTile(9, "/tiles/construction/rightShelf1", true);
        addTile(10, "/tiles/construction/rightShelf2", true);

        addTile(11, "/tiles/construction/rightShelf3", true);
        addTile(12, "/tiles/construction/upperWindow", true);
        addTile(13, "/tiles/construction/upperSpam", true);
        addTile(14, "/tiles/construction/upperCornedBeef", true);
        addTile(15, "/tiles/construction/upperSandoks", true);
        addTile(16, "/tiles/construction/upperRef", true);
        addTile(17, "/tiles/construction/leftWall", true);
        addTile(18, "/tiles/outsideArea/sidewalk1", false);
        addTile(19, "/tiles/outsideArea/sidewalk2", false);
        addTile(20, "/tiles/outsideArea/grassSidewalk", true);
        addTile(21, "/tiles/outsideArea/roadUpper", false);
        addTile(22, "/tiles/outsideArea/roadLower", false);
        addTile(23, "/tiles/outsideArea/roadLeft", false);
        addTile(24, "/tiles/outsideArea/roadRight", false);
        addTile(25, "/tiles/outsideArea/plainRoad", false);
        addTile(26, "/tiles/outsideArea/grass", false);
        addTile(27, "/tiles/outsideArea/fenceSideWalk", true);
        addTile(28, "/tiles/outsideArea/fenceGrass", true);
        addTile(29, "/tiles/outsideArea/door", true);


    }

    // IMPORT MAP AND READ ITS MATRIX
    private void loadMap(String filePath) {

        try (InputStream is = getClass().getResourceAsStream(filePath);
              BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {

            int col = 0;
            int row = 0;

            // READ 1 ROW LINE OF MATRIX
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    // split the line of data matrix into solo digits
                    String[] numbers = line.split(" ");

                    // parse String to int
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Trouble reading and loading map into game: " + e.getMessage());
        }
    }

}