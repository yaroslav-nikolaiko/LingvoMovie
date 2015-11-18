package lingvo.movie.core.dao;

import lingvo.movie.core.entity.MediaItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yaroslav on 10/18/15.
 */
@Repository
public interface MediaItemRepository extends CrudRepository<MediaItem, Long> {
    String URL_PATH = "mediaItems";

    @RestResource(exported = false)
    @Query(value = "SELECT * FROM media_item WHERE dictionary_id = ?1", nativeQuery = true)
    List<MediaItem> getAllByDictionaryId(Long dictionaryId);
}
