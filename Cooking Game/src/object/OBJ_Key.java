package object;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Key extends SuperObject {

    GamePanel gp;

    public OBJ_Key (GamePanel gp) {

        this.gp = gp;

        name = "Key";
        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            Utility.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
