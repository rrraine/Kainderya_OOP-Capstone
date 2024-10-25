package interfaces;

import java.awt.*;

public interface Drawable {
    // FOR CLASSES THAT REQUIRE TO BE CONSTANTLY UPDATED AND DRAWN EVERY FRAME

    void update();
    void draw(Graphics2D g2);
}
