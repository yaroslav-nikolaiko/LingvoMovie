package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by yaroslav on 01.03.15.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    User findByName(@Param("name")String name);
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);

    @RestResource(exported = false)
    @Query(value = "SELECT user.id FROM user " +
            "JOIN dictionary ON dictionary.user_id=user.id " +
            "WHERE dictionary.id = ?1", nativeQuery = true)
    Long getUserIdByDictionaryId(Long dictionaryId);

    @RestResource(exported = false)
    @Query(value = "SELECT user.id FROM user " +
            "JOIN dictionary on dictionary.user_id=user.id " +
            "JOIN media_item on media_item.dictionary_id=dictionary.id " +
            "where media_item.id = ?1", nativeQuery = true)
    Long getUserIdByMediaItemId(Long mediaItemId);
}
