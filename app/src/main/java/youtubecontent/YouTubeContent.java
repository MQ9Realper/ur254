package youtubecontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dennismwebia on 31/10/16.
 */
public class YouTubeContent {

    /**
     * An array of YouTube videos
     */
    public static List<YouTubeVideo> ITEMS = new ArrayList<>();

    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();

    static {
        addItem(new YouTubeVideo("tJFbnYi6m2k", "Jubilee Party launch"));
        addItem(new YouTubeVideo("thFJmjv2GeQ", "Jubilee Anthem 2016"));
        addItem(new YouTubeVideo("7PSr748tT7A", "Jubilee Party Launch"));
        addItem(new YouTubeVideo("6__1gqIOkrE", "Jubilee Rally in Mathare"));
        addItem(new YouTubeVideo("TB2VAuVnKkU", "Jubilee Party Launch"));
        addItem(new YouTubeVideo("TbONOeT6hMg", "Jubilee Grand Merger"));
    }

    private static void addItem(final YouTubeVideo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;

        public YouTubeVideo(String id, String content) {
            this.id = id;
            this.title = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
