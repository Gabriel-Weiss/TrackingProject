package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.UserDto;
import md.ex.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private static final String INDEX_PAGE = "index";

    @GetMapping("/")
    public String index(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("listOfUsers", users);
        return INDEX_PAGE;
    }

    @GetMapping("update/{id}")
    public String updateStatus(@PathVariable(value = "id") String id) {
        userService.changeStatus(id);
        return "redirect:/";
    }
}