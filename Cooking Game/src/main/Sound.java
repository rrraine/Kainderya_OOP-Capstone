package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    // ~ FIELDS
    Clip clip; // STORE AUDIO FILE
    URL soundURL[] = new URL[30]; // STORE SOUND PATH DIRECTORY
    FloatControl volume;

    // ~ METHODS

    // IMPORT AUDIO AND STORE THEM IN AN ARRAY
    public Sound() {

        soundURL[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/cpr.wav");
    }

    // FORMAT TO OPEN AUDIO FILE
    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    public void adjustVolume(float vol) {
        if (volume != null) {
            volume.setValue(vol);
        }
    }
}