import java.io.File;

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

//based off of https://www.geeksforgeeks.org/play-audio-file-using-java/
public class AudioPlayer {
    
    Long currentFrame; 
    Clip clip; 
      
    String status; 

    AudioInputStream as;

    AudioPlayer(Song song) {
        File file = song.getFile();
        try {
            as = AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            clip = AudioSystem.getClip();

            clip.open(as);
        }catch(Exception e) {
            System.err.println("Fatal error in AudioPlayer");
            e.printStackTrace();
            
        }
        

        
    }

    public void pause() {
        if(status == "playing") {
            status = "paused";
            clip.stop();
        }else {
            status = "playing";
            clip.start();
        }
        
    }

    public void stop() {
        clip.stop();
            try {
                as.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
    }

    public void play() {
        if(status == "playing") {
            
            
        }
        clip.start();

        status = "playing";
        
    }
}
