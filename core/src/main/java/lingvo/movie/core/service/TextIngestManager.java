package lingvo.movie.core.service;

import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Text;
import lingvo.movie.core.entity.lookup.TextType;
import lingvo.movie.core.service.impl.SubtitlesIngestServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by ynikolaiko on 11/25/15.
 */

@Service
public class TextIngestManager {
    @Autowired
    Map<String, TextIngestService> ingestServices;

    public TextIngestService get(Text input){
        TextType textType = input.getType();
        return ingestServices.get(resolveServiceName(textType));
    }

    public Text ingest(Text input, ContentMedia content){
        /*
        process original text:
        -   if no byte[] present -> find it in the WEB
        -   convert byte[] to Java .srt/lyrics/general text representation
        -   save this representation to MongoDB and place Long sourceId into Text input
         */
        return input;
    }

    String resolveServiceName(TextType textType) {
        switch (textType) {
            case SUBTITLES: return SubtitlesIngestServiceService.class.getSimpleName();
        }
        //TODO: handle exceptions in more consistent way
        throw new IllegalArgumentException("No text service for type" + textType);
    }
}


