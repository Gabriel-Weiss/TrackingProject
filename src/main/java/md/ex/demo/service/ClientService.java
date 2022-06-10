package md.ex.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.dto.ClientDto;
import md.ex.demo.mapper.ClientMapper;
import md.ex.demo.mapper.DateMapper;
import md.ex.demo.model.Client;
import md.ex.demo.model.Date;
import md.ex.demo.repository.ClientRepository;
import md.ex.demo.twilio.TwilioService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final TwilioService twilioService;

    public Client getByUserId(String userId) {
        return clientRepository.findByClientId(userId).orElseThrow(
                () -> new NoSuchElementException("Client with id" + userId + " not present")
        );
    }

    public ClientDto getClientByUserId(String userId) {
        Client client = getByUserId(userId);
        return ClientMapper.modelClientToDtoClient(client);
    }

    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::modelClientToDtoClient)
                .collect(Collectors.toList());
    }

    public ClientDto addClient(ClientDto clientDto) {
        boolean existsByUserId = clientRepository.existsByClientId(clientDto.getClientId());

        if (!existsByUserId) {
            Client client = new Client();
            client.setClientId(clientDto.getClientId());
            client.setPhone(clientDto.getClientPhone());
            client.setClientStatus(clientDto.getClientStatus());
            clientRepository.save(client);
            log.info("addUser() called. Created: client = [" + client.getClientId() + "]");
            return ClientMapper.modelClientToDtoClient(client);
        } else {
            Client userByClientId = getByUserId(clientDto.getClientId());
            List<Date> dates = clientDto.getDates().stream()
                    .map(DateMapper::dtoDateToModelDate)
                    .collect(Collectors.toList());
            dates.forEach(date -> {
                Date newDate = new Date();
                date.getLocations().forEach(newDate::addLocation);
                newDate.setDate(date.getDate());
                newDate.setSavedCodes(date.getSavedCodes());
                userByClientId.addDate(newDate);
            });
            clientRepository.save(userByClientId);
            log.info("addUser() called. Updated: user = [" + userByClientId.getClientId() + "]");
            return ClientMapper.modelClientToDtoClient(userByClientId);
        }
    }

    public void changeStatus(String userId) {
        Client client = getByUserId(userId);
        client.setClientStatus(!client.getClientStatus());

//        twilioService.notifyInfected(client.getPhone());
        log.info("notifyInfected(): called. Send Message with twilio service to " + client.getPhone());

        List<String> list = client
                .getDates()
                .stream()
                .map(Date::getSavedCodes)
                .flatMap(Collection::stream)
                .distinct().collect(Collectors.toList());
        list.forEach(s -> {
            String userPhone = null;
            try {
                userPhone = getClientByUserId(s).getClientPhone();
            } catch (NoSuchElementException e) {
                log.error("Client with id " + s + " not present");
            }
//            twilioService.notifyQuarantin(userPhone);
            log.info("notifyQuarantin(): called. Send Message with twilio service to " + userPhone);
        });

        clientRepository.save(client);
    }
}