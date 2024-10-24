package object;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Door extends SuperObject {

    GamePanel gp;

    public OBJ_Door(GamePanel gp) {

        this.gp = gp;

        name = "Door";
        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            Utility.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // HAS DEFAULT COLLISION, FALSE IF KEY IS USED
        collision = true;
    }
}
