package md.ex.demo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.UserDto;
import md.ex.demo.mapper.Mapper;
import md.ex.demo.model.Date;
import md.ex.demo.model.User;
import md.ex.demo.repository.UserRepository;
import md.ex.demo.twilio.TwilioService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TwilioService twilioService;

    public User getByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("User with id" + userId + "not present")
        );
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
            user.setName(userDto.getUserName());
            user.setEmail(userDto.getUserEmail());
            user.setPhone(userDto.getUserPhone());
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
//        twilioService.notifyInfected(user.getPhone());
        log.info("notifyInfected(): called. Send Message with twilio service to " + user.getPhone());

        List<String> list = user
                        .getDates()
                        .stream()
                        .map(Date::getSavedCodes)
                        .flatMap(Collection::stream)
                        .distinct().collect(Collectors.toList());
        list.forEach(s -> {
            String userPhone = getUserByUserId(s).getUserPhone();
//            twilioService.notifyQuarantin(userPhone);
            log.info("notifyQuarantin(): called. Send Message with twilio service to " + userPhone);
        });

        userRepository.save(user);
    }
}
