package lingvo.movie.core.security.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by yaroslav on 02.05.15.
 */
public class UserPrincipalWithId extends User {
    final Long id;

    public UserPrincipalWithId(@NotNull Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
