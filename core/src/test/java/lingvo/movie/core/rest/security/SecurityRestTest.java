package lingvo.movie.core.rest.security;

import org.hamcrest.core.IsNot;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static lingvo.movie.core.utils.EntityFactory.principalWithRoleUSER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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
    public void userCouldAccessHisDictionariesTest() throws Exception {
        String id = user.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/" + id + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenWhenUserAccessAnotherUserDictionaryTest() throws Exception {
        String anotherUserID = admin.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/" + anotherUserID + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCouldAccessAnotherUserDictionaryTest() throws Exception {
        String anotherUserID = user.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getName());

        mockMvc.perform(get("/users/" + anotherUserID + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void lookupAvailableForNonAuthorizedUserTest() throws Exception {
        mockMvc.perform(get("/public/lookup").param("name", "language"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenWhenUserSearchForUserByNameTest() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/search/findByName").param("name", user.getName()).with(user(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCouldSearchForUserByNameTest() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getName());

        mockMvc.perform(get("/users/search/findByName").param("name",user.getName()).with(user(userDetails)))
                .andExpect(status().isOk());
    }
}
