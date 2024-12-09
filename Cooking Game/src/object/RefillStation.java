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

public abstract class RefillStation extends Station{

    public RefillStation(GamePanel gp, String name) {
        super(gp, name);
    }

    // inner classes
    public static class WaterDispenser extends RefillStation implements Importable {
        public WaterDispenser(GamePanel gp) {
            super(gp, "Water Dispenser");
            image = importImage("/objects/item/ingredients/waterBot", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 64, 40);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (obj == null) {
                    gp.player.setItemOnHandCreate(new Drink.Water(gp));
                    animF.switchState((AnimationState.CARRY_WATER));
                }
                else if (obj instanceof Drink.Cola) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class VendingMachine extends RefillStation implements Importable {
        public VendingMachine(GamePanel gp) {
            super(gp, "Vending Machine");
            image = importImage("/objects/item/ingredients/vendingMachine", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 50, 40);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){

                if (obj == null) {
                    gp.player.setItemOnHandCreate(new Drink.Cola(gp));
                    animF.switchState((AnimationState.CARRY_COKE));
                }
                else if (obj instanceof Drink.Cola) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class stationaryEgg extends RefillStation implements Importable {
        public stationaryEgg (GamePanel gp) {
            super(gp, "Egg");
            // import image
            image = importImage("/objects/item/ingredients/stationaryEgg", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

            if(en instanceof Player){
                if (obj == null) {
                    gp.player.setItemOnHandCreate(new Ingredients.Egg(gp));
                    animF.switchState((AnimationState.CARRY_EGG));
                }
                else if (obj instanceof Ingredients.Egg) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class stationarySpam extends RefillStation implements Importable {
        public stationarySpam(GamePanel gp) {
            super(gp, "stationarySpam");
            image = importImage ("/objects/item/ingredients/stationarySpam", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    gp.player.setItemOnHandCreate(new Ingredients.Spam(gp));
                    animF.switchState((AnimationState.CARRY_SPAM));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_SPAM) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class stationaryCornedBeef extends RefillStation implements Importable {
        public stationaryCornedBeef(GamePanel gp) {
            super(gp, "Corned Beef");
            image = importImage ("/objects/item/ingredients/stationaryCornedBeef", gp.tileSize);
            setDefaultCollisions(true, 12, 24, 40, 37);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    gp.player.setItemOnHandCreate(new Ingredients.CornedBeef(gp));
                    animF.switchState((AnimationState.CARRY_CORNEDBEEF));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_CORNEDBEEF) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class riceSack extends RefillStation implements Importable {
        public riceSack (GamePanel gp) {
            super(gp, "RiceSack");
            image = importImage("/objects/item/ingredients/riceSack", gp.tileSize);
            setDefaultCollisions(true, 0, 0, 64, 40);
        }
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    gp.player.setItemOnHandCreate(new Ingredients.Rice(gp));
                    animF.switchState((AnimationState.CARRY_RAWRICE));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_RAWRICE) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class stationaryTapa extends RefillStation implements Importable {

        public stationaryTapa(GamePanel gp) {
            super(gp, "stationaryTapa");
            image = importImage("/objects/item/ingredients/stationaryTapa", gp.tileSize);
            setDefaultCollisions(true, 20, 0, 46, 44);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    gp.player.setItemOnHandCreate(new Ingredients.Tapa(gp));
                    animF.switchState((AnimationState.CARRY_TAPA));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_TAPA) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class stationaryOnion extends RefillStation implements Importable {

        public stationaryOnion(GamePanel gp) {
            super(gp, "stationaryOnion");
            image = importImage("/objects/item/ingredients/onion_raw", gp.tileSize);
            setDefaultCollisions(true, 0, 12, 40, 64);
        }

        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if(en instanceof Player){
                if (animF.getCurrentState() == AnimationState.BASE) {
                    gp.player.setItemOnHandCreate(new Ingredients.Onion(gp));
                    animF.switchState((AnimationState.CARRY_ONION));
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_ONION) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState((AnimationState.BASE));
                }
            }
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
