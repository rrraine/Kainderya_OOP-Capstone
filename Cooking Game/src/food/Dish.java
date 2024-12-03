package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
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

    // TODO IMPORT TAPSILOG IMAGE
    public static class Tapsilog extends Dish{

        public Tapsilog(GamePanel gp) {
            super(gp, "Tapsilog");
            loadFoodImage("TODO pPATHHH OF TAPSILOIG");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_TAPSILOG);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_TAPSILOG) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
        @Override
        public boolean isPickingUp(AnimationState curr) {
            return curr == AnimationState.BASE;
        }
    }

    // TODO IMPORT SPAMSILOG IMAGE
    public static class Spamsilog extends Dish{

        public Spamsilog(GamePanel gp) {
            super(gp, "Spamsilog");
            loadFoodImage("TODO pPATHHH OF SPAMSILOG");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_SPAMSILOG);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_SPAMSILOG) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
        @Override
        public boolean isPickingUp(AnimationState curr) {
            return curr == AnimationState.BASE;
        }
    }

    // TODO IMPORT CORNSILOG IMAGE
    public static class Cornsilog extends Dish{

        public Cornsilog(GamePanel gp) {
            super(gp, "Cornsilog");
            loadFoodImage("TODO pPATHHH OF CONRSILOG");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_CORNSILOG);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_CORNSILOG) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
        @Override
        public boolean isPickingUp(AnimationState curr) {
            return curr == AnimationState.BASE;
        }
    }

}
