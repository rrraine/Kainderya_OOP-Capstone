package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.NPC;
import entity.Player;
import interfaces.Importable;
import interfaces.Pickupable;
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

        @Override
        public void interact(Entity en, AnimationFactory animF) {
            if (en instanceof NPC) {
                // TODO
            }
        }
    }

    public static class Stool1 extends Item implements Importable {
        public Stool1(GamePanel gp) {
            super(gp, "Stool");
            image = importImage("/objects/item/diningArea/stool", gp.tileSize);
            // To do: Ayusin anf collision
            setDefaultCollisions(true, 12, 24, 30, 27);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF) {

        }
    }

    public static class Door extends Item implements Importable {

        // CONSTRUCTOR
        public Door(GamePanel gp) {
            super(gp, "Door");
            image = importImage("/objects/oldFiles/item/door/door", gp.tileSize);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF) {

        }
    }

    // kitchenTools -------------------------------
    public static class Pan extends Item implements Importable, Pickupable {
        public Pan (GamePanel gp) {
            super(gp, "Pan");
            image = importImage("/objects/item/kitchenTools/pan", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 50, 30);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_PAN));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_PAN) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public boolean isPickingUp(AnimationState curr) {

            if (curr == AnimationState.BASE) {

                return true;
            }
            if (curr == AnimationState.CARRY_PAN) {
                return false;
            }
            return false;
        }


    }

    public static class Plates extends Item implements Importable, Pickupable{

        public Plates (GamePanel gp) { super(gp, "Plates"); }

        @Override
        public boolean isPickingUp(AnimationState curr) {
            if (curr == AnimationState.BASE) {
                return true;
            }
            if (curr == AnimationState.CARRY_PLATE) {
                return false;
            }
            return false;
        }


        // inner classes
        public static class counterPlates extends Plates implements Importable{
            public counterPlates (GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenTools/plateCounter", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            @Override
            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    if (animF.getCurrentState() == AnimationState.BASE) {
                        animF.switchState((AnimationState.CARRY_PLATE));
                    }
                    else if (animF.getCurrentState() == AnimationState.CARRY_PLATE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }
            }
        }

        public static class diningPlate extends Plates implements Importable{
            public diningPlate (GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenTools/plate", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }
        }

        @Override
        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_PAN) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    // misc ---------------------------------------
    public static class rightWall extends Item implements Importable {

        public rightWall(GamePanel gp) {
            super(gp, "right wall");
            image = importImage("/objects/item/kitchenArea/rightWall", gp.tileSize);
            setDefaultCollisions(true, 40, 0, 24, 64);
        }
        public void interact(Entity en, AnimationFactory animF) {}
    }

    public static class bush extends Item implements Importable{
        public bush(GamePanel gp){
            super (gp, "Bush");
            image = importImage("/objects/item/outsideRestaurant/bush", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 10, 64);
        }
        public void interact(Entity en, AnimationFactory animF) {}
    }

    public static class rightShelf1 extends Item implements Importable{
        public rightShelf1(GamePanel gp){
            super (gp, "Right Shelf 1");
            image = importImage("/objects/item/kitchenArea/rightShelf1", gp.tileSize);
            setDefaultCollisions(true, 20, 20, 44, 54);
        }
        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }
    }

    public static class rightShelf2 extends Item implements Importable{
        public rightShelf2(GamePanel gp){
            super (gp, "Right Shelf 2");
            image = importImage("/objects/item/kitchenArea/rightShelf2", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 44, 64);
        }
        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }
    }

/*
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
*/

}
