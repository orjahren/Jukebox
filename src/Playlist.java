import java.util.ArrayList;

public class Playlist {
    ArrayList<Song> list;

    Playlist() {
        list = new ArrayList<Song>();
    }

    public void add(Song s) {
        list.add(s);
    }

    public Song get(int index) {
        return list.get(index);
    }

    public Song search(String key) {
        return null;
    }

    public boolean delete(Song s) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == s) {
                return this.delete(i);
            }
        }
        return false;
    }

    public boolean delete(int index) {
        if (list.get(index) != null) {
            list.remove(index);
            return true;
        }
        return false;
    }

    public void randomizeOrder() {
        ArrayList<Song> random = new ArrayList<Song>();
        ArrayList<Song> bak = new ArrayList<Song>(list);

        while (bak.size() != 0) {
            int rand = (int) Math.floor(Math.random() * bak.size());

            random.add(bak.get(rand));
            bak.remove(rand);
        }

        list = random;
    }
}
