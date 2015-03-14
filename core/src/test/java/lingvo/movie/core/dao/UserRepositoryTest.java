package lingvo.movie.core.dao;

import lingvo.movie.core.Application;
import lingvo.movie.core.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    UserRepository userRepository;

    @Test
    public void findSavedUserById() throws Exception {
        user = userRepository.save(user);

        em.clear();

        assertEquals(user, userRepository.findOne(user.getId()));
    }

    @Test
    public void checkEagerFetchDictionaries() throws Exception {
        user = userRepository.save(user);

        em.clear();

        User admin = userRepository.findOne(user.getId());
        assertEquals(user.getDictionaries(), admin.getDictionaries());
    }
}