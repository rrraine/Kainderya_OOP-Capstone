package main;

import entity.Entity;
import entity.NPC;

import java.util.List;

public class CollisionChecker {

    // ~ FIELDS
    GamePanel gp;

    // ~ METHODS
    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    // CHECK IF ENTITY IS COLLIDING AGAINST SOLID TILES CALLED BY PLAYER
    public void checkTile(Entity en) {

        // ENTITY'S COLLISION BOX COORDINATES RELATIVE TO WORLD LOCATION
        int entityLeftWorldX = en.worldX + en.solidArea.x;
        int entityRightWorldX = en.worldX + en.solidArea.x + en.solidArea.width;
        int entityTopWorldY = en.worldY + en.solidArea.y;
        int entityBottomWorldY = en.worldY + en.solidArea.y + en.solidArea.height;

        // ENTITY'S COLLISION SIDES, FORMING A BOX
        // CHECK DOCS FOR VISUALIZATION
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        // PREDICT COLLISION BY EVERY FACING SIDES OF BOX
        switch (en.direction) {

            case "up":
                entityTopRow = (entityTopWorldY - en.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    en.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + en.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    en.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - en.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    en.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + en.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    en.collisionOn = true;
                }
                break;
        }
    }

    // CHECK IF ENTITY IS COLLIDING AGAINST SUBEROBJECTS CALLED BY PLAYER
    public int checkObject(Entity en, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // GET ENTITY'S SOLID AREA POS
                en.solidArea.x = en.worldX + en.solidArea.x;
                en.solidArea.y = en.worldY + en.solidArea.y;

                // GET SUPEROBJECT'S SOLID AREA POS
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY+ gp.obj[i].solidArea.y;

                switch (en.direction) {
                    case "up":
                        en.solidArea.y -= en.speed;
                        if (en.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                en.collisionOn = true;
                            }
                            // IF PLAYER ONLY, SUPEROBJECT CAN BE PICKED UP
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        en.solidArea.y += en.speed;
                        if (en.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                en.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        en.solidArea.x -= en.speed;
                        if (en.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                en.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        en.solidArea.x += en.speed;
                        if (en.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                en.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                en.solidArea.x = en.solidAreaDefaultX;
                en.solidArea.y = en.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        // RETURN INDEX OF ITEM FROM THE SUPEROBJECT ARRAY
        return index;
    }

    // NPC COLLISION
    public int checkEntity(Entity en, List<NPC> npc) {

        int index = 999;

        for (int i = 0; i < npc.size(); i++) {

            if (npc.get(i) != null) {

                // GET ENTITY'S SOLID AREA POS
                en.solidArea.x = en.worldX + en.solidArea.x;
                en.solidArea.y = en.worldY + en.solidArea.y;

                // GET NPC SOLID AREA POS
                npc.get(i).solidArea.x = npc.get(i).worldX + npc.get(i).solidArea.x;
                npc.get(i).solidArea.y = npc.get(i).worldY+ npc.get(i).solidArea.y;

                switch (en.direction) {
                    case "up":
                        en.solidArea.y -= en.speed;
                        if (en.solidArea.intersects(npc.get(i).solidArea)) {
                            en.collisionOn = true;
                            index = i;
                        }
                        break;

                    case "down":
                        en.solidArea.y += en.speed;
                        if (en.solidArea.intersects(npc.get(i).solidArea)) {
                            en.collisionOn = true;
                            index = i;
                        }
                        break;

                    case "left":
                        en.solidArea.x -= en.speed;
                        if (en.solidArea.intersects(npc.get(i).solidArea)) {
                            en.collisionOn = true;
                            index = i;
                        }
                        break;

                    case "right":
                        en.solidArea.x += en.speed;
                        if (en.solidArea.intersects(npc.get(i).solidArea)) {
                            en.collisionOn = true;
                            index = i;
                        }
                        break;
                }

                en.solidArea.x = en.solidAreaDefaultX;
                en.solidArea.y = en.solidAreaDefaultY;
                npc.get(i).solidArea.x = npc.get(i).solidAreaDefaultX;
                npc.get(i).solidArea.y = npc.get(i).solidAreaDefaultY;
            }
        }

        // RETURN INDEX OF ITEM FROM THE NPC ARRAY
        return index;
    }

    // NPC TO PLAYER COLLISION
    public void checkNPC2Player(NPC en) {

        // GET ENTITY'S SOLID AREA POS
        en.solidArea.x = en.worldX + en.solidArea.x;
        en.solidArea.y = en.worldY + en.solidArea.y;

        // GET SUPEROBJECT'S SOLID AREA POS
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY+ gp.player.solidArea.y;

        switch (en.direction) {
            case "up":
                en.solidArea.y -= en.speed;
                if (en.solidArea.intersects(gp.player.solidArea))
                        en.collisionOn = true;

                break;

            case "down":
                en.solidArea.y += en.speed;
                if (en.solidArea.intersects(gp.player.solidArea))
                        en.collisionOn = true;

                break;

            case "left":
                en.solidArea.x -= en.speed;
                if (en.solidArea.intersects(gp.player.solidArea))
                        en.collisionOn = true;

                break;

            case "right":
                en.solidArea.x += en.speed;
                if (en.solidArea.intersects(gp.player.solidArea))
                        en.collisionOn = true;

                break;
        }

        en.solidArea.x = en.solidAreaDefaultX;
        en.solidArea.y = en.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}
