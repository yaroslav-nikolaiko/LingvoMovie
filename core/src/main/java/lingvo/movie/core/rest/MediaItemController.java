package lingvo.movie.core.rest;

import lingvo.movie.core.dao.MediaItemRepository;
import lingvo.movie.core.entity.dto.MediaItemsTreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        return toTreeView(mediaItemRepository.getAllByDictionaryId(dictionaryId));
    }
}
