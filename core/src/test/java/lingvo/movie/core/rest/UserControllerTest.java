package lingvo.movie.core.rest;

import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.User;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 6/27/15.
 */
public class UserControllerTest extends AbstractRestTest {
    @Test
    public void postUser() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/users")
                .content(json(admin))
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        String link = response.getHeader("Location");

        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(admin.getName())));
    }

    @Test
    public void getUserByIdWithDictionaries() throws Exception {
        User admin = userRepository.save(this.admin);
        Dictionary dictionary = admin.getDictionaries().get(0);

        em.flush();
        em.clear();//to clear L1 cache and perform actual select from DB

        mockMvc.perform(get("/users/" + admin.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(admin.getName())))
                .andExpect(jsonPath(".dictionaries[0].name", is(dictionary.getName())));
/*                .andExpect(jsonPath(".dictionaries[0].mediaItems", hasSize(mediaItems.size())))
                .andExpect(jsonPath(".dictionaries[0].mediaItems[0].name", is(mediaItems.get(0).getName())));*/

    }
}
