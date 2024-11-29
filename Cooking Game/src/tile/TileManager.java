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
        tile = new Tile[35]; // stores different types of tile
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
            try {

                if (inView(gp.tileSize, gp.player, worldX, worldY)) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }

            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
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

        // adding using the addTIle function
        /*
           addTile(0, "tiles", "construction", "floor", "tileFloor", false);

           addTile(0, path, true)
        */

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
        addTile(20, "/tiles/outsideArea/grassSideWalk", true);
        addTile(21, "/tiles/outsideArea/roadUpper", false);
        addTile(22, "/tiles/outsideArea/roadLower", false);
        addTile(23, "/tiles/outsideArea/roadLeft", false);
        addTile(24, "/tiles/outsideArea/roadRight", false);
        addTile(25, "/tiles/outsideArea/plainRoad", false);
        addTile(26, "/tiles/outsideArea/grass", false);
        addTile(27, "/tiles/outsideArea/fenceSideWalk", true);
        addTile(28, "/tiles/outsideArea/fenceGrass", true);
        addTile(29, "/tiles/outsideArea/door", true);


        /* addTile(18, "/tiles/construction/leftRiceCooker", true);
        addTile(19, "/tiles/construction/leftStove", true);
        addTile(20, "/tiles/construction/leftStraightTable", true);

        addTile(21, "/tiles/construction/leftCornerTable", true);
        addTile(22, "/tiles/construction/door", false);
        addTile(23, "/tiles/kitchenArea/leftChoppingBoard", true);
        addTile(24, "/tiles/kitchenArea/middleKitchenIsland", true);
        addTile(25, "/tiles/kitchenArea/rightKitchenIsland", true);
        addTile(26, "/tiles/kitchenArea/leftCounter", true);
        addTile(27, "/tiles/kitchenArea/centerSink", true);
        addTile(28, "/tiles/kitchenArea/rightCounter", true);
        addTile(29, "/tiles/kitchenArea/middleCounter", true);
        addTile(30, "/tiles/kitchenArea/lowerRef", true);

        addTile(31, "/tiles/kitchenArea/riceSack", true);
        addTile(32, "/tiles/kitchenArea/trashcan1", true);
        addTile(33, "/tiles/diningArea/middleTable", true);
        addTile(34, "/tiles/diningArea/rightTable", true);
        addTile(35, "/tiles/diningArea/leftTable", true);
        addTile(36, "/tiles/diningArea/stool", true);
        addTile(37, "/tiles/oldfiles/outsideArea/sidewalk1", false);
        addTile(38, "/tiles/oldfiles/outsideArea/sidewalk2", false);
        addTile(39, "/tiles/oldfiles/outsideArea/roadRightUpper", false);
        addTile(40, "/tiles/oldfiles/outsideArea/roadRightLower", false);

        addTile(41, "/tiles/oldfiles/outsideArea/roadLeft", false);
        addTile(42, "/tiles/oldfiles/outsideArea/roadRight", false);
        addTile(43, "/tiles/oldfiles/outsideArea/grass", false);
        addTile(44, "/tiles/oldfiles/outsideArea/plainRoad", false);
*/
        /*
        tile[0] = new Tile(gp.tileSize);
        tile[0].image = importImage("/tiles/construction/tileFloor", gp.tileSize);
        tile[0].collision = false;

         */
    /*
        tile[1] = new Tile(gp.tileSize);
        tile[1].image = importImage("tiles", "construction", "floor", "upperCornerLeft", gp.tileSize);
        tile[1].collision = true;

        tile[2] = new Tile(gp.tileSize);
        tile[2].image = importImage("tiles", "construction", "floor", "upperCornerRight", gp.tileSize);
        tile[2].collision = true;

        tile[3] = new Tile(gp.tileSize);
        tile[3].image = importImage("tiles", "construction", "floor", "lowerCornerLeft", gp.tileSize);
        tile[3].collision = true;

        tile[4] = new Tile(gp.tileSize);
        tile[4].image = importImage("tiles", "construction", "floor", "lowerCornerRight", gp.tileSize);
        tile[4].collision = true;

        tile[5] = new Tile(gp.tileSize);
        tile[5].image = importImage("tiles", "construction", "floor", "upperStraight", gp.tileSize);
        tile[5].collision = true;

        tile[6] = new Tile(gp.tileSize);
        tile[6].image = importImage("tiles", "construction", "floor", "middleOuterWall", gp.tileSize);
        tile[6].collision = true;

        tile[7] = new Tile(gp.tileSize);
        tile[7].image = importImage("tiles", "construction", "floor", "rightCornerWall", gp.tileSize);
        tile[7].collision = true;

        tile[8] = new Tile(gp.tileSize);
        tile[8].image = importImage("tiles", "construction", "floor", "rightWall", gp.tileSize);
        tile[8].collision = true;

        tile[9] = new Tile(gp.tileSize);
        tile[9].image = importImage("tiles", "construction", "floor", "rightShelf1", gp.tileSize);
        tile[9].collision = true;

        tile[10] = new Tile(gp.tileSize);
        tile[10].image = importImage("tiles", "construction", "floor", "rightShelf2", gp.tileSize);
        tile[10].collision = true;

        tile[11] = new Tile(gp.tileSize);
        tile[11].image = importImage("tiles", "construction", "floor", "rightShelf3", gp.tileSize);
        tile[11].collision = true;

        tile[12] = new Tile(gp.tileSize);
        tile[12].image = importImage("tiles", "construction", "floor", "upperWindow", gp.tileSize);
        tile[12].collision = true;

        tile[13] = new Tile(gp.tileSize);
        tile[13].image = importImage("tiles", "construction", "floor", "upperSpam", gp.tileSize);
        tile[13].collision = true;

        tile[14] = new Tile(gp.tileSize);
        tile[14].image = importImage("tiles", "construction", "floor", "upperCornedBeef", gp.tileSize);
        tile[14].collision = true;

        tile[15] = new Tile(gp.tileSize);
        tile[15].image = importImage("tiles", "construction", "floor", "upperSandoks", gp.tileSize);
        tile[15].collision = true;

        tile[16] = new Tile(gp.tileSize);
        tile[16].image = importImage("tiles", "construction", "floor", "upperRef", gp.tileSize);
        tile[16].collision = true;

        tile[17] = new Tile(gp.tileSize);
        tile[17].image = importImage("tiles", "construction", "floor", "leftWall", gp.tileSize);
        tile[17].collision = true;

        tile[18] = new Tile(gp.tileSize);
        tile[18].image = importImage("tiles", "construction", "floor", "leftRiceCooker", gp.tileSize);
        tile[18].collision = true;

        tile[19] = new Tile(gp.tileSize);
        tile[19].image = importImage("tiles", "construction", "floor", "leftStove", gp.tileSize);
        tile[19].collision = true;

        tile[20] = new Tile(gp.tileSize);
        tile[20].image = importImage("tiles", "construction", "floor", "leftStraightTable", gp.tileSize);
        tile[20].collision = true;

        tile[21] = new Tile(gp.tileSize);
        tile[21].image = importImage("tiles", "construction", "floor", "leftCornerTable", gp.tileSize);
        tile[21].collision = true;

        tile[22] = new Tile(gp.tileSize);
        tile[22].image = importImage("tiles", "construction", "floor", "door", gp.tileSize);
        tile[22].collision = false;

    // kitchen area

        tile[23] = new Tile(gp.tileSize);
        tile[23].image = importImage("tiles", "kitchenArea", "floor", "leftChoppingBoard", gp.tileSize);
        tile[23].collision = true;

        tile[24] = new Tile(gp.tileSize);
        tile[24].image = importImage("tiles", "kitchenArea", "floor", "middleKitchenIsland", gp.tileSize);
        tile[24].collision = true;

        tile[25] = new Tile(gp.tileSize);
        tile[25].image = importImage("tiles", "kitchenArea", "floor", "rightKitchenIsland", gp.tileSize);
        tile[25].collision = true;

        tile[26] = new Tile(gp.tileSize);
        tile[26].image = importImage("tiles", "kitchenArea", "floor", "leftCounter", gp.tileSize);
        tile[26].collision = true;

        tile[27] = new Tile(gp.tileSize);
        tile[27].image = importImage("tiles", "kitchenArea", "floor", "centerSink", gp.tileSize);
        tile[27].collision = true;

        tile[28] = new Tile(gp.tileSize);
        tile[28].image = importImage("tiles", "kitchenArea", "floor", "rightCounter", gp.tileSize);
        tile[28].collision = true;

        tile[29] = new Tile(gp.tileSize);
        tile[29].image = importImage("tiles", "kitchenArea", "floor", "middleCounter", gp.tileSize);
        tile[29].collision = true;

        tile[30] = new Tile(gp.tileSize);
        tile[30].image = importImage("tiles", "kitchenArea", "floor", "lowerRef", gp.tileSize);
        tile[30].collision = true;

        tile[31] = new Tile(gp.tileSize);
        tile[31].image = importImage("tiles", "kitchenArea", "floor", "riceSack", gp.tileSize);
        tile[31].collision = true;

        tile[32] = new Tile(gp.tileSize);
        tile[32].image = importImage("tiles", "kitchenArea", "floor", "trashcan1", gp.tileSize);
        tile[32].collision = true;

    // dining area

        tile[33] = new Tile(gp.tileSize);
        tile[33].image = importImage("tiles", "diningArea", "floor", "middleTable", gp.tileSize);
        tile[33].collision = true;

        tile[34] = new Tile(gp.tileSize);
        tile[34].image = importImage("tiles", "diningArea", "floor", "rightTable", gp.tileSize);
        tile[34].collision = true;

        tile[35] = new Tile(gp.tileSize);
        tile[35].image = importImage("tiles", "diningArea", "floor", "leftTable", gp.tileSize);
        tile[35].collision = true;

        tile[36] = new Tile(gp.tileSize);
        tile[36].image = importImage("tiles", "diningArea", "floor", "stool", gp.tileSize);
        tile[36].collision = true;

    // outside area

        tile[37] = new Tile(gp.tileSize);
        tile[37].image = importImage("tiles", "outsideArea", "floor", "sidewalk1", gp.tileSize);
        tile[37].collision = false;

        tile[38] = new Tile(gp.tileSize);
        tile[38].image = importImage("tiles", "outsideArea", "floor", "sidewalk2", gp.tileSize);
        tile[38].collision = false;

        tile[39] = new Tile(gp.tileSize);
        tile[39].image = importImage("tiles", "outsideArea", "floor", "roadRightUpper", gp.tileSize);
        tile[39].collision = false;

        tile[40] = new Tile(gp.tileSize);
        tile[40].image = importImage("tiles", "outsideArea", "floor", "roadRightLower", gp.tileSize);
        tile[40].collision = false;

        tile[41] = new Tile(gp.tileSize);
        tile[41].image = importImage("tiles", "outsideArea", "floor", "roadLeft", gp.tileSize);
        tile[41].collision = false;

        tile[42] = new Tile(gp.tileSize);
        tile[42].image = importImage("tiles", "outsideArea", "floor", "roadRight", gp.tileSize);
        tile[42].collision = false;

        tile[43] = new Tile(gp.tileSize);
        tile[43].image = importImage("tiles", "outsideArea", "floor", "grass", gp.tileSize);
        tile[43].collision = false;

        tile[44] = new Tile(gp.tileSize);
        tile[44].image = importImage("tiles", "outsideArea", "floor", "plainRoad", gp.tileSize);
        tile[44].collision = false;

         end of comment
     */


    /*
        tile[0] = new Tile(gp.tileSize);
        tile[0].image = importImage("tiles", "old","floor", "grass", gp.tileSize);
        tile[0].collision = false;

        tile[1] = new Tile(gp.tileSize);
        tile[1].image = importImage("tiles", "old","floor", "wall", gp.tileSize);
        tile[1].collision = true;

        tile[2] = new Tile(gp.tileSize);
        tile[2].image = importImage("tiles", "old","floor", "water", gp.tileSize);
        tile[2].collision = true;

        tile[3] = new Tile(gp.tileSize);
        tile[3].image = importImage("tiles", "old","floor", "earth", gp.tileSize);
        tile[3].collision = false;

        tile[4] = new Tile(gp.tileSize);
        tile[4].image = importImage("tiles", "old","floor", "tree", gp.tileSize);
        tile[4].collision = true;

        tile[5] = new Tile(gp.tileSize);
        tile[5].image = importImage("tiles", "old","floor", "sand", gp.tileSize);
        tile[5].collision = false;

        // road tiles for outside environment
        // left muna
        tile[6] = new Tile(gp.tileSize);
        tile[6].image = importImage("tiles","roads", "floor", "LeftRoad", gp.tileSize);
        tile[6].collision = false;

        tile[7] = new Tile(gp.tileSize);
        tile[7].image = importImage("tiles","roads", "floor", "LeftSidewalk", gp.tileSize);
        tile[7].collision = false;

        tile[8] = new Tile(gp.tileSize);
        tile[8].image = importImage("tiles","roads", "floor", "LeftRUSidewalk", gp.tileSize);
        tile[8].collision = false;

        // road right side
        tile[9] = new Tile(gp.tileSize);
        tile[9].image = importImage("tiles","roads", "floor", "RightRoad", gp.tileSize);
        tile[9].collision = false;

        tile[10] = new Tile(gp.tileSize);
        tile[10].image = importImage("tiles","roads", "floor", "RightSidewalk", gp.tileSize);
        tile[10].collision = false;

        tile[11] = new Tile(gp.tileSize);
        tile[11].image = importImage("tiles","roads", "floor", "RightLUSidewalk", gp.tileSize);
        tile[11].collision = false;

        // below road

        tile[12] = new Tile(gp.tileSize);
        tile[12].image = importImage("tiles","roads", "floor", "BelowRoad", gp.tileSize);
        tile[12].collision = false;

        tile[13] = new Tile(gp.tileSize);
        tile[13].image = importImage("tiles","roads", "floor", "AboveRoad", gp.tileSize);
        tile[13].collision = false;

        tile[14] = new Tile(gp.tileSize);
        tile[14].image = importImage("tiles","roads", "floor", "BelowSidewalk", gp.tileSize);
        tile[14].collision = false;

        tile[15] = new Tile(gp.tileSize);
        tile[15].image = importImage("tiles","roads", "floor", "pedestrian", gp.tileSize);
        tile[15].collision = false;

        tile[16] = new Tile(gp.tileSize);
        tile[16].image = importImage("tiles","roads", "floor", "plainRoad", gp.tileSize);
        tile[16].collision = false;

        tile[17] = new Tile(gp.tileSize);
        tile[17].image = importImage("tiles","roads", "floor", "roadUpSidewalk", gp.tileSize);
        tile[17].collision = false;

        tile[18] = new Tile(gp.tileSize);
        tile[18].image = importImage("tiles","roads", "floor", "BelowSidewalk", gp.tileSize);
        tile[18].collision = false;

    // concrete tiles, daghan for textures

        tile[19] = new Tile(gp.tileSize);
        tile[19].image = importImage("tiles","concrete", "floor", "concrete1", gp.tileSize);
        tile[19].collision = false;

        tile[20] = new Tile(gp.tileSize);
        tile[20].image = importImage("tiles","concrete", "floor", "concrete2", gp.tileSize);
        tile[20].collision = false;

        tile[21] = new Tile(gp.tileSize);
        tile[21].image = importImage("tiles","concrete", "floor", "concrete3", gp.tileSize);
        tile[21].collision = false;

        tile[22] = new Tile(gp.tileSize);
        tile[22].image = importImage("tiles","concrete", "floor", "crackedConcrete", gp.tileSize);
        tile[22].collision = false;

        tile[23] = new Tile(gp.tileSize);
        tile[23].image = importImage("tiles","concrete", "floor", "slightCracked", gp.tileSize);
        tile[23].collision = false;

    // restaurant floor

        tile[24] = new Tile(gp.tileSize);
        tile[24].image = importImage("tiles","restaurant", "floor", "patternedTile", gp.tileSize);
        tile[24].collision = false;

        tile[25] = new Tile(gp.tileSize);
        tile[25].image = importImage("tiles","restaurant", "floor", "plainTile", gp.tileSize);
        tile[25].collision = false;

    // exterior walls

        tile[26] = new Tile(gp.tileSize);
        tile[26].image = importImage("tiles","exteriorWalls", "floor", "in01", gp.tileSize);
        tile[26].collision = true;

        tile[27] = new Tile(gp.tileSize);
        tile[27].image = importImage("tiles","exteriorWalls", "floor", "ex2", gp.tileSize);
        tile[27].collision = true;

        tile[28] = new Tile(gp.tileSize);
        tile[28].image = importImage("tiles","exteriorWalls", "floor", "ex3", gp.tileSize);
        tile[28].collision = true;

        tile[29] = new Tile(gp.tileSize);
        tile[29].image = importImage("tiles","exteriorWalls", "floor", "ex4", gp.tileSize);
        tile[29].collision = true;

        tile[30] = new Tile(gp.tileSize);
        tile[30].image = importImage("tiles","exteriorWalls", "floor", "ex5", gp.tileSize);
        tile[30].collision = true;

        tile[31] = new Tile(gp.tileSize);
        tile[31].image = importImage("tiles","exteriorWalls", "floor", "ex6", gp.tileSize);
        tile[31].collision = true;

        tile[32] = new Tile(gp.tileSize);
        tile[32].image = importImage("tiles","exteriorWalls", "floor", "ex7", gp.tileSize);
        tile[32].collision = true;

        tile[33] = new Tile(gp.tileSize);
        tile[33].image = importImage("tiles","exteriorWalls", "floor", "ex8", gp.tileSize);
        tile[33].collision = true;

        tile[34] = new Tile(gp.tileSize);
        tile[34].image = importImage("tiles","exteriorWalls", "floor", "ex9", gp.tileSize);
        tile[34].collision = true;

        tile[35] = new Tile(gp.tileSize);
        tile[35].image = importImage("tiles","exteriorWalls", "floor", "ex10", gp.tileSize);
        tile[35].collision = true;

        tile[36] = new Tile(gp.tileSize);
        tile[36].image = importImage("tiles","exteriorWalls", "floor", "ex11", gp.tileSize);
        tile[36].collision = true;

        tile[37] = new Tile(gp.tileSize);
        tile[37].image = importImage("tiles","exteriorWalls", "floor", "ex12", gp.tileSize);
        tile[37].collision = true;

        tile[38] = new Tile(gp.tileSize);
        tile[38].image = importImage("tiles","exteriorWalls", "floor", "kitchenSide", gp.tileSize);
        tile[38].collision = true;

        // sidewalks

        tile[39] = new Tile(gp.tileSize);
        tile[39].image = importImage("tiles","sidewalk", "floor", "sidewalkPlain", gp.tileSize);
        tile[39].collision = false;

        tile[40] = new Tile(gp.tileSize);
        tile[40].image = importImage("tiles","sidewalk", "floor", "sidewalkRight", gp.tileSize);
        tile[40].collision = false;

        tile[41] = new Tile(gp.tileSize);
        tile[41].image = importImage("tiles","sidewalk", "floor", "sidewalkLeft", gp.tileSize);
        tile[41].collision = false;

        tile[42] = new Tile(gp.tileSize);
        tile[42].image = importImage("tiles","sidewalk", "floor", "sidewalkAbove", gp.tileSize);
        tile[42].collision = false;

        tile[43] = new Tile(gp.tileSize);
        tile[43].image = importImage("tiles","sidewalk", "floor", "sidewalkBelow", gp.tileSize);
        tile[43].collision = false;

        // grass wow

        tile[44] = new Tile(gp.tileSize);
        tile[44].image = importImage("tiles","grass", "floor", "grass21", gp.tileSize);
        tile[44].collision = false;

        tile[45] = new Tile(gp.tileSize);
        tile[45].image = importImage("tiles","grass", "floor", "grass22", gp.tileSize);
        tile[45].collision = false;

        tile[46] = new Tile(gp.tileSize);
        tile[46].image = importImage("tiles","grass", "floor", "grass23", gp.tileSize);
        tile[46].collision = false;

        tile[47] = new Tile(gp.tileSize);
        tile[47].image = importImage("tiles","grass", "floor", "grass24", gp.tileSize);
        tile[47].collision = false;

        tile[48] = new Tile(gp.tileSize);
        tile[48].image = importImage("tiles","grass", "floor", "grass25", gp.tileSize);
        tile[48].collision = false;

        tile[49] = new Tile(gp.tileSize);
        tile[49].image = importImage("tiles","grass", "floor", "grass26", gp.tileSize);
        tile[49].collision = false;

        // with appliances
        */

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

                    System.out.println("Row " + row + ", Col " + col + ": " + numbers[col]);

                    if (numbers.length != gp.maxWorldCol) { // Validate column count
                        throw new IllegalArgumentException("Invalid number of columns in row " + row + ". Expected: "
                                + gp.maxWorldCol + ", Found: " + numbers.length);
                    }

                    // parse String to int
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;

                }

                if (col <= gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        }
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Trouble reading and loading map into game: " + e.getMessage());
        }
    }

}