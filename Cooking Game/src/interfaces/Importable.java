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
    default BufferedImage importImage(String path, String type, String image, int tileSize) {

        BufferedImage importedImage = null;

        try (InputStream is = getClass().getResourceAsStream("/" + path + "/" + type + "/" + image + ".png")) {

            if (is == null)
                throw new IOException("Resource not found: " + path + "/" + image + ".png");

            importedImage = ImageIO.read(Objects.requireNonNull(is));

            if (importedImage == null)
                throw new IOException("Failed to read: " + path + "/" + image + ".png");

            importedImage = Utility.Scaler.scaleImage(importedImage, tileSize, tileSize);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Illegal argument / attempt to pass null: " + path + "/" + image + ".png");
        } catch (Exception e) {
            System.err.println("Unexpected error (" + image + "): " + e.getMessage());
            e.printStackTrace();
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
            importedFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is));

        } catch (IOException | FontFormatException | NullPointerException e) {
            System.err.println("Trouble importing font (" + font + "): " + e.getMessage());
        }

        System.out.println("Font (" + font + ") imported");
        return importedFont;
    }
}
