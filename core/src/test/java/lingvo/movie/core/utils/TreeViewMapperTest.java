package lingvo.movie.core.utils;

import lingvo.movie.core.entity.MediaItem;
import lingvo.movie.core.entity.dto.MediaItemsTreeView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static lingvo.movie.core.utils.TreeViewMapper.getFirstFolder;
import static lingvo.movie.core.utils.TreeViewMapper.getNextFolder;
import static lingvo.movie.core.utils.TreeViewMapper.toTreeView;
import static org.junit.Assert.*;

/**
 * Created by yaroslav on 10/17/15.
 */
public class TreeViewMapperTest {
    @Test
    public void testGetFirstFolder() throws Exception {
        String path1 = "/movies/interstellar";
        String path2 = "/movies/interstellar/";
        String path3 = "movies/interstellar/";
        String path4 = "movies";
        String path5 = "/movies";
        String path6 = "/movies/";
        String path7 = "movies/";

        assertEquals("movies", getFirstFolder(path1));
        assertEquals("movies", getFirstFolder(path2));
        assertEquals("movies", getFirstFolder(path3));
        assertEquals("movies", getFirstFolder(path4));
        assertEquals("movies", getFirstFolder(path5));
        assertEquals("movies", getFirstFolder(path6));
        assertEquals("movies", getFirstFolder(path7));
    }

    @Test
    public void testGetNextFolder() throws Exception {
        String currentPath1 = "/movies", fullPath1 = "/movies/blockbusters/episodes/";
        String currentPath2 = "/movies", fullPath2 = "/movies/blockbusters/episodes";
        String currentPath3 = "movies", fullPath3 = "movies/blockbusters/episodes";
        String currentPath4 = "movies/blockbusters", fullPath4 = "movies/blockbusters/episodes";

        assertEquals("/movies/blockbusters", getNextFolder(currentPath1, fullPath1));
        assertEquals("/movies/blockbusters", getNextFolder(currentPath2, fullPath2));
        assertEquals("movies/blockbusters", getNextFolder(currentPath3, fullPath3));
        assertEquals("movies/blockbusters/episodes", getNextFolder(currentPath4, fullPath4));
    }

    @Test
    public void testToTreeView() throws Exception {
        List<MediaItem> items = new ArrayList<>();
        items.add(mediaItem("HIMYM/season 1","1 episode"));
        items.add(mediaItem("HIMYM/season 1","episode 2"));
        items.add(mediaItem("HIMYM/season 1","episode 3"));
        items.add(mediaItem("HIMYM/season 2","1 episode"));
        items.add(mediaItem("HIMYM/season 2","2 episode"));
        items.add(mediaItem("TVShows/entertainment/nightShow","first"));
        items.add(mediaItem("TVShows/entertainment/nightShow","second"));
        items.add(mediaItem("movies","Interstellar"));

        List<MediaItemsTreeView> treeView = toTreeView(items);
        assertEquals(3, treeView.size());
        assertEquals(2, treeView.get(0).getNodes().size());
        assertEquals(3, treeView.get(0).getNodes().get(0).getNodes().size());
        assertEquals("first", treeView.get(1).getNodes().get(0).getNodes().get(0).getNodes().get(0).getItem().getName());
    }

    MediaItem mediaItem(String displayPath, String name) {
        MediaItem item = new MediaItem();
        item.setDisplayPath(displayPath);
        item.setName(name);
        return item;
    }
}