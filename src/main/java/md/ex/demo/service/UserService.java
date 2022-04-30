package md.ex.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.UserDto;
import md.ex.demo.mapper.Mapper;
import md.ex.demo.model.Date;
import md.ex.demo.model.User;
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

    public User getByUserId(String userId) {
        return userRepository.getByUserId(userId);
    }

    public UserDto getUserByUserId(String userId) {
        User user = getByUserId(userId);
        return Mapper.modelUserToDtoUser(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(Mapper::modelUserToDtoUser)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        boolean existsByUserId = userRepository.existsByUserId(userDto.getUserId());

        if (!existsByUserId) {
            User user = new User();
            user.setUserId(userDto.getUserId());
            user.setUserStatus(userDto.getUserStatus());
            userRepository.save(user);
            log.info("addUser() called. Created: user = [" + user.getUserId() + "]");
            return Mapper.modelUserToDtoUser(user);
        } else {
            User userByUserId = getByUserId(userDto.getUserId());
            List<Date> dates = userDto.getDates().stream()
                    .map(Mapper::dtoDateToModelDate)
                    .collect(Collectors.toList());
            dates.forEach(date -> {
                Date newDate = new Date();
                date.getLocations().forEach(newDate::addLocation);
                newDate.setDate(date.getDate());
                newDate.setSavedCodes(date.getSavedCodes());
                userByUserId.addDate(newDate);
            });
            userRepository.save(userByUserId);
            log.info("addUser() called. Updated: user = [" + userByUserId.getUserId() + "]");
            return Mapper.modelUserToDtoUser(userByUserId);
        }
    }

    public void changeStatus(String userId) {
        User user = getByUserId(userId);
        user.setUserStatus(!user.getUserStatus());
        userRepository.save(user);
    }
}
