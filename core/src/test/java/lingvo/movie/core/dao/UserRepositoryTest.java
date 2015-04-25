package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintViolationException;

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

    @Test
    public void checkOrphanRemovalDictionaries() throws Exception {
        user = userRepository.save(user);
        em.flush();
        em.clear();

        User admin = userRepository.findOne(user.getId());
        admin.removeDictionary(admin.getDictionaries().get(0));
        em.flush();
        em.clear();

        int expectedDictListSize = user.getDictionaries().size() - 1;
        int actualDictListSize = userRepository.findOne(user.getId()).getDictionaries().size();
        assertEquals(expectedDictListSize, actualDictListSize);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldFailEmailValidation() throws Exception {
        user.setEmail("wrong.email.format");

        user = userRepository.save(user);
    }

    @Test
    public void findByName() throws Exception {
        user = userRepository.save(user);
        em.flush();
        em.clear();

        assertEquals(user, userRepository.findByName(user.getName()));
    }

    @Test
    public void findByNameAndPassword() throws Exception {
        user = userRepository.save(user);
        em.flush();
        em.clear();

        assertEquals(user, userRepository.findByNameAndPassword(user.getName(), user.getPassword()));
    }

    @Test
    public void findByNonExistedName() throws Exception {
        user = userRepository.save(user);
        em.flush();
        em.clear();

        assertNull(userRepository.findByName("nonExistedName"));
    }
}