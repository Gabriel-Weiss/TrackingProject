package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class LocationDto implements Serializable {
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private String time;
}
