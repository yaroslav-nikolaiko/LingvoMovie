package lingvo.movie.core.utils;

import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.MediaContent;
import lingvo.movie.core.entity.MediaItem;
import lingvo.movie.core.entity.User;
import lingvo.movie.core.entity.lookup.Language;
import lingvo.movie.core.entity.lookup.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.*;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * Created by ynikolaiko on 6/29/15.
 */
public class EntityFactory {
    public static User userWithRoleUSER() {
        User user = new User();
        user.setName("ivan");
        user.setPassword("1234");
        user.setEmail("ivan@gmail.com");
        return user;
    }

    public static User userWithRoleADMIN() {
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("root");
        admin.setEmail("admin@gmail.com");

        admin.setDictionaries(Arrays.asList(dictionary()));

        return admin;
    }

    public static Dictionary dictionary() {
        Dictionary dictionary = new Dictionary();
        dictionary.setLearningLanguage(Language.en);
        dictionary.setNativeLanguage(Language.ru);
        dictionary.setLevel(Level.ADVANCED);
        dictionary.setName("en-ru");
        dictionary.setMediaItems(mediaItems());
        return dictionary;
    }

    public static List<MediaItem> mediaItems() {
        List<MediaItem> mediaItems = new ArrayList<>();

        MediaItem item1 = new MediaItem();
        item1.setDisplayPath("/TVShows/Game Of Throne/2 season");
        item1.setName("12 episode");

        Map<String, String> metaInfo1 = new HashMap<>();
        metaInfo1.put("year", "2012");
        metaInfo1.put("genre", "fantasy");
        item1.setMetaInfo(metaInfo1);

        MediaItem item2 = new MediaItem();
        item2.setDisplayPath("/TVShows/Game Of Throne/2 season");
        item2.setName("15 episode");

        Map<String, String> metaInfo2 = new HashMap<>();
        metaInfo2.put("year", "2012");
        metaInfo2.put("genre", "fantasy");
        item2.setMetaInfo(metaInfo2);

        mediaItems.add(item2);
        mediaItems.add(item1);

        return mediaItems;
    }

    public static List<MediaContent> mediaContents() {
        List<MediaContent> contents = new ArrayList<>();

        MediaContent content1 = new MediaContent();
        content1.setHashSum(111L);

        Map<String, String> metaInfo1 = new HashMap<>();
        metaInfo1.put("bitrate", "128");
        metaInfo1.put("hd", "tru");
        content1.setMetaInfo(metaInfo1);

        MediaContent content2 = new MediaContent();
        content2.setHashSum(222L);

        Map<String, String> metaInfo2 = new HashMap<>();
        metaInfo1.put("bitrate", "256");
        metaInfo1.put("hd", "tru");
        metaInfo1.put("name", "bla-blab-bla");
        content2.setMetaInfo(metaInfo2);

        contents.add(content1);
        contents.add(content2);

        return contents;
    }

}
