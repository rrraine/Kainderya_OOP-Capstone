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
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    // ~ METHODS -------------------------------------------------


    // CONSTRUCTOR -------------------------------------------------
    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10]; // stores different types of tile
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // stores the map matrix

        tileSetup();
        loadMap("/maps/world01.txt");
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

        tile[0] = new Tile();
        tile[0].image = importImage("tiles", "grass", gp.tileSize);
        tile[0].collision = false;

        tile[1] = new Tile();
        tile[1].image = importImage("tiles", "wall", gp.tileSize);
        tile[1].collision = true;

        tile[2] = new Tile();
        tile[2].image = importImage("tiles", "water", gp.tileSize);
        tile[2].collision = true;

        tile[3] = new Tile();
        tile[3].image = importImage("tiles", "earth", gp.tileSize);
        tile[3].collision = false;

        tile[4] = new Tile();
        tile[4].image = importImage("tiles", "tree", gp.tileSize);
        tile[4].collision = true;

        tile[5] = new Tile();
        tile[5].image = importImage("tiles", "sand", gp.tileSize);
        tile[5].collision = false;

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