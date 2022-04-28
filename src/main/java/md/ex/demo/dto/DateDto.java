package md.ex.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "locations",
        "codes"
})
public class DateDto implements Serializable {

    @JsonProperty("date")
    private String date;
    @JsonProperty("locations")
    private List<LocationDto> locations = new ArrayList<>();
    @JsonProperty("codes")
    private Set<String> codes = new LinkedHashSet<>();
}
