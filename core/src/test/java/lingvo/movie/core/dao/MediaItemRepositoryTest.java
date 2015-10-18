package lingvo.movie.core.dao;

import lingvo.movie.core.entity.MediaContent;
import lingvo.movie.core.entity.MediaItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static lingvo.movie.core.utils.EntityFactory.mediaContents;
import static lingvo.movie.core.utils.EntityFactory.mediaItems;
import static org.junit.Assert.*;

/**
 * Created by yaroslav on 18.10.15.
 */
public class MediaItemRepositoryTest extends AbstractRepositoryTest{
    @Autowired MediaItemRepository mediaItemRepository;
    @Autowired MediaContentRepository mediaContentRepository;

    @Test
    public void saveMediaContentFirstAndThanMediaItems() throws Exception {
        MediaItem item = mediaItems().get(0);
        MediaContent content = mediaContents().get(0);

        content = mediaContentRepository.save(content);
        item.setMediaContent(content);
        item = mediaItemRepository.save(item);

        em.flush();

        assertNotNull("Media Item Id should not be null",item.getId());
        assertNotNull("Media Content Id should not be null",content.getId());
    }

    @Test
    public void deletingMediaItemShouldNotDeleteMediaContent() throws Exception {
        saveMediaContentFirstAndThanMediaItems();

        Iterable<MediaItem> all = mediaItemRepository.findAll();
        MediaItem item = all.iterator().next();
        Long contentMediaId = item.getMediaContent().getId();

        mediaItemRepository.delete(item.getId());

        em.flush();
        em.clear();

        assertNotNull("Media Content should still be present in DDB", mediaContentRepository.findOne(contentMediaId));
    }
}