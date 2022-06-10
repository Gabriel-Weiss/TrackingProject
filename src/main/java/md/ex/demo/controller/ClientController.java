package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.ClientDto;
import md.ex.demo.service.ClientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
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
    public void addUserEvent(@RequestBody ClientDto clientDto) {
        ClientDto addClient = clientService.addClient(clientDto);
        emitters.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("clients").data(addClient));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
            }
        });
    }

}
