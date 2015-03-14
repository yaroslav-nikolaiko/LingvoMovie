package lingvo.movie.core.dao;

import lingvo.movie.Application;
import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.User;
import lingvo.movie.core.entity.utils.Language;
import lingvo.movie.core.entity.utils.Level;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

/**
 * Created by yaroslav on 14.03.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@ActiveProfiles("test")
public abstract  class AbstractRepositoryTest {
    @PersistenceContext
    EntityManager em;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setName("admin");
        user.setPassword("root");
        user.setEmail("admin@gmail.com");

        Dictionary dictionary = new Dictionary();
        dictionary.setLearningLanguage(Language.en);
        dictionary.setNativeLanguage(Language.ru);
        dictionary.setLevel(Level.ADVANCED);
        dictionary.setName("en-ru");


        user.setDictionaries(Arrays.asList(dictionary));
    }
}
