package lingvo.movie.core.dao;

import lingvo.movie.core.entity.MediaItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by yaroslav on 10/18/15.
 */
public interface MediaItemRepository extends CrudRepository<MediaItem, Long> {
    @RestResource(exported = false)
    @Query(value = "SELECT * FROM media_item WHERE dictionary_id = ?1", nativeQuery = true)
    List<MediaItem> getAllByDictionaryId(Long dictionaryId);
}
