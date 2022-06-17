package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.ClientDto;
import md.ex.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class ClientController {

    private final ClientService clientService;
    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @CrossOrigin
    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> log.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> log.info("SseEmitter got error:", ex));

        executor.submit(() -> {
            try {
                sseEmitter.send(SseEmitter.event().name("INIT"));
            } catch (IOException e) {
                log.error(e.toString());
                sseEmitter.completeWithError(e);
            }
        });
        emitters.add(sseEmitter);
        return sseEmitter;
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDto> addUserEvent(@RequestBody ClientDto clientDto) {
        ClientDto addClient = clientService.addClient(clientDto);
        emitters.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("clients").data(addClient, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
            }
        });
        return ResponseEntity.ok(addClient);
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientDto>> getClients() {
        List<ClientDto> users = clientService.getClients();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") String clientId) {
        ClientDto clientDto = clientService.getClientByClientId(clientId);
        return ResponseEntity.ok(clientDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateStatus(@PathVariable(value = "id") String id) {
        clientService.changeStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteClientById(@PathVariable("id") String clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
