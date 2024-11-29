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
    public static class Stool extends Item implements Importable {

        // CONSTRUCTOR
        public Stool(GamePanel gp) {
            super(gp, "Stool");
            image = importImage("/objects/oldFiles/item/stool/stool", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Stool1 extends Item implements Importable {
        public Stool1(GamePanel gp) {
            super(gp, "Stool");
            image = importImage("/objects/oldFiles/item/stool/stool", gp.tileSize);
            // To do: Ayusin anf collision
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Door extends Item implements Importable {

        // CONSTRUCTOR
        public Door(GamePanel gp) {
            super(gp, "Door");
            image = importImage("/objects/oldFiles/item/door/door", gp.tileSize);
        }
    }

    public static class Boots extends Item implements Importable {

        public final int speedBoost = 2;

        // CONSTRUCTOR
        public Boots(GamePanel gp) {
            super(gp, "Boots");
            image = importImage("/objects/oldFiles/item/boots/boots", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

    public static class Chest extends Item implements Importable {

        // CONSTRUCTOR
        public Chest(GamePanel gp) {
            super(gp, "Chest");
            image = importImage("/objects/oldFiles/item/chest/chest", gp.tileSize); // FROM INTERFACE: IMPORTABLE
        }
    }

    // ingredients: not cooked bleh

    public static class Egg extends Item implements Importable {
        public Egg (GamePanel gp) {
            super(gp, "Egg");
            // import image
            image = importImage("/objects/oldFiles/item/ingredients/egg", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Spam extends Item implements Importable {
        public Spam (GamePanel gp) {
            super(gp, "Spam");
            image = importImage ("/objects/oldFiles/item/ingredients/spam", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
        // import image

    }

    public static class CornedBeef extends Item implements Importable {
        public CornedBeef (GamePanel gp) {
            super(gp, "Corned Beef");
            image = importImage ("/objects/oldFiles/item/ingredients/cornedBeef", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
        // import image;

    }

    public static class Rice extends Item implements Importable {
        public Rice (GamePanel gp) {
            super(gp, "Rice");
            // import image;
            image = importImage("/objects/oldFiles/item/ingredients/rice", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Tapa extends Item implements Importable {

        public Tapa (GamePanel gp) {
            super(gp, "Cola");
            // import image;
            image = importImage("/objects/oldFiles/item/ingredients/tapa", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class Water extends Item implements Importable {

        public Water (GamePanel gp) {
            super(gp, "Water");
            // import image;
            // image = importImage("objects", "item", "ingredients", "water", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class Cola extends Item implements Importable {

        public Cola (GamePanel gp) {
            super(gp, "Cola");
            // import image;
            // image = importImage("objects", "item", "ingredients", "cola", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    // cooked items

    public static class CookedEgg extends Item implements Importable {
        public CookedEgg (GamePanel gp) {
            super(gp, "Cooked Egg");
            // import image
            // image = importImage("objects", "items", "food", "cookedEgg", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class CookedSpam extends Item implements Importable {
        public CookedSpam (GamePanel gp) {
            super(gp, "Cooked Spam");
            // import image
            // image = importImage("objects", "items", "food", "cookedSpam", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class CookedTapa extends Item implements Importable {
        public CookedTapa (GamePanel gp) {
            super(gp, "Cooked Tapa");
            // import image
            // image = importImage("objects", "items", "food", "cookedTapa", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class CookedRice extends Item implements Importable {
        public CookedRice (GamePanel gp) {
            super(gp, "Cooked Rice");
            // import image
            // image = importImage("objects", "items", "food", "cookedRice", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    // cooked meals

    public static class Tapsilog extends Item implements Importable {
        public Tapsilog (GamePanel gp) {
            super(gp, "Tapsilog");
            // import image
            // image = importImage("objects", "items", "food", "tapsilog", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class Cornedsilog extends Item implements Importable {
        public Cornedsilog (GamePanel gp) {
            super(gp, "Cornedsilog");
            // import image
            // image = importImage("objects", "items", "food", "cornedsilog", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class Spamsilog extends Item implements Importable {
        public Spamsilog (GamePanel gp) {
            super(gp, "Spamsilog");
            // import image
            // image = importImage("objects", "items", "food", "spamsilog", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Tables extends Item implements Importable{
        public Tables (GamePanel gp){
            super(gp, "Tables");
        }

        public static class LeftTable extends Item implements Importable{
           public LeftTable(GamePanel gp){
               super(gp, "LeftTable");
           }

            //image = importImage("/objects/item/ingredients/egg", gp.tileSize);
            //setDefaultCollisions(true, 12, 24, 40, 37);



        }
    }
}
