package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
//@PostAuthorize("hasRole('ROLE_ADMIN')")
public interface UserRepository extends CrudRepository<User, Long>{
    //@PreAuthorize("user.name == authentication.name")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    User findByName(@Param("name")String name);
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);
}
