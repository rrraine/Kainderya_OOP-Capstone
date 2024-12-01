
package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Importable;
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


    public static class Tables extends WorkStation implements Importable {
        public Tables (GamePanel gp){
            super(gp, "Tables");
        }

        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }



        public static class leftTable extends Tables implements Importable{
            public leftTable(GamePanel gp){
                super(gp);
                image = importImage("/objects/item/diningArea/leftTable", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
            }
        }

        public static class middleTable extends Tables implements Importable{
            public middleTable(GamePanel gp){
                super(gp);
                image = importImage("/objects/item/diningArea/middleTable", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
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

    public static class centerSink extends WorkStation implements Importable {

        public centerSink(GamePanel gp) {
            super(gp, "centerSink");
            image = importImage("/objects/item/kitchenArea/sink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }

    }

    public static class leftChoppingBoard extends WorkStation implements Importable {

        public leftChoppingBoard(GamePanel gp) {
            super(gp, "leftChoppingBoard");
            image = importImage("/objects/item/kitchenArea/leftChoppingBoard", gp.tileSize);
            setDefaultCollisions(true, 0, 24, 67, 37);
        }

        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }

    }

    public static class Counter extends WorkStation implements Importable{
        public Counter(GamePanel gp) {
            super(gp, "Counter");
        }

        public void interact(Entity en, AnimationFactory animF) {

        }

        public static class leftCounter extends Counter  {

            public leftCounter(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftCounter", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 55, 64);
            }

            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }

            }

        }
        public static class rightCounter extends Counter  {

            public rightCounter(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/rightCounter", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 50, 64);
            }

            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }

            }

        }

        public static class leftCornerTable extends Counter  {

            public leftCornerTable(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftCornerTable", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 50, 64);
            }

            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }

            }


        }

        public static class leftRiceCooker extends Counter  {

            public leftRiceCooker(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftRiceCooker", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    Player player = (Player) en;
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }


                }

            }

        }

        public static class leftStove extends Counter  {

            public leftStove(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftStove", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }

            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }

            }

        }

        public static class leftStraightTable extends Counter  {

            public leftStraightTable(GamePanel gp) {
                super(gp);
                image = importImage("/objects/item/kitchenArea/leftStraightTable", gp.tileSize);
                setDefaultCollisions(true, 0, 0, 58, 64);
            }
            public void interact(Entity en, AnimationFactory animF) {

                if(en instanceof Player){
                    if (animF.getCurrentState() != AnimationState.BASE) {
                        animF.switchState((AnimationState.BASE));
                    }
                }

            }
        }


    }

    public static class KitchenIsland extends WorkStation implements Importable {

        public KitchenIsland(GamePanel gp) {
            super(gp, "KitchenIsland");
            image = importImage("/objects/item/kitchenArea/centerSink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public void interact(Entity en, AnimationFactory animF) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }

        public static class leftKitchenIsland extends Item implements Importable {

            public leftKitchenIsland(GamePanel gp) {
                super(gp, "leftKitchenIsland");
                image = importImage("/objects/item/kitchenArea/leftKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
            }
        }

        public static class rightKitchenIsland extends Item implements Importable {

            public rightKitchenIsland(GamePanel gp) {
                super(gp, "rightKitchenIsland");
                image = importImage("/objects/item/kitchenArea/rightKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
            }

        }

        public static class middleKitchenIsland extends Item implements Importable {

            public middleKitchenIsland(GamePanel gp) {
                super(gp, "middleKitchenIsland");
                image = importImage("/objects/item/kitchenArea/middleKitchenIsland", gp.tileSize);
                setDefaultCollisions(true, 12, 24, 40, 37);
            }

            public void interact(Entity en, AnimationFactory animF) {
                if(en instanceof Player){
                    // animF.switchState((AnimationState.CARRY_PAN));
                }
            }

        }

    }

    public static class lowerRef extends Item implements Importable {

        public lowerRef(GamePanel gp) {
            super(gp, "lowerRef");
            image = importImage("/objects/item/kitchenArea/lowerRef", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        public void interact(Entity en, AnimationFactory animF) {
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
