package lingvo.movie.core.rest;

import lingvo.movie.core.dao.ContentMediaRepository;
import lingvo.movie.core.dao.MediaItemRepository;
import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.MediaItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.Map;

import static lingvo.movie.core.utils.EntityFactory.contentMedias;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
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
    @Autowired
    ContentMediaRepository contentMediaRepository;

    @Override
    public void setup() throws Exception {
        super.setup();
        admin = userRepository.save(admin);
    }

    @Test(expected = NestedServletException.class)
    public void failedToCreateItemWithoutContentMedia() throws Exception {
        Long dictionaryId = admin.getDictionaries().get(0).getId();
        MediaItem mediaItem = mediaItems().get(0);

        mockMvc.perform(post("/dictionaries/" + dictionaryId + "/mediaItems")
                .content(json(mediaItem))
                .contentType(contentType))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createContentMediaTest() throws Exception {
        String link = createContentMedia();

        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void createItemWithLinkToContentMediaInExistingDictionary() throws Exception {
        String contentMediaLink = createContentMedia();
        Long dictionaryId = admin.getDictionaries().get(0).getId();
        MediaItem mediaItem = mediaItems().get(0);

        Map<String, String> links = new HashMap<>();
        links.put("contentMedia", contentMediaLink);

        String json = json(mediaItem, links);
        MockHttpServletResponse response = mockMvc.perform(post("/dictionaries/"+dictionaryId+"/mediaItems")
                .content(json)
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        String link = response.getHeader("Location");

        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mediaItem.getName())))
                .andExpect(jsonPath("$._links.contentMedia", notNullValue()));
    }



    @Test
    public void patchShouldChangeNameAndLeaveTheRest() throws Exception {
        createItemWithLinkToContentMediaInExistingDictionary();
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

    String createContentMedia() throws Exception {
        ContentMedia contentMedia = contentMedias().get(0);

        String json = json(contentMedia);
        MockHttpServletResponse response = mockMvc.perform(post("/contentMedias")
                .content(json)
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        return response.getHeader("Location");
    }
}
