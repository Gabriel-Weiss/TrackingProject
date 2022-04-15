package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "user_status", nullable = false)
    private Boolean userStatus = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dates = new ArrayList<>();

    @ElementCollection
    @Column(name = "saved_id")
    @CollectionTable(name = "user_id_saved_id", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> saved_ids = new LinkedHashSet<>();

    public void addDate(Date date) {
        this.dates.add(date);
        date.setUser(this);
    }

    public void removeDate(Date date) {
        this.dates.remove(date);
        date.setUser(null);
    }
}