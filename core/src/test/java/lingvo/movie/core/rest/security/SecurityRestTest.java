package lingvo.movie.core.rest.security;

import lingvo.movie.core.dao.DictionaryRepository;
import lingvo.movie.core.dao.MediaItemRepository;
import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.MediaItem;
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

import static lingvo.movie.core.utils.EntityFactory.dictionary;
import static lingvo.movie.core.utils.EntityFactory.mediaItems;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * Created by yaroslav on 6/28/15.
 */
public class SecurityRestTest extends AbstractSecurityRestTest{
    @Autowired
    MediaItemRepository mediaItemRepository;

    @Test
    public void unauthorizedStatus() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(contentType))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginPermitAll() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "does not matter")
                .param("password", "does not matter"))
                .andExpect(status().is(IsNot.not(401)))
                .andExpect(status().is(IsNot.not(403)))
                .andExpect(status().is(IsNot.not(404)));
    }

    @Test
    public void loginSuccessShouldReturnUserId() throws Exception {
        String id = admin.getId().toString();

        mockMvc.perform(post("/login")
                .param("username", admin.getName())
                .param("password", admin.getPassword()))
                .andExpect(status().isOk())
                .andExpect(status().is(IsNot.not(403)))
                .andExpect(content().string(id));
    }

    @Test
    public void userCouldAccessOwnDictionaries() throws Exception {
        String id = user.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/" + id + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenWhenUserAccessAnotherUserDictionary() throws Exception {
        String anotherUserID = admin.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/" + anotherUserID + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCouldAccessAnotherUserDictionary() throws Exception {
        String anotherUserID = user.getId().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getName());

        mockMvc.perform(get("/users/" + anotherUserID + "/dictionaries").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void lookupAvailableForNonAuthorizedUser() throws Exception {
        mockMvc.perform(get("/public/lookup").param("name", "language"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenWhenUserSearchForUserByName() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/users/search/findByName").param("name", user.getName()).with(user(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCouldSearchForUserByName() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getName());

        mockMvc.perform(get("/users/search/findByName").param("name",user.getName()).with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void userCouldAccessOwnMediaItems() throws Exception {
        MediaItem item = mediaItems().get(0);
        Dictionary dictionary = user.getDictionaries().get(0);
        Long dictionaryID = dictionary.getId();

        item.setDictionary(dictionary);
        item = mediaItemRepository.save(item);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/dictionaries/" + dictionaryID + "/mediaItems").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void userForbiddenAccessAnotherMediaItems() throws Exception {
        MediaItem item = mediaItems().get(0);
        Dictionary dictionary = admin.getDictionaries().get(0);
        Long dictionaryID = dictionary.getId();

        item.setDictionary(dictionary);
        item = mediaItemRepository.save(item);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        mockMvc.perform(get("/dictionaries/"+dictionaryID+"/mediaItems").with(user(userDetails)))
                .andExpect(status().isForbidden());
    }
}
