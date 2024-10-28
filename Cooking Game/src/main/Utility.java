package main;

import entity.Entity;
import entity.NPC;
import entity.Player;
import object.Item;
import object.SuperObject;

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

                obj.addFirst(new Item.Key(gp));
                obj.getFirst().setWorldX(tileSize * 23);
                obj.getFirst().setWorldX(tileSize * 7);

                obj.add(1, new Item.Key(gp));
                obj.get(1).setWorldX(tileSize * 23);
                obj.get(1).setWorldX(tileSize * 7);

                obj.add(2, new Item.Key(gp));
                obj.get(2).setWorldX(tileSize * 23);
                obj.get(2).setWorldX(tileSize * 7);

                obj.add(3, new Item.Door(gp));
                obj.get(3).setWorldX(tileSize * 23);
                obj.get(3).setWorldX(tileSize * 7);

                obj.add(4, new Item.Door(gp));
                obj.get(4).setWorldX(tileSize * 23);
                obj.get(4).setWorldX(tileSize * 7);

                obj.add(5, new Item.Door(gp));
                obj.get(5).setWorldX(tileSize * 23);
                obj.get(5).setWorldX(tileSize * 7);

                obj.add(6, new Item.Chest(gp));
                obj.get(6).setWorldX(tileSize * 23);
                obj.get(6).setWorldX(tileSize * 7);

                obj.add(7, new Item.Boots(gp));
                obj.get(7).setWorldX(tileSize * 23);
                obj.get(7).setWorldX(tileSize * 7);

            } catch (NullPointerException e) {
                System.err.println("Accessing null element in (List<SuperObject> obj): " + e.getMessage());
            }
        }
        public static void deployNPCInMap(GamePanel gp, int tileSize, List<NPC> npc) {

            try {

                npc.addFirst(new NPC.StudentFemale(gp));
                npc.getFirst().setWorldX(tileSize * 21);
                npc.getFirst().setWorldY(tileSize * 21);
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

            // PREDICT COLLISION BY EVERY FACING SIDES OF BOX
            switch (en.getDirection()) {

                case "up":
                    entityTopRow = (entityTopWorldY - en.getSpeed()) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        en.setCollisionOn(true);
                    }
                    break;

                case "down":
                    entityBottomRow = (entityBottomWorldY + en.getSpeed()) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        en.setCollisionOn(true);
                    }
                    break;

                case "left":
                    entityLeftCol = (entityLeftWorldX - en.getSpeed()) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        en.setCollisionOn(true);
                    }
                    break;

                case "right":
                    entityRightCol = (entityRightWorldX + en.getSpeed()) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        en.setCollisionOn(true);
                    }
                    break;
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
                                if (obj.get(i).isCollision()) {
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

                if (npc.get(i) != null) {

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
    }

    // ------------------------------------------------------------

    // HANDLES TEXT OR IMAGE ALIGNMENT
    public static class Aligner {

        public static int centerText(String text, GamePanel gp, Graphics2D g2) {
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
    }

}