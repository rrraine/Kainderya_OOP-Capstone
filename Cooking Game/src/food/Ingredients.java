package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Importable;
import interfaces.Pickupable;
import main.Asset;
import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Ingredients extends SuperObject implements Importable, Pickupable {

    GamePanel gp;

    BufferedImage ingredient;

    public Ingredients(GamePanel gp, String name) {
        super(gp, name);
    }


    void loadImage(String path) {
        ingredient = importImage(path, gp.tileSize);
    }

    @Override
    public boolean isPickingUp(AnimationState curr) {

        if (curr == AnimationState.BASE) {

            return true;
        }
        if (curr == AnimationState.CARRY_COKE) {
            return false;
        }
        return false;
    }

    public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
        if(en instanceof Player){

            // if carrying -> clear hand -> deploy item
            if (animF.getCurrentState() != AnimationState.BASE) {

                (obj).reposition(obj, this); // repositions obj's coordinates
                gp.getAssetPool().add((SuperObject)obj); // add to pool for printing
                System.out.println("YOU ARE HOLDING: " + obj);
                gp.player.setItemOnHandDestroy(true); // destroy item on player's hand
                animF.switchState((AnimationState.BASE)); // base animation
            }else if(animF.getCurrentState() == AnimationState.BASE){
                animF.switchState((AnimationState.CARRY_COKE));
            }


        }
    }

    // inner classes

    public static class Tapa extends Ingredients {

        public Tapa(GamePanel gp) {
            super(gp, "Tapa");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class CornedBeef extends Ingredients {

        public CornedBeef(GamePanel gp) {
            super(gp, "CornedBeef");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class Spam extends Ingredients {

        public Spam(GamePanel gp) {
            super(gp, "Spam");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class Egg extends Ingredients {

        public Egg(GamePanel gp) {
            super(gp, "Egg");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class Rice extends Ingredients {

        public Rice(GamePanel gp) {
            super(gp, "Rice");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class Onion extends Ingredients {

        public Onion(GamePanel gp) {
            super(gp, "Onion");
            image = importImage("/food/coke/cola", gp.tileSize);
            setDefaultCollisions(false, 0, 0, gp.tileSize, gp.tileSize);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
           /* if(en instanceof Player){

                // if carrying -> clear hand -> deploy item
                if (animF.getCurrentState() != AnimationState.BASE) {

                    (obj).reposition(obj, this); // repositions obj's coordinates
                    gp.getAssetPool().add((SuperObject)obj); // add to pool for printing
                    System.out.println("YOU ARE HOLDING: " + obj);
                    gp.player.setItemOnHandDestroy(true); // destroy item on player's hand
                    animF.switchState((AnimationState.BASE)); // base animation
                }
            }

            */
        }
    }
}
