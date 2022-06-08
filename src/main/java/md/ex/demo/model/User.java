package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(value = "users")
public class User {
    @Id
    private String id;
    private String phone;
    private Boolean status;
    private List<Date> dates = new ArrayList<>();
}