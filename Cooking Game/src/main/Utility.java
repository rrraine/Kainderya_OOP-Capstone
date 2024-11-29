package main;

import entity.Entity;
import entity.NPC;
import entity.Player;
import object.Item;
import object.SuperObject;
import object.WorkStation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

        public static void deploySuperObjectInMap(GamePanel gp, int tileSize, List<SuperObject> obj) {

            try {

                // DEPLOY OBJECTS HERE
                obj.add(0, new Item.Stool(gp));
                obj.get(0).setWorldX(tileSize * 13);
                obj.get(0).setWorldY(tileSize * 12);

                obj.add(1, new Item.Stool(gp));
                obj.get(1).setWorldX(tileSize * 14);
                obj.get(1).setWorldY(tileSize * 12);

                obj.add(2, new Item.Stool(gp));
                obj.get(2).setWorldX(tileSize * 15);
                obj.get(2).setWorldY(tileSize * 12);

                obj.add(3, new Item.Stool(gp));
                obj.get(3).setWorldX(tileSize * 16);
                obj.get(3).setWorldY(tileSize * 12);

                obj.add(4, new Item.Stool(gp));
                obj.get(4).setWorldX(tileSize * 17);
                obj.get(4).setWorldY(tileSize * 12);

                obj.add(5, new Item.Door(gp));
                obj.get(5).setWorldX(tileSize * 10);
                obj.get(5).setWorldY(tileSize * 12);

                obj.add(6, new Item.Egg(gp));
                obj.get(6).setWorldX(tileSize * 17);
                obj.get(6).setWorldY(tileSize * 12);

                obj.add(7, new Item.Spam(gp));
                obj.get(7).setWorldX(tileSize * 14);
                obj.get(7).setWorldY(tileSize * 7);

                obj.add(8, new Item.CornedBeef(gp));
                obj.get(8).setWorldX(tileSize * 16);
                obj.get(8).setWorldY(tileSize * 7);

                obj.add(9, new Item.Tapa(gp));
                obj.get(9).setWorldX(tileSize * 18);
                obj.get(9).setWorldY(tileSize * 12);

                obj.add(10, new Item.Egg(gp));
                obj.get(10).setWorldX(tileSize * 18);
                obj.get(10).setWorldY(tileSize * 12);


/*

                  obj.add(3, new Item.Door(gp));
                  obj.get(3).setWorldX(tileSize * 10);
                  obj.get(3).setWorldY(tileSize * 11);

                  obj.add(4, new Item.Door(gp));
                  obj.get(4).setWorldX(tileSize * 8);
                  obj.get(4).setWorldY(tileSize * 28);

                  obj.add(5, new Item.Door(gp));
                  obj.get(5).setWorldX(tileSize * 12);
                  obj.get(5).setWorldY(tileSize * 22);

                  obj.add(6, new Item.Chest(gp));
                  obj.get(6).setWorldX(tileSize * 10);
                  obj.get(6).setWorldY(tileSize * 7);

                  obj.add(7, new Item.Boots(gp));
                  obj.get(7).setWorldX(tileSize * 37);
                  obj.get(7).setWorldY(tileSize * 42);
*/
            } catch (NullPointerException e) {
                System.err.println("Accessing null element in (List<SuperObject> obj): " + e.getMessage());
            }
        }
        public static void deployNPCInMap(GamePanel gp, int tileSize, List<NPC> npc) {

            try {
                /*
                npc.addFirst(new NPC.StudentFemale(gp));
                npc.getFirst().setWorldX(tileSize * 21);
                npc.getFirst().setWorldY(tileSize * 21);
                */

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

            }
            catch (NullPointerException e) {
                System.err.println("Accessing null element in (List<NPC> npc): " + e.getMessage());
            }
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
        public static int entityHitsSuperObject(Entity en, List<SuperObject> obj) {

            int index = 999;

            for (int i = 0; i < obj.size(); i++) {

                if (obj.get(i) != null) {

                    // GET ENTITY'S SOLID AREA POS
                    en.getSolidArea().x = en.getWorldX() + en.getSolidArea().x;
                    en.getSolidArea().y = en.getWorldY() + en.getSolidArea().y;

                    // GET SUPEROBJECT'S SOLID AREA POS
                    obj.get(i).getSolidArea().x = obj.get(i).getWorldX() + obj.get(i).getSolidArea().x;
                    obj.get(i).getSolidArea().y = obj.get(i).getWorldY() + obj.get(i).getSolidArea().y;

                    switch (en.getDirection()) {
                        case "up":
                            en.getSolidArea().y -= en.getSpeed();
                            if (en.getSolidArea().intersects(obj.get(i).getSolidArea())) {
                                // BLOCK NPC FROM ENTERING DOORS
                                if (obj.get(i).isCollision() || obj.get(i) instanceof Item.Door && en instanceof NPC) {
                                    en.setCollisionOn(true);
                                }
                                // IF PLAYER ONLY, SUPEROBJECT CAN BE PICKED UP
                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;

                        case "down":
                            en.getSolidArea().y += en.getSpeed();
                            if (en.getSolidArea().intersects(obj.get(i).getSolidArea())) {
                                if (obj.get(i).isCollision()) {
                                    en.setCollisionOn(true);
                                }

                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;

                        case "left":
                            en.getSolidArea().x -= en.getSpeed();
                            if (en.getSolidArea().intersects(obj.get(i).getSolidArea())) {
                                if (obj.get(i).isCollision()) {
                                    en.setCollisionOn(true);
                                }

                                if (en instanceof Player) {
                                    index = i;
                                }
                            }
                            break;

                        case "right":
                            en.getSolidArea().x += en.getSpeed();
                            if (en.getSolidArea().intersects(obj.get(i).getSolidArea())) {
                                if (obj.get(i).isCollision()) {
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
                    obj.get(i).getSolidArea().x = obj.get(i).getSolidAreaDefaultX();
                    obj.get(i).getSolidArea().y = obj.get(i).getSolidAreaDefaultY();
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
            return halfTile - (textWidth * 6);
        }
    }

    // -------------------------------------------------------------

    // HANDLES TIME OR DURATION CONTROL
    public static class Regulator {

        private static double blockedTime = 0;
        public static boolean block(int sec) {

            if (blockedTime > (GamePanel.FPS * sec)) {
                blockedTime = 0;
                return true;
            }
            blockedTime++;
            return false;
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

    // added for debugging
    private static void handleWorkStationInteraction(Player player, WorkStation workStation) {
        // Check if the workstation is occupied
        if (workStation.isOccupied()) {
            // Handle the case where the workstation is occupied (could show a message or prevent interaction)
            System.out.println("The workstation is currently occupied!");
        } else {
            // Handle the case where the workstation is not occupied (could trigger interaction or task)
            System.out.println(player.getPlayerName() + " is interacting with the " + workStation.getName());
            workStation.setOccupied(true); // Mark the workstation as occupied
        }
    }
}