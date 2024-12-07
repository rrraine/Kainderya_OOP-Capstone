package entity;

import main.GamePanel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ShopManager {
    GamePanel gp;
    private Queue<NPC_Customer> waitingQueue;
    private NPC_Customer[] seatedCustomers;
    private List<NPC> freeRoamingNPCs;
    private Random random = new Random(); // Reuse the same Random instance

    private Point[] seatLocations = {
            new Point(4, 5), new Point(4, 6), new Point(4, 9), new Point(4, 8),
            new Point(10, 11), new Point(11, 11), new Point(12, 11), new Point(13, 11),
            new Point(14, 11)
    };

    public ShopManager(GamePanel gp) {
        this.gp = gp;
        waitingQueue = new LinkedList<>();
        seatedCustomers = new NPC_Customer[seatLocations.length];
        freeRoamingNPCs = new ArrayList<>();
    }

    public void addCustomer(NPC_Customer customer) {
        waitingQueue.add(customer);
    }

    public void addFreeRoamingNPC(NPC npc) {
        freeRoamingNPCs.add(npc);
    }

    // Generate customers and free-roaming NPCs
    public void generateNPCs() {
        Set<Point> occupiedSeats = new HashSet<>();
        System.out.println("Generating NPCs");

        // Generate customers and assign seats
        for (int i = 0; i < gp.getMaxCustomers(); i++) {
            NPC_Customer customer = getRandomCustomer();

            Point seat;
            do {
                seat = seatLocations[random.nextInt(seatLocations.length)];
            } while (occupiedSeats.contains(seat));
            occupiedSeats.add(seat);
            customer.assignSeat(seat);
            addCustomer(customer);
            gp.getNpc().add(customer);  // Add customer to the general npc list
            //gp.getAssetPool().add(customer);  // Add customer to asset pool
           // System.out.println("Generated customer: " + customer.getClass().getSimpleName());
        }

        // Generate free-roaming NPCs
        for (int i = 0; i < gp.getMaxRoamers(); i++) {
            NPC freeRoamingNPC = getRandomNPC();

            gp.getNpc().add(freeRoamingNPC);  // Add free-roaming NPC to the general npc list
            freeRoamingNPCs.add(freeRoamingNPC);  // Add to the freeRoamingNPCs list
            gp.getAssetPool().add(freeRoamingNPC);  // Add to asset pool
            //System.out.println("Generated freeRoamingNPC: " + freeRoamingNPC.getClass().getSimpleName());
        }

        System.out.println("Customer in waiting queue: " + occupiedSeats.size());
        System.out.println("Free roaming npcs: " + freeRoamingNPCs.size());;
    }

    public void update() {
        // Seat waiting customers when seats are available
        for (int i = 0; i < seatedCustomers.length; i++) {
            if (seatedCustomers[i] == null && !waitingQueue.isEmpty()) {
                NPC_Customer customer = waitingQueue.poll();
                customer.assignSeat(seatLocations[i]);
                seatedCustomers[i] = customer;
            }
        }

        // Update seated customers and handle those leaving
        for (int i = 0; i < seatedCustomers.length; i++) {

            NPC_Customer customer = seatedCustomers[i];

            if (customer != null) {
                if (customer.isMovingToSeat()) {
                    customer.moveToSeat(); // Update their position
                } else if (customer.isSeated() && customer.reducePatienceTimer()) {
                    if (customer.reducePatienceTimer()) {
                        seatedCustomers[i] = null; // Seat is now available
                        customer.leaveSeat(); // Customer leaves
                        System.out.println("Customer left seat at: " + seatLocations[i]);

                        // Generate a new customer to fill the seat
                        generateNewCustomerForSeat(i);
                    }
                }
            }
        }

        // Update free-roaming NPCs
        for (NPC npc : freeRoamingNPCs) {
            if (npc instanceof NPC_FreeRoaming) {
                npc.update();
            }
        }
    }


    public Point[] getSeatLocations() {
        return seatLocations;
    }

    // return a new instance of NPC_Customer
    private NPC_Customer getRandomCustomer() {
        int npcTypeIndex = random.nextInt(4);

        NPC customerNPC = switch (npcTypeIndex) {
            case 0 -> new NPC.StudentMale(gp);
            case 1 -> new NPC.StudentFemale(gp);
            case 2 -> new NPC.civilianFemale1(gp);
            case 3 -> new NPC.Tambay1(gp);
            default -> new NPC.StudentMale(gp);
        };

        return new NPC_Customer(gp, customerNPC);
    }

    // return a new NPC_Free Roaming
    private NPC getRandomNPC() {
        int npcTypeIndex = random.nextInt(4);

        NPC randomNPC = switch (npcTypeIndex) {
            case 0 -> new NPC.StudentMale(gp);
            case 1 -> new NPC.StudentFemale(gp);
            case 2 -> new NPC.civilianFemale1(gp);
            case 3 -> new NPC.Tambay1(gp);
            default -> new NPC.StudentMale(gp);
        };

        return new NPC_FreeRoaming(gp, randomNPC);
    }



    // GETTERS AND SETTERS --------------------------------------

     public int getDefaultX() {
        return random.nextInt(25);  // Generate a random X position within a valid range
    }

    public int getDefaultY() {
        return random.nextInt(15);  // Generate a random Y position within a valid range
    }

    public Queue<NPC_Customer> getWaitingQueue() {
        return waitingQueue;
    }

    public List<NPC> getFreeRoamingNPCs() {
        return freeRoamingNPCs;
    }

    public List<NPC_Customer> getSeatedCustomers() {
        List<NPC_Customer> seated = new ArrayList<>();
        for (NPC_Customer customer : seatedCustomers) {
            if (customer != null) {
                seated.add(customer);
            }
        }
        return seated;
    }

}
