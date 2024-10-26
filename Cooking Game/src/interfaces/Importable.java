package interfaces;

import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public interface Importable {
    // FOR CLASSES THAT REQUIRE IMPORTING OF EXTERNAL FILES AND RESOURCES

    // PRESET FORMATS
    default BufferedImage importImage(String path, String image, int tileSize) {

        BufferedImage importedImage = null;

        try {
            importedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/" + path + "/" + image + ".png")));
            importedImage = Utility.scaleImage(importedImage, tileSize, tileSize);
        }
        catch (Exception e) {
            System.err.println("Trouble importing image (" + image + "): " + e.getMessage());
        }

        System.out.println("Tile (" + image + ") imported");
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

        System.out.println("Sound (" + audio + ") imported");
        return sound;
    }
    default Font importFont(String font) {

        Font importedFont = null;

        try(InputStream is = getClass().getResourceAsStream("/fonts/" + font + ".ttf")) {
            assert is != null;
            importedFont = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (IOException | FontFormatException e) {
            System.err.println("Trouble importing font (" + font + "): " + e.getMessage());
        }

        System.out.println("Font (" + font + ") imported");
        return importedFont;
    }
}
