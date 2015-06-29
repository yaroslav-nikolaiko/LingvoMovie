package lingvo.movie.core.dao;

import lingvo.movie.core.entity.Dictionary;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DictionaryRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    DictionaryRepository dictionaryRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void userPersistShouldCascadeToDictionaries() throws Exception {
        admin = userRepository.save(admin);

        em.flush();
        em.clear();

        Dictionary dictionary = admin.getDictionaries().get(0);
        Long dictionaryId = dictionary.getId();

        assertNotNull("Dictionary Id should not be null",dictionaryId);
        assertEquals(dictionary, dictionaryRepository.findOne(dictionaryId));
    }
}