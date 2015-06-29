package lingvo.movie.core.rest.security;

import org.hamcrest.core.IsNot;
import org.junit.Ignore;
import org.junit.Test;

import static lingvo.movie.core.utils.EntityFactory.principalWithRoleUSER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 6/28/15.
 */
public class SecurityRestTest extends AbstractSecurityRestTest{
    @Test
    public void unauthorizedStatusTest() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(contentType))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginPermitAllTest() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "does not matter")
                .param("password", "does not matter"))
                .andExpect(status().is(IsNot.not(401)))
                .andExpect(status().is(IsNot.not(403)))
                .andExpect(status().is(IsNot.not(404)));
    }

    @Test
    public void loginSuccessShouldReturnUserIdTest() throws Exception {
        String id = admin.getId().toString();

        mockMvc.perform(post("/login")
                .param("username", admin.getName())
                .param("password", admin.getPassword()))
                .andExpect(status().isOk())
                .andExpect(status().is(IsNot.not(403)))
                .andExpect(content().string(id));
    }

    @Test
    @Ignore
    public void userCouldAccessHisDictionariesTest() throws Exception {
        String id = user.getId().toString();

        mockMvc.perform(get("/users/" + id + "/dictionaries").with(principalWithRoleUSER()))
                .andExpect(status().isOk());
    }
}
