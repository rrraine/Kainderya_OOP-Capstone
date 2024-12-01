package food;

import interfaces.Importable;
import main.Asset;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.List;

public class Ingredients extends Asset implements Importable {

    GamePanel gp;

    BufferedImage ingredient;
    String name;

    public Ingredients(GamePanel gp, String name) {
        super(gp);
        this.name = name;
    }


    void loadImage(String path) {
        ingredient = importImage(path, gp.tileSize);
    }

    // inner classes

    public static class Tapa extends Ingredients {

        public Tapa(GamePanel gp) {
            super(gp, "Tapa");
        }
    }

    public static class CornedBeef extends Ingredients {

        public CornedBeef(GamePanel gp) {
            super(gp, "CornedBeef");
        }
    }

    public static class Spam extends Ingredients {

        public Spam(GamePanel gp) {
            super(gp, "Spam");
        }
    }

    public static class Egg extends Ingredients {

        public Egg(GamePanel gp) {
            super(gp, "Egg");
        }
    }

    public static class Rice extends Ingredients {

        public Rice(GamePanel gp) {
            super(gp, "Rice");
        }
    }

    public static class Onion extends Ingredients {

        public Onion(GamePanel gp) {
            super(gp, "Onion");
        }
    }
}
