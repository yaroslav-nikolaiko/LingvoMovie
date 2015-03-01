package lingvo.movie.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "MEDIA_CONTENT")
@Data
@ToString(of = {"hashSum", "id"}) @EqualsAndHashCode(of = {"hashSum", "physicalStorage", "originalText"})
public class MediaContent {
    @Id @GeneratedValue
    Long id;
    Long hashSum;
    @OneToOne
    PhysicalStorage physicalStorage;
    @OneToOne
    Text originalText;

    @ManyToMany
    @JoinTable(name="ORIGINAL_TO_TRANSLATED_MAPPING")
    List<Text> texts;

    @ElementCollection
    @JoinTable(name="MEDIA_CONTENT_META_INFO")
    @MapKeyColumn(name = "attr_name", unique = true)
    @Column(name="attr_value")
    Map<String, String> metaInfo;
}
