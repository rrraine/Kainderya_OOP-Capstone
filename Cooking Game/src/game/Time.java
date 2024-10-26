package game;

import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.text.DecimalFormat;

public class Time implements Drawable {

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
    public Time(GamePanel gp) {
        this.gp = gp;

        double minutes, seconds;

        // SET TIMER
        minutes = 5.01;
        timer = GamePanel.FPS * (minutes * 60);

        // SET CUSTOMER TIME
        seconds = 20;
        patience = GamePanel.FPS * seconds;

        seconds = 16;
        customerEating = GamePanel.FPS * seconds;

        // SET STATION TIME
        seconds = 4;
        chopping = GamePanel.FPS * seconds;

        seconds = 8;
        cooking = GamePanel.FPS * seconds;

        seconds = 2;
        cooking_burnt = GamePanel.FPS * seconds;

        seconds = 4;
        riceCooking = GamePanel.FPS * seconds;

        seconds = 5;
        washing = GamePanel.FPS * seconds;

        seconds = 4;
        fillingDrink = GamePanel.FPS * seconds;
    }

    // FROM INTERFACE: DRAWABLE -------------------------------------------------
    @Override
    public void update() {

        timer--;
        if (timer < 0) {
            timer = 0;
        }
    }
    @Override
    public void draw(Graphics2D g2) {} // DRAWN IN CLASS UI INSTEAD


    // FROM THIS CLASS ----------------------------------------------------------
    public static String getTimer() {

        // CONVERT TO FORMATTED TIME
        double remSeconds = timer / GamePanel.FPS;

        int min = (int) (remSeconds / 60);
        int sec = (int) (remSeconds % 60);

        return String.format("%01d:%02d", min, sec);
    }


    // GETTERS -------------------------------------------------------------------
}
