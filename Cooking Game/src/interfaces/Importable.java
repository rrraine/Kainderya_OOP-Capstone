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
    default BufferedImage importImage(String path, String structure, String type, String image, int tileSize) {

        BufferedImage importedImage = null;

        try (InputStream is = getClass().getResourceAsStream("/" + path + "/" + structure + "/" + type + "/" + image + ".png")) {

            if (is == null)
                throw new IOException("Resource not found: " + path + "/" + structure + "/" + type + "/" + image + ".png");

            importedImage = ImageIO.read(is);

            if (importedImage == null)
                throw new IOException("Failed to read: " + path + "/" + structure + "/" + type + "/" + image + ".png");

            importedImage = Utility.Scaler.scaleImage(importedImage, tileSize, tileSize);

            System.out.println(path + " (" + image + ") imported");

        } catch (IOException e) {
            System.err.println(e.getMessage() + " [Inspect: " + this.getClass() + "]");
        } catch (Exception e) {
            System.err.println("Unexpected error (" + path + "/" + structure + "/" + type + "/" + image + ".png): " + " [Inspect: " + this.getClass() + "]");
        }

        return importedImage;
    }
    default BufferedImage importWallpaper(String path, String category, String image) {

        BufferedImage importedImage = null;

        try (InputStream is = getClass().getResourceAsStream("/" + path + "/" + category + "/" + image + ".png")) {

            if (is == null)
                throw new IOException("Resource not found: " + path + "/" + category + "/" + image + ".png");

            importedImage = ImageIO.read(is);

            if (importedImage == null)
                throw new IOException("Failed to read: " + path + "/" + category + "/" + image + ".png");

            System.out.println(path + " (" + image + ") imported");

        } catch (IOException e) {
            System.err.println(e.getMessage() + " [Inspect: " + this.getClass() + "]");
        } catch (Exception e) {
            System.err.println("Unexpected error (" + path + "/" + category + "/" + image + ".png): " + " [Inspect: " + this.getClass() + "]");
        }


        return importedImage;
    }
    default URL importSound(String path, String audio) {

        URL sound = null;

        try {
            sound = getClass().getResource("/" + path + "/" + audio + ".wav");

            if (sound == null) {
                throw new Exception();
            }

            System.out.println("Sound (" + audio + ") imported");

        } catch (Exception e) {
            System.err.println("Trouble importing sound (" + audio + "): " + " [Inspect: " + this.getClass() + "]");
        }

        return sound;
    }
    default Font importFont(String font) {

        Font importedFont = null;

        try(InputStream is = getClass().getResourceAsStream("/fonts/" + font + ".ttf")) {
            importedFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is));

            System.out.println("Font (" + font + ") imported");

        } catch (IOException | FontFormatException | NullPointerException e) {
            System.err.println("Trouble importing font (" + font + "): " + " [Inspect: " + this.getClass() + "]");
        }

        return importedFont;
    }
}
