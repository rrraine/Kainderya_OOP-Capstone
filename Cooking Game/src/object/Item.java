package object;

import interfaces.Importable;
import main.GamePanel;

public abstract class Item extends SuperObject {

    // CONSTRUCTOR --------------------------------------------
    public Item(GamePanel gp, String name) {
        super(gp, name);
    }

    // INNER STATIC CLASSES -----------------------------------
    public static class Key extends Item implements Importable {

        // CONSTRUCTOR
        public Key(GamePanel gp) {
            super(gp, "Key");
            image = importImage("objects", "item", "key", "key", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

    public static class Boots extends Item implements Importable {

        public final int speedBoost = 2;

        // CONSTRUCTOR
        public Boots(GamePanel gp) {
            super(gp, "Boots");
            image = importImage("objects", "item", "boots","boots", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

    public static class Chest extends Item implements Importable {

        // CONSTRUCTOR
        public Chest(GamePanel gp) {
            super(gp, "Chest");
            image = importImage("objects", "item", "chest", "chest", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

    public static class Door extends Item implements Importable {

        // CONSTRUCTOR
        public Door(GamePanel gp) {
            super(gp, "Door");
            image = importImage("objects", "item", "door", "door", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

}
