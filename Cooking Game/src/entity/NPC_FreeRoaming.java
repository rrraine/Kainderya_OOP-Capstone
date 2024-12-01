package entity;


import main.GamePanel;

import java.util.Random;

public class NPC_FreeRoaming extends NPC{

    public NPC_FreeRoaming(GamePanel gp) {
        super(gp, 1, "idle");

    }

    @Override
    public void setNPCAction() {
        actionInterval++;

        if (actionInterval == 120) {

            Random random = new Random();
            int i = random.nextInt(125) +1;

            if (i <= 25) {
                direction = "up";
                lastDirection = lastRecordedDirection.UP;
            }
            else if (i <= 50) {
                direction = "down";
                lastDirection = lastRecordedDirection.DOWN;
            }
            else if (i <= 75) {
                direction = "left";
            }
            else if (i <= 100){
                direction = "right";
            }
            else {
                direction = "idle";
            }

            actionInterval = 0;
        }

    }

    @Override
    void getAvatar() {

    }

    //@Override
    public int getDefaultX() {
        Random rand = new Random();
        return rand.nextInt(25);  // Generate a random X position within a valid range
    }

    // @Override
    public int getDefaultY() {
        Random rand = new Random();
        return rand.nextInt(15);  // Generate a random Y position within a valid range
    }


}
