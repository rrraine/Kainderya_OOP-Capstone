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

    private Point[] seatLocations =  {
            new Point(4, 5), new Point(4, 6), new Point(4, 7), new Point(4, 8),
            new Point(10, 11), new Point(11, 11), new Point(11, 12), new Point(11, 13),
            new Point(14, 11)

    };

    public ShopManager(GamePanel gp) {
        this.gp = gp;
        waitingQueue = new LinkedList<>();
        // seatedCustomers = new NPC_Customer[seatLocations.length];
        seatedCustomers = new NPC_Customer[gp.getMaxCustomers()];
        freeRoamingNPCs = new ArrayList<>();
    }

    public void addCustomer(NPC_Customer customer){
        waitingQueue.add(getRandomCustomer());
    }

    public void addFreeRoamingNPC(NPC npc){
        freeRoamingNPCs.add(npc);
    }

    // Generate customers and free-roaming NPCs
    public void generateNPCs() {
        Random random = new Random();

        // Generate customers and assign seats
        for (int i = 0; i < gp.getMaxCustomers(); i++) {
            NPC_Customer customer = getRandomCustomer();
            Point seat = seatLocations[random.nextInt(seatLocations.length)];
            customer.assignSeat(seat);
            gp.getNpc().add(customer);  // Add customer to the general npc list
            gp.getAssetPool().add(customer);  // Add customer to asset pool
        }

        // Generate free-roaming NPCs
        for (int i = 0; i < gp.getMaxRoamers(); i++) {
            NPC freeRoamingNPC = getRandomNPC();
            gp.getNpc().add(freeRoamingNPC);  // Add free-roaming NPC to the general npc list
            freeRoamingNPCs.add(freeRoamingNPC);  // Add to the freeRoamingNPCs list
            gp.getAssetPool().add(freeRoamingNPC);  // Add to asset pool
        }
    }

    public void update(){
        // seat the waiting customers when a seat is available
        for (int i = 0; i < seatedCustomers.length; i++) {
            if (seatedCustomers[i] == null && !waitingQueue.isEmpty()) {
                // poll a customer from the waiting queue
                NPC_Customer customer = waitingQueue.poll();
                // assign the customer to the seat
                customer.assignSeat(seatLocations[i]);
                seatedCustomers[i] = customer;
            }
        }

        // update the seated customers patience timer and handle them leaving
        for (int i = 0; i <seatedCustomers.length; i++) {
            NPC_Customer customer = seatedCustomers[i];
            if (customer != null && customer.reducePatienceTimer()){
                // if customer has no patience, they leave their seat
                seatedCustomers[i] = null;
                customer.leaveSeat();
            }
        }

        for (NPC npc : freeRoamingNPCs) {
            npc.update();
        }
    }

    public List<NPC> getNPCs() {
        List<NPC> npcs = new ArrayList<>();
        npcs.addAll(waitingQueue);
        Collections.addAll(npcs, seatedCustomers);
        npcs.addAll(freeRoamingNPCs);
        npcs.removeIf(Objects::isNull);
        return npcs;
    }

    public Point[] getSeatLocations() {
        return seatLocations;
    }

    private NPC_Customer getRandomCustomer(){
        Random random = new Random();
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

    private NPC getRandomNPC(){
        Random random = new Random();
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


    public int getDefaultX() {
        Random rand = new Random();
        return rand.nextInt(25);  // Generate a random X position within a valid range
    }
    public int getDefaultY() {
        Random rand = new Random();
        return rand.nextInt(15);  // Generate a random Y position within a valid range
    }


    // PUBLIC GETTERS

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

    public List<NPC> getAllNPCs(){
        List<NPC> allNPCs = new ArrayList<>();
        allNPCs.addAll(waitingQueue);
        allNPCs.addAll(freeRoamingNPCs);
        allNPCs.addAll(getSeatedCustomers());
        for(NPC npc : allNPCs){
            System.out.println(npc.getClass());
        }
        return allNPCs;
    }
}