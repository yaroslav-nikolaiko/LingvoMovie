package lingvo.movie.core.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "MEDIA_ITEM")
@Data
@ToString(of = {"name", "displayPath"}) @EqualsAndHashCode(of = {"name", "displayPath"})
public class MediaItem {
    @Id @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String displayPath;

    @ElementCollection
    @JoinTable(name="MEDIA_ITEM_META_INFO")
    @MapKeyColumn(name = "attr_name", unique = true)
    @Column(name="attr_value")
    Map<String, String> metaInfo;

    @ManyToOne
    MediaContent mediaContent;

    public MediaItem() {
        metaInfo = new HashMap<>();
    }
}
