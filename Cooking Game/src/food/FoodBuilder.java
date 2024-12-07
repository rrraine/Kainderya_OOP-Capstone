package food;

import interfaces.Pickupable;
import main.Asset;
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

        if (interactedItem instanceof WorkStation.leftRiceCooker) {

            // COOK RAW RICE
            if (!((WorkStation) interactedItem).isOccupied()) {
                return null;
            }
            else {

                if (onHand instanceof Item.Plates) {
                    // TODO
                }
            }
        }

        if (interactedItem instanceof WorkStation.Stove) {

        }

        if (interactedItem instanceof Item.Pan) {

        }

        if (interactedItem instanceof WorkStation.centerSink) {

        }

        // TODO TRASH CAN
        //if (interactedItem instanceof WorkStation.TrashCan) {}

        return null;
    }
}
