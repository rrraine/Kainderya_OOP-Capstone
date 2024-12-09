package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.NPC;
import entity.Player;
import interfaces.Importable;
import interfaces.Pickupable;
import interfaces.Swappable;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Item extends SuperObject {

    // NOTE: DEPLOY THE ITEMS IN THE UTILITY CLASS, ASSETSETTER SUBCLASS!!!

    // CONSTRUCTOR --------------------------------------------
    public Item(GamePanel gp, String name) {
        super(gp, name);
    }

    @Override
    public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {}

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
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
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
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

        }
    }

    public static class Door extends Item implements Importable {

        // CONSTRUCTOR
        public Door(GamePanel gp) {
            super(gp, "Door");
            image = importImage("/objects/oldFiles/item/door/door", gp.tileSize);
        }
    }

    // kitchenTools -------------------------------
    public static class Pan extends Item implements Importable, Pickupable, Swappable {

        public boolean isCooked;
        public WorkStation surface;

        public HashMap<String, BufferedImage> panVersions;

        public Pan (GamePanel gp) {
            super(gp, "Pan");
            setDefaultCollisions(false, -8, -8, 80, 80);

            isCooked = false;

            panVersions = new HashMap<>();

            panVersions.put("pan", importImage("/objects/item/pan/pan", gp.tileSize));
            panVersions.put("panBurnt", importImage("/objects/item/pan/burnt", gp.tileSize));
            panVersions.put("panCBeef", importImage("/objects/item/pan/cbeef", gp.tileSize));

            panVersions.put("panEgg", importImage("/objects/item/pan/egg", gp.tileSize));
            panVersions.put("panOnion", importImage("/objects/item/pan/onion", gp.tileSize));

            panVersions.put("panCBeefEgg", importImage("/objects/item/pan/onionCbeef", gp.tileSize));
            panVersions.put("panSpam", importImage("/objects/item/pan/spam", gp.tileSize));
            panVersions.put("panTapa", importImage("/objects/item/pan/tapa", gp.tileSize));

            image = panVersions.get("pan");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if (en instanceof Player){

                if (!isCooked) {

                    if (!(surface instanceof WorkStation.Stove) || !surface.isOccupied) {

                        // GENERAL PICK UP INGREDIENTS FROM SURFACE
                        if (gp.player.getItemOnHand() == null ) {

                            gp.getAssetPool().remove(objIndex); // remove from printing
                            gp.player.setItemOnHandCreate(this); // add item on player's hand

                            if (surface != null) {
                                surface.itemOnTop = null;
                                surface = null;
                            }

                            updateSpriteAnimation(animF);
                        }
                    }
                }
                else {
                    gp.player.setItemOnHandCreate(gp.fBuilder.build(gp.player.getItemOnHand(), this, animF, objIndex));
                }
            }
        }

        private void updateSpriteAnimation(AnimationFactory animF) {

            BufferedImage img = ((SuperObject) gp.player.getItemOnHand()).image;

            if (img == panVersions.get("pan")) {
                animF.switchState(AnimationState.CARRY_PAN);
            }
            else if (img == panVersions.get("dirtyPlate")) {
                animF.switchState(AnimationState.CARRY_DIRTYPLATE);
            }
            else if (img == panVersions.get("noMain")) {
                animF.switchState(AnimationState.CARRY_NOMAIN);
            }
            else if (img == panVersions.get("cookedEggOnly")) {
                animF.switchState(AnimationState.CARRY_COOKEDEGGONLY);
            }
            else if (img == panVersions.get("cookedRiceOnly")) {
                animF.switchState(AnimationState.CARRY_COOKEDRICEONLY);
            }
            else if (img == panVersions.get("onionOnly")) {
                animF.switchState(AnimationState.CARRY_ONIONONLY);
            }
        }

        @Override
        public void swapImage(String key) {
            image = panVersions.get(key);
        }
        @Override
        public boolean checkCurrentImage(String key, Pickupable obj) {

            if (obj instanceof Item) {
                return (((Item)obj).image == panVersions.get(key));
            }
            return false;
        }


    }

    public static class Plates extends Item implements Importable, Pickupable, Swappable {

        public HashMap<String, BufferedImage> plateVersions;

        public Plates (GamePanel gp) {
            super(gp, "Plates");
            setDefaultCollisions(false, 0, -8, 60, 80);

            plateVersions = new HashMap<>();

            // TODO IMPORT PLATE IMAGE INSTANCES
            plateVersions.put("diningPlate", importImage("/objects/item/plate/diningplate", gp.tileSize));
            plateVersions.put("counterPlate", importImage("/objects/item/plate/counterPlate", gp.tileSize));

            plateVersions.put("dirtyPlate", importImage("/objects/item/plate/dirtyPlate", gp.tileSize));
            plateVersions.put("noMain", importImage("/objects/item/plate/noMain", gp.tileSize));

            plateVersions.put("cookedEggOnly", importImage("/objects/item/plate/cookedEggOnly", gp.tileSize));
            plateVersions.put("cookedRiceOnly", importImage("/objects/item/plate/cookedRiceOnly", gp.tileSize));
            plateVersions.put("onionOnly", importImage("/objects/item/plate/onion", gp.tileSize));

            // default
            image = plateVersions.get("counterPlate");
        }

        @Override
        public boolean isHoldingSomething(AnimationState curr) {
            if (curr == AnimationState.BASE) {
                return true;
            }
            if (curr == AnimationState.CARRY_PLATE) {
                return false;
            }
            return false;
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if(en instanceof Player){

                // GENERAL PICK UP INGREDIENTS FROM SURFACE
                if (gp.player.getItemOnHand() == null) {

                    gp.getAssetPool().remove(objIndex); // remove from printing
                    gp.player.setItemOnHandCreate(this); // add item on player's hand

                    // update animation sprite
                    updateSpriteAnimation(animF);
                }
            }
        }


        @Override
        public void swapImage(String key) {
            image = plateVersions.get(key);
        }

        @Override
        public boolean checkCurrentImage(String key, Pickupable obj) {

            if (obj instanceof Item) {
                return (((Item)obj).image == plateVersions.get(key));
            }
            return false;
        }

        private void updateSpriteAnimation(AnimationFactory animF) {

            BufferedImage img = ((SuperObject) gp.player.getItemOnHand()).image;

            if (img == plateVersions.get("diningPlate") || img == plateVersions.get("counterPlate")) {
                animF.switchState(AnimationState.CARRY_PLATE);
            }
            else if (img == plateVersions.get("dirtyPlate")) {
                animF.switchState(AnimationState.CARRY_DIRTYPLATE);
            }
            else if (img == plateVersions.get("noMain")) {
                animF.switchState(AnimationState.CARRY_NOMAIN); //TODO KANI OY
            }
            else if (img == plateVersions.get("cookedEggOnly")) {
                animF.switchState(AnimationState.CARRY_COOKEDEGGONLY);
            }
            else if (img == plateVersions.get("cookedRiceOnly")) {
                animF.switchState(AnimationState.CARRY_COOKEDRICEONLY);
            }
            else if (img == plateVersions.get("onionOnly")) {
                animF.switchState(AnimationState.CARRY_ONIONONLY);
            }
        }
        public void CounterToDiningPlate(boolean change) {

            if (change) { image = plateVersions.get("diningPlate"); }
            else if (image != plateVersions.get("counterPlate")) { image = plateVersions.get("counterPlate"); }
        }


    }

    // misc ---------------------------------------
    public static class rightWall extends Item implements Importable {

        public rightWall(GamePanel gp) {
            super(gp, "right wall");
            image = importImage("/objects/item/kitchenArea/rightWall", gp.tileSize);
            setDefaultCollisions(true, 40, 0, 24, 64);
        }
    }

    public static class bush extends Item implements Importable{
        public bush(GamePanel gp){
            super (gp, "Bush");
            image = importImage("/objects/item/outsideRestaurant/bush", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 10, 64);
        }

    }

    public static class rightShelf1 extends Item implements Importable{
        public rightShelf1(GamePanel gp){
            super (gp, "Right Shelf 1");
            image = importImage("/objects/item/kitchenArea/rightShelf1", gp.tileSize);
            setDefaultCollisions(true, 20, 20, 44, 54);
        }
    }

    public static class rightShelf2 extends Item implements Importable{
        public rightShelf2(GamePanel gp){
            super (gp, "Right Shelf 2");
            image = importImage("/objects/item/kitchenArea/rightShelf2", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 44, 64);
        }
    }

    public class DirtyPlate extends Item implements Importable{
        public DirtyPlate(GamePanel gp){
            super (gp, "R");
            image = importImage("/objects/item/kitchenArea/rightShelf2", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 44, 64);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
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
