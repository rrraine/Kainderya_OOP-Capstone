package food;

import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class FoodBuilder {

    GamePanel gp;

    private List<Ingredients> ingredients;
    private String foodName;

    public FoodBuilder(GamePanel gp, String foodName) {
        this.gp = gp;
        this.foodName = foodName;
        ingredients = new ArrayList<>();
    }

    public FoodBuilder addIngredient(Ingredients ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public Food build() {

        if (ingredients.size() != 3) {
            throw new IllegalArgumentException("3 ingredients required");
        }

        // TODO IDENTIFY WHAT DISH BIATCH
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
