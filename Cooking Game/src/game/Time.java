package game;

import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Time implements Drawable {

    private static Time instance;

    List<Double> defaults; // STORES DEFAULT VALUES

    GamePanel gp;
    DecimalFormat timeFormat;

    private static double timer;

    private double patience;
    private double customerEating;

    private double chopping;
    private double cooking;
    private double cooking_burnt;
    private double riceCooking;
    private double washing;
    private double fillingDrink;


    // CONSTRUCTOR -------------------------------------------------------------------
    private Time(GamePanel gp) {
        this.gp = gp;

        defaults = new ArrayList<>();
        double minutes, seconds;

        // SET TIMER
        minutes = 5.01;
        timer = GamePanel.FPS * (minutes * 60);
        defaults.add(timer);

        // SET CUSTOMER TIME
        seconds = 20;
        patience = GamePanel.FPS * seconds;
        defaults.add(patience);

        seconds = 16;
        customerEating = GamePanel.FPS * seconds;
        defaults.add(customerEating);

        // SET STATION TIME
        seconds = 4;
        chopping = GamePanel.FPS * seconds;
        defaults.add(chopping);

        seconds = 8;
        cooking = GamePanel.FPS * seconds;
        defaults.add(cooking);

        seconds = 2;
        cooking_burnt = GamePanel.FPS * seconds;
        defaults.add(cooking_burnt);

        seconds = 4;
        riceCooking = GamePanel.FPS * seconds;
        defaults.add(riceCooking);

        seconds = 5;
        washing = GamePanel.FPS * seconds;
        defaults.add(washing);

        seconds = 4;
        fillingDrink = GamePanel.FPS * seconds;
        defaults.add(fillingDrink);
    }
    // SINGLETON INSTANTIATE --------------------------------------------------
    public static Time instantiate(GamePanel gp) {
        if (instance == null) {
            instance = new Time(gp);
        }
        return instance;
    }
    public void reinitialize() {

        try {
            if (instance == null)
                throw new NullPointerException("Initialize an instance of (Time) first!");
        } catch (NullPointerException e) {
            e.getMessage();
        }

        timer = defaults.get(0);
        patience = defaults.get(1);
        customerEating = defaults.get(2);
        chopping = defaults.get(3);
        cooking = defaults.get(4);
        cooking_burnt = defaults.get(5);
        riceCooking = defaults.get(6);
        washing = defaults.get(7);
        fillingDrink = defaults.get(8);

    }

    // FROM INTERFACE: DRAWABLE -------------------------------------------------
    @Override
    public void update() {

        timer--;
        if (timer < 0) {
            gp.gameState = GamePanel.state.TERMINAL;
            timer = 0;
        }
    }
    @Override
    public void draw(Graphics2D g2) {} // USELESS


    // FROM THIS CLASS ----------------------------------------------------------

    // GETTERS -------------------------------------------------------------------
    public static String getTimer() {

        // CONVERT TO FORMATTED TIME
        double remSeconds = timer / GamePanel.FPS;

        int min = (int) (remSeconds / 60);
        int sec = (int) (remSeconds % 60);

        return String.format("%01d:%02d", min, sec);
    }
}
