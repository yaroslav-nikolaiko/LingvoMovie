package lingvo.movie.core.dao;

import lingvo.movie.core.entity.MediaContent;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yaroslav on 18.10.15.
 */
public interface MediaContentRepository extends CrudRepository<MediaContent, Long> {
}
