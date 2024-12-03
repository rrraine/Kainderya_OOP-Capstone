package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
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
    public static class Water extends Drink{

        public Water(GamePanel gp) {
            super(gp, "Water");
            loadImage("TODO pPATHHH OF WATER");
        }

        @Override
        public boolean isPickingUp(AnimationState curr) {

            if (curr == AnimationState.BASE) {
                return true;
            }
            // TODO CHANGE
            if (curr == AnimationState.CARRY_COKE) {
                return false;
            }
            return false;
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }

    public static class Cola extends Drink{

        public Cola(GamePanel gp) {
            super(gp, "Cola");
            loadImage("/food/coke/coke1");
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

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

        }
    }
}
