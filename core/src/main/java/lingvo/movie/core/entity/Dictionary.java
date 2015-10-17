package lingvo.movie.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lingvo.movie.core.entity.lookup.Language;
import lingvo.movie.core.entity.lookup.Level;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "DICTIONARY", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "dictionary_id")
    @JsonIgnore
    List<MediaItem> mediaItems = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dictionary_id")
    @XmlTransient
    Set<WordCell> wordCells = new HashSet<>();

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
