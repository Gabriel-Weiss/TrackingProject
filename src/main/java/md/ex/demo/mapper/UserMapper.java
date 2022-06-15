package md.ex.demo.mapper;

import md.ex.demo.dto.DateDto;
import md.ex.demo.dto.UserDto;
import md.ex.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto modelUserToDtoUser(User user) {
        UserDto userDto = new UserDto();
        List<DateDto> datesDto = user.getDates().stream()
                .map(DateMapper::modelDateToDtoDate)
                .collect(Collectors.toList());
        userDto.setUserId(user.getUserId());
        userDto.setUserPhone(user.getPhone());
        userDto.setUserStatus(user.getUserStatus());
        userDto.setDates(datesDto);
        return userDto;
    }
}
