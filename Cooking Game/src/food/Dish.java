package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import interfaces.Pickupable;
import main.GamePanel;

public abstract class Dish extends Food {
    public Dish(GamePanel gp, String name) {
        super(gp, name);
    }

    @Override
    public void prepare() {
        for (Ingredients ingredient : ingredients) {
            System.out.println("Adding ingredient: " + ingredient.getClass().getSimpleName());
        }
    }

    // inner classes

    public static class Tapsilog extends Dish{

        public Tapsilog(GamePanel gp) {
            super(gp, "Tapsilog");
            loadImage("TODO pPATHHH OF TAPSILOIG");
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

    public static class Spamsilog extends Dish{

        public Spamsilog(GamePanel gp) {
            super(gp, "Spamsilog");
            loadImage("TODO pPATHHH OF SPAMSILOG");
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

    public static class Cornsilog extends Dish{

        public Cornsilog(GamePanel gp) {
            super(gp, "Cornsilog");
            loadImage("TODO pPATHHH OF CONRSILOG");
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

}
