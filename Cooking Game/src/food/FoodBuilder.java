package food;

import animation.AnimationFactory;
import animation.AnimationState;
import interfaces.Pickupable;
import main.GamePanel;
import object.Item;
import object.SuperObject;
import object.WorkStation;

import java.util.ArrayList;
import java.util.List;

public class FoodBuilder {

    private static FoodBuilder instance;
    GamePanel gp;

    private List<Ingredients> ingredients;

    private FoodBuilder(GamePanel gp) {
        this.gp = gp;

        ingredients = new ArrayList<>();
    }
    public static FoodBuilder instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new FoodBuilder(gp);
        }
        return instance;
    }

    public Pickupable build(Pickupable onHand, SuperObject interactedItem, AnimationFactory animF, int onHandIndex) {

        // RICE COOKER - DONE
        if (interactedItem instanceof WorkStation.RiceCooker cooker) {

            // RICE COOKER STATES
            if (!cooker.isOccupied()) { // COOK RICE

                // INPUTS
                if (onHand instanceof Ingredients.Rice) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState(AnimationState.BASE);

                    cooker.setOccupied(true);
                    cooker.setCooked(false);
                    cooker.setServingsCount(3);

                    return null; // OUTPUT
                }
            }
            else { // SANDOK

                // INPUTS
                if (cooker.isOccupied() && cooker.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (onHand instanceof Item.Plates) {

                        if (((Item.Plates) onHand).checkCurrentImage("counterPlate", onHand)) {

                            cooker.consumeServings();
                            animF.switchState(AnimationState.CARRY_COOKEDRICEONLY);

                            ((Item.Plates) onHand).swapImage("cookedRiceOnly"); // SWAP IMAGE

                            if (cooker.getServings() == 0) {
                                cooker.setOccupied(false);
                            }

                            return onHand; // OUTPUT

                        }
                        if (((Item.Plates) onHand).checkCurrentImage("cookedEggOnly", onHand)) {

                            cooker.consumeServings();
                            animF.switchState(AnimationState.CARRY_NOMAIN);

                            ((Item.Plates) onHand).swapImage("noMain"); // SWAP IMAGE

                            if (cooker.getServings() == 0) {
                                cooker.setOccupied(false);
                            }

                            return onHand; // OUTPUT

                        }
                    }
                }
                else if (onHand instanceof Dish.Spamsilog && cooker.isOccupied() && cooker.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Spamsilog) onHand).checkCurrentImage("spamsilogNoRice", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_SPAMSILOGFINAL);

                        ((Dish) onHand).swapImage("spamsilogFinal");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;


                    }
                    else if (((Dish.Spamsilog) onHand).checkCurrentImage("cookedSpamOnly", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_SPAMSILOGNOEGG);

                        ((Dish) onHand).swapImage("spamsilogNoEgg");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;

                    }

                }
                else if (onHand instanceof Dish.Cornsilog && cooker.isOccupied() && cooker.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Cornsilog) onHand).checkCurrentImage("cornsilogNoRice", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_CORNSILOGFINAL);

                        ((Dish) onHand).swapImage("cornsilogFinal");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;


                    }
                    else if (((Dish.Cornsilog) onHand).checkCurrentImage("cookedCBeefOnly", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_CORNSILOGNOEGG);

                        ((Dish) onHand).swapImage("cornsilogNoEgg");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;
                    }
                }
                else if (onHand instanceof Dish.Tapsilog && cooker.isOccupied() && cooker.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish)onHand).checkCurrentImage("tapsilogNoRice", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_TAPSILOGFINAL);

                        ((Dish) onHand).swapImage("tapsilogFinal");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;
                    }
                    else if (((Dish.Tapsilog) onHand).checkCurrentImage("cookedTapaOnly", onHand)) {

                        cooker.consumeServings();
                        animF.switchState(AnimationState.CARRY_TAPSILOGNOEGG);

                        ((Dish) onHand).swapImage("tapsilogNoEgg");

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand;
                    }
                }
            }
        }

        // CHOPPING BOARD
        if (interactedItem instanceof WorkStation.ChoppingBoard board) {

            if (onHand instanceof Ingredients.Onion && !board.isOccupied()) {

                board.setOccupied(true);
                board.setPlayerLocked(true);
                gp.getKeyB().enableMovement(false);

                gp.player.setItemOnHandDestroy();
                return null;
            }
        }

        // CHOPPING BOARD - ONION
        if (interactedItem instanceof Ingredients.Onion onion) {

            if (onHand instanceof Item.Plates && ((Item.Plates) onHand).checkCurrentImage("counterPlate", onHand)) {

                animF.switchState(AnimationState.CARRY_ONIONONLY);

                gp.getAssetPool().remove(onHandIndex);
                onion.surface.setOccupied(false);

                onion.surface.itemOnTop = null;
                onion.surface = null;

                ((Item.Plates) onHand).swapImage("onionOnly");
                return onHand;
            }
        }

        // STOVE
        if (interactedItem instanceof WorkStation.Stove stove) {

            // STOVE STATES
            if (!((WorkStation) interactedItem).isOccupied()) { // EMPTY STOVE

                // INPUTS
                if (onHand instanceof Item.Pan) { // DROP

                    gp.player.setItemOnHandDestroy();
                    return null; // OUTPUT
                }
            }
        }

        // PAN
        if (interactedItem instanceof Item.Pan pan) {


            if (pan.checkCurrentImage("pan", pan)) {

                if (onHand instanceof Ingredients.CornedBeef) {

                    if (pan.checkCurrentImage("pan", (Pickupable) interactedItem)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.BASE);

                        ((Item.Pan) interactedItem).swapImage("panCBeef");
                        return null;
                    }
                }
                else if (onHand instanceof Ingredients.Egg) {

                    gp.player.setItemOnHandDestroy();
                    animF.switchState(AnimationState.BASE);

                    ((Item.Pan) interactedItem).swapImage("panEgg");
                    return null;

                }
                else if (onHand instanceof Ingredients.Spam) {

                    gp.player.setItemOnHandDestroy();
                    animF.switchState(AnimationState.BASE);

                    ((Item.Pan) interactedItem).swapImage("panSpam");
                    return null;

                }
                else if (onHand instanceof Ingredients.Tapa) {

                    gp.player.setItemOnHandDestroy();
                    animF.switchState(AnimationState.BASE);

                    ((Item.Pan) interactedItem).swapImage("panTapa");
                    return null;

                }
                else if (onHand instanceof Item.Plates plate) {

                    if (plate.checkCurrentImage("onionOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.BASE);

                        ((Item.Pan) interactedItem).swapImage("panOnion");
                        return null;
                    }
                    else if (pan.checkCurrentImage("panCBeef", (Pickupable) interactedItem)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.BASE);

                        ((Item.Pan) interactedItem).swapImage("panCBeefOnion");
                        return null;
                    }
                }
            }
            else if (pan.checkCurrentImage("panEgg", pan)) {

                if (onHand instanceof Item.Plates plate) {

                    if (plate.checkCurrentImage("counterPlate", plate)) {

                        plate.swapImage("cookedEggOnly");
                        animF.switchState(AnimationState.CARRY_COOKEDEGGONLY);
                        pan.resetState();
                        return plate;
                    }
                    else if (plate.checkCurrentImage("cookedRiceOnly", plate)) {

                        plate.swapImage("noMain");
                        animF.switchState(AnimationState.CARRY_NOMAIN);
                        pan.resetState();
                        return plate;
                    }
                }
                else if (onHand instanceof Dish.Tapsilog dish) {

                    if (dish.checkCurrentImage("tapsilogNoEgg", dish)) {

                        dish.swapImage("tapsilogFinal");
                        animF.switchState(AnimationState.CARRY_TAPSILOGFINAL);
                        pan.resetState();
                        return dish;
                    }
                    else if (dish.checkCurrentImage("cookedTapaOnly", dish)) {

                        dish.swapImage("tapsilogNoRice");
                        animF.switchState(AnimationState.CARRY_TAPSILOGNORICE);
                        pan.resetState();
                        return dish;
                    }
                }
                else if (onHand instanceof Dish.Spamsilog dish) {

                    if (dish.checkCurrentImage("spamsilogNoEgg", dish)) {

                        dish.swapImage("spamsilogFinal");
                        animF.switchState(AnimationState.CARRY_SPAMSILOGFINAL);
                        pan.resetState();
                        return dish;
                    }
                    else if (dish.checkCurrentImage("cookedSpamOnly", dish)) {

                        dish.swapImage("spamsilogNoRice");
                        animF.switchState(AnimationState.CARRY_SPAMSILOGNORICE);
                        pan.resetState();
                        return dish;
                    }
                }
                else if (onHand instanceof Dish.Cornsilog dish) {

                    if (dish.checkCurrentImage("cornsilogNoEgg", dish)) {

                        dish.swapImage("cornsilogFinal");
                        animF.switchState(AnimationState.CARRY_CORNSILOGFINAL);
                        pan.resetState();
                        return dish;
                    }
                    else if (dish.checkCurrentImage("cookedCBeefOnly", dish)) {

                        dish.swapImage("cornsilogNoRice");
                        animF.switchState(AnimationState.CARRY_CORNSILOGNORICE);
                        pan.resetState();
                        return dish;
                    }
                }
            }
            else if (pan.checkCurrentImage("panCBeefOnion", pan)) {

                if (onHand instanceof Item.Plates plate) {

                    if (plate.checkCurrentImage("counterPlate", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_COOKEDCBEEFONLY);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("cookedCBeefOnly");
                        return d;
                    }
                    else if (plate.checkCurrentImage("noMain", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_CORNSILOGFINAL);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("cornsilogFinal");
                        return d;

                    }
                    else if (plate.checkCurrentImage("cookedEggOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_CORNSILOGNORICE);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("cornsilogNoRice");
                        return d;
                    }
                    else if (plate.checkCurrentImage("cookedRiceOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_CORNSILOGNOEGG);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("cornsilogNoEgg");
                        return d;
                    }
                }
            }
            else if (pan.checkCurrentImage("panSpam", pan)) {

                if (onHand instanceof Item.Plates plate) {

                    if (plate.checkCurrentImage("counterPlate", plate)) {
                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_COOKEDSPAMONLY);
                        pan.resetState();
                        Dish d = new Dish.Spamsilog(gp);
                        d.swapImage("cookedSpamOnly");
                        return d;
                    }
                    else if (plate.checkCurrentImage("noMain", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_SPAMSILOGFINAL);
                        pan.resetState();
                        Dish d = new Dish.Spamsilog(gp);
                        d.swapImage("spamsilogFinal");
                        return d;

                    }
                    else if (plate.checkCurrentImage("cookedEggOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_SPAMSILOGNORICE);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("spamsilogNoRice");
                        return d;
                    }
                    else if (plate.checkCurrentImage("cookedRiceOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_SPAMSILOGNOEGG);
                        pan.resetState();
                        Dish d = new Dish.Cornsilog(gp);
                        d.swapImage("spamsilogNoEgg");
                        return d;
                    }
                }
            }
            else if (pan.checkCurrentImage("panTapa", pan)) {


                if (onHand instanceof Item.Plates plate) {

                    if (plate.checkCurrentImage("counterPlate", plate)) {
                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_COOKEDTAPAONLY);
                        pan.resetState();
                        Dish d = new Dish.Tapsilog(gp);
                        d.swapImage("cookedTapaOnly");
                        return d;
                    }
                    else if (plate.checkCurrentImage("noMain", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_TAPSILOGFINAL);
                        pan.resetState();
                        Dish d = new Dish.Tapsilog(gp);
                        d.swapImage("tapsilogFinal");
                        return d;

                    }
                    else if (plate.checkCurrentImage("cookedEggOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_TAPSILOGNORICE);
                        pan.resetState();
                        Dish d = new Dish.Tapsilog(gp);
                        d.swapImage("tapsilogNoRice");
                        return d;
                    }
                    else if (plate.checkCurrentImage("cookedRiceOnly", plate)) {

                        gp.player.setItemOnHandDestroy();
                        animF.switchState(AnimationState.CARRY_TAPSILOGNOEGG);
                        pan.resetState();
                        Dish d = new Dish.Tapsilog(gp);
                        d.swapImage("tapsilogNoEgg");
                        return d;
                    }
                }
            }
            else if (pan.checkCurrentImage("panOnion", (Pickupable) interactedItem)) {

                if (onHand instanceof Ingredients.CornedBeef cbeef) {

                gp.player.setItemOnHandDestroy();
                animF.switchState(AnimationState.BASE);

                ((Item.Pan) interactedItem).swapImage("panCBeefOnion");
                return null;
            }













            else if (onHand == null) { // PICK UP PAN

                animF.switchState(AnimationState.CARRY_PAN);
                gp.getAssetPool().remove(onHandIndex);
                pan.surface.setOccupied(false);

                pan.surface.itemOnTop = null;
                pan.surface = null;

                // TODO
                return new Item.Pan(gp);
            }
        }
            else if (pan.checkCurrentImage("panCBeef", pan)) {

                if (onHand instanceof Item.Plates plate && plate.checkCurrentImage("onionOnly", onHand)) {

                    animF.switchState(AnimationState.CARRY_PLATE);
                    plate.swapImage("counterPlate");
                    ((Item.Pan) interactedItem).swapImage("panCBeefOnion");
                    return plate;
                }
            }
            }

        // SINK - DONE
        if (interactedItem instanceof WorkStation.Sink) {
            if(onHand instanceof Item.Plates){
                if (((Item.Plates) onHand).checkCurrentImage("dirtyPlate", onHand)) {
                    ((Item.Plates) onHand).swapImage("counterPlate"); // SWAP IMAGE
                    return onHand; // OUTPUT
                }
            }
        }

        // TRASH CAN - DONE
        if (interactedItem instanceof WorkStation.TrashCan) {

            if(onHand instanceof Item.Plates){
                if (!((Item.Plates) onHand).checkCurrentImage("counterPlate", onHand)) {
                    ((Item.Plates) onHand).swapImage("counterPlate"); // SWAP IMAGE
                    return onHand; // OUTPUT
                }
            }
            else if (onHand instanceof Item.Pan) {
                if (!((Item.Pan) onHand).checkCurrentImage("pan", onHand)) {
                    ((Item.Pan) onHand).swapImage("pan"); // SWAP IMAGE
                    return onHand; // OUTPUT
                }
            }
            else if(onHand instanceof Ingredients){
                gp.player.setItemOnHandDestroy();
                return null;
            }
        }

        return onHand;
    }

    public void resetParams() {

        instance = null;
    }
}
