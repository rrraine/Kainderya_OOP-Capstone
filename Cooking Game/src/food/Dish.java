package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Pickupable;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Dish extends Food {

    public static class DishInstance {
        String label;
        BufferedImage image;

        public DishInstance(String label, BufferedImage image) {
            this.label = label;
            this.image = image;
        }
    }
    public HashMap<String, DishInstance> silogVersions;

    public Dish(GamePanel gp, String name) {
        super(gp, name);
        silogVersions = new HashMap<>();
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

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("tapsilogFinal", new DishInstance("tapsilogFinal", importImage("/food/meals/center/tapsilog", gp.tileSize)));
            silogVersions.put("tapsilogNoEgg", new DishInstance("tapsilogNoEgg", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("tapsilogNoRice", new DishInstance("tapsilogNoRice", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("cookedTapaOnly", new DishInstance("cookedTapaOnly", importImage("TODO PATH", gp.tileSize)));

            image = silogVersions.get("tapsilogFinal").image;
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

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("spamsilogFinal", new DishInstance("spamsilogFinal", importImage("/food/meals/center/spamsilog", gp.tileSize)));
            silogVersions.put("spamsilogNoEgg", new DishInstance("spamsilogNoEgg", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("spamsilogNoRice", new DishInstance("spamsilogNoRice", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("cookedSpamOnly", new DishInstance("cookedSpamOnly", importImage("TODO PATH", gp.tileSize)));

            image = silogVersions.get("spamsilogFinal").image;
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

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("cornsilogFinal", new DishInstance("cornsilogFinal", importImage("/food/meals/center/cornsilog", gp.tileSize)));
            silogVersions.put("cornsilogNoEgg", new DishInstance("cornsilogNoEgg", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("cornsilogNoRice", new DishInstance("cornsilogNoRice", importImage("TODO PATH", gp.tileSize)));
            silogVersions.put("cookedCBeefOnly", new DishInstance("cookedCBeefOnly", importImage("TODO PATH", gp.tileSize)));

            image = silogVersions.get("cornsilogFinal").image;
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
