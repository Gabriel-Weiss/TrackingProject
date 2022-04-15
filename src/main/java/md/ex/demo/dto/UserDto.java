package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDto implements Serializable {
    private String userId;
    private Boolean userStatus = false;
    private List<DateDto> dates = new ArrayList<>();
}
