package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DateDto implements Serializable {

    private String date;
    private List<LocationDto> locations = new ArrayList<>();
    private Set<String> codes = new LinkedHashSet<>();
}
