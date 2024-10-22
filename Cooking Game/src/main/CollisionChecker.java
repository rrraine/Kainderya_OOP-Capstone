package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity en) {

        // COLLISION BOX COORDINATES RELATIVE TO WORLD LOCATION
        int entityLeftWorldX = en.worldX + en.solidArea.x;
        int entityRightWorldX = en.worldX + en.solidArea.x + en.solidArea.width;
        int entityTopWorldY = en.worldY + en.solidArea.y;
        int entityBottomWorldY = en.worldY + en.solidArea.y + en.solidArea.height;

        // DETECT COLLISION
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        // PREDICT COLLISION BY EVERY FACING POINT OF THE COLLISION BOX
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

    // RETURN INDEX OF OBJ THE PLAYER IS COLLIDING AGAINST
    public int checkObject(Entity en, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // GET ENTITY'S SOLID AREA POS
                en.solidArea.x = en.worldX + en.solidArea.x;
                en.solidArea.y = en.worldY + en.solidArea.y;

                // GET OBJ'S SOLID AREA POS
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY+ gp.obj[i].solidArea.y;

                switch (en.direction) {
                    case "up":
                        en.solidArea.y -= en.speed;
                        if (en.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                en.collisionOn = true;
                            }
                            // IF PLAYER ONLY, OBJ CAN BE PICKED UP
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

        return index;
    }
}
