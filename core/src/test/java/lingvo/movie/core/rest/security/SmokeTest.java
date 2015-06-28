package lingvo.movie.core.rest.security;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 6/28/15.
 */
public class SmokeTest extends AbstractSecurityRestTest{
    @Test
    public void simpleTest() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(contentType))
                .andExpect(status().isUnauthorized());
    }
}
