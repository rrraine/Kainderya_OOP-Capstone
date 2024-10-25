package main;

import interfaces.Importable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sound implements Importable {

    // ~ FIELDS -----------------------------------------------------------------
    Clip clip; // STORE AUDIO FILE
    List<URL> soundURL = new ArrayList<>(); // STORE SOUND PATH DIRECTORY
    FloatControl volume;

    // ~ METHODS -----------------------------------------------------------------

    // CONSTRUCTOR -----------------------------------------------------------------
    public Sound() {

        soundURL.add(0, importSound("sounds", "BlueBoyAdventure"));
        soundURL.add(1, importSound("sounds", "coin"));
        soundURL.add(2, importSound("sounds", "powerup"));
        soundURL.add(3, importSound("sounds", "unlock"));
        soundURL.add(4, importSound("sounds", "fanfare"));
    }

    // FROM THIS CLASS ------------------------------------------------------------
    public void setSound(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL.get(i));
            clip = AudioSystem.getClip();
            clip.open(ais);

            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {
            System.err.println("Trouble setting up audio (soundURL[" + i + "]): " + e.getMessage());
        }

    }
    public void playSound() {
        if (clip != null) {
            clip.start();
        }
    }
    public void loopSound() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    public void adjustSoundVolume(float vol) {
        if (volume != null) {
            volume.setValue(vol);
        }
    }
}