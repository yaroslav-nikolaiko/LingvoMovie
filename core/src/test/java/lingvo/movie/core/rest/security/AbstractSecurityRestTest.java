package lingvo.movie.core.rest.security;

import lingvo.movie.core.dao.UserRepository;
import lingvo.movie.core.rest.AbstractRestTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static lingvo.movie.core.utils.EntityFactory.userWithRoleADMIN;
import static lingvo.movie.core.utils.EntityFactory.userWithRoleUSER;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * Created by yaroslav on 6/28/15.
 */
public class AbstractSecurityRestTest extends AbstractRestTest {
    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        admin = userWithRoleADMIN();
        user = userWithRoleUSER();

        admin = userRepository.save(admin);
        user = userRepository.save(user);

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

}
