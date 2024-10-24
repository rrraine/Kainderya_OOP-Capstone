package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Boots extends SuperObject {

    GamePanel gp;
    public int speedIncrease = 2;

    public OBJ_Boots(GamePanel gp) {

        this.gp = gp;

        name = "Boots";
        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INCREASES PLAYER SPEED;
}
