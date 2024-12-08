package entity;

import animation.AnimationFactory;
import interfaces.Interactable;
import interfaces.Pickupable;
import interfaces.Servable;
import main.Asset;
import main.GamePanel;
import game.Score;
import main.Utility;
import object.Item;
import ui.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Random;

public class NPC_Customer extends NPC implements Interactable {

    private String order;
    private List<Point> path;
    private boolean orderReceived;
    private int patienceTimer;
    private boolean isReordered;
    private boolean isSeated;
    private boolean isMovingToSeat;
    private Point seatLocation;
    private NPC npcType;
    private boolean orderAcknowledged; // this marks the start of the patience timer
    private boolean hasPlacedOrder; // this locks the customer to having only one order at a time

    private Random rand;
    private Utility.Regulator utilTool;

    private final BufferedImage tapsilog;
    private final BufferedImage spamsilog;
    private final BufferedImage cornedsilog;
    private final BufferedImage water;
    private final BufferedImage cola;

    public NPC_Customer(GamePanel gp, NPC npcType) {
        super(gp, 1, "idle");
        this.npcType = npcType;
        getAvatar();
        patienceTimer = 30 * GamePanel.FPS; // 30 seconds at 60 FPS
        isReordered = false;
        isSeated = false;
        isMovingToSeat = false;
        seatLocation = null;
        orderReceived = false;
        orderAcknowledged = false;
        hasPlacedOrder = false;

        rand = new Random();
        utilTool = new Utility.Regulator();

        tapsilog = importImage("/food/meals/tapsilog/tapsilogFinal", gp.tileSize);
        spamsilog = importImage("/food/meals/spamsilog/spamsilogFinal", gp.tileSize);
        cornedsilog = importImage("/food/meals/cornsilog/cornsilogFinal", gp.tileSize);
        water = importImage("/food/meals/center/burnt", gp.tileSize); // TODO WALA PA WATER IMAGE
        cola = importImage("/food/drinks/cola", gp.tileSize);
    }

    // TODO PLAYER CUSTOMER INTERACTION HERE
    @Override
    public void interact(Entity en, AnimationFactory animF, Pickupable obj, int objIndex) {

        // only players can interact with customer
        if (en instanceof Player) {

            if (!orderReceived && !orderAcknowledged) { // acknowledge customers
                orderAcknowledged = true;
            }
            else if (orderAcknowledged && obj instanceof Servable onHand) { // serve to customers
                servingOrder(onHand);
            }
        }
    }

    // TODO CUSTOMER DRAW ORDER THOUGHT BUBBLE
    @Override
    public void draw (Graphics2D g2) {
        super.draw(g2);

        if (order != null) {

            if (orderAcknowledged) {
                drawOrderBubble(g2);
            }
            else {
                drawAcknowledgeMe(g2);
            }
        }
    }
    private void drawOrderBubble(Graphics2D g2) {

        // SET FONT STYLE
        g2.setFont(UI.getStandardFont());
        g2.setFont(g2.getFont().deriveFont(21F));
        g2.setColor(Color.WHITE);

        // CUSTOMER'S XY COORDINATES
        int x = screenX - 20;
        int y = screenY - 30;

        // ORDER BUBBLE
        g2.fillOval(x, y, 48, 48);
        g2.drawImage(visualizeOrder(), x - 8, y - 9, null);
    }
    private void drawAcknowledgeMe(Graphics2D g2) {

        // CUSTOMER'S XY COORDINATES
        int x = screenX;
        int y = screenY - 30;

        // EXCLAMATION POINT
        if (Utility.Regulator.flipSwitch(2)) {
            g2.setColor(UI.primary);
        }
        else {
            g2.setColor(UI.secondary);
        }

        g2.fillRect(x, y, 8, 42);
    }
    private BufferedImage visualizeOrder() {

        return switch (order) {
            case "Tapsilog" -> tapsilog;
            case "CornedSilog" -> cornedsilog;
            case "Spamsilog" -> spamsilog;
            case "Water" -> water;
            case "Cola" -> cola;
            default -> null;
        };

    }

    private void generateOrder() {
        String[] mealsAndDrinks = {"Tapsilog", "CornedSilog", "Spamsilog", "Water", "Cola"};

        order = mealsAndDrinks[rand.nextInt(mealsAndDrinks.length)];
        hasPlacedOrder = true;

    }

    public void assignSeat(Point location) {
        this.seatLocation = location;
        isMovingToSeat = true;
        isSeated = false;
    }

    // TODO HELP WHY
    private String deduceSeatOrientation() {

        // debugging purposes
        System.out.println("SEAT X: " + seatLocation.x + " SEAT Y: " + seatLocation.y);
        if (seatLocation.x == 4) {
            System.out.println("SitSide");
        }
        else if (seatLocation.y == 11) {
            System.out.println("SitUp");
        }

        // -------
        if (seatLocation.x == 4 && seatLocation.y != 11) return "sitSide";
        else if (seatLocation.y == 11 && seatLocation.x != 4) return "sitUp";

        return null; // must not reach this unta, causes NPC placement camera issues
    }

    // TODO HELP WHY
    private void deleteChairAssetAtLocation() {

        // Create a copy of the asset pool to iterate over
        for (Asset a : new ArrayList<>(gp.getAssetPool())) {
            if (a instanceof Item.Stool || a instanceof Item.Stool1) {

                if (a.textMapX == seatLocation.x && a.textMapY == seatLocation.y) {

                    System.out.println("Customer now seated, deleting chair: x=" + a.textMapX + ", y=" + a.textMapY + " | Seat Location: " + seatLocation);
                    gp.getAssetPool().remove(a);
                }
            }
        }
    }

    public void moveToSeat() {
        // old pathfinding, dili A*
        /*
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

         */
        if (seatLocation == null) {
            System.out.println("No seat assigned.");
            return;
        }

        if (path == null || path.isEmpty()) {
            Point start = new Point(worldX / gp.tileSize, worldY / gp.tileSize);
            Point target = new Point(seatLocation.x, seatLocation.y);
            path = findPath(start, target);

            if (path == null || path.isEmpty()) {
                System.out.println("Path not found from " + start + " to " + target);
                return;
            } else {
                System.out.println("Path generated: " + path);
            }
        }

        if (!path.isEmpty()) {
            Point nextStep = path.get(0);
            int targetX = nextStep.x * gp.tileSize;
            int targetY = nextStep.y * gp.tileSize;

            System.out.println("Moving to: (" + worldX + ", " + worldY + "), Target: (" + targetX + ", " + targetY + ")");

            if (Math.abs(worldX - targetX) > speed) {
                direction = (worldX < targetX) ? "right" : "left";
                worldX += (worldX < targetX) ? speed : -speed;
            } else if (Math.abs(worldY - targetY) > speed) {
                direction = (worldY < targetY) ? "down" : "up";
                worldY += (worldY < targetY) ? speed : -speed;
            } else {
                // Snap to target step
                worldX = targetX;
                worldY = targetY;
                path.remove(0); // Move to the next step
            }

            if (path.isEmpty()) {
                System.out.println("Customer has reached seat at: " + seatLocation);
                isMovingToSeat = false;
                isSeated = true;
                direction = deduceSeatOrientation();
                deleteChairAssetAtLocation();
                startPatienceTimer();
            }
        }
    }


    private void startPatienceTimer() {
        patienceTimer = 30 * GamePanel.FPS; // Reset patience timer to 30 seconds
        //patienceTimer = 30 * 60; // Reset patience timer to 30 seconds
    }

    public void reducePatienceTimer() {
        if (isSeated && patienceTimer > 0) {
            patienceTimer--; // Reduce patience only if seated
            if (patienceTimer <= 0 && !orderReceived) {
                gp.score.deductScore(5);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + "did not received their order. 5 points deducted. New score: " +  gp.score.getTotalScore());
            }

        }
    }

    private void checkPatience() {
        if (patienceTimer <= 0 && !orderReceived) {
            System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " is impatient and their patience ran out.");
            gp.score.deductScore(5);
        }
    }


    public void reorder(){
        // System.out.println("Customer is reordering.");

        if (!hasPlacedOrder) {
            generateOrder();
            patienceTimer = 30 * GamePanel.FPS;
            System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): reordered " + order +"| Patience: " + patienceTimer /60);
            orderReceived = false;
        }
    }

    public void servingOrder(Servable onHand){

        if (onHand.serve(onHand, order)) { // checks if order name and onHand name matches

            orderReceived = true;
            System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " received order: " + order);
            if (order.contains("Tapsilog") || order.contains("CornedSilog") || order.contains("Spamsilog")) {
               gp.score.addScore(20);
            } else if (order.contains("Water") || order.contains("Cola")) {
                gp.score.addScore(15);
            }

            resetOrderParameters();
            gp.score.addScore((patienceTimer > 0 && patienceTimer < 15) ? 5 : 10);
        }
    }

    private void resetOrderParameters() {
        order = null;
        hasPlacedOrder = false;
        orderAcknowledged = false;
    }

    private boolean hasReceivedOrder() {
        return orderReceived;
    }

    @Override
    public void setNPCAction() {
        if (isMovingToSeat) {
            System.out.println("Customer is moving to their seat.");
            moveToSeat();
        } else if (isSeated) {
            // generateOrder();
            if (!orderReceived) {
                if (order == null || !isReordered) { // Only generate a new order if there's none or it's a reorder
                    generateOrder();
                    isReordered = false; // Reset the reorder state after generating the order
                }
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " is seated and waiting for their order. Patience Timer: " + patienceTimer /60);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + "ordered " + order);
            }
            System.out.println( gp.score.getTotalScore());
            checkPatience();
        } else {
            System.out.println("Customer is idle, waiting for seat assignment.");
            direction = "idle";
        }
    }


    // TODO UPDATE CUSTOMER STATS IN REAL TIME
    @Override
    public void update() {

        // Gi comment out lang nako if gi tuyo ba jud full override ang NPC Class update hehe
        //super.update();

        if (isSeated && patienceTimer > 0) {
            patienceTimer--;
            if (patienceTimer <= 0 && !orderReceived) {
                gp.score.deductScore(5);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " did not receive their order. 5 points deducted. New score: " +  gp.score.getTotalScore());
                reorder();
                isReordered = true;
            }
        }

        setNPCAction();
        // Trigger frame updates for animation
        spriteCounter++;
        if (spriteCounter > 10) { // Example: change frame every 10 frames
            spriteNum = (spriteNum == 1) ? 2 : 1; // Alternate between sprite 1 and 2
            spriteCounter = 0;
        }
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
        sitUp = npcType.sitUp;
        sitSide = npcType.sitSide;
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

    public boolean isOrderReceived() {
        return orderReceived;
    }

    public int getPatienceTimer() {
        return patienceTimer;
    }

    public String getOrder() {
        return order;
    }

    // PATHFINDING PURPOSES -------------------------------------------------

    private class Node {
        Point position;
        Node parent;
        int gCost; // Cost from start
        int hCost; // Heuristic cost

        Node(Point position) {
            this.position = position;
        }

        int getFCost() {
            return gCost + hCost;
        }
    }


    // A* Pathfinding Logic
    private List<Point> findPath(Point start, Point target) {
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        Node startNode = new Node(start);
        Node targetNode = new Node(target);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // Get the node with the lowest fCost
            Node currentNode = openList.stream()
                    .min(Comparator.comparingInt(Node::getFCost))
                    .orElse(null);

            if (currentNode == null) break;

            // If target reached, reconstruct path
            if (currentNode.position.equals(targetNode.position)) {
                return reconstructPath(currentNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            // Process neighbors
            for (Point neighbor : getNeighbors(currentNode.position)) {
                if (closedList.stream().anyMatch(node -> node.position.equals(neighbor))) {
                    continue; // Skip already processed nodes
                }

                Node neighborNode = new Node(neighbor);
                int tentativeGCost = currentNode.gCost + 1; // 1 is the cost per step

                if (openList.stream().noneMatch(node -> node.position.equals(neighbor)) ||
                        tentativeGCost < neighborNode.gCost) {
                    neighborNode.parent = currentNode;
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.hCost = calculateHeuristic(neighborNode.position, targetNode.position);

                    if (openList.stream().noneMatch(node -> node.position.equals(neighbor))) {
                        openList.add(neighborNode);
                    }
                }
            }
        }
        return null; // No path found
    }

    private List<Point> reconstructPath(Node node) {
        List<Point> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.position); // Add to the beginning to reverse order
            node = node.parent;
        }
        return path;
    }

    private List<Point> getNeighbors(Point position) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Up, Down, Left, Right

        for (int[] dir : directions) {
            Point neighbor = new Point(position.x + dir[0], position.y + dir[1]);
            if (isNavigable(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean isNavigable(Point point) {
        // Replace with actual game logic for checking tile navigability
        return true; // For example purposes, assume all points are navigable
    }

    private int calculateHeuristic(Point a, Point b) {
        // Manhattan distance heuristic
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}
