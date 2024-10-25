package interfaces;

import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

public interface Importable {
    // FOR CLASSES THAT REQUIRE IMPORTING OF EXTERNAL FILES AND RESOURCES

    default BufferedImage importImage(String path, String image, int tileSize) {

        BufferedImage importedImage = null;

        try {
            importedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + path + "/" + image + ".png")));
            Utility.scaleImage(importedImage, tileSize, tileSize);
        }
        catch (Exception e) {
            System.err.println("Trouble importing image (" + image + "): " + e.getMessage());
        }

        System.out.println("Image (" + image + ") successfully imported");
        return importedImage;
    }

    default URL importSound(String path, String audio) {

        URL sound = null;

        try {
            sound = getClass().getResource("/" + path + "/" + audio + ".wav");

            if (sound == null) {
                throw new Exception();
            }

        } catch (Exception e) {
            System.err.println("Trouble importing sound (" + audio + "): " + e.getMessage());
        }

        System.out.println("Sound (" + audio + ") successfully imported");
        return sound;
    }
}
