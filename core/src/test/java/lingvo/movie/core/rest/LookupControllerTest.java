package lingvo.movie.core.rest;

import lingvo.movie.core.entity.lookup.Language;
import org.junit.Test;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 27.06.15.
 */
public class LookupControllerTest extends AbstractRestTest{

    @Test
    public void getLanguagesNotEmptyArray() throws Exception {
        mockMvc.perform(get("/public/lookup").param("name", "language")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(Language.values().length)));
    }

}