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
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_WATER);
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
                super.interact(en, animF, obj, objIndex);
                animF.switchState(AnimationState.CARRY_COKE);
            }
        }
    }
}
