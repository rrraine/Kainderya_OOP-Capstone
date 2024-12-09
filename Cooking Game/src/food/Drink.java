package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Pickupable;
import main.GamePanel;

public abstract class Drink extends Food {
    public Drink(GamePanel gp, String name) {
        super(gp, name);
    }

    @Override
    public void prepare() {
        for (Ingredients ingredient : ingredients) {
            System.out.println("Adding ingredient: " + ingredient.getClass().getSimpleName());
        }
    }

    // inner classes

    // TODO IMPORT WATER IMAGE
    public static class Water extends Drink{

        public Water(GamePanel gp) {
            super(gp, "Water");
            loadFoodImage("/food/drinks/water");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_WATER);
                    gp.player.setItemOnHandCreate(this);
                    gp.getAssetPool().remove(objIndex); // remove from prin
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_WATER) {
                    animF.switchState((AnimationState.BASE));
                    gp.player.setItemOnHandDestroy();
                }
            }
        }
    }

    public static class Cola extends Drink{

        public Cola(GamePanel gp) {
            super(gp, "Cola");
            loadFoodImage("/food/drinks/cola");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_COKE);
                    gp.player.setItemOnHandCreate(this);
                    gp.getAssetPool().remove(objIndex);
                    //pickUpFx(gp);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_COKE) {
                    animF.switchState((AnimationState.BASE));
                    gp.player.setItemOnHandDestroy();
                }
            }
        }
    }
}
