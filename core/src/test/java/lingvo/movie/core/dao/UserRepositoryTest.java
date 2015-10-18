package lingvo.movie.core.dao;

import lingvo.movie.core.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;

public class UserRepositoryTest extends AbstractRepositoryTest{
    @Test
    public void findSavedUserById() throws Exception {
        assertEquals(admin, userRepository.findOne(admin.getId()));
    }

    @Test
    public void checkEagerFetchDictionaries() throws Exception {
        User admin = userRepository.findOne(this.admin.getId());
        em.clear(); //To clear L1 cache. That how we will know that dictionaries fetched EAGRly
        assertEquals(this.admin.getDictionaries().size(), admin.getDictionaries().size());
    }

    @Test
    public void checkOrphanRemovalDictionaries() throws Exception {
        User admin = userRepository.findOne(this.admin.getId());
        admin.removeDictionary(admin.getDictionaries().get(0));
        em.flush();
        em.clear();

        int expectedDictListSize = this.admin.getDictionaries().size() - 1;
        int actualDictListSize = userRepository.findOne(this.admin.getId()).getDictionaries().size();
        assertEquals(expectedDictListSize, actualDictListSize);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldFailEmailValidation() throws Exception {
        admin.setEmail("wrong.email.format");

        admin = userRepository.save(admin);
        em.flush();
    }

    @Test
    public void findByName() throws Exception {
        assertEquals(admin, userRepository.findByName(admin.getName()));
    }

    @Test
    public void findByNameAndPassword() throws Exception {
        assertEquals(admin, userRepository.findByNameAndPassword(admin.getName(), admin.getPassword()));
    }

    @Test
    public void findByNonExistedName() throws Exception {
        assertNull(userRepository.findByName("nonExistedName"));
    }

    @Test
    public void shouldCascadePersistToMediaItems() throws Exception {
        int expectedMediaItemsSize = this.admin.getDictionaries().get(0).getMediaItems().size();
        int actualMediaItemsSize = userRepository.findOne(this.admin.getId()).getDictionaries().get(0).getMediaItems().size();

        assertEquals(expectedMediaItemsSize, actualMediaItemsSize);
    }
}