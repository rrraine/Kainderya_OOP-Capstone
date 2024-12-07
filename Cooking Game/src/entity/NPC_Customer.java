package entity;

import main.GamePanel;
import game.Score;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;

public class NPC_Customer extends NPC {

    private String order;
    private List<Point> path;
    private boolean orderReceived;
    private int patienceTimer;
    private boolean isReordered;
    private boolean isSeated;
    private boolean isMovingToSeat;
    private Point seatLocation;
    private NPC npcType;
    private Score score;

    public NPC_Customer(GamePanel gp, NPC npcType) {
        super(gp, 1, "idle");
        this.npcType = npcType;
        score = new Score();
        getAvatar();
        patienceTimer = 30 ; // 30 seconds at 60 FPS
        isReordered = false;
        isSeated = false;
        isMovingToSeat = false;
        seatLocation = null;
        orderReceived = false;
    }

    private void generateOrder() {
        Random rand = new Random();
        String[] mealsAndDrinks = {"Tapsilog", "CornedSilog", "Spamsilog", "Water", "Cola"};

        order = mealsAndDrinks[rand.nextInt(mealsAndDrinks.length)];

    }

    public void assignSeat(Point location) {
        this.seatLocation = location;
        isMovingToSeat = true;
        isSeated = false;
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
                direction = "idle";
                isMovingToSeat = false;
                isSeated = true;
                startPatienceTimer();
            }
        }
    }


    private void startPatienceTimer() {
        patienceTimer = 30; // Reset patience timer to 30 seconds
        //patienceTimer = 30 * 60; // Reset patience timer to 30 seconds
    }

    public void reducePatienceTimer() {
        if (isSeated && patienceTimer > 0) {
            patienceTimer--; // Reduce patience only if seated
            if (patienceTimer <= 0 && !orderReceived) {
                score.deductScore(5);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + "did not received their order. 5 points deducted. New score: " + score.getTotalScore());
            }

        }
    }

    private void checkPatience() {
        if (patienceTimer <= 0 && !orderReceived) {
            System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " is impatient and their patience ran out.");
            score.deductScore(5);
        }
    }


    public void reorder(){
        // System.out.println("Customer is reordering.");
        generateOrder();
        patienceTimer = 30 ;
        System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): reordered " + order +"| Patience: " + patienceTimer);
        orderReceived = false;
    }

    public void servingOrder(){ // todo if ever player-customer interaction
        orderReceived = true;
        System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " received order: " + order);
        if (order.contains("Tapsilog") || order.contains("CornedSilog") || order.contains("Spamsilog")) {
            score.addScore(20);
        } else if (order.contains("Water") || order.contains("Cola")) {
            score.addScore(15);
        }

        score.addScore((patienceTimer > 0 && patienceTimer < 15) ? 5 : 10);

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
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " is seated and waiting for their order. Patience Timer: " + patienceTimer);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + "ordered " + order);
            }
            System.out.println(score.getTotalScore());
            checkPatience();
        } else {
            System.out.println("Customer is idle, waiting for seat assignment.");
            direction = "idle";
        }
    }


    @Override
    public void update() {

        if (isSeated && patienceTimer > 0) {
            patienceTimer--;
            if (patienceTimer <= 0 && !orderReceived) {
                score.deductScore(5);
                System.out.println("Customer at (" + seatLocation.x + "," + seatLocation.y + "): " + " did not receive their order. 5 points deducted. New score: " + score.getTotalScore());
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
