package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.UserDto;
import md.ex.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    private static final String LOGIN_PAGE = "login";
    public static final String REGISTER_PAGE = "registration";

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("/login")
    public String login() {
        return LOGIN_PAGE;
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return REGISTER_PAGE;
    }

    @PostMapping("/registration")
    public String registerUserAcount(@ModelAttribute("user") UserDto userDto) {
        userService.save(userDto);
        return "redirect:/" + LOGIN_PAGE;
    }
}
