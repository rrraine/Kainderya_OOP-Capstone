
package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import food.Ingredients;
import interfaces.Drawable;
import interfaces.Importable;
import interfaces.Pickupable;
import main.GamePanel;
import main.Utility;
import ui.UI;
import ui.UIElement;

import java.awt.*;

public abstract class WorkStation extends Station implements Drawable {

    boolean isCooked; // process time is finished
    boolean isOccupied; // station has food instance
    boolean playerLocked; // player cannot move
    int processTime;

    public SuperObject itemOnTop;

    Utility.Regulator utilTool;
    UIElement processBar;

    public WorkStation(GamePanel gp, String name, int processTime) {
        super(gp, name);
        this.processTime = processTime;

        utilTool = new Utility.Regulator();

        isCooked = false;
        isOccupied = false;
        playerLocked = false;

        processBar = new UIElement(gp, "loadingBar", null, 0, false);
    }

    // GENERAL ITEM DEPLOY ON SURFACE
    public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

        if(en instanceof Player){

            // GENERAL ITEM DEPLOY ON SURFACE
            if (obj != null) {

                (obj).reposition(obj, this); // repositions obj's coordinates
                gp.getAssetPool().add((SuperObject)obj); // add to pool for printing
                gp.player.setItemOnHandDestroy(); // destroy item on player's hand
                animF.switchState((AnimationState.BASE));// base animation
            }
        }
    }

    @Override
    public void update() {

        if (isOccupied) {

            if (utilTool.block(processTime)) {
                isCooked = true;

                if (this instanceof WorkStation.Stove && itemOnTop instanceof Item.Pan) {
                    ((Item.Pan) itemOnTop).isCooked = true;
                }
                if (this instanceof WorkStation.ChoppingBoard && itemOnTop instanceof Ingredients.Onion) {
                    ((Ingredients.Onion) itemOnTop).isCooked = true;
                }

                if (playerLocked) {
                    playerLocked = false;
                    gp.getKeyB().enableMovement(true);
                }
            }
        }
        else {
            isCooked = false;
            utilTool.resetBlock();
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        if (isOccupied) {
            drawProcessing(g2);
        }
    }
    public void drawProcessing(Graphics2D g2) {

        // DRAW PROCESSING BAR
        int time = (int) utilTool.getBlockedTime();
        time /= GamePanel.FPS;
        processBar.drawProcessBar(g2, screenX -10, screenY - 40, time, processTime);

        // DRAW SERVINGS
        if (this instanceof RiceCooker && isCooked) {

            String text = RiceCooker.getServingsCount() + "x";
            int x = screenX + 42;
            int y = screenY + 48;

            g2.setFont(UI.getStandardFont());
            g2.setFont(g2.getFont().deriveFont(21F));
            UI.drawLetterBorder(g2, text, Color.BLACK, 2, x, y);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
        }
    }


    // inner classes ---------------------------------

    // actual work stations
    public static class Sink extends WorkStation implements Importable {

        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING DIRTY PLATE -> THEN CARRY CLEAN PLATE
        public Sink(GamePanel gp) {
            super(gp, "centerSink", 5);
            image = importImage("/objects/item/kitchenArea/sink", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if (en instanceof Player) {


                gp.player.setItemOnHandCreate(gp.fBuilder.build(obj, this, animF, objIndex));


            }
        }

    }
    public static class ChoppingBoard extends WorkStation implements Importable {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING ONION -> THEN CARRY CHOPPED ONION

        public ChoppingBoard(GamePanel gp) {
            super(gp, "leftChoppingBoard", 3);
            image = importImage("/objects/item/kitchenArea/leftChoppingBoard", gp.tileSize);
            setDefaultCollisions(true, 0, 24, 67, 37);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if (en instanceof Player) {


                super.interact(en, animF, obj, objIndex);
                gp.player.setItemOnHandCreate(gp.fBuilder.build(obj, this, animF, objIndex));

            }
        }

    }
    public static class RiceCooker extends WorkStation implements Importable  {

        private static int servingsCount;

        public RiceCooker(GamePanel gp) {
            super(gp, "Rice Cooker", 5);
            image = importImage("/objects/item/kitchenArea/leftRiceCooker", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 64, 64);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if (en instanceof Player) {
                gp.player.setItemOnHandCreate(gp.fBuilder.build(obj, this, animF, objIndex));
            }
        }

        public int getServings() {
            return servingsCount;
        }
        public void consumeServings() {
            servingsCount--;
        }
        public static int getServingsCount() {
            return servingsCount;
        }
        public void setServingsCount(int add) {
            servingsCount = add;
        }
    }
    public static class Stove extends WorkStation implements Importable  {
        // INTERACTION ONLY WORKS WHEN ANIMATION STATE IS CARRYING COOKABLE INGREDIENTS -> MUST CARRY CLEAN PLATE -> THEN CARRY COOKED PRODUCT

        public Stove(GamePanel gp) {
            super(gp, "Stove", 5);
            image = importImage("/objects/item/kitchenArea/stove", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 58, 64);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if (en instanceof Player) {

                if (animF.getCurrentState() == AnimationState.CARRY_PAN && !isOccupied) {

                    // DEPLOY PAN ON SURFACE
                    super.interact(en, animF, obj, objIndex);

                    if (obj instanceof Item.Pan) {
                        ((Item.Pan) obj).surface = this;
                        itemOnTop = (SuperObject) obj;
                    }
                    isOccupied = true;

                    // TODO LOGIC TO START COOKING W/ INGREDIENTS
                }

            }
        }




    }

    // surfaces
    public static class Tables extends WorkStation implements Importable {

        public Tables (GamePanel gp){
            super(gp, "Tables", 0);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

//            if (obj instanceof Item.Plates) {
//                ((Item.Plates) obj).CounterToDiningPlate(true);
//            }
            super.interact(en, animF, obj, objIndex);
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
            super(gp, "Counter", 0);
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
            super(gp, "KitchenIsland", 0);
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

    // TODO WHY IS LOWER REF HERE???
    public static class lowerRef extends Item implements Importable {

        public lowerRef(GamePanel gp) {
            super(gp, "lowerRef");
            image = importImage("/objects/item/kitchenArea/lowerRef", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                // animF.switchState((AnimationState.CARRY_PAN));
            }
        }

    }

    public static class TrashCan extends Item implements Importable {
        public TrashCan(GamePanel gp) {
            super(gp, "Trashcan");
            image = importImage("/objects/item/kitchenArea/trashcan", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if(en instanceof Player){

                gp.player.setItemOnHandCreate(gp.fBuilder.build(obj, this, animF, objIndex));
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

    public boolean isCooked() {
        return isCooked;
    }

    public void setCooked(boolean cooked) {
        isCooked = cooked;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isPlayerLocked() {
        return playerLocked;
    }

    public void setPlayerLocked(boolean playerLocked) {
        this.playerLocked = playerLocked;
    }

    public int getProcessTime() {
        return processTime;
    }


}
