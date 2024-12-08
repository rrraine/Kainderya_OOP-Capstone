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
                if (onHand instanceof Item.Plates && cooker.isOccupied() && cooker.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
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
                        animF.switchState(AnimationState.CARRY_COOKEDEGGONLY);

                        ((Item.Plates) onHand).swapImage("noMain"); // SWAP IMAGE

                        if (cooker.getServings() == 0) {
                            cooker.setOccupied(false);
                        }

                        return onHand; // OUTPUT

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

                ((Ingredients.Onion) onHand).surface = board;
                board.itemOnTop = (SuperObject) onHand;

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

                ((Item.Plates) onHand).swapImage("onionOnly");
                return onHand;
            }
        }

        // STOVE
        if (interactedItem instanceof WorkStation.Stove) {

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

            if (onHand instanceof Item.Plates) {

                animF.switchState(AnimationState.CARRY_COKE);

                gp.getAssetPool().remove(onHandIndex);
                pan.surface.setOccupied(false);

                ((Item.Plates) onHand).swapImage("onionOnly");
                return onHand;
            }
            else if (onHand == null) {

                animF.switchState(AnimationState.CARRY_PAN);
                gp.getAssetPool().remove(onHandIndex);
                pan.surface.setOccupied(false);

                // TODO
                return new Item.Pan(gp);
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
}
