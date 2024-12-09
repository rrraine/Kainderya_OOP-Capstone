package main;

import entity.*;
import object.Item;
import object.RefillStation;
import object.SuperObject;
import object.WorkStation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    // IMAGE SCALING -------------------------------------------------
    public static class Scaler {

        public static BufferedImage scaleImage(BufferedImage original, int width, int height) {

            BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();

            return scaledImage;
        }
    }

    // -----------------------------------------------------------

    // DEPLOY ASSETS IN MAP THRU COORDINATES
    public static class AssetSetter {


        private static void addItem(int index, List<SuperObject> obj, SuperObject item, int tileSize, int x, int y){
            obj.add(index, item);
            obj.get(index).setWorldX(tileSize * x);
            obj.get(index).setWorldY(tileSize * y);

            obj.get(index).textMapX = x;
            obj.get(index).textMapY = y;
        }

        public static void deploySuperObjectInMap(GamePanel gp, int tileSize, List<SuperObject> obj) {

            try {
                addItem(0, obj, new Item.Stool(gp), 0, 10, 11);
                addItem(1, obj, new Item.Stool(gp), 0, 11, 11);
                addItem(2, obj, new Item.Stool(gp), 0, 12, 11);
                addItem(3, obj, new Item.Stool(gp), 0, 13, 11);
                addItem(4, obj, new Item.Stool(gp), 0, 14, 11);
                addItem(5, obj, new Item.Door(gp), tileSize, 7, 11);
                addItem(6, obj, new WorkStation.Tables.leftTable(gp), tileSize, 10, 10);
                addItem(7, obj, new WorkStation.Tables.middleTable(gp), tileSize, 11, 10);
                addItem(8, obj, new WorkStation.Tables.middleTable(gp), tileSize, 12, 10);
                addItem(9, obj, new WorkStation.Tables.middleTable(gp), tileSize, 13, 10);
                addItem(10, obj, new WorkStation.Tables.rightTable(gp), tileSize, 14, 10);

                addItem(11, obj, new WorkStation.Tables.outsideLowerTable(gp), tileSize, 5, 9);
                addItem(12, obj, new WorkStation.Tables.outsideUpperTable(gp), tileSize, 5, 8);
                addItem(13, obj, new WorkStation.Tables.outsideLowerTable(gp), tileSize, 5, 6);
                addItem(14, obj, new WorkStation.Tables.outsideUpperTable(gp), tileSize, 5, 5);

                addItem(15, obj, new Item.Stool1(gp), 0, 4, 9);
                addItem(16, obj, new Item.Stool1(gp), 0, 4, 8);
                addItem(17, obj, new Item.Stool1(gp), 0, 4, 6);
                addItem(18, obj, new Item.Stool1(gp), 0, 4, 5);

                addItem(19, obj, new WorkStation.ChoppingBoard(gp), tileSize, 9, 7);
                addItem(20, obj, new WorkStation.KitchenIsland.middleKitchenIsland(gp), tileSize, 10, 7);
                addItem(21, obj, new WorkStation.KitchenIsland.middleKitchenIsland(gp), tileSize, 11, 7);
                addItem(22, obj, new WorkStation.KitchenIsland.middleKitchenIsland(gp), tileSize, 12, 7);
                addItem(23, obj, new WorkStation.KitchenIsland.rightKitchenIsland(gp), tileSize, 13, 7);

                addItem(24, obj, new WorkStation.KitchenIsland.leftKitchenIsland(gp), tileSize, 9, 4);
                addItem(25, obj, new WorkStation.Sink(gp), tileSize, 10, 4);
                addItem(26, obj, new WorkStation.KitchenIsland.middleKitchenIsland(gp), tileSize, 11, 4);
                addItem(27, obj, new WorkStation.KitchenIsland.middleKitchenIsland(gp), tileSize, 12, 4);
                addItem(28, obj, new WorkStation.KitchenIsland.rightKitchenIsland(gp), tileSize, 13, 4);
                addItem(29, obj, new WorkStation.lowerRef(gp), tileSize, 8, 4);
                addItem(30, obj, new RefillStation.riceSack(gp), tileSize, 14, 4);


                //left counter
                addItem(31, obj, new WorkStation.Counter.leftCornerTable(gp), tileSize, 6, 9);
                addItem(32, obj, new WorkStation.RiceCooker(gp), tileSize, 6, 4);
                addItem(33, obj, new WorkStation.Counter.leftStraightTable(gp), tileSize, 6, 6);
                //addItem(34, obj, new WorkStation.Counter.leftStove(gp), tileSize, 6, 7);
                addItem(34, obj, new WorkStation.Stove(gp), tileSize, 6, 7);
                addItem(35, obj, new WorkStation.Counter.leftStraightTable(gp), tileSize, 6, 5);


                addItem(36, obj, new RefillStation.stationaryEgg(gp), tileSize, 15, 8);
                addItem(37, obj, new RefillStation.stationaryOnion(gp), tileSize, 15, 9);

                addItem(38, obj, new RefillStation.WaterDispenser(gp), tileSize, 15, 4);

                addItem(39, obj, new Item.rightWall(gp), tileSize, 15, 5);
                addItem(40, obj, new Item.rightWall(gp), tileSize, 15, 6);
                addItem(41, obj, new Item.rightWall(gp), tileSize, 15, 7);

                addItem(42, obj, new RefillStation.stationarySpam(gp), tileSize, 11, 4);
                addItem(43, obj, new RefillStation.stationaryCornedBeef(gp), tileSize, 13, 4);
                addItem(44, obj, new RefillStation.stationaryTapa(gp), tileSize, 8, 4);

                addItem(45, obj, new RefillStation.stationaryTapa(gp), tileSize, 8, 4);
                addItem(46, obj, new RefillStation.stationaryTapa(gp), tileSize, 8, 4);

                addItem(47, obj, new Item.Pan(gp), tileSize, 6, 6);
                addItem(48, obj, new Item.Pan(gp), tileSize, 6, 9);

                addItem(49, obj, new Item.bush(gp), tileSize, 16, 3);
                addItem(50, obj, new Item.bush(gp), tileSize, 16, 4);
                addItem(51, obj, new Item.bush(gp), tileSize, 16, 5);
                addItem(52, obj, new Item.bush(gp), tileSize, 16, 6);
                addItem(53, obj, new Item.bush(gp), tileSize, 16, 7);
                addItem(54, obj, new Item.bush(gp), tileSize, 16, 8);
                addItem(55, obj, new Item.bush(gp), tileSize, 16, 9);
                addItem(56, obj, new Item.bush(gp), tileSize, 16, 10);

                addItem(57, obj, new Item.rightShelf1(gp), tileSize, 15, 5);
                addItem(58, obj, new Item.rightShelf2(gp), tileSize, 15, 6);

                addItem(59, obj, new Item.Plates(gp), tileSize, 6, 5);
                addItem(60, obj, new Item.Plates(gp), tileSize, 9, 4);
                addItem(61, obj, new Item.Plates(gp), tileSize, 10, 7);
                addItem(62, obj, new Item.Plates(gp), tileSize, 11, 7);
                addItem(63, obj, new Item.Plates(gp), tileSize, 12, 7);
                addItem(64, obj, new Item.Plates(gp), tileSize, 13, 7);
                addItem(65, obj, new Item.Plates(gp), tileSize, 12, 4);

                addItem(66, obj, new WorkStation.Stove(gp), tileSize, 6, 8);
                addItem(67, obj, new WorkStation.TrashCan(gp), tileSize, 6, 10);
                addItem(68, obj, new RefillStation.VendingMachine(gp), tileSize, 2,4);

            } catch (NullPointerException e) {
                System.err.println("Accessing null element in (List<SuperObject> obj): " + e.getMessage());
            }
        }
        public static void deployNPCInMap(GamePanel gp, int tileSize, List<NPC> npc, ShopManager shopManager) {

            try {

            /*
                // Add StudentFemale NPC
                npc.add(new NPC.StudentFemale(gp));
                npc.get(npc.size() - 1).setWorldX(tileSize * 12);
                npc.get(npc.size() - 1).setWorldY(tileSize * 13);

                // Add StudentMale NPC
                npc.add(new NPC.StudentMale(gp));
                npc.get(npc.size() - 1).setWorldX(tileSize * 13);
                npc.get(npc.size() - 1).setWorldY(tileSize * 13);

                // Add Teacher NPC
                npc.add(new NPC.Tambay1(gp));
                npc.get(npc.size() - 1).setWorldX(tileSize * 14);
                npc.get(npc.size() - 1).setWorldY(tileSize * 13);

                // Add 1st Civilian (Female) NPC

                npc.add(new NPC.civilianFemale1(gp));

                npc.get(npc.size() - 1).setWorldX(tileSize * 15);
                npc.get(npc.size() - 1).setWorldY(tileSize * 13);
*/

                List<NPC> shopManagerNPCs = new ArrayList<>(gp.getNpc());  // Get the combined list of customers and free-roaming NPCs
                System.out.println("Deploying NPCs. Count: " + shopManagerNPCs.size());

                // Loop through each NPC from the ShopManager
                for (NPC shopNPC : shopManagerNPCs) {

                    // generate spawnPoint outside the restaurant
                    Point spawnPoint;

                    do {
                        spawnPoint = getRandomSpawnPointOutsideRestaurant(tileSize);
                    }while (isWithinRestrictedArea(spawnPoint));

                    // set the coordinate of their spawn point
                    shopNPC.setWorldX(spawnPoint.x * tileSize);
                    shopNPC.setWorldY(spawnPoint.y * tileSize);

                    // Determine positions for the NPCs
                    if (shopNPC instanceof NPC_Customer customer) {
                        // Assign world positions for seated customers based on their seat
                        Point seat = customer.getSeatLocation();
                        if (seat != null) {
                            ((NPC_Customer) shopNPC).assignSeat(seat);
                            ((NPC_Customer) shopNPC).setMovingToSeat(true);
                            ((NPC_Customer) shopNPC).moveToSeat();
                        }
                    }
                    // remove the else-if for freeroamers
                    /*
                    else if (shopNPC instanceof NPC_FreeRoaming) {
                        // For free-roaming NPCs or others, assign default/random positions
                        int worldX = shopNPC.getDefaultX();  // Placeholder for default X
                        int worldY = shopNPC.getDefaultY();  // Placeholder for default Y

                        shopNPC.setWorldX(worldX * tileSize);  // Scale by tile size
                        shopNPC.setWorldY(worldY * tileSize);  // Scale by tile size
                    }
                    */

                    // Log deployment for debugging
                    System.out.println("Deployed " + shopNPC.getClass().getSimpleName() +
                            " at (" + shopNPC.getWorldX() / tileSize +
                            ", " + shopNPC.getWorldY() / tileSize + ")");
                }
                shopManager.update();

            }
            catch (NullPointerException e) {
                System.err.println("Accessing null element in (List<NPC> npc): " + e.getMessage());
            }
        }

        private static Point getRandomSpawnPointOutsideRestaurant(int tileSize) {
            Random random = new Random();
            int x, y;

            // Example map boundaries: adjust based on your map size
            int mapWidth = 24;  // Example map width
            int mapHeight = 14; // Example map height

            do {
                x = random.nextInt(mapWidth);
                y = random.nextInt(mapHeight);
            } while (isWithinRestrictedArea(new Point(x, y)));

            return new Point(x, y);
        }

        private static boolean isWithinRestrictedArea(Point point) {
            int x = point.x;
            int y = point.y;

            // Restricted area for the restaurant
            return (x >= 4 && x <= 16) && (y >= 3 && y <= 11);
        }

    }


    // -----------------------------------------------------------

    // DETECT & ENFORCE COLLISION BETWEEN 2 COLLIDING ASSETS
    public static class CollisionChecker {

        public static void entityHitsTile(Entity en, GamePanel gp) {

            // ENTITY'S COLLISION BOX COORDINATES RELATIVE TO WORLD LOCATION
            int entityLeftWorldX = en.getWorldX() + en.getSolidArea().x;
            int entityRightWorldX = en.getWorldX() + en.getSolidArea().x + en.getSolidArea().width;
            int entityTopWorldY = en.getWorldY() + en.getSolidArea().y;
            int entityBottomWorldY = en.getWorldY() + en.getSolidArea().y + en.getSolidArea().height;

            // ENTITY'S COLLISION SIDES FORM A BOX
            int entityLeftCol = entityLeftWorldX / gp.tileSize;
            int entityRightCol = entityRightWorldX / gp.tileSize;
            int entityTopRow = entityTopWorldY / gp.tileSize;
            int entityBottomRow = entityBottomWorldY / gp.tileSize;

            int tileNum1, tileNum2;

            try {

                // PREDICT COLLISION BY EVERY FACING SIDES OF BOX
                switch (en.getDirection()) {

                    case "up":
                        entityTopRow = (entityTopWorldY - en.getSpeed()) / gp.tileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                            en.setCollisionOn(true);
                        }
                        break;

                    case "down":
                        entityBottomRow = (entityBottomWorldY + en.getSpeed()) / gp.tileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                            en.setCollisionOn(true);
                        }
                        break;

                    case "left":
                        entityLeftCol = (entityLeftWorldX - en.getSpeed()) / gp.tileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                            en.setCollisionOn(true);
                        }
                        break;

                    case "right":
                        entityRightCol = (entityRightWorldX + en.getSpeed()) / gp.tileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                        if (gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()) {
                            en.setCollisionOn(true);
                        }
                        break;
                }

            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Attempting to cross beyond the map matrix: [" + en.getClass() + "]");
            }

        }
        public static int entityHitsSuperObject(Entity en, List<Asset> obj) {

            int index = 999;

            for (int i = 0; i < obj.size(); i++) {

                if (obj.get(i) != null && obj.get(i) instanceof SuperObject) {

                    SuperObject sObj = (SuperObject) obj.get(i);

                    // GET ENTITY'S SOLID AREA POS
                    en.getSolidArea().x = en.getWorldX() + en.getSolidArea().x;
                    en.getSolidArea().y = en.getWorldY() + en.getSolidArea().y;

                    // GET SUPEROBJECT'S SOLID AREA POS
                    sObj.getSolidArea().x = sObj.getWorldX() + sObj.getSolidArea().x;
                    sObj.getSolidArea().y = sObj.getWorldY() + sObj.getSolidArea().y;

                    switch (en.getDirection()) {
                        case "up":
                            en.getSolidArea().y -= en.getSpeed();
                            if (en.getSolidArea().intersects(sObj.getSolidArea())) {
                                // BLOCK NPC FROM ENTERING DOORS
                                if (sObj.isCollision() || obj.get(i) instanceof Item.Door && en instanceof NPC) {
                                    en.setCollisionOn(true);
                                }

                                /*
                                if (player.getWorldX() == workstation.getWorldX() && player.getWorldY() == workstation.getWorldY()) {
                                    return i;  // Return index of the object if collision is detected
                                }
                                */

                                // IF PLAYER ONLY, SUPEROBJECT CAN BE PICKED UP
                                if (en instanceof Player) {

                                    // TODO change accordingly to what station
                                    if (obj.get(i) instanceof WorkStation) {
                                        // Handle interaction with workstation
                                     //   handleWorkStationInteraction((Player) en, (WorkStation) obj.get(i));
                                    }
                                    index = i; // Store the index of the workstation or object
                                    index = i;
                                }
                            }
                            break;

                        case "down":
                            en.getSolidArea().y += en.getSpeed();
                            if (en.getSolidArea().intersects(sObj.getSolidArea())) {
                                if (sObj.isCollision()) {
                                    en.setCollisionOn(true);
                                }

                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;

                        case "left":
                            en.getSolidArea().x -= en.getSpeed();
                            if (en.getSolidArea().intersects(sObj.getSolidArea())) {
                                if (sObj.isCollision()) {
                                    en.setCollisionOn(true);
                                }

                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;

                        case "right":
                            en.getSolidArea().x += en.getSpeed();
                            if (en.getSolidArea().intersects(sObj.getSolidArea())) {
                                if (sObj.isCollision()) {
                                    en.setCollisionOn(true);
                                }

                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;
                    }

                    en.getSolidArea().x = en.getSolidAreaDefaultX();
                    en.getSolidArea().y = en.getSolidAreaDefaultY();
                    sObj.getSolidArea().x = sObj.getSolidAreaDefaultX();
                    sObj.getSolidArea().y = sObj.getSolidAreaDefaultY();
                }
            }

            // RETURN INDEX OF ITEM FROM THE SUPEROBJECT ARRAY
            return index;
        }
        public static int entityHitsNPC(Entity en, List<NPC> npc) {

            int index = 999;

            for (int i = 0; i < npc.size(); i++) {

                if (npc.get(i) != null && en != npc.get(i)) {

                    // GET ENTITY'S SOLID AREA POS
                    en.getSolidArea().x = en.getWorldX() + en.getSolidArea().x;
                    en.getSolidArea().y = en.getWorldY() + en.getSolidArea().y;

                    // GET NPC SOLID AREA POS
                    npc.get(i).getSolidArea().x = npc.get(i).getWorldX() + npc.get(i).getSolidArea().x;
                    npc.get(i).getSolidArea().y = npc.get(i).getWorldY() + npc.get(i).getSolidArea().y;

                    switch (en.getDirection()) {
                        case "up":
                            en.getSolidArea().y -= en.getSpeed();
                            if (en.getSolidArea().intersects(npc.get(i).getSolidArea())) {
                                en.setCollisionOn(true);
                                index = i;
                            }
                            break;

                        case "down":
                            en.getSolidArea().y += en.getSpeed();
                            if (en.getSolidArea().intersects(npc.get(i).getSolidArea())) {
                                en.setCollisionOn(true);
                                index = i;
                            }
                            break;

                        case "left":
                            en.getSolidArea().x -= en.getSpeed();
                            if (en.getSolidArea().intersects(npc.get(i).getSolidArea())) {
                                en.setCollisionOn(true);
                                index = i;
                            }
                            break;

                        case "right":
                            en.getSolidArea().x += en.getSpeed();
                            if (en.getSolidArea().intersects(npc.get(i).getSolidArea())) {
                                en.setCollisionOn(true);
                                index = i;
                            }
                            break;
                    }

                    en.getSolidArea().x = en.getSolidAreaDefaultX();
                    en.getSolidArea().y = en.getSolidAreaDefaultY();
                    npc.get(i).getSolidArea().x = npc.get(i).getSolidAreaDefaultX();
                    npc.get(i).getSolidArea().y = npc.get(i).getSolidAreaDefaultY();
                }
            }

            // RETURN INDEX OF ITEM FROM THE NPC ARRAY
            return index;
        }
        public static void NPCHitsPlayer(NPC en, Player player) {

            // GET ENTITY'S SOLID AREA POS
            en.getSolidArea().x = en.getWorldX() + en.getSolidArea().x;
            en.getSolidArea().y = en.getWorldY() + en.getSolidArea().y;

            // GET SUPEROBJECT'S SOLID AREA POS
            player.getSolidArea().x = player.getWorldX() + player.getSolidArea().x;
            player.getSolidArea().y = player.getWorldY() + player.getSolidArea().y;

            switch (en.getDirection()) {
                case "up":
                    en.getSolidArea().y -= en.getSpeed();
                    if (en.getSolidArea().intersects(player.getSolidArea()))
                        en.setCollisionOn(true);

                    break;

                case "down":
                    en.getSolidArea().y += en.getSpeed();
                    if (en.getSolidArea().intersects(player.getSolidArea()))
                        en.setCollisionOn(true);

                    break;

                case "left":
                    en.getSolidArea().x -= en.getSpeed();
                    if (en.getSolidArea().intersects(player.getSolidArea()))
                        en.setCollisionOn(true);

                    break;

                case "right":
                    en.getSolidArea().x += en.getSpeed();
                    if (en.getSolidArea().intersects(player.getSolidArea()))
                        en.setCollisionOn(true);

                    break;
            }

            en.getSolidArea().x = en.getSolidAreaDefaultX();
            en.getSolidArea().y = en.getSolidAreaDefaultY();
            player.getSolidArea().x = player.getSolidAreaDefaultX();
            player.getSolidArea().y = player.getSolidAreaDefaultY();
        }
        public static void entityHitsEvent(Entity en, GamePanel gp) {
            gp.eH.detectEvents(en);
        }
    }

    // ------------------------------------------------------------

    // HANDLES TEXT OR IMAGE ALIGNMENT
    public static class Aligner {

        public static int centerTextOnScreen(String text, GamePanel gp, Graphics2D g2) {
            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            return gp.screenWidth / 2 - length / 2;
        }
        public static List<Integer> centerCursor(String text, int x, int y, GamePanel gp, Graphics2D g2) {
            List<Integer> coordinates = new ArrayList<>();

            int endX = x + g2.getFontMetrics().stringWidth(text);

            coordinates.add(0, x - gp.tileSize); // CURSOR LEFT X
            coordinates.add(1, y); // CURSOR LEFT Y
            coordinates.add(2, endX + (gp.tileSize /2)); // CURSOR RIGHT X
            coordinates.add(3, y); // CURSOR RIGHT Y
            coordinates.add(4, x); // UNDERLINE LEFT X
            coordinates.add(5, y +5); // UNDERLINE LEFT Y
            coordinates.add(6, endX); // UNDERLINE RIGHT X
            coordinates.add(7, y+5); // UNDERLINE RIGHT Y

            return coordinates;
        }
        public static int centerTextOnAvatar(String text, GamePanel gp, Graphics2D g2) {

            int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int halfTile = gp.player.getPlayerCenteredScreenX() + (gp.tileSize/2);
            return halfTile - (textWidth / 2);
        }
    }

    // -------------------------------------------------------------

    // HANDLES TIME OR DURATION CONTROL
    public static class Regulator {

        private boolean stop = false;
        private double blockedTime = 0;
        public boolean block(int sec) {

            if (!stop) {
                if (blockedTime > (GamePanel.FPS * sec)) {
                    blockedTime = GamePanel.FPS * sec;
                    stop = true;
                }
                blockedTime++;
                return false;
            }
            return true;
        }
        public double getBlockedTime() {
            return blockedTime;
        }
        public void resetBlock() {
            stop = false;
            blockedTime = 0;
        }

        private static long lastToggleTime = 0;
        private static boolean toggleState = false;
        public static boolean flipSwitch(int sec) {

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastToggleTime >= sec * 1000L) {

                toggleState = !toggleState;
                lastToggleTime = currentTime;
            }

            return toggleState;
        }
    }

}