package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String clientId;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "status")
    private Boolean clientStatus;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dates = new ArrayList<>();

    public void addDate(Date date) {
        this.dates.add(date);
        date.setClient(this);
    }
}