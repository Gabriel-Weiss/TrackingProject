package md.ex.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.UserDto;
import md.ex.demo.mapper.Mapper;
import md.ex.demo.model.Date;
import md.ex.demo.model.Location;
import md.ex.demo.model.User;
import md.ex.demo.repository.DateRepository;
import md.ex.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("User object with id: " + id + " not present"));
    }

    public UserDto getUser(Long id) {
        User user = getUserById(id);
        return Mapper.modelUserToDtoUser(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(Mapper::modelUserToDtoUser)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        List<Date> dates = userDto.getDates().stream()
                .map(Mapper::dtoDateToModelDate)
                .collect(Collectors.toList());
        User user = new User();
        dates.forEach(date -> {
            Date newDate = new Date();
            date.getLocations().forEach(newDate::addLocation);
            newDate.setDate(date.getDate());
            newDate.setSavedCodes(date.getSavedCodes());
            user.addDate(newDate);
        });
        user.setUserId(userDto.getUserId());
        user.setUserStatus(userDto.getUserStatus());
        userRepository.save(user);
        log.info("addUser() called with: userDto = [" + user + "]");
        return Mapper.modelUserToDtoUser(user);
    }
}
