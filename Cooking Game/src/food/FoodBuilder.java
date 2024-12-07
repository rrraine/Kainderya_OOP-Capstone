package food;

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

    public Pickupable build(Pickupable onHand, SuperObject interactedItem) {

        // RICE COOKER - DONE
        if (interactedItem instanceof WorkStation.leftRiceCooker) {

            // RICE COOKER STATES
            if (!((WorkStation) interactedItem).isOccupied()) {

                // INPUTS
                if (onHand instanceof Ingredients.Rice) {
                    return null; // OUTPUT
                }
            }
            else {

                // INPUTS
                if (onHand instanceof Item.Plates) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Item.Plates) onHand).checkCurrentImage("counterPlate", onHand)) {
                        ((Item.Plates) onHand).swapImage("cookedRiceOnly"); // SWAP IMAGE
                        return onHand; // OUTPUT
                    }
                    if (((Item.Plates) onHand).checkCurrentImage("cookedEggOnly", onHand)) {
                        ((Item.Plates) onHand).swapImage("noMain"); // SWAP IMAGE
                        return onHand; // OUTPUT
                    }
                }
                else if (onHand instanceof Dish) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Spamsilog) onHand).checkCurrentImage("spamsilogNoRice", onHand)) {
                        ((Dish) onHand).swapImage("spamsilogFinal");
                        return onHand;
                    }
                    else if (((Dish.Spamsilog) onHand).checkCurrentImage("cookedSpamOnly", onHand)) {
                        ((Dish) onHand).swapImage("spamsilogNoEgg");
                        return onHand;
                    }

                }
                else if (onHand instanceof Dish.Cornsilog) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish.Cornsilog) onHand).checkCurrentImage("cornsilogNoRice", onHand)) {
                        ((Dish) onHand).swapImage("cornilogFinal");
                        return onHand;
                    }
                    else if (((Dish.Cornsilog) onHand).checkCurrentImage("cookedCBeefOnly", onHand)) {
                        ((Dish) onHand).swapImage("cornsilogNoEgg");
                        return onHand;
                    }
                }
                else if (onHand instanceof Dish.Tapsilog) {

                    // DETERMINE WHAT KIND OF INPUT
                    if (((Dish)onHand).checkCurrentImage("tapsilogNoRice", onHand)) {
                        ((Dish) onHand).swapImage("tapsilogFinal");
                        return onHand;
                    }
                    else if (((Dish.Tapsilog) onHand).checkCurrentImage("cookedTapaOnly", onHand)) {
                        ((Dish) onHand).swapImage("tapsilogNoEgg");
                        return onHand;
                    }
                }
            }
        }

        // CHOPPING BOARD
        if (interactedItem instanceof Ingredients.Onion) {

            //
        }

        // STOVE
        if (interactedItem instanceof WorkStation.Stove) {

            // STOVE STATES
            if (!((WorkStation) interactedItem).isOccupied()) {

                // INPUTS
                if (onHand instanceof Item.Pan) {
                    return null; // OUTPUT
                }
            }
        }

        // PAN
        if (interactedItem instanceof Item.Pan) {

        }

        // SINK
        if (interactedItem instanceof WorkStation.centerSink) {

        }

        // TODO TRASH CAN
        //if (interactedItem instanceof WorkStation.TrashCan) {}

        return null;
    }
}
