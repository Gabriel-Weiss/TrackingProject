package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    public static final String REGISTER_PAGE = "registration";
    public final UserService userService;

    @GetMapping
    public String getRegistrationPage() {
        return REGISTER_PAGE;
    }

//    @PostMapping
//    public String registerUserAcount(@ModelAttribute("user")UserDto userDto) {
//        userService.save(userDto);
//        return "redirect:/" + REGISTER_PAGE + "?success";
//    }
}
