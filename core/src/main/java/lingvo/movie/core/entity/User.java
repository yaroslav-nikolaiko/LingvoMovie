package lingvo.movie.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
@Entity
@Table(name = "USER")
@Data
@ToString(of = {"name", "email"}) @EqualsAndHashCode(of = {"name", "email"})
public class User {
    @Id @GeneratedValue
    Long id;

    @Size(max=20)
    @Column(nullable = false, unique = true)
    String name;

    @Size(min=3, max=20)
    @Column(nullable = false)
    String password;

    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Email is not in valid format")
    @Column(unique = true)
    String email;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    List<Dictionary> dictionaries;

    public User() {
        dictionaries = new ArrayList<>();
    }

    public void addDictionary(Dictionary dictionary){
        dictionaries.add(dictionary);
    }

    public void removeDictionary(Dictionary dictionary) {
        dictionaries.remove(dictionary);
    }
}
