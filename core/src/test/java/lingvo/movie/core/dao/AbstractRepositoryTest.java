package lingvo.movie.core.dao;

import lingvo.movie.Application;
import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.User;
import lingvo.movie.core.entity.lookup.Language;
import lingvo.movie.core.entity.lookup.Level;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

import static lingvo.movie.core.utils.EntityFactory.userWithRoleADMIN;

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
    User admin;

    @Before
    public void setup() throws Exception {
        admin = userWithRoleADMIN();
    }
}
