package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager implements TileName {

    GamePanel gp;
    public Tile[] tile;

    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10]; // stores different types of tile
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // stores the map matrix

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

        try {

            tile[TileName.GRASS] = new Tile();
            tile[TileName.GRASS].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[TileName.WALL] = new Tile();
            tile[TileName.WALL].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[TileName.WALL].collision = true;

            tile[TileName.WATER] = new Tile();
            tile[TileName.WATER].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[TileName.WATER].collision = true;

            tile[TileName.EARTH] = new Tile();
            tile[TileName.EARTH].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[TileName.TREE] = new Tile();
            tile[TileName.TREE].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[TileName.TREE].collision = true;

            tile[TileName.SAND] = new Tile();
            tile[TileName.SAND].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MAP FUNCTION
    public void loadMap(String filePath) {

        try {

            // import and read the map matrix
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            // read one row line of the map matrix data
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
            br.close();

        }
        catch (Exception e) {


        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        // CAMERA FUNCTION
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            // World XY: POSITION OF TILE ON THE MAP
            // Screen XY: POSITION OF TILE ON THE SCREEN
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // IMPROVED RENDERING: Only draw tiles player can see in screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
