package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Pickupable;
import interfaces.Swappable;
import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Dish extends Food implements Swappable {

    public HashMap<String, BufferedImage> silogVersions;

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

    @Override
    public void swapImage(String key) {
        image = silogVersions.get(key);
    }

    @Override
    public boolean checkCurrentImage(String key, Pickupable obj) {

        if (obj instanceof Dish) {
            return (((Dish)obj).image == silogVersions.get(key));
        }
        return false;
    }

    // inner classes

    // TODO IMPORT TAPSILOG IMAGE
    public static class Tapsilog extends Dish{

        public Tapsilog(GamePanel gp) {
            super(gp, "Tapsilog");

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("tapsilogFinal", importImage("/food/meals/center/tapsilog", gp.tileSize));
            silogVersions.put("tapsilogNoEgg", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("tapsilogNoRice", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("cookedTapaOnly", importImage("TODO PATH", gp.tileSize));

            image = silogVersions.get("tapsilogFinal");
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
            silogVersions.put("spamsilogFinal", importImage("/food/meals/center/spamsilog", gp.tileSize));
            silogVersions.put("spamsilogNoEgg", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("spamsilogNoRice", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("cookedSpamOnly", importImage("TODO PATH", gp.tileSize));

            image = silogVersions.get("spamsilogFinal");
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
            silogVersions.put("cornsilogFinal", importImage("/food/meals/center/cornsilog", gp.tileSize));
            silogVersions.put("cornsilogNoEgg", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("cornsilogNoRice", importImage("TODO PATH", gp.tileSize));
            silogVersions.put("cookedCBeefOnly", importImage("TODO PATH", gp.tileSize));

            image = silogVersions.get("cornsilogFinal");
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
