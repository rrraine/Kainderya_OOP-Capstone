package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Importable;
import interfaces.Pickupable;
import main.GamePanel;
import object.SuperObject;
import object.WorkStation;

public abstract class Ingredients extends SuperObject implements Importable, Pickupable {

    public boolean isCooked;
    public WorkStation surface;

    public Ingredients(GamePanel gp, String name) {
        super(gp, name);
    }

    public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

        if(en instanceof Player){

            // GENERAL PICK UP INGREDIENTS FROM SURFACE
            if (gp.player.getItemOnHand() == null) {
                gp.player.setItemOnHandCreate(this);
                gp.getAssetPool().remove(objIndex); // remove from printing
            }
        }
    }


    // inner classes
    public static class Tapa extends Ingredients {

        public Tapa(GamePanel gp) {
            super(gp, "Tapa");
            image = importImage("/food/ingredients/tapa_item", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_TAPA);

            }
        }
    }

    public static class CornedBeef extends Ingredients {

        public CornedBeef(GamePanel gp) {
            super(gp, "CornedBeef");
            image = importImage("/food/ingredients/cBeef", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_CORNEDBEEF);

            }
        }
    }

    public static class Spam extends Ingredients {

        public Spam(GamePanel gp) {
            super(gp, "Spam");
            image = importImage("/food/ingredients/spam", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_SPAM);

            }
        }
    }

    public static class Egg extends Ingredients {

        public Egg(GamePanel gp) {
            super(gp, "Egg");
            image = importImage("/food/ingredients/egg", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_EGG);
            }
        }
    }

    // TODO ADD RICE IMAGE
    public static class Rice extends Ingredients {

        public Rice(GamePanel gp) {
            super(gp, "Rice");
            image = importImage("/food/ingredients/rice", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_RAWRICE);
            }
        }
    }

    public static class Onion extends Ingredients {

        public Onion(GamePanel gp) {
            super(gp, "Onion");
            image = importImage("/food/ingredients/onion", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);

            isCooked = false;
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

           if (en instanceof Player) {

               if (!isCooked) { // PICK UP
                   super.interact(en, animF, this, objIndex);
                   animF.switchState(AnimationState.CARRY_ONION);

                   if (surface != null) {
                       surface.itemOnTop = null;
                       surface = null;
                   }

               }
               else {
                   gp.player.setItemOnHandCreate(gp.fBuilder.build(gp.player.getItemOnHand(), this, animF, objIndex));
               }
           }
        }

        public void setIsCooked(boolean isCooked) {
            this.isCooked = isCooked;
        }
    }

    public void resetParams() {
        super.resetParams();
        isCooked = false;
        surface = null;
    }

}
