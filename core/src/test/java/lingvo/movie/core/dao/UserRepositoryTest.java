package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.*;

public class UserRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    UserRepository userRepository;

    @Test
    public void findSavedUserById() throws Exception {
        user = userRepository.save(user);

        em.flush();
        em.clear();

        assertEquals(user, userRepository.findOne(user.getId()));
    }

    @Test
    public void checkEagerFetchDictionaries() throws Exception {
        user = userRepository.save(user);

        em.flush();
        em.clear();

        User admin = userRepository.findOne(user.getId());
        em.clear(); //To clear L1 cache. That how we will know that dictionaries fetched EAGRly
        assertEquals(user.getDictionaries().size(), admin.getDictionaries().size());
    }
}