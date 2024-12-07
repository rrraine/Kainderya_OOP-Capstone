package food;

import interfaces.Importable;
import interfaces.Pickupable;
import main.GamePanel;
import object.SuperObject;

import java.util.List;

public abstract class Food extends SuperObject implements Importable, Pickupable {

    // NOTE: THIS CLASS CAN USE ASSET CLASS' ABSOLUTE POSITIONING IN MAP & ALSO COLLISION BOX

    // TODO LINK FOOD WITH ORDER OF CUSTOMER USING SERVABLE

    protected List<Ingredients> ingredients;

    public Food(GamePanel gp, String name) {
        super(gp, name);
        setDefaultCollisions(false, -8, -8, 80, 80);
    }


    void loadFoodImage(String path) {
        image = importImage(path, gp.tileSize);
    }

    protected void addIngredient(Ingredients i) {
        ingredients.add(i);
    }
    public abstract void prepare();
}
