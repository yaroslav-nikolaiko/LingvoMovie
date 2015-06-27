package lingvo.movie.core.rest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 6/27/15.
 */
public class UserControllerTest extends AbstractRestTest {

    @Test
    public void postUserTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/users")
                .content(json(user))
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        String link = response.getHeader("Location");

        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(user.getName())));
    }
}
