package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class NPC extends Entity {

    public NPC (GamePanel gp, String direction, int speed) {
        super(gp);

        this.direction = direction;
        this.speed = speed;
    }

    public static class StudentFemale extends NPC {

        public StudentFemale (GamePanel gp) {
            super(gp, "down", 4);
            getAvatarImage();
        }

        @Override
        public void getAvatarImage() {

            idle = setUpAvatar("npc","studentFemale", "idle");
            up1 = setUpAvatar("npc","studentFemale", "up1");
            up2 = setUpAvatar("npc","studentFemale", "up2");
            down1 = setUpAvatar("npc","studentFemale", "down1");
            down2 = setUpAvatar("npc","studentFemale", "down2");
            left1 = setUpAvatar("npc","studentFemale", "left1");
            left2 = setUpAvatar("npc","studentFemale", "left2");
            right1 = setUpAvatar("npc","studentFemale", "right1");
            right2 = setUpAvatar("npc","studentFemale", "right2");
        }
    }
}
