import java.io.File;

public class Song {
    String title;
    String name;
    String duration;
    String filePath;
    File file;

    Song(String relPath) {
        file = new File(Jukebox.TRACKS_PATH + "/" + relPath);
        filePath = relPath;
    }

    public File getFile() {
        return file;
    }
}
