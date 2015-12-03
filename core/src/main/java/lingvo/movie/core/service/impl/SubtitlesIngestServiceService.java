package lingvo.movie.core.service.impl;

import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Text;
import lingvo.movie.core.service.TextIngestService;
import org.springframework.stereotype.Service;

/**
 * Created by ynikolaiko on 11/26/15.
 */
@Service
public class SubtitlesIngestServiceService implements TextIngestService {
    public Text ingest(Text input, ContentMedia content) {
        /*
        process original text:
        -   if no byte[] present -> find it in the WEB
        -   convert byte[] to Java .srt representation
        -   save this representation to MongoDB and place Long sourceId into Text input
         */

        return input;
    }
}
