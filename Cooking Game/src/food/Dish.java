package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Pickupable;
import interfaces.Swappable;
import main.GamePanel;

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
            silogVersions.put("tapsilogFinal", importImage("/food/meals/tapsilog/tapsilogFinal", gp.tileSize));
            silogVersions.put("tapsilogNoEgg", importImage("/food/meals/tapsilog/tapsilogNoEgg", gp.tileSize));
            silogVersions.put("tapsilogNoRice", importImage("/food/meals/tapsilog/tapsilogNoRice", gp.tileSize));
            silogVersions.put("cookedTapaOnly", importImage("/food/meals/tapsilog/cookedTapaOnly", gp.tileSize));

            image = silogVersions.get("tapsilogFinal");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_TAPSILOGFINAL);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_TAPSILOGFINAL) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    // TODO IMPORT SPAMSILOG IMAGE
    public static class Spamsilog extends Dish{

        public Spamsilog(GamePanel gp) {
            super(gp, "Spamsilog");

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("spamsilogFinal", importImage("/food/meals/spamsilog/spamsilogFinal", gp.tileSize));
            silogVersions.put("spamsilogNoEgg", importImage("/food/meals/spamsilog/spamsilogNoEgg", gp.tileSize));
            silogVersions.put("spamsilogNoRice", importImage("/food/meals/spamsilog/spamsilogNoRice", gp.tileSize));
            silogVersions.put("cookedSpamOnly", importImage("/food/meals/spamsilog/cookedSpamOnly", gp.tileSize));

            image = silogVersions.get("spamsilogFinal");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_SPAMSILOGFINAL);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_SPAMSILOGFINAL) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    // TODO IMPORT CORNSILOG IMAGE
    public static class Cornsilog extends Dish{

        public Cornsilog(GamePanel gp) {
            super(gp, "Cornsilog");

            // TODO IMPORT SILOG VERSIONS
            silogVersions.put("cornsilogFinal", importImage("/food/meals/cornsilog/cornsilogFinal", gp.tileSize));
            silogVersions.put("cornsilogNoEgg", importImage("/food/meals/cornsilog/cornsilogNoEgg", gp.tileSize));
            silogVersions.put("cornsilogNoRice", importImage("/food/meals/cornsilog/cornsilogNoRice", gp.tileSize));
            silogVersions.put("cookedCBeefOnly", importImage("/food/meals/cornsilog/cookedCBeefOnly", gp.tileSize));

            image = silogVersions.get("cornsilogFinal");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_CORNSILOGFINAL);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_CORNSILOGFINAL) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

}
