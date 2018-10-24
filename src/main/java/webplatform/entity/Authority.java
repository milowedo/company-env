package webplatform.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "authorities_table")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="authority")
    @NotNull
    @Size(min = 5)
    private String authority;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_authorities",
    joinColumns = @JoinColumn(name = "authority_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
