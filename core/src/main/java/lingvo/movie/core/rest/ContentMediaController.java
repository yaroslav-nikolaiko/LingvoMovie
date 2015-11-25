package lingvo.movie.core.rest;

import lingvo.movie.core.dao.ContentMediaRepository;
import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Text;
import lingvo.movie.core.service.TextIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ynikolaiko on 11/25/15.
 */
@RestController
@RequestMapping(value = "contentMedias/{id}")
public class ContentMediaController {
    @Autowired
    ContentMediaRepository contentMediaRepository;
    @Autowired
    Map<String, TextIngestService > ingestServices;

    @RequestMapping(value = "text/original", method = RequestMethod.PUT)
    public void addOriginalText(@RequestBody Text text, @PathVariable Long id) {
        ContentMedia content = contentMediaRepository.findOne(id);
        Text processed = ingestServices.get(text.getType().name()).ingest(text, content);
        content.setOriginalText(processed);
        contentMediaRepository.save(content);
    }
}
