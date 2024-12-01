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

    public ShopManager() {
        waitingQueue = new LinkedList<>();
        seatedCustomers = new NPC_Customer[seatLocations.length];
        freeRoamingNPCs = new ArrayList<>();
    }

    public void addCustomer(NPC_Customer customer){
        waitingQueue.add(getRandomCustomer());
    }

    public void addFreeRoamingNPC(NPC npc){
        freeRoamingNPCs.add(npc);
    }

    public void update(){
        for (int i = 0; i < seatedCustomers.length; i++) {
            if (seatedCustomers[i] == null && !waitingQueue.isEmpty()) {
                NPC_Customer customer = waitingQueue.poll();
                customer.assignSeat(seatLocations[i]);
                seatedCustomers[i] = customer;
            }
        }

        for (int i = 0; i <seatedCustomers.length; i++) {
            NPC_Customer customer = seatedCustomers[i];
            if (customer != null && customer.reducePatienceTimer()){
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

        return randomNPC;
    }

    public Point[] getSeatLocations() {
        return seatLocations;
    }


    public int getDefaultX() {
        Random rand = new Random();
        return rand.nextInt(25);  // Generate a random X position within a valid range
    }


    public int getDefaultY() {
        Random rand = new Random();
        return rand.nextInt(15);  // Generate a random Y position within a valid range
    }
}