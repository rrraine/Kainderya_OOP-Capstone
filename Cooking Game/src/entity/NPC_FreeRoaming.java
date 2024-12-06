package entity;


import main.GamePanel;

import java.util.Random;

public class NPC_FreeRoaming extends NPC{

    private NPC npcType;

    public NPC_FreeRoaming(GamePanel gp, NPC npc) {
        super(gp, 1, "idle");
        this.npcType = npc;

        getAvatar();
    }

    @Override
    public void setNPCAction() {
        actionInterval++;

        if (actionInterval == 120) {
            Random random = new Random();
            int i = random.nextInt(120) + 1;

            // Determine direction
            String proposedDirection;
            if (i <= 25) {
                proposedDirection = "up";
            } else if (i <= 50) {
                proposedDirection = "down";
            } else if (i <= 75) {
                proposedDirection = "left";
            } else if (i <= 100) {
                proposedDirection = "right";
            } else {
                proposedDirection = "idle";
            }

            // Check if the proposed direction is valid
            if (isDirectionWithinBounds(proposedDirection)) {
                direction = proposedDirection;
            } else {
                direction = "up"; // Default to idle if the direction would go out of bounds
            }

            // Update the last direction
            switch (direction) {
                case "up" -> lastDirection = lastRecordedDirection.UP;
                case "down" -> lastDirection = lastRecordedDirection.DOWN;
                case "left" -> lastDirection = lastRecordedDirection.DOWN;
                case "right" -> lastDirection = lastRecordedDirection.UP;
                default -> {} // Keep last direction unchanged if idle
            }

            actionInterval = 0;
        }
    }

    // Helper method to check if a direction is valid
    private boolean isDirectionWithinBounds(String direction) {
        int tileSize = gp.tileSize; // Assuming GamePanel has a method to get the tile size
        int mapWidth = 25 * tileSize; // Map width in pixels
        int mapHeight = 15 * tileSize; // Map height in pixels

        // Predict new position
        int newX = worldX;
        int newY = worldY;

        switch (direction) {
            case "up" -> newY -= tileSize;
            case "down" -> newY += tileSize;
            case "left" -> newX -= tileSize;
            case "right" -> newX += tileSize;
        }

        // Check if the new position is within bounds
        return newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight;
    }


    @Override
    void getAvatar() {
        //npctype.getAvatar();
        idle1 = npcType.idle1;
        idle2 = npcType.idle2;
        left1 = npcType.left1;
        left2 = npcType.left2;
        right1 = npcType.right1;
        right2 = npcType.right2;
        down1 = npcType.down1;
        down2 = npcType.down2;
        up1 = npcType.up1;
        up2 = npcType.up2;
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
