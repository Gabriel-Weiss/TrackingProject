package md.ex.demo.mapper;

import md.ex.demo.dto.ClientDto;
import md.ex.demo.dto.DateDto;
import md.ex.demo.model.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientDto modelClientToDtoClient(Client client) {
        ClientDto clientDto = new ClientDto();
        List<DateDto> datesDto = client.getDates().stream()
                .map(DateMapper::modelDateToDtoDate)
                .collect(Collectors.toList());
        clientDto.setClientId(client.getClientId());
        clientDto.setClientPhone(client.getPhone());
        clientDto.setClientStatus(client.getClientStatus());
        clientDto.setDates(datesDto);
        return clientDto;
    }
}
