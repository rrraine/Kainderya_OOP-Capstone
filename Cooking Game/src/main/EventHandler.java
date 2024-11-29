package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {

    // ~ FIELDS ----------------------------------------------
    private static EventHandler instance;

    GamePanel gp;
    Rectangle eventBox;
    int eventBoxDefaultX, eventBoxDefaultY;

    // CONSTRUCTOR ------------------------------------------
    private EventHandler(GamePanel gp) {
        this.gp = gp;

        eventBox = new Rectangle();
        eventBox.x = eventBox.y = 23;
        eventBox.width = eventBox.height = 2; // EVENT TRIGGER POINT
        eventBoxDefaultX = eventBox.x;
        eventBoxDefaultY = eventBox.y;
    }
    // SINGLETON INSTANTIATE
    public static EventHandler instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new EventHandler(gp);
        }
        return instance;
    }

    // FROM THIS CLASS ---------------------------------------
    public void detectEvents(Entity en) {

        if (hit(27, 16, "right", en)) { slowPit(en); }
        if (hit(7, 24, "up", en)) { speedPool(en); }

    }
    private boolean hit(int row, int col, String direction, Entity en) {
        // CHECKS IF ENTITY HITS EVENT BOX

        boolean hit = false;

        en.getSolidArea().x = en.getWorldX() + en.getSolidArea().x;
        en.getSolidArea().y = en.getWorldY() + en.getSolidArea().y;
        eventBox.x = col * gp.tileSize + eventBox.x;
        eventBox.y = row * gp.tileSize + eventBox.y;

        // player colliding against event box
        if (en.getSolidArea().intersects(eventBox)) {
            if (en.getDirection().contentEquals(direction) || direction.contentEquals("any")) {
                hit = true;
            }
        }

        // reset player & event box's solid areas
        en.getSolidArea().x = en.getSolidAreaDefaultX();
        en.getSolidArea().y = en.getSolidAreaDefaultY();
        eventBox.x = eventBoxDefaultX;
        eventBox.y = eventBoxDefaultY;

        return hit;
    }

    // SPECIFIC EVENTS
    private void slowPit(Entity en) {

        System.out.println("Entity is hitting the slow pit!");

        //gp.gameState;
        gp.uiM.setNotif("You slow down"); // TODO DISPLAY NOTIF
        en.setSpeed(en.getSpeed() -3);
    }
    private void speedPool(Entity en) {

        if (gp.getKeyB().isPlayer1EnterPressed()) {
            System.out.println("You are hitting the speed pool");
            en.setSpeed(7);
        }
    }

}