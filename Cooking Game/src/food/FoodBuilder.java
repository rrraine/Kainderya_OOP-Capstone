package food;

import interfaces.Pickupable;
import main.Asset;
import main.GamePanel;

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

    public Asset build(Pickupable onHand, Asset interactedItem) {




        if (ingredients.size() != 3) {
            throw new IllegalArgumentException("3 ingredients required");
        }

        // TODO IDENTIFY WHAT DISH
        Dish dish = null;

        switch (foodName) {
            case "Tapsilog":
                dish = new Dish.Tapsilog(gp);
                break;
            case "Spamsilog":
                dish = new Dish.Spamsilog(gp);
                break;
            case "Cornsilog":
                dish = new Dish.Cornsilog(gp);
                break;
            default:
                throw new IllegalArgumentException("3 ingredients required");
        }

        for (Ingredients ingredient : ingredients) {
            dish.addIngredient(ingredient);
        }

        return dish;
    }
}
