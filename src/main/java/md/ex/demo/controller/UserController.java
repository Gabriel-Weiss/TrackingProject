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
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        UserDto addUser = userService.addUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addUser);
    }

    @GetMapping("update/{id}")
    public String updateStatus(@PathVariable(value = "id") String id) {
        userService.changeStatus(id);
        return "redirect:/";
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("listOfUsers", users);
        return "index";
    }
}
