

public class AudioPlayer {
    Runtime rt;
    Process pr;

    AudioPlayer() {
        rt = Runtime.getRuntime();
    }

    public void play(Song song) {
        try {
            pr = rt.exec("mplayer " + song.filePath);
            pr.getOutputStream();
            System.out.println("Sang skal være spilende");
            
            pr.waitFor();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
}
