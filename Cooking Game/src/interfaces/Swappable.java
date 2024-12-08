package interfaces;

import object.SuperObject;

import java.awt.image.BufferedImage;

public interface Swappable {

    void swapImage(String key);
    boolean checkCurrentImage(String key, Pickupable obj);
}
