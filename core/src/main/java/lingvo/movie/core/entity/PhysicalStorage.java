package lingvo.movie.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "PHYSICAL_STORAGE")
@Data
public class PhysicalStorage {
    @Id @GeneratedValue
    Long id;
}
