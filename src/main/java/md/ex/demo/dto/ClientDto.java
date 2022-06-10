package md.ex.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientDto implements Serializable {
    private String clientId;
    private String clientPhone;
    private Boolean clientStatus = false;
    private List<DateDto> dates = new ArrayList<>();
}
