package lingvo.movie.core.entity;

import lingvo.movie.core.entity.utils.Language;
import lingvo.movie.core.entity.utils.Level;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "DICTIONARY")
@Data @ToString(of = {"name"}) @EqualsAndHashCode(of = {"name", "learningLanguage", "nativeLanguage", "level"})
public class Dictionary {
    @Id @GeneratedValue
    Long id;

    @Size(max=20)
    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Language learningLanguage;

    @Enumerated(EnumType.STRING)
    Language nativeLanguage;

    @Enumerated(EnumType.STRING)
    Level level;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "dictionary_id")
    List<MediaItem> mediaItems;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dictionary_id")
    @XmlTransient
    List<WordCell> wordCells;

    public Dictionary() {
        mediaItems = new ArrayList<>();
    }

/*

    public void addMediaItem(MediaItem item) {
        mediaItems.add(item);
    }

    public void removeMediaItems(Collection<MediaItem> items){
        mediaItems.removeAll(items);
    }

    public void removeMediaItem(MediaItem item){
        mediaItems.remove(item);
    }


    public void update(Dictionary managedDictionary) {
        this.name = managedDictionary.name;
        this.learningLanguage = managedDictionary.learningLanguage;
        this.nativeLanguage = managedDictionary.nativeLanguage;
        this.level = managedDictionary.level;
        this.mediaItems = managedDictionary.mediaItems;
    }*/
}
