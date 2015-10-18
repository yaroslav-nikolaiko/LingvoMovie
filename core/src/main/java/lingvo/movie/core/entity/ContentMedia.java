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
@Table(name = "CONTENT_MEDIA")
@Data
@ToString(of = {"hashSum", "id"}) @EqualsAndHashCode(of = {"hashSum", "physicalStorage", "originalText"})
public class ContentMedia {
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
    @JoinTable(name="CONTENT_MEDIA_META_INFO")
    @MapKeyColumn(name = "attr_name", unique = true)
    @Column(name="attr_value")
    Map<String, String> metaInfo;
}
