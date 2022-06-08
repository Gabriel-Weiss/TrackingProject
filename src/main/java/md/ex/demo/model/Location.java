package md.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(value = "locations")
public class Location {
    @Id
    private String time;
    private Double longitude;
    private Double latitude;
    private Double altitude;
}