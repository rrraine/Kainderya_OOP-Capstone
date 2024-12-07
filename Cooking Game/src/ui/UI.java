package ui;

import game.Time;
import interfaces.Drawable;
import interfaces.Importable;
import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public abstract class UI implements Drawable, Importable {

    // ~ FIELDS -----------------------------------------------------------------
    static GamePanel gp;
    static Time time;
    static Graphics2D g2;

    // FONTS
    static Font fredokaSemiBold;
    static Font luckiestGuy;
    static Font balooMedium;
    static Font paytoneOne;
    static Font balooBold;

    // TIME FORMATS
    static DecimalFormat timeFormat;

    // NOTIFICATIONS
    static String notif;
    static boolean notifOn;
    static int notifDuration;

    // GAME TERMINAL STATE
    static boolean isTerminal;

    // UI SUBSTATES
    static int command;

    // SHAKE EFFECT
    static Random random;

    // COLORS
    public static final Color transBlack = new Color(0,0,0, 175);
    public static Color transWhite = new Color(255, 255, 255, 185);
    public static Color primary = new Color( 255, 219, 75);
    public static Color primaryAccent = new Color(65, 52, 18);
    public static Color secondary = new Color(255, 75, 81);
    public static Color secondaryAccent;
    public static Color blue = new Color(75, 165, 255);
    public static Color orange = new Color(255, 171, 75);

    public static Color player1 = new Color(173, 227, 63);


    // CONSTRUCTOR -----------------------------------------------------------------
    public UI(GamePanel gp, Time time) {
        UI.gp = gp;
        UI.time = time;

        notifDuration = 0;
        notifOn = false;
        notif = "";
        isTerminal = false;
        timeFormat = new DecimalFormat("#0:00");

        // FONT SETUP
        fredokaSemiBold = importFont("Fredoka-SemiBold");
        luckiestGuy = importFont("LuckiestGuy-Regular");
        balooMedium = importFont("Baloo2-Medium");
        paytoneOne = importFont("PaytoneOne-Regular");
        balooBold = importFont("Baloo2-Bold");

        // OPTIONS UI
        command = 0;

        // SHAKE EFFECT
        random = new Random();
    }

    // FROM INTERFACE: DRAWABLE -----------------------------------------------------
    @Override
    public void update() {} // USELESS
    @Override
    public void draw(Graphics2D g2) {}

    // FROM THIS CLASS -------------------------------------------------------------
    static void drawCursor(Graphics2D g2, String text, int x, int y, boolean singleArrow, boolean underline) {

        List<Integer> coord = Utility.Aligner.centerCursor(text, x, y, gp, g2);

        if (singleArrow) { // SINGLE ARROW
            drawLetterBorder(g2, ">", Color.BLACK, 3, coord.get(0) + gp.tileSize - 25, coord.get(1));
            g2.setColor(primary);
            g2.drawString(">", coord.get(0) + gp.tileSize - 25, coord.get(1));
            g2.setStroke(new BasicStroke(1.2F));
        }
        else { // DOUBLE ARROW
            drawLetterBorder(g2, ">", Color.BLACK, 3, coord.get(0) + gp.tileSize/9, coord.get(1));
            drawLetterBorder(g2, "<", Color.BLACK, 3, coord.get(2), coord.get(3));
            g2.setColor(primary);
            g2.drawString(">", coord.get(0) + gp.tileSize/9, coord.get(1));
            g2.drawString("<", coord.get(2), coord.get(3));
            g2.setStroke(new BasicStroke(1.2F));
        }
        if (underline) // UNDERLINE
            g2.drawLine(coord.get(4), coord.get(5), coord.get(6), coord.get(7));
    }
    static void drawElement(Graphics2D g2, String text, int x, int y) {}
    public static void showNotification(String text) {
        notif = text;
        notifOn = true;
    }
    public static boolean freeze(int sec) {
        if (notifOn) {
            notifDuration++;

            if (notifDuration > (GamePanel.FPS * sec)) {
                notifDuration = 0;
                notifOn = false;
                return false;
            }
            return true;
        }
        return false;
    }
    static int shakeEffect(int intensity) {
        return random.nextInt(intensity * 2 + 1) - intensity;
    }
    static void drawPopUpWindow(Graphics2D g2, int x, int y, int width, int height) {

        g2.setColor(transBlack);

        // DRAW WINDOW
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    static void drawPopUpWindow(Graphics2D g2, int x, int y, int width, int height, Color window, Color border) {

        g2.setColor(window);

        // DRAW WINDOW
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(border);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    static void drawCircle(Graphics2D g2, int x, int y, int width, int height, Color bg, Color border) {

        g2.setColor(bg);

        // DRAW OVAL
        g2.fillOval(x, y, width, height);

        g2.setColor(border);
        g2.setStroke(new BasicStroke(5));
        g2.drawOval(x+5, y+5, width -10, height -10);
    }
    public static void drawLetterBorder(Graphics2D g2, String text, Color color, int thickness, int x, int y) {

        g2.setColor(color);
        for (int i = thickness *-1; i <= thickness; i++) {
            for (int j = thickness *-1; j <= thickness; j++) {
                if (i != 0 || j != 0) {
                    g2.drawString(text, x + i, y + j);
                }
            }
        }
    }

    // COMMAND GETTERS & SETTERS
    public static int getCommand() { return command; }
    public static void setCommand(int command) { UI.command = command; }

    // OTHER GETTERS & SETTERS
    public static void setNotif(String notif) {
        UI.notif = notif;
    }
    public static Font getStandardFont() {
        return balooBold;
    }
}