package lingvo.movie.core.rest.security;

import lingvo.movie.core.dao.UserRepository;
import lingvo.movie.core.rest.AbstractRestTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import static lingvo.movie.core.utils.EntityFactory.userWithRoleADMIN;
import static lingvo.movie.core.utils.EntityFactory.userWithRoleUSER;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by yaroslav on 6/28/15.
 */
public class AbstractSecurityRestTest extends AbstractRestTest {
    @Autowired
    UserRepository userRepository;

/*    @Autowired
    private Filter springSecurityFilterChain;*/

    @Autowired
    UserDetailsService userDetailsService;

    @Before
    public void setup() throws Exception {
        admin = userWithRoleADMIN();
        user = userWithRoleUSER();

        admin = userRepository.save(admin);
        user = userRepository.save(user);

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

/*        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();*/
    }

}
