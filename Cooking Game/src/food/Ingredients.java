package food;

import animation.AnimationFactory;
import animation.AnimationState;
import entity.Entity;
import entity.Player;
import interfaces.Importable;
import interfaces.Pickupable;
import main.GamePanel;
import object.SuperObject;

public abstract class Ingredients extends SuperObject implements Importable, Pickupable {

    GamePanel gp;

    public Ingredients(GamePanel gp, String name) {
        super(gp, name);
    }

    void loadIngredientImage(String path) {
        image = importImage(path, gp.tileSize);
        setDefaultCollisions(false, -8, -8, 80, 80);
    }

    @Override
    public boolean isPickingUp(AnimationState curr) {
        return curr == AnimationState.BASE;
    }

    // inner classes
    public static class Tapa extends Ingredients {

        public Tapa(GamePanel gp) {
            super(gp, "Tapa");
            loadIngredientImage("/food/ingredients/tapa_item");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_TAPA);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_TAPA) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class CornedBeef extends Ingredients {

        public CornedBeef(GamePanel gp) {
            super(gp, "CornedBeef");
            loadIngredientImage("/food/ingredients/cBeef");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_CORNEDBEEF);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_CORNEDBEEF) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class Spam extends Ingredients {

        public Spam(GamePanel gp) {
            super(gp, "Spam");
            loadIngredientImage("/food/ingredients/spam");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_SPAM);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_SPAM) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class Egg extends Ingredients {

        public Egg(GamePanel gp) {
            super(gp, "Egg");
            loadIngredientImage("/food/ingredients/egg");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_EGG);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_EGG) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    // TODO ADD RICE IMAGE
    public static class Rice extends Ingredients {

        public Rice(GamePanel gp) {
            super(gp, "Rice");
            loadIngredientImage("/food/ingredients/rice");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {
            if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_RAW_RICE);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_RAW_RICE) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }

    public static class Onion extends Ingredients {

        public Onion(GamePanel gp) {
            super(gp, "Onion");
            loadIngredientImage("/food/ingredients/onion");
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj) {

           if (en instanceof Player) {
                if (animF.getCurrentState() == AnimationState.BASE) {
                    animF.switchState(AnimationState.CARRY_ONION);
                }
                else if (animF.getCurrentState() == AnimationState.CARRY_ONION) {
                    animF.switchState((AnimationState.BASE));
                }
            }
        }
    }
}
