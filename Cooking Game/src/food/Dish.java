package food;

import main.GamePanel;

public abstract class Dish extends Food {
    public Dish(GamePanel gp, String name) {
        super(gp, name);
    }



    // inner classes

    public static class Tapsilog extends Dish{

        public Tapsilog(GamePanel gp) {
            super(gp, "Tapsilog");
            loadImage("TODO pPATHHH OF TAPSILOIG");
        }

    }

    public static class Spamsilog extends Dish{

        public Spamsilog(GamePanel gp) {
            super(gp, "Spamsilog");
            loadImage("TODO pPATHHH OF SPAMSILOG");
        }

    }

    public static class Cornsilog extends Dish{

        public Cornsilog(GamePanel gp) {
            super(gp, "Cornsilog");
            loadImage("TODO pPATHHH OF CONRSILOG");
        }
    }

}
