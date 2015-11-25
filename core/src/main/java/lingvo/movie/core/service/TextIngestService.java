package lingvo.movie.core.service;

import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Text;
import lingvo.movie.core.entity.lookup.TextType;
import org.springframework.stereotype.Service;

import static lingvo.movie.core.utils.Constants.*;

/**
 * Created by ynikolaiko on 11/25/15.
 */

public interface TextIngestService {
    Text ingest(Text input, ContentMedia content);
}

@Service(Subtitles)
class SubtitlesIngestService implements TextIngestService {
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
