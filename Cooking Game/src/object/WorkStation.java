
package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import food.Drink;
import food.Ingredients;
import interfaces.Importable;
import interfaces.Pickupable;
import main.GamePanel;

public abstract class WorkStation extends Station{

    boolean isOccupied;

    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public WorkStation(GamePanel gp, String name) {
        super(gp, name);
        isOccupied = false;
    }
    public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        if(en instanceof Player){

            // if carrying -> clear hand -> deploy item
            if (animF.getCurrentState() != AnimationState.BASE) {

                (obj).reposition(obj, this); // repositions obj's coordinates
                gp.getAssetPool().add((SuperObject)obj); // add to pool for printing
                System.out.println("YOU ARE HOLDING: " + obj);
                gp.player.setItemOnHandDestroy(true); // destroy item on player's hand
                animF.switchState((AnimationState.BASE));// base animation
                isOccupied = true;
            }else{
                //if(animF.getCurrentState() == AnimationState.BASE && isOccupied){
                    //(obj).reposition(obj, this); // repositions obj's coordinates
                    //gp.getAssetPool().remove((SuperObject)obj);
                    //gp.player.setItemOnHandCreate(new Ingredients.Onion(gp));
                    //animF.switchState((AnimationState.CARRY_COKE));
                //}
            }
        }
    }


    // inner classes

    // actual work stations
    public static class centerSink extends WorkStation implements Importable {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING DIRTY PLATE -> THEN CARRY CLEAN PLATE
        public centerSink(GamePanel gp) {
            super(gp, "centerSink");
            image = importImage("/objects/item/kitchenArea/sink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
    }
    public static class leftChoppingBoard extends WorkStation implements Importable {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING ONION -> THEN CARRY CHOPPED ONION

        public leftChoppingBoard(GamePanel gp) {
            super(gp, "leftChoppingBoard");
            image = importImage("/objects/item/kitchenArea/leftChoppingBoard", gp.tileSize);
            setDefaultCollisions(true, 0, 24, 67, 37);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

            if (obj instanceof Ingredients) {
                super.interact(en, animF, obj);
            }
        }
    }
    public static class leftRiceCooker extends Counter  {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING RICE -> MUST CARRY CLEAN PLATE -> THEN CARRY PLATE RICE

        public leftRiceCooker(GamePanel gp) {
            super(gp);
            image = importImage("/objects/item/kitchenArea/leftRiceCooker", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 58, 64);
        }
    }
    public static class leftStove extends Counter  {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING COOKABLE INGREDIENTS -> MUST CARRY CLEAN PLATE -> THEN CARRY COOKED PRODUCT

        public leftStove(GamePanel gp) {
            super(gp);
            image = importImage("/objects/item/kitchenArea/leftStove", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 58, 64);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

            if (obj instanceof Item.Pan) {
                super.interact(en, animF, obj);
            }
        }

    }

    // surfaces
    public static class Tables extends WorkStation implements Importable {

        public Tables (GamePanel gp){
            super(gp, "Tables");
        }


        // insideRestaurant
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
    public static class Counter extends WorkStation implements Importable{

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
                setDefaultCollisions(true, 0, 0, 50, 64);
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
    public static class KitchenIsland extends WorkStation implements Importable {
        public KitchenIsland(GamePanel gp) {
            super(gp, "KitchenIsland");
            image = importImage("/objects/item/kitchenArea/centerSink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public static class leftKitchenIsland extends KitchenIsland implements Importable {

            public leftKitchenIsland(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

        }
        public static class rightKitchenIsland extends KitchenIsland implements Importable {

            public rightKitchenIsland(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/rightKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }


        }
        public static class middleKitchenIsland extends KitchenIsland implements Importable {

            public middleKitchenIsland(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/middleKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }


        }

    }
    public static class lowerRef extends Item implements Importable {

        public lowerRef(GamePanel gp) {
            super(gp, "lowerRef");
            image = importImage("/objects/item/kitchenArea/lowerRef", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }

    }

    /*
    public static class Counter extends WorkStation{

        public Counter(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class Sink extends WorkStation{

        public Sink(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class Stove extends WorkStation{

        public Stove(GamePanel gp, String name) {
            super(gp, name);
        }
    }
    */

}
