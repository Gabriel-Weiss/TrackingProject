package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.UserDto;
import md.ex.demo.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        emitters.add(sseEmitter);
        return sseEmitter;
    }

    @PostMapping("/add")
    public void addUserEvent(@RequestBody UserDto userDto) {
        UserDto addUser = userService.addUser(userDto);
        emitters.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("user_dto").data(addUser));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
            }
        });
    }

}
