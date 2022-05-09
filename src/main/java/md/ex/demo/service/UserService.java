package md.ex.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.UserDto;
import md.ex.demo.mapper.DateMapper;
import md.ex.demo.mapper.UserMapper;
import md.ex.demo.model.Date;
import md.ex.demo.model.User;
import md.ex.demo.repository.UserRepository;
import md.ex.demo.twilio.TwilioService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TwilioService twilioService;

    public User getByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("User with id" + userId + " not present")
        );
    }

    public UserDto getUserByUserId(String userId) {
        User user = getByUserId(userId);
        return UserMapper.modelUserToDtoUser(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::modelUserToDtoUser)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        boolean existsByUserId = userRepository.existsByUserId(userDto.getUserId());

        if (!existsByUserId) {
            User user = new User();
            user.setUserId(userDto.getUserId());
            user.setPhone(userDto.getUserPhone());
            user.setUserStatus(userDto.getUserStatus());
            userRepository.save(user);
            log.info("addUser() called. Created: user = [" + user.getUserId() + "]");
            return UserMapper.modelUserToDtoUser(user);
        } else {
            User userByUserId = getByUserId(userDto.getUserId());
            List<Date> dates = userDto.getDates().stream()
                    .map(DateMapper::dtoDateToModelDate)
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
            return UserMapper.modelUserToDtoUser(userByUserId);
        }
    }

    public void changeStatus(String userId) {
        User user = getByUserId(userId);
        user.setUserStatus(!user.getUserStatus());

//        twilioService.notifyInfected(user.getPhone());
        log.info("notifyInfected(): called. Send Message with twilio service to " + user.getPhone());

        List<String> list = user
                .getDates()
                .stream()
                .map(Date::getSavedCodes)
                .flatMap(Collection::stream)
                .distinct().collect(Collectors.toList());
        list.forEach(s -> {
            String userPhone = null;
            try {
                userPhone = getUserByUserId(s).getUserPhone();
            } catch (NoSuchElementException e) {
                log.error("User with id" + s + " not present");
            }
//            twilioService.notifyQuarantin(userPhone);
            log.info("notifyQuarantin(): called. Send Message with twilio service to " + userPhone);
        });

        userRepository.save(user);
    }
}