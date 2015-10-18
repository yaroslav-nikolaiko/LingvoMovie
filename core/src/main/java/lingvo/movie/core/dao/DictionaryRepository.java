package lingvo.movie.core.dao;

import lingvo.movie.core.entity.Dictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by yaroslav on 01.03.15.
 */
@RepositoryRestResource(exported = false)
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
}
