package object;

import main.GamePanel;

public abstract class RefillStation extends Station{

    public RefillStation(GamePanel gp, String name) {
        super(gp, name);
    }

    public static class TapaContainer extends RefillStation{

        public TapaContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class CornedBeefContainer extends RefillStation{

        public CornedBeefContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class SpamContainer extends RefillStation{

        public SpamContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class EggContainer extends RefillStation{

        public EggContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

    public static class RiceContainer extends RefillStation{

        public RiceContainer(GamePanel gp, String name) {
            super(gp, name);
        }
    }

}
