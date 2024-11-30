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

            // diningArea-----------------------------------
    public static class Stool extends Item implements Importable {

        // CONSTRUCTOR
        public Stool(GamePanel gp) {
            super(gp, "Stool");
            image = importImage("/objects/item/diningArea/stool", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class Stool1 extends Item implements Importable {
        public Stool1(GamePanel gp) {
            super(gp, "Stool");
            image = importImage("/objects/item/diningArea/stool", gp.tileSize);
            // To do: Ayusin anf collision
            setDefaultCollisions(true, 12, 24, 30, 27);
        }
    }

    public static class Door extends Item implements Importable {

        // CONSTRUCTOR
        public Door(GamePanel gp) {
            super(gp, "Door");
            image = importImage("/objects/oldFiles/item/door/door", gp.tileSize);
        }
    }

    public static class Tables extends Item implements Importable{
        public Tables (GamePanel gp){
            super(gp, "Tables");
        }

        public static class leftTable extends Tables implements Importable{
            public leftTable(GamePanel gp){
                super(gp);
                image = importImage("/objects/item/diningArea/leftTable", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }
        }

        public static class middleTable extends Tables implements Importable{
            public middleTable(GamePanel gp){
                super(gp);
                image = importImage("/objects/item/diningArea/middleTable", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }
        }

        public static class rightTable extends Tables implements Importable{
            public rightTable(GamePanel gp){
                super(gp);
                image = importImage("/objects/item/diningArea/rightTable", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }
        }

        //outsideRestaurant
            public static class outsideUpperTable extends Tables implements Importable{
                public outsideUpperTable(GamePanel gp){
                    super(gp);
                    image = importImage("/objects/item/outsideRestaurant/OutsideUpperTable", gp.tileSize);
                    setDefaultCollisions(true, 24, 10, 30, 50);
                }
            }

            public static class outsideLowerTable extends Tables implements Importable{
                public outsideLowerTable(GamePanel gp){
                    super(gp);
                    image = importImage("/objects/item/outsideRestaurant/OutsideLowerTable", gp.tileSize);
                    setDefaultCollisions(true, 24, 0, 30, 50);
                }
            }
        }


    // ingredients: not cooked bleh

    public static class stationaryEgg extends Item implements Importable {
        public stationaryEgg (GamePanel gp) {
            super(gp, "Egg");
            // import image
            image = importImage("/objects/item/ingredients/stationaryEgg", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }
    }

    public static class stationarySpam extends Item implements Importable {
        public stationarySpam(GamePanel gp) {
            super(gp, "stationarySpam");
            image = importImage ("/objects/item/ingredients/stationarySpam", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class stationaryCornedBeef extends Item implements Importable {
        public stationaryCornedBeef(GamePanel gp) {
            super(gp, "Corned Beef");
            image = importImage ("/objects/item/ingredients/stationaryCornedBeef", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class riceSack extends Item implements Importable {
        public riceSack (GamePanel gp) {
            super(gp, "RiceSack");
            image = importImage("/objects/item/ingredients/riceSack", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }

    public static class stationaryTapa extends Item implements Importable {

        public stationaryTapa(GamePanel gp) {
            super(gp, "stationaryTapa");
            image = importImage("/objects/item/ingredients/stationaryTapa", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class stationaryOnion extends Item implements Importable {

        public stationaryOnion(GamePanel gp) {
            super(gp, "stationaryOnion");
            image = importImage("/objects/item/ingredients/onion_raw", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }

    }

        //kitchenArea
    public static class centerSink extends Item implements Importable {

        public centerSink(GamePanel gp) {
            super(gp, "centerSink");
            image = importImage("/objects/item/kitchenArea/sink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class leftChoppingBoard extends Item implements Importable {

        public leftChoppingBoard(GamePanel gp) {
            super(gp, "leftChoppingBoard");
            image = importImage("/objects/item/kitchenArea/leftChoppingBoard", gp.tileSize);
            setDefaultCollisions(true, 0, 24, 67, 37);
        }

    }

    public static class Counter extends Item implements Importable{
        public Counter(GamePanel gp) {
            super(gp, "Counter");
        }
        public static class leftCounter extends Counter  {

            public leftCounter(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftCounter", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 55, 64);
            }

        }
        public static class rightCounter extends Counter  {

            public rightCounter(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/rightCounter", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 50, 64);
            }

        }

        public static class leftCornerTable extends Counter  {

            public leftCornerTable(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftCornerTable", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

        }

        public static class leftRiceCooker extends Counter  {

            public leftRiceCooker(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftRiceCooker", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

        }

        public static class leftStove extends Counter  {

            public leftStove(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftStove", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

        }

        public static class leftStraightTable extends Counter  {

            public leftStraightTable(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftStraightTable", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

        }


    }

    public static class KitchenIsland extends Item implements Importable {

        public KitchenIsland(GamePanel gp) {
            super(gp, "KitchenIsland");
            image = importImage("/objects/item/kitchenArea/centerSink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public static class leftKitchenIsland extends Item implements Importable {

            public leftKitchenIsland(GamePanel gp) {
                super(gp, "leftKitchenIsland");
                image = importImage("/objects/item/kitchenArea/leftKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

        }

        public static class rightKitchenIsland extends Item implements Importable {

            public rightKitchenIsland(GamePanel gp) {
                super(gp, "rightKitchenIsland");
                image = importImage("/objects/item/kitchenArea/rightKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

        }

        public static class middleKitchenIsland extends Item implements Importable {

            public middleKitchenIsland(GamePanel gp) {
                super(gp, "middleKitchenIsland");
                image = importImage("/objects/item/kitchenArea/middleKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

        }

    }

    public static class lowerRef extends Item implements Importable {

        public lowerRef(GamePanel gp) {
            super(gp, "lowerRef");
            image = importImage("/objects/item/kitchenArea/lowerRef", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class waterDispenser extends Item implements Importable {

        public waterDispenser(GamePanel gp) {
            super(gp, "water dispenser");
            image = importImage("/objects/item/ingredients/waterBot", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class rightWall extends Item implements Importable {

        public rightWall(GamePanel gp) {
            super(gp, "right wall");
            image = importImage("/objects/item/kitchenArea/rightWall", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }






    public static class Tapa extends Item implements Importable {

        public Tapa (GamePanel gp) {
            super(gp, "Tapa");
            //image = importImage("/objects/item/ingredients/stationaryOnion", gp.tileSize);
            //setDefaultCollisions(true, 12, 24, 40, 37);
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
            super(gp, "Cooked stationarySpam");
            // import image
            // image = importImage("objects", "items", "food", "cookedSpam", gp.tileSize);
            // setDefaultCollisions(true, 12, 24, 40, 37);
        }

    }

    public static class CookedTapa extends Item implements Importable {
        public CookedTapa (GamePanel gp) {
            super(gp, "Cooked stationaryOnion");
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

            //kitchenTools
    public static class Pan extends Item implements Importable {
        public Pan (GamePanel gp) {
            super(gp, "Pan");
            image = importImage("/objects/item/kitchenTools/pan", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }





}
