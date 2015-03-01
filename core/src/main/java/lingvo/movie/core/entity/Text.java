package lingvo.movie.core.entity;

import lingvo.movie.core.entity.utils.Language;
import lingvo.movie.core.entity.utils.TextType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "TEXT")
@Data
@ToString(of = {"sourceId", "language", "type"}) @EqualsAndHashCode(of = {"sourceId", "language", "type"})
public class Text {
    @Id @GeneratedValue
    Long id;

    @Enumerated(EnumType.STRING)
    TextType type;

    @Enumerated(EnumType.STRING)
    Language language;
    Long sourceId;
    String ranking;
    @OneToOne
    User uploadUser;
}
