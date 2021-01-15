import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
//demolink https://www.youtube.com/playlist?list=PLokwSdaX9hWIJHPu8P7dhCM7uPWMb5O7q
public class Program {

    static ArrayList<Playlist> playlists;
    static ArrayList<Song> songs;

    static AudioPlayer ap;

    static boolean playing = false;

    public static void main(String[] args) {
        System.out.println("Jukas v0.02");
        File conf = handleConfig();

        //if config.convertAll()
        convertAllTracks();
        

        songs = detectSongs();

        Scanner scanner = new Scanner(System.in);

        boolean ferdig = false;
        while(!ferdig) {
            ferdig = handleMenuLoop(scanner);
        }
    }

    private static ArrayList<Song> detectSongs() {
        ArrayList<Song> ret = new ArrayList<Song>();
        File folder;
        try {
            folder = new File("../tracks");
        }catch(Exception e) {
            System.out.println("Your system has not been set up correctly, exiting");
            throw new IllegalStateException();
        }

        String[] paths = folder.list();
        for(String s : paths) {
            System.out.println("Finner: " + s);
            Song song = new Song(s);
            ret.add(song);
        }
        return ret;
    }

    private static void printAllSongs() {
        for(int i = 0; i < songs.size(); i++) {
            Song s = songs.get(i);
            System.out.println("[" + i + "] " + s.filePath);
        }
    }

    private static void playSong(Song song) {
        if(playing) {
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

    private static boolean handleMenuLoop(Scanner scanner) {
        printMenuLoop();
        int inp = getIntInput(scanner);
    
        switch(inp) {
            case 0:
                //list all songs
                printAllSongs();
                //select song
                inp = getIntInput(scanner);

                playSong(songs.get(inp));
                System.out.println("Spiller av " + songs.get(inp));
                break;
            case 1:
                //toggle play/pause
                try {
                    ap.pause();
                }catch(NullPointerException e) {
                    System.out.println("You must start a song first");
                }
                break;
            case 2:
                downloadNew(scanner);
                break;
            case 3:
                return true;
            default:
                System.out.println("Your command was not recognized");
        }   return false;
    }

    private static File handleConfig() {
        try {
            File conf = new File("../config.ini");

            if(conf.createNewFile()) {
                initConfig(conf);
            }else {
                loadConfig();
            }

            return conf;
        }catch(Exception e) {
            //no config for you
            e.printStackTrace();
        }
        return null;
    }

    private static void convertAllTracks() {
        try {
            Runtime.getRuntime().exec("python convertFilesToWaw.py");
        }catch(Exception e) {
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
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        convertAllTracks();
        updateSongs();
    }

    private static void printMenuLoop() {
        System.out.println("[0] Select song \n[1] Play/Pause \n[2] Download new video or playlist \n[3] Quit");
    }

    private static void loadConfig() {

    }

    private static void initConfig(File file) {

    }
}
