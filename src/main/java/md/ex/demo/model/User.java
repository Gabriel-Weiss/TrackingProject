package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_status")
    private Boolean userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dates = new ArrayList<>();

    public void addDate(Date date) {
        this.dates.add(date);
        date.setUser(this);
    }

    public void removeDate(Date date) {
        this.dates.remove(date);
        date.setUser(null);
    }
}