package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto implements Serializable {
    private String userId;
    private String userPhone;
    private Boolean userStatus = false;
    private List<DateDto> dates = new ArrayList<>();
}
