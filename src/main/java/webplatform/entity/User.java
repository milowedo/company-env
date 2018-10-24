package webplatform.entity;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    @NotNull(message = "cannot be null")
    @Size(max = 15, message = "shorter than 15")
    @Size(min = 1, message = "cannot be null")
    private String username;

    @Column(name="password")
    @NotNull(message = "cannot be null")
    @Size(min = 8, message = "at least 8 char")
    private String password;

    @Column(name="first_name")
    @NotNull(message = "cannot be null")
    @Size(max=15, message = "shorter than 15")
    @Size(min=2, message = "at least 2 char")
    private String firstName;

    @Column(name="last_name")
    @NotNull(message = "cannot be null")
    @Size(max = 25, message = "shorter than 25")
    @Size(min=2, message = "at least 2 char")
    private String lastName;

    @Column(name="email")
    @NotNull(message = "cannot be null")
    @Size(max = 25, message = "shorter than 25")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "Not a valid email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users_authorities",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    //needs to have a String version of Authority for the form purposes
    @Transient
    private List<String> authorities_string;

    //takes the authorities objects gotten from the database, and makes a list of their names in String
    public User fillAuthorities_string(){
        authorities_string = new ArrayList<>();
        authorities.stream()
                .forEach(auth -> authorities_string.add(auth.getAuthority()));
        return this;
    }

    //add an authority to the database connected field
    public void addAuthority(Authority authority){
        if(authorities == null) authorities = new ArrayList<>();

        if(!authorities.contains(authority)) authorities.add(authority);
    }

    //converts current Authorities into a list of spring security friendly objects
    public List<SimpleGrantedAuthority> getGrantedAuthorities(){
        List<SimpleGrantedAuthority> returned = new ArrayList<>();
        this.authorities.stream().forEach(auth -> returned.add(new SimpleGrantedAuthority(auth.getAuthority())));
        return returned;
    }
}
