package lingvo.movie.core.dao;

import lingvo.movie.core.Application;
import lingvo.movie.core.entity.Dictionary;
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
public class DictionaryRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    DictionaryRepository dictionaryRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void userPersistShouldCascadeToDictionaries() throws Exception {
        user = userRepository.save(user);
        em.clear();

        Dictionary dictionary = user.getDictionaries().get(0);
        Long dictionaryId = dictionary.getId();

        assertNotNull("Dictionary Id should not be null",dictionaryId);
        assertEquals(dictionary, dictionaryRepository.findOne(dictionaryId));
    }
}