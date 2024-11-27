package object;

import interfaces.Importable;
import main.GamePanel;

public abstract class Item extends SuperObject {

    // NOTE: DEPLOY THE ITEMS IN THE UTILITY CLASS, ASSETSETTER SUBCLASS!!!

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

    // ingredients: not cooked bleh

    public static class egg extends Item implements Importable {
        public egg(GamePanel gp, String name) {
            super(gp, name);
            // import image
            // image = importImage("objects", "item", "egg", "egg", gp.tileSize);
        }
    }

    public static class spam extends Item implements Importable {
        public spam(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image
        // image = importImage ("objects", "item", "spam", "spam", gp.tileSize);
    }

    public static class cornedBeef extends Item implements Importable {
        public cornedBeef(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image;
        // image = importImage("objects", "item", "cornedBeef", "cornedBeef", gp.tileSize);
    }

    public static class rice extends Item implements Importable {
        public rice(GamePanel gp, String name) {
            super(gp, name);
        }

        // import image;
        // image = importImage("objects", "item", "rice", "rice", gp.tileSize);
    }

    public static class water extends Item implements Importable {

        public water(GamePanel gp, String name) {
            super(gp, name);
        }

        // import image;
        // image = importImage("objects", "item", "water", "water", gp.tileSize);
    }

    // cooked items

    public static class cookedEgg extends Item implements Importable {
        public cookedEgg(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image
       // image = importImage("objects", "items", "cookedEgg", "cookedEgg", gp.tileSize);
    }

    public static class cookedSpam extends Item implements Importable {
        public cookedSpam(GamePanel gp, String name) {
            super(gp, name);
        }

        // import image
       // image = importImage("objects", "items", "cookedSpam", "cookedSpam", gp.tileSize);
    }

    public static class cookedTapa extends Item implements Importable {
        public cookedTapa(GamePanel gp, String name) {
            super(gp, name);
        }

        // import image
        // image = importImage("objects", "items", "cookedTapa", "cookedTapa", gp.tileSize);
    }

    public static class cookedRice extends Item implements Importable {
        public cookedRice(GamePanel gp, String name) {
            super(gp, name);
        }

        // import image
        // image = importImage("objects", "items", "cookedRice", "cookedRice", gp.tileSize);
    }

    // cooked meals

    public static class tapsilog extends Item implements Importable {
        public tapsilog(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image
        // image = importImage("objects", "items", "tapsilog", "tapsilog", gp.tileSize);
    }

    public static class cornedsilog extends Item implements Importable {
        public cornedsilog(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image
        // image = importImage("objects", "items", "cornedsilog", "cornedsilog", gp.tileSize);
    }

    public static class spamsilog extends Item implements Importable {
        public spamsilog(GamePanel gp, String name) {
            super(gp, name);
        }
        // import image
        // image = importImage("objects", "items", "spamsilog", "spamsilog", gp.tileSize);
    }
}
