package md.ex.demo.service;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.UserDto;
import md.ex.demo.mapper.UserMapper;
import md.ex.demo.model.User;
import md.ex.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public User save(UserDto userDto) {
        User user = UserMapper.userDtoToUser(userDto);
        user.setRoles(List.of("ROLE_ADMIN"));
        return userRepository.save(user);
    }
}
