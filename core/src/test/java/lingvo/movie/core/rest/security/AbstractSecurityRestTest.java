package lingvo.movie.core.rest.security;

import lingvo.movie.core.entity.User;
import lingvo.movie.core.rest.AbstractRestTest;
import org.junit.Before;
import org.junit.Test;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * Created by yaroslav on 6/28/15.
 */
public class AbstractSecurityRestTest extends AbstractRestTest {
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        user = new User();
        user.setName("admin");
        user.setPassword("root");
        user.setEmail("admin@gmail.com");
    }

}
