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

    public Pickupable build(Pickupable onHand, SuperObject interactedItem, AnimationFactory animF) {

        // RICE COOKER - DONE
        if (interactedItem instanceof WorkStation.RiceCooker srfc) {

            // RICE COOKER STATES
            if (!srfc.isOccupied()) { // COOK RICE

                // INPUTS
                if (onHand instanceof Ingredients.Rice) {
                    gp.player.setItemOnHandDestroy();
                    animF.switchState(AnimationState.BASE);

                    srfc.setOccupied(true);
                    srfc.setCooked(false);
                    srfc.setServingsCount(3);

                    return null; // OUTPUT
                }
            } else { // SANDOK

                // INPUTS
                if (onHand instanceof Item.Plates && srfc.isOccupied() && srfc.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Item.Plates) onHand).checkCurrentImage("counterPlate", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_RICE_PLATE);

                        ((Item.Plates) onHand).swapImage("cookedRiceOnly"); // SWAP IMAGE

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand; // OUTPUT

                    }
                    if (((Item.Plates) onHand).checkCurrentImage("cookedEggOnly", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_EGG_PLATE);

                        ((Item.Plates) onHand).swapImage("noMain"); // SWAP IMAGE

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand; // OUTPUT

                    }
                }
                else if (onHand instanceof Dish.Spamsilog && srfc.isOccupied() && srfc.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Spamsilog) onHand).checkCurrentImage("spamsilogNoRice", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_SPAMSILOG);

                        ((Dish) onHand).swapImage("spamsilogFinal");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;


                    }
                    else if (((Dish.Spamsilog) onHand).checkCurrentImage("cookedSpamOnly", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_SPAMSI_PLATE);

                        ((Dish) onHand).swapImage("spamsilogNoEgg");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;

                    }

                }
                else if (onHand instanceof Dish.Cornsilog && srfc.isOccupied() && srfc.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Cornsilog) onHand).checkCurrentImage("cornsilogNoRice", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_CORNSILOG);

                        ((Dish) onHand).swapImage("cornsilogFinal");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;


                    }
                    else if (((Dish.Cornsilog) onHand).checkCurrentImage("cookedCBeefOnly", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_CORNSI_PLATE);

                        ((Dish) onHand).swapImage("cornsilogNoEgg");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;
                    }
                }
                else if (onHand instanceof Dish.Tapsilog && srfc.isOccupied() && srfc.isCooked()) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish)onHand).checkCurrentImage("tapsilogNoRice", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_TAPSILOG);

                        ((Dish) onHand).swapImage("tapsilogFinal");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;
                    }
                    else if (((Dish.Tapsilog) onHand).checkCurrentImage("cookedTapaOnly", onHand)) {

                        srfc.consumeServings();
                        animF.switchState(AnimationState.CARRY_TAPSI_PLATE);

                        ((Dish) onHand).swapImage("tapsilogNoEgg");

                        if (srfc.getServings() == 0) {
                            srfc.setOccupied(false);
                        }

                        return onHand;
                    }
                }
            }
        }

        // CHOPPING BOARD


        // STOVE
        if (interactedItem instanceof WorkStation.Stove) {

            // STOVE STATES
            if (!((WorkStation) interactedItem).isOccupied()) {

                // INPUTS
                if (onHand instanceof Item.Pan) {
                    return null; // OUTPUT
                }else ((Dish) onHand).swapImage("cornsilogNoEgg");
                return onHand;



            }
        }

        // PAN
        if (interactedItem instanceof Item.Pan) {

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
