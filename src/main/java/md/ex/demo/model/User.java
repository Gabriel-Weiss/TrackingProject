package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(length = 70)
    private String password;
    @ElementCollection
    @Column(name = "role", length = 20)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> roles = new ArrayList<>();

}