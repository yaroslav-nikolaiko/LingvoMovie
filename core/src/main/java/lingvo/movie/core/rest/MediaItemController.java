package lingvo.movie.core.rest;

import lingvo.movie.core.dao.MediaItemRepository;
import lingvo.movie.core.entity.MediaItem;
import lingvo.movie.core.entity.dto.MediaItemsTreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static lingvo.movie.core.utils.TreeViewMapper.toTreeView;

/**
 * Created by yaroslav on 10/17/15.
 */
@RestController
public class MediaItemController {
    @Autowired
    MediaItemRepository mediaItemRepository;


    @RequestMapping(value = "users/{userId}/dictionaries/{dictionaryId}/mediaItems", method = RequestMethod.GET)
    public List<MediaItemsTreeView> getItems(@PathVariable Long userId, @PathVariable Long dictionaryId) {
        //return toTreeView(mediaItemRepository.getAllByDictionaryId(dictionaryId));
        List<MediaItem> items = new ArrayList<>();
        items.add(mediaItem("HIMYM/season 1","1 episode"));
        items.add(mediaItem("HIMYM/season 1","episode 2"));
        items.add(mediaItem("HIMYM/season 1","episode 3"));
        items.add(mediaItem("HIMYM/season 2","1 episode"));
        items.add(mediaItem("HIMYM/season 2","2 episode"));
        items.add(mediaItem("TVShows/entertainment/nightShow","first"));
        items.add(mediaItem("TVShows/entertainment/nightShow","second"));
        items.add(mediaItem("movies","Interstellar"));

        return toTreeView(items);
    }

    MediaItem mediaItem(String displayPath, String name) {
        MediaItem item = new MediaItem();
        item.setDisplayPath(displayPath);
        item.setName(name);
        return item;
    }
}
