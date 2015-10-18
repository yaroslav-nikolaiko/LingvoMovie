package lingvo.movie.core.dao;

import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.MediaItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lingvo.movie.core.utils.EntityFactory.contentMedias;
import static lingvo.movie.core.utils.EntityFactory.mediaItems;
import static org.junit.Assert.*;

/**
 * Created by yaroslav on 18.10.15.
 */
public class MediaItemRepositoryTest extends AbstractRepositoryTest{
    @Autowired MediaItemRepository mediaItemRepository;
    @Autowired ContentMediaRepository contentMediaRepository;

    @Test
    public void saveMediaContentFirstAndThanMediaItems() throws Exception {
        MediaItem item = mediaItems().get(0);
        item.setDictionary(admin.getDictionaries().get(0));
        ContentMedia content = contentMedias().get(0);

        content = contentMediaRepository.save(content);
        item.setContentMedia(content);
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
        Long contentMediaId = item.getContentMedia().getId();

        mediaItemRepository.delete(item.getId());

        em.flush();
        em.clear();

        assertNotNull("Media Content should still be present in DB", contentMediaRepository.findOne(contentMediaId));
    }
}