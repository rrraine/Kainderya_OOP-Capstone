package food;

import main.GamePanel;

public abstract class Drink extends Food {
    public Drink(GamePanel gp, String name) {
        super(gp, name);
    }

    // inner classes
    public static class Water extends Drink{

        public Water(GamePanel gp) {
            super(gp, "Water");
            loadImage("TODO pPATHHH OF WATER");
        }


    }

    public static class Cola extends Drink{

        public Cola(GamePanel gp) {
            super(gp, "Cola");
            loadImage("TODO pPATHHH OF COLA");
        }
    }
}
