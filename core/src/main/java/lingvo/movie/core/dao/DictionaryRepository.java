package lingvo.movie.core.dao;

import lingvo.movie.core.entity.Dictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by yaroslav on 01.03.15.
 */
@Repository
@RepositoryRestResource(exported = false)
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
}
