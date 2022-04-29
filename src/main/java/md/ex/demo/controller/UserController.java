package md.ex.demo.controller;

import lombok.AllArgsConstructor;
import md.ex.demo.dto.UserDto;
import md.ex.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        UserDto addUser = userService.addUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addUser);
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("listOfUsers", users);
        return "index";
    }
}
