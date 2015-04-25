package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByName(@Param("name")String name);
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);
}
