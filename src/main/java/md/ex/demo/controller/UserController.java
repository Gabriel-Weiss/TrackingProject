package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.model.User;
import md.ex.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> addUser(@RequestBody final User user) {
        return userService.addUser(user);
    }

//    @GetMapping("update/{id}")
//    public String updateStatus(@PathVariable(value = "id") String id) {
//        userService.changeStatus(id);
//        return "redirect:/";
//    }

    @GetMapping(value = "/stream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<User> getUsers() {
        return userService.getUsers();
    }
}
