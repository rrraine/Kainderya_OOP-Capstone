package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Customer extends NPC {

    private String mealOrder;
    private String drinkOrder;
    private boolean orderReceived; // Tracks if the order is served
    private int patienceTimer;
    private boolean isSeated;
    private boolean isMovingToSeat;
    private Point seatLocation; // Unified field name
    private Random random;
    private NPC npcType;

    public NPC_Customer(GamePanel gp, NPC npcType) {
        super(gp, 1, "idle");
        generateOrder();
        this.npcType = npcType;

        getAvatar();
        patienceTimer = 30 * 60; // 30 seconds at 60 FPS
        isSeated = false;
        isMovingToSeat = false;
        seatLocation = null;
        orderReceived = false;
    }

    private void generateOrder() {
        Random rand = new Random();
        String[] meals = {"Tapsilog", "CornedSilog", "Spamsilog"};
        String[] drinks = {"Water", "Cola"};

        mealOrder = meals[rand.nextInt(meals.length)];
        drinkOrder = drinks[rand.nextInt(drinks.length)];
    }

    public void assignSeat(Point location) {
        this.seatLocation = location;
        isMovingToSeat = true;
        isSeated = false;
    }

    public void moveToSeat() {
        if (seatLocation == null) return; // No seat assigned, skip movement

        int targetX = seatLocation.x * gp.tileSize; // Convert seat grid position to world position
        int targetY = seatLocation.y * gp.tileSize;

        // Move incrementally toward the target seat
        if (Math.abs(worldX - targetX) > speed) {
            direction = (worldX < targetX) ? "right" : "left";
            worldX += (worldX < targetX) ? speed : -speed;
        } else if (Math.abs(worldY - targetY) > speed) {
            direction = (worldY < targetY) ? "down" : "up";
            worldY += (worldY < targetY) ? speed : -speed;
        } else {
            // Reached the seat
            System.out.println("Customer has reached seat at: " + seatLocation);
            direction = "idle"; // Stop movement
            isMovingToSeat = false;
            isSeated = true;
            startPatienceTimer();
        }
    }


    private void startPatienceTimer() {
        patienceTimer = 30 * 60; // Reset patience timer to 30 seconds
    }

    public boolean reducePatienceTimer() {
        if (isSeated && patienceTimer > 0) {
            patienceTimer--; // Reduce patience only if seated
            return false;    // Still has patience left
        }
        return patienceTimer <= 0; // No patience left
    }

    private void checkPatience() {
        if (isSeated) {
            if (patienceTimer > 0) {
                patienceTimer--; // Reduce patience if the customer is seated
            } else {
                // Patience has run out; customer leaves without receiving order
                leaveSeat();
                System.out.println("Customer left due to impatience.");
            }
        }
    }



    public void leaveSeat() {

        isSeated = false;
        isMovingToSeat = false; // Stop movement
        seatLocation = null;   // Reset the seat
        if (hasReceivedOrder()) {
            System.out.println("Customer left happily after receiving their order.");
        } else {
            System.out.println("Customer left angrily due to impatience.");
        }
    }

    private boolean hasReceivedOrder() {
        return orderReceived;
    }

    @Override
    public void setNPCAction() {
        if (isMovingToSeat) {
            moveToSeat();
        } else if (isSeated) {
            checkPatience();
        } else {
            direction = "idle"; // Default behavior
        }
    }

    @Override
    public void update() {
        setNPCAction();

        // Trigger frame updates for animation
        spriteCounter++;
        if (spriteCounter > 10) { // Example: change frame every 10 frames
            spriteNum = (spriteNum == 1) ? 2 : 1; // Alternate between sprite 1 and 2
            spriteCounter = 0;
        }
    }

    private static boolean isWithinRestrictedArea(Point point) {
        int x = point.x;
        int y = point.y;

        // Restricted area for the restaurant
        return (x >= 6 && x <= 16) && (y >= 3 && y <= 11);
    }


    @Override
    void getAvatar() {
        // Assign sprites based on NPC type
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

    // GETTERS & SETTERS ----------------------------------------------

    public Point getSeatLocation() {
        return seatLocation;
    }

    public boolean isSeated() {
        return isSeated;
    }

    public void setMovingToSeat(boolean movingToSeat) {
        isMovingToSeat = movingToSeat;
    }

    public boolean isMovingToSeat() {
        return isMovingToSeat;
    }

    public void setSeated(boolean seated) {
        isSeated = seated;
    }
}
