package lingvo.movie.core.entity;

import lingvo.movie.core.entity.utils.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "WORD_CELL")
@Data
@ToString(of = {"rootForm"}) @EqualsAndHashCode(of = {"rootForm"})
public class WordCell {
    @Id @GeneratedValue
    Long id;
    String rootForm;
    @Enumerated(EnumType.STRING)
    Category category;
    @Transient
    Map<String,String> translation = new HashMap<>();
}
