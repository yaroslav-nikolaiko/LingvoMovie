package lingvo.movie.core.dao;

import lingvo.movie.core.entity.Dictionary;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yaroslav on 01.03.15.
 */
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
}
