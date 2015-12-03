package lingvo.movie.core.service;

import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Text;

/**
 * Created by ynikolaiko on 11/26/15.
 */
public interface TextIngestService {
    Text ingest(Text input, ContentMedia content);
}

