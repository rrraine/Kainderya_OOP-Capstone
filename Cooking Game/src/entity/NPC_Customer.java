package entity;

import main.GamePanel;

import java.awt.*;
import java.util.*;

public class NPC_Customer extends NPC{

    private String mealOrder;
    private String drinkOrder;
    private int patienceTimer;
    private boolean  isSeated;
    private boolean isMovingToSeat;
    private Point seatLocation;
    private Random random;
    private NPC npcType;

    public NPC_Customer(GamePanel gp, NPC npcType){
        super(gp, 1, "idle");
        generateOrder();
        this.npcType = npcType;
        getAvatar();
        patienceTimer = 30 * 60;
        isSeated = false;
        isMovingToSeat = false;
        seatLocation = null;
    }

    private void generateOrder(){
        Random rand = new Random();
        String[] meals = {"Tapsilog", "CornedSilog", "Spamsilog"};
        String[] drinks = {"Water", "Cola"};

        mealOrder = meals[rand.nextInt(meals.length)];
        drinkOrder = drinks[rand.nextInt(drinks.length)];
    }

    public void assignSeat(Point location){
        seatLocation = location;
        isMovingToSeat = true;
    }

    private void moveToSeat(){
        if (seatLocation != null){
            if (worldX < seatLocation.x){
                direction = "right";
                worldX += speed;

            }else if (worldX > seatLocation.x) {
                direction = "left";
                worldX -= speed;
            } else if (worldY < seatLocation.y) {
                direction = "down";
                worldY += speed;
            } else if (worldY > seatLocation.y) {
                direction = "up";
                worldY -= speed;
            } else {
                // Reached the seat
                isMovingToSeat = false;
                isSeated = true;
            }
        } // todo ano mangyari if naa na sila sa seat
        // todo mag-add ng animation????
    }

    public boolean isSeated() {
        return isSeated;
    }

    public boolean reducePatienceTimer(){
        if (patienceTimer > 0){
            if (isSeated){
                patienceTimer--;
                return false; // still has patience
            }
        }
        return true; // no patience
    }

    public void leaveSeat(){
        isSeated = false;
        isMovingToSeat = false;
        seatLocation = null;

        // todo add movement to leave
        Point exitPoint = new Point(0, 0);
        seatLocation = exitPoint;
        //moveToSeat();
        isMovingToSeat = true;
    }
    @Override
    public void setNPCAction() {
        if (isMovingToSeat){
            moveToSeat();
        } else if (isSeated){
            direction = "idle"; // todo change photo depends on location
            // todo change the photo if facing right or up
        }
    }

    @Override
    void getAvatar() {
        // todo getAvatar based on what kind of NPC
        npcType.getAvatar();

    }

    // In NPC_Customer class
    public Point getAssignedSeat() {
        return seatLocation; // Ensure this is a Point field updated in assignSeat()
    }

    // For free-roaming NPCs or general NPCs
    public int getDefaultX() {
        return random.nextInt(20); // Replace with logic for determining X
    }

    public int getDefaultY() {
        return random.nextInt(20); // Replace with logic for determining Y
    }

}

