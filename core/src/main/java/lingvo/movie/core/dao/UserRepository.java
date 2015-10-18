package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByName(@Param("name")String name);
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);

    @RestResource(exported = false)
    @Query(value = "SELECT user.id FROM user JOIN dictionary on dictionary.user_id=user.id where dictionary.id = ?1", nativeQuery = true)
    Long getUserIdByDictionaryId(Long dictionaryId);
}
