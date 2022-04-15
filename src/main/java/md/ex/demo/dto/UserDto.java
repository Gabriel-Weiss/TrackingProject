package md.ex.demo.dto;

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
public class UserDto implements Serializable {
    private String userId;
    private Boolean userStatus = false;
    private List<DateDto> dates = new ArrayList<>();
    private Set<String> savedIds = new LinkedHashSet<>();
}
