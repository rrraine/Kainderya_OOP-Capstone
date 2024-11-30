package animation;

import interfaces.Importable;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation implements Importable {

    GamePanel gp;
    String avatar;
    AnimationState currentState;
    List<BufferedImage> sprites;

    public Animation(GamePanel gp, String avatar, String path) {
        this.gp = gp;
        this.avatar = avatar;
        sprites = new ArrayList<BufferedImage>();

        loadSprites(path);
    }

    public void loadSprites(String path) {

        // TODO CHANGE PATHS
        sprites.add(0, importImage("/avatar/player/" + avatar + path + "idle", gp.tileSize));
        sprites.add(1, importImage("/avatar/player/" + avatar + path + "idleUp", gp.tileSize));
        sprites.add(2, importImage("/avatar/player/" + avatar + path + "up1", gp.tileSize));
        sprites.add(3, importImage("/avatar/player/" + avatar + path + "up2", gp.tileSize));
        sprites.add(4, importImage("/avatar/player/" + avatar + path + "down1", gp.tileSize));
        sprites.add(5, importImage("/avatar/player/" + avatar + path + "down2", gp.tileSize));
        sprites.add(6, importImage("/avatar/player/" + avatar + path + "left1", gp.tileSize));
        sprites.add(7, importImage("/avatar/player/" + avatar + path + "left2", gp.tileSize));
        sprites.add(8, importImage("/avatar/player/" + avatar + path + "right1", gp.tileSize));
        sprites.add(9, importImage("/avatar/player/" + avatar + path + "right2", gp.tileSize));
    }
    public List<BufferedImage> getSprites() {

        return sprites;
    }
}
