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
        loadMap("/maps/world01.txt");
    }
    // SINGLETON INSTANTIATE --------------------------------------
    public static TileManager instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new TileManager(gp);
        }
        return instance;
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
        tile[0].image = importImage("tiles", "old","floor", "grass", gp.tileSize);
        tile[0].collision = false;

        tile[1] = new Tile();
        tile[1].image = importImage("tiles", "old","floor", "wall", gp.tileSize);
        tile[1].collision = true;

        tile[2] = new Tile();
        tile[2].image = importImage("tiles", "old","floor", "water", gp.tileSize);
        tile[2].collision = true;

        tile[3] = new Tile();
        tile[3].image = importImage("tiles", "old","floor", "earth", gp.tileSize);
        tile[3].collision = false;

        tile[4] = new Tile();
        tile[4].image = importImage("tiles", "old","floor", "tree", gp.tileSize);
        tile[4].collision = true;

        tile[5] = new Tile();
        tile[5].image = importImage("tiles", "old","floor", "sand", gp.tileSize);
        tile[5].collision = false;

        // road tiles for outside environment
        // left muna
        tile[6] = new Tile();
        tile[6].image = importImage("tiles","roads", "floor", "LeftRoad", gp.tileSize);
        tile[6].collision = false;

        tile[7] = new Tile();
        tile[7].image = importImage("tiles","roads", "floor", "LeftSidewalk", gp.tileSize);
        tile[7].collision = false;

        tile[8] = new Tile();
        tile[8].image = importImage("tiles","roads", "floor", "LeftRUSidewalk", gp.tileSize);
        tile[8].collision = false;

        // road right side
        tile[9] = new Tile();
        tile[9].image = importImage("tiles","roads", "floor", "RightRoad", gp.tileSize);
        tile[9].collision = false;

        tile[10] = new Tile();
        tile[10].image = importImage("tiles","roads", "floor", "RightSidewalk", gp.tileSize);
        tile[10].collision = false;

        tile[11] = new Tile();
        tile[11].image = importImage("tiles","roads", "floor", "RightLUSidewalk", gp.tileSize);
        tile[11].collision = false;

        // below road

        tile[12] = new Tile();
        tile[12].image = importImage("tiles","roads", "floor", "BelowRoad", gp.tileSize);
        tile[12].collision = false;

        tile[13] = new Tile();
        tile[13].image = importImage("tiles","roads", "floor", "AboveRoad", gp.tileSize);
        tile[13].collision = false;

        tile[14] = new Tile();
        tile[14].image = importImage("tiles","roads", "floor", "BelowSidewalk", gp.tileSize);
        tile[14].collision = false;

        tile[15] = new Tile();
        tile[15].image = importImage("tiles","roads", "floor", "pedestrian", gp.tileSize);
        tile[15].collision = false;

        tile[16] = new Tile();
        tile[16].image = importImage("tiles","roads", "floor", "plainRoad", gp.tileSize);
        tile[16].collision = false;

        tile[17] = new Tile();
        tile[17].image = importImage("tiles","roads", "floor", "roadUpSidewalk", gp.tileSize);
        tile[17].collision = false;

        tile[18] = new Tile();
        tile[18].image = importImage("tiles","roads", "floor", "BelowSidewalk", gp.tileSize);
        tile[18].collision = false;

    // concrete tiles, daghan for textures

        tile[19] = new Tile();
        tile[19].image = importImage("tiles","concrete", "floor", "concrete1", gp.tileSize);
        tile[19].collision = false;

        tile[20] = new Tile();
        tile[20].image = importImage("tiles","concrete", "floor", "concrete2", gp.tileSize);
        tile[20].collision = false;

        tile[21] = new Tile();
        tile[21].image = importImage("tiles","concrete", "floor", "concrete3", gp.tileSize);
        tile[21].collision = false;

        tile[22] = new Tile();
        tile[22].image = importImage("tiles","concrete", "floor", "crackedConcrete", gp.tileSize);
        tile[22].collision = false;

        tile[23] = new Tile();
        tile[23].image = importImage("tiles","concrete", "floor", "slightCracked", gp.tileSize);
        tile[23].collision = false;

    // restaurant floor

        tile[24] = new Tile();
        tile[24].image = importImage("tiles","restaurant", "floor", "patternedTile", gp.tileSize);
        tile[24].collision = false;

        tile[25] = new Tile();
        tile[25].image = importImage("tiles","restaurant", "floor", "plainTile", gp.tileSize);
        tile[25].collision = false;

    // exterior walls

        tile[26] = new Tile();
        tile[26].image = importImage("tiles","exteriorWalls", "floor", "in01", gp.tileSize);
        tile[26].collision = true;

        tile[27] = new Tile();
        tile[27].image = importImage("tiles","exteriorWalls", "floor", "ex2", gp.tileSize);
        tile[27].collision = true;

        tile[28] = new Tile();
        tile[28].image = importImage("tiles","exteriorWalls", "floor", "ex3", gp.tileSize);
        tile[28].collision = true;

        tile[29] = new Tile();
        tile[29].image = importImage("tiles","exteriorWalls", "floor", "ex4", gp.tileSize);
        tile[29].collision = true;

        tile[30] = new Tile();
        tile[30].image = importImage("tiles","exteriorWalls", "floor", "ex5", gp.tileSize);
        tile[30].collision = true;

        tile[31] = new Tile();
        tile[31].image = importImage("tiles","exteriorWalls", "floor", "ex6", gp.tileSize);
        tile[31].collision = true;

        tile[32] = new Tile();
        tile[32].image = importImage("tiles","exteriorWalls", "floor", "ex7", gp.tileSize);
        tile[32].collision = true;

        tile[33] = new Tile();
        tile[33].image = importImage("tiles","exteriorWalls", "floor", "ex8", gp.tileSize);
        tile[33].collision = true;

        tile[34] = new Tile();
        tile[34].image = importImage("tiles","exteriorWalls", "floor", "ex9", gp.tileSize);
        tile[34].collision = true;

        tile[35] = new Tile();
        tile[35].image = importImage("tiles","exteriorWalls", "floor", "ex10", gp.tileSize);
        tile[35].collision = true;

        tile[36] = new Tile();
        tile[36].image = importImage("tiles","exteriorWalls", "floor", "ex11", gp.tileSize);
        tile[36].collision = true;

        tile[37] = new Tile();
        tile[37].image = importImage("tiles","exteriorWalls", "floor", "ex12", gp.tileSize);
        tile[37].collision = true;

        tile[38] = new Tile();
        tile[38].image = importImage("tiles","exteriorWalls", "floor", "kitchenSide", gp.tileSize);
        tile[38].collision = true;

        // sidewalks

        tile[39] = new Tile();
        tile[39].image = importImage("tiles","sidewalk", "floor", "sidewalkPlain", gp.tileSize);
        tile[39].collision = false;

        tile[40] = new Tile();
        tile[40].image = importImage("tiles","sidewalk", "floor", "sidewalkRight", gp.tileSize);
        tile[40].collision = false;

        tile[41] = new Tile();
        tile[41].image = importImage("tiles","sidewalk", "floor", "sidewalkLeft", gp.tileSize);
        tile[41].collision = false;

        tile[42] = new Tile();
        tile[42].image = importImage("tiles","sidewalk", "floor", "sidewalkAbove", gp.tileSize);
        tile[42].collision = false;

        tile[43] = new Tile();
        tile[43].image = importImage("tiles","sidewalk", "floor", "sidewalkBelow", gp.tileSize);
        tile[43].collision = false;

        // grass wow

        tile[44] = new Tile();
        tile[44].image = importImage("tiles","grass", "floor", "grass21", gp.tileSize);
        tile[44].collision = false;

        tile[45] = new Tile();
        tile[45].image = importImage("tiles","grass", "floor", "grass22", gp.tileSize);
        tile[45].collision = false;

        tile[46] = new Tile();
        tile[46].image = importImage("tiles","grass", "floor", "grass23", gp.tileSize);
        tile[46].collision = false;

        tile[47] = new Tile();
        tile[47].image = importImage("tiles","grass", "floor", "grass24", gp.tileSize);
        tile[47].collision = false;

        tile[48] = new Tile();
        tile[48].image = importImage("tiles","grass", "floor", "grass25", gp.tileSize);
        tile[48].collision = false;

        tile[49] = new Tile();
        tile[49].image = importImage("tiles","grass", "floor", "grass26", gp.tileSize);
        tile[49].collision = false;

        // with appliances



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