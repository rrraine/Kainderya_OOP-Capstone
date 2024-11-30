package food;

import interfaces.Importable;
import main.Asset;
import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Food extends Asset implements Importable {

    // NOTE: THIS CLASS CAN USE ASSET CLASS' ABSOLUTE POSITIONING IN MAP & ALSO COLLISION BOX

    // TODO LINK FOOD WITH ORDER OF CUSTOMER USING SERVABLE

    BufferedImage food;
    String name;
    protected List<Ingredients> ingredients;

    public Food(GamePanel gp, String name) {
        super(gp);
        this.name = name;
    }


    void loadImage(String path) {
        food = importImage(path, gp.tileSize);
    }

    public void addIngredient(Ingredients i) {
        ingredients.add(i);
    }
    public abstract void prepare();
}
