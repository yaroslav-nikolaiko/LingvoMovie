package lingvo.movie.core.utils;

import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.User;
import lingvo.movie.core.entity.lookup.Language;
import lingvo.movie.core.entity.lookup.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;

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

        Dictionary dictionary = new Dictionary();
        dictionary.setLearningLanguage(Language.en);
        dictionary.setNativeLanguage(Language.ru);
        dictionary.setLevel(Level.ADVANCED);
        dictionary.setName("en-ru");


        admin.setDictionaries(Arrays.asList(dictionary));

        return admin;
    }

    public static RequestPostProcessor principalWithRoleUSER() {
        User user = userWithRoleUSER();
        return user(user.getName()).password(user.getPassword()).roles("USER");
    }

    public static RequestPostProcessor principalWithRoleADMIN() {
        User admin = userWithRoleADMIN();
        return user(admin.getName()).password(admin.getPassword()).roles("ADMIN");
    }
}
