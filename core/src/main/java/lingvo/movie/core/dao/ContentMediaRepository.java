package lingvo.movie.core.dao;

import lingvo.movie.core.entity.ContentMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yaroslav on 18.10.15.
 */
@Repository
public interface ContentMediaRepository extends CrudRepository<ContentMedia, Long> {
}
