package lingvo.movie.core.rest;

import lingvo.movie.core.dao.MediaItemRepository;
import lingvo.movie.core.entity.MediaItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static lingvo.movie.core.utils.EntityFactory.mediaItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaroslav on 18.10.15.
 */
public class MediaItemControllerTest extends AbstractRestTest{
    @Autowired
    MediaItemRepository mediaItemRepository;

    @Override
    public void setup() throws Exception {
        super.setup();
        admin = userRepository.save(admin);

    }

    @Test
    public void createItemInExistingDictionary() throws Exception {
        Long dictionaryId = admin.getDictionaries().get(0).getId();
        MediaItem mediaItem = mediaItems().get(0);

        MockHttpServletResponse response = mockMvc.perform(post("/dictionaries/"+dictionaryId+"/mediaItems")
                .content(json(mediaItem))
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        String link = response.getHeader("Location");

        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mediaItem.getName())));
    }

    @Test
    public void patchShouldChangeNameAndLeaveTheRest() throws Exception {
        createItemInExistingDictionary();
        MediaItem mediaItem = mediaItemRepository.getAllByDictionaryId(admin.getDictionaries().get(0).getId()).get(0);

        MockHttpServletResponse response = mockMvc.perform(
                patch("/mediaItems/" + mediaItem.getId())
                .content("{\"name\" : \"New Name\"}")
                .contentType(contentType))
                .andReturn().getResponse();

        em.flush();
        em.clear();

        MediaItem updated = mediaItemRepository.findOne(mediaItem.getId());

        assertEquals("New Name", updated.getName());
        assertEquals(mediaItem.getDisplayPath(), updated.getDisplayPath());
        assertEquals(mediaItem.getDictionary().getId(), updated.getDictionary().getId());
    }
}
