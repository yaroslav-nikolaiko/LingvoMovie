package lingvo.movie.core.rest;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 6/27/15.
 */
public class SmokeTest extends AbstractRestTest {

    @Test
    public void smokeTest() throws Exception {
        mockMvc.perform(get("/")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

}
