package md.ex.demo.service;

import lombok.AllArgsConstructor;
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
public class UserService {

    private final UserRepository userRepository;
    private final DateRepository dateRepository;
    private final DateService dateService;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "User object with id: " + id + " not present"
                        )
                );
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

        Location newLocation = new Location();

        dates.forEach(date -> {
            Date newDate = new Date();

            date.getLocations().forEach(location -> {
                newDate.addLocation(location);
                newDate.setDate(date.getDate());

                System.out.println(location.getTime());
                System.out.println(location.getAltitude());
                System.out.println(location.getLongitude());
                System.out.println(location.getLatitude());
                System.out.println(newDate.getDate().toString());
            });

            user.addDate(newDate);
        });

        user.setUserId(userDto.getUserId());
        user.setUserStatus(userDto.getUserStatus());

//        dates.forEach(date -> {
//            user.addDate(date);
//            dateRepository.save(date);
////            dateService.addDate(Mapper.modelDateToDtoDate(date));
//        });
        userRepository.save(user);
        return Mapper.modelUserToDtoUser(user);
    }
}
