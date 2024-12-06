package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        this.seatLocation = location;
        isMovingToSeat = true;
    }

    public void moveToSeat(){
        if (seatLocation!= null && !isSeated){
            System.out.println("Current Position: " + worldX + ", " + worldY);
            // System.out.println("Assigned seat: " + seatLocation);
            System.out.println("Target Seat: " + seatLocation);
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
                System.out.println(gp.getDebug() + "Customer has reached their seat." + gp.getReset()); // dili magprint
                direction = "idle1"; // todo should change depending on the seat if right-faced seat or up sitting smth
                isMovingToSeat = false;
                isSeated = true;
                if (reducePatienceTimer()){
                    leaveSeat();
                }

            }
        } // todo ano mangyari if naa na sila sa seat
        // todo mag-add ng animation????
    }

    public boolean isSeated() {
        return isSeated;
    }

    public boolean reducePatienceTimer(){
        /*while (patienceTimer > 0){
            if (isSeated){
                patienceTimer--;
                return false; // naay patience
            }
        }

          */


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
        //isMovingToSeat = false;
    }
    @Override
    public void setNPCAction() {
        if (isMovingToSeat){
            moveToSeat();
        } else if (isSeated){
            reducePatienceTimer();
            direction = "idle"; // todo change photo depends on location
            // todo change the photo if facing right or up
        }
    }

    @Override
    void getAvatar() {
        // todo getAvatar based on what kind of NPC
        //npcType.getAvatar();
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


    @Override
    public void update(){

        if (isMovingToSeat) {
            moveToSeat();
        } else {
            super.update(); // Use the default behavior if not moving to a seat
        }


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

    public void setMovingToSeat(boolean movingToSeat) {
        isMovingToSeat = movingToSeat;
    }
}
