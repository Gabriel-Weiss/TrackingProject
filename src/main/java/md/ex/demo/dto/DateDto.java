package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import md.ex.demo.model.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DateDto implements Serializable {
    private LocalDate date;
    private List<LocationDto> locations = new ArrayList<>();
}
