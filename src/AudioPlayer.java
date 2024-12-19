import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//based off of https://www.geeksforgeeks.org/play-audio-file-using-java/
public class AudioPlayer {

    Long currentFrame;
    Clip clip;

    boolean isPaused;

    AudioInputStream as;

    AudioPlayer(Song song) {
        final File file = song.getFile();
        try {
            as = AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            clip = AudioSystem.getClip();

            clip.open(as);
        } catch (Exception e) {
            System.err.println("Fatal error in AudioPlayer");
            e.printStackTrace();
        }
    }

    public void togglePlay() {
        if (isPaused)
            clip.start();
        else
            clip.stop();

        isPaused = !isPaused;
    }

    public void stop() {
        clip.stop();
        try {
            as.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (isPaused) {
            clip.start();
            isPaused = false;
        } else {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
            isPaused = false;
        }
    }
}
