package object;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import food.Drink;
import food.Ingredients;
import interfaces.Importable;
import interfaces.Pickupable;
import main.Asset;
import main.GamePanel;

public abstract class RefillStation extends Station{

    // THIS CLASS SHARES THE METHOD CREATECOPY() THAT CREATES INSTANCES OF THEIR RESPECTIVE REFILLS
    // TODO REFACTOR PAN AND PLATES AS ITEMS

    public RefillStation(GamePanel gp, String name) {
        super(gp, name);
    }

    public abstract Asset createCopy();

    public static class WaterDispenser extends RefillStation implements Importable {
        public WaterDispenser(GamePanel gp) {
            super(gp, "Water Dispenser");
            image = importImage("/objects/item/ingredients/waterBot", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 64, 40);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_PLATE));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_PLATE) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Drink.Water(gp);
        }
    }

    public static class VendingMachine extends RefillStation implements Importable {
        public VendingMachine(GamePanel gp) {
            super(gp, "Vending Machine");
            image = importImage("/objects/item/ingredients/vendingMachine", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 50, 40);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){

                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_COKE));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_COKE) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Drink.Cola(gp);
        }
    }

    public static class stationaryEgg extends RefillStation implements Importable {
        public stationaryEgg (GamePanel gp) {
            super(gp, "Egg");
            // import image
            image = importImage("/objects/item/ingredients/stationaryEgg", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_EGG));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_EGG) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.Egg(gp);
        }
    }

    public static class stationarySpam extends RefillStation implements Importable {
        public stationarySpam(GamePanel gp) {
            super(gp, "stationarySpam");
            image = importImage ("/objects/item/ingredients/stationarySpam", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_SPAM));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_SPAM) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.Spam(gp);
        }
    }

    public static class stationaryCornedBeef extends RefillStation implements Importable {
        public stationaryCornedBeef(GamePanel gp) {
            super(gp, "Corned Beef");
            image = importImage ("/objects/item/ingredients/stationaryCornedBeef", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_CORNEDBEEF));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_CORNEDBEEF) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.CornedBeef(gp);
        }
    }

    public static class riceSack extends RefillStation implements Importable {
        public riceSack (GamePanel gp) {
            super(gp, "RiceSack");
            image = importImage("/objects/item/ingredients/riceSack", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 64, 40);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_RAW_RICE));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_RAW_RICE) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.Rice(gp);
        }
    }

    public static class stationaryTapa extends RefillStation implements Importable {

        public stationaryTapa(GamePanel gp) {
            super(gp, "stationaryTapa");
            image = importImage("/objects/item/ingredients/stationaryTapa", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_TAPA));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_TAPA) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.Tapa(gp);
        }
    }

    public static class stationaryOnion extends RefillStation implements Importable {

        public stationaryOnion(GamePanel gp) {
            super(gp, "stationaryOnion");
            image = importImage("/objects/item/ingredients/onion_raw", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState((AnimationState.CARRY_ONION));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_ONION) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }

        @Override
        public Asset createCopy() {
            return new Ingredients.Onion(gp);
        }
    }

    /*
    public static class TapaContainer extends RefillStation{

        public TapaContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class CornedBeefContainer extends RefillStation{

        public CornedBeefContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class SpamContainer extends RefillStation{

        public SpamContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class EggContainer extends RefillStation{

        public EggContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class RiceContainer extends RefillStation{

        public RiceContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }
    */
}
