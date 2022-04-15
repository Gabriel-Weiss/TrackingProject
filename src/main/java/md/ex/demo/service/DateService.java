package md.ex.demo.service;

import lombok.AllArgsConstructor;
import md.ex.demo.dto.DateDto;
import md.ex.demo.mapper.Mapper;
import md.ex.demo.model.Date;
import md.ex.demo.model.Location;
import md.ex.demo.repository.DateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DateService {

    private final DateRepository dateRepository;
    private final LocationService locationService;

    public Date getDateById(Long id) {
        return dateRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "Child object with id: " + id + " not present"
                        )
                );
    }

    public DateDto getDate(Long id) {
        Date date = getDateById(id);
        return Mapper.modelDateToDtoDate(date);
    }

    public DateDto addDate(DateDto dateDto) {
        List<Location> locations = dateDto.getLocations().stream()
                .map(Mapper::dtoLocationToModelLocation)
                .collect(Collectors.toList());
        Date date = new Date();
        date.setDate(dateDto.getDate());
        locations.forEach(date::addLocation);
        dateRepository.save(date);
        return Mapper.modelDateToDtoDate(date);
    }

    public List<DateDto> getDates() {
        List<Date> dates = dateRepository.findAll();
        return Mapper.listModelDateToDtoDate(dates);
    }

    public DateDto deleteDate(Long id, DateDto dateDto) {
        Date date = getDateById(id);
        dateRepository.delete(date);
        return Mapper.modelDateToDtoDate(date);
    }
}
