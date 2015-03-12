package lingvo.movie.core.dao;

import lingvo.movie.core.Application;
import lingvo.movie.core.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired UserRepository userRepository;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setName("admin");
        user.setPassword("root");
        user.setEmail("admin@gmail.com");
    }

    @Test
    public void findSavedUserById() throws Exception {
        user = userRepository.save(user);

        assertEquals(user, userRepository.findOne(user.getId()));
    }
}