package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(value = "dates")
public class Date {
    @Id
    private String date;
    private List<Location> locations = new ArrayList<>();
    private Set<String> savedCodes = new LinkedHashSet<>();
}