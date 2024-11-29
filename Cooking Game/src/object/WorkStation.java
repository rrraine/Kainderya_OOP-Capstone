
package object;

import main.GamePanel;

public abstract class WorkStation extends Station{
    boolean isOccupied;

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public WorkStation(GamePanel gp, String name) {
        super(gp, name);
        isOccupied = false;
    }

    public static class Counter extends WorkStation{

        public Counter(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class Sink extends WorkStation{

        public Sink(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class Stove extends WorkStation{

        public Stove(GamePanel gp, String name) {
            super(gp, name);
        }
    }


}
