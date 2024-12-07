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

    public Ingredients(GamePanel gp, String name) {
        super(gp, name);
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
            image = importImage("/food/ingredients/cBeef", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
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
            image = importImage("/food/ingredients/spam", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
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
            image = importImage("/food/ingredients/egg", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
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
            image = importImage("/food/ingredients/rice", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {
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

        private boolean isCooked;

        public Onion(GamePanel gp) {
            super(gp, "Onion");
            image = importImage("/food/ingredients/onion", gp.tileSize);
            setDefaultCollisions(false, -8, -8, 80, 80);

            isCooked = false;
        }

        @Override
        public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

           if (en instanceof Player) {

               if (!isCooked) {

                   if (animF.getCurrentState() == AnimationState.BASE) {
                       animF.switchState(AnimationState.CARRY_ONION);
                   }
                   else if (animF.getCurrentState() == AnimationState.CARRY_ONION) {
                       animF.switchState((AnimationState.BASE));
                   }
               }
               else {

                   animF.switchState(AnimationState.CARRY_ONION);
               }

            }
        }

        public void setIsCooked(boolean isCooked) {
            this.isCooked = isCooked;
        }
    }



}
