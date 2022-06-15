package md.ex.demo.controller;

import lombok.RequiredArgsConstructor;
import md.ex.demo.dto.ClientDto;
import md.ex.demo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ClientService clientService;
    private static final String INDEX_PAGE = "index";

    @GetMapping("/")
    public String index(Model model) {
        List<ClientDto> users = clientService.getClients();
        model.addAttribute("listOfClients", users);
        return INDEX_PAGE;
    }

    @GetMapping("update/{id}")
    public String updateStatus(@PathVariable(value = "id") String id) {
        clientService.changeStatus(id);
        return "redirect:/";
    }
}