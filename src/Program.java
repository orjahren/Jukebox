import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
//demolink https://www.youtube.com/playlist?list=PLokwSdaX9hWIJHPu8P7dhCM7uPWMb5O7q
public class Program {

    static ArrayList<Playlist> playlists;
    static ArrayList<Song> songs;
    public static void main(String[] args) {
        System.out.println("Jukas v0.01");

        File conf = handleConfig();

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
        AudioPlayer ap = new AudioPlayer();
        ap.play(song);
    }

    private static boolean handleMenuLoop(Scanner scanner) {
        printMenuLoop();
        int inp = scanner.nextInt();
    
        switch(inp) {
            case 0:
                //list all songs
                printAllSongs();
                //select song
                inp = scanner.nextInt();
                playSong(songs.get(inp));
                System.out.println("Spiller av " + songs.get(inp));
                break;
            case 1:
                //toggle play/pause
                break;
            case 2:
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

    private static void printMenuLoop() {
        System.out.println("[0] Select song \n[1] Play/Pause \n[2] Quit");
    }

    private static void loadConfig() {

    }

    private static void initConfig(File file) {

    }
}
