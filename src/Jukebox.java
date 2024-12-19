import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

//demolink https://www.youtube.com/playlist?list=PLokwSdaX9hWIJHPu8P7dhCM7uPWMb5O7q
public class Jukebox {

    static ArrayList<Playlist> playlists;
    static ArrayList<Song> songs;

    static AudioPlayer ap;

    static boolean playing = false;

    public static final String TRACKS_PATH = "../tracks";

    public static void main(String[] args) {
        File conf = handleConfig();

        // if config.convertAll()
        convertAllTracks();

        songs = detectSongs();

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Jukas v0.03");
        } while (handleMenuLoop(scanner));

        System.out.println("Goodbye :)");
    }

    private static boolean handleMenuLoop(Scanner scanner) {

        // TODO: Implement type safety between command list and switch stmt.
        final String[] commands = { "Select song", "Play/Pause", "Download new video or playlist", "Quit" };
        for (int i = 0; i < commands.length; i++) {
            System.out.println("\t[" + i + "] " + commands[i]);
        }

        final int command = getIntInput(scanner);

        switch (command) {
            case 0:
                // list all songs
                printAllSongs();
                // select song
                final int songIdx = getIntInput(scanner);

                playSong(songs.get(songIdx));
                System.out.println("Spiller av " + songs.get(songIdx));
                break;
            case 1:
                // toggle play/pause
                try {
                    ap.togglePlay();
                } catch (NullPointerException e) {
                    System.out.println("You must start a song first");
                }
                break;
            case 2:
                downloadNew(scanner);
            case 3:
                return false;
            default:
                System.out.println("Your command was not recognized");
        }
        return true;
    }

    private static ArrayList<Song> detectSongs() {
        ArrayList<Song> ret = new ArrayList<Song>();
        File folder;
        try {
            folder = new File(TRACKS_PATH);
        } catch (Exception e) {
            System.out.println("Your system has not been set up correctly, exiting");
            throw new IllegalStateException();
        }

        String[] paths = folder.list();
        for (String s : paths) {
            System.out.println("Finner: " + s);
            Song song = new Song(s);
            ret.add(song);
        }
        return ret;
    }

    private static void printAllSongs() {
        for (int i = 0; i < songs.size(); i++) {
            Song s = songs.get(i);
            System.out.println("\t[" + i + "] " + s.filePath);
        }
    }

    private static void playSong(Song song) {
        if (playing) {
            ap.stop();
            playing = false;
        }

        ap = new AudioPlayer(song);
        ap.play();
        playing = true;

    }

    private static int getIntInput(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    // TODO Implement config system.
    private static File handleConfig() {
        try {
            File conf = new File("../config.ini");

            if (conf.createNewFile()) {
                initConfig(conf);
            } else {
                loadConfig();
            }

            return conf;
        } catch (Exception e) {
            // no config for you
            e.printStackTrace();
        }
        return null;
    }

    private static void convertAllTracks() {
        try {
            Runtime.getRuntime().exec("python convertFilesToWaw.py");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateSongs() {
        songs = detectSongs();
    }

    private static void downloadNew(Scanner scanner) {
        System.out.print("\tLink to youtube download: $");
        String link = scanner.nextLine();
        System.out.println(link);
        try {
            Runtime.getRuntime().exec(("python downloadFromYoutube.py " + link));
        } catch (Exception e) {
            e.printStackTrace();
        }

        convertAllTracks();
        updateSongs();
    }

    private static void loadConfig() {

    }

    private static void initConfig(File file) {

    }
}
