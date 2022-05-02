package md.ex.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "phone",
        "userStatus",
        "dates"
})
public class UserDto implements Serializable {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("userStatus")
    private Boolean userStatus = false;
    @JsonProperty("dates")
    private List<DateDto> dates = new ArrayList<>();
}
