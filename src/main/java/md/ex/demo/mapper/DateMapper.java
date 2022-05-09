package md.ex.demo.mapper;

import md.ex.demo.dto.DateDto;
import md.ex.demo.dto.LocationDto;
import md.ex.demo.model.Date;
import md.ex.demo.model.Location;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DateMapper {

    public static DateDto modelDateToDtoDate(Date date) {
        DateDto dateDto = new DateDto();
        List<LocationDto> locationsDto = date.getLocations().stream()
                .map(LocationMapper::modelLocationToDtoLocation)
                .collect(Collectors.toList());
        Set<String> savedCodes = date.getSavedCodes();
        dateDto.setDate(date.getDate());
        dateDto.setLocations(locationsDto);
        dateDto.setCodes(savedCodes);
        return dateDto;
    }

    public static Date dtoDateToModelDate(DateDto dateDto) {
        Date date = new Date();
        List<Location> locations = dateDto.getLocations().stream()
                .map(LocationMapper::dtoLocationToModelLocation)
                .collect(Collectors.toList());
        Set<String> codes = dateDto.getCodes();
        date.setDate(dateDto.getDate());
        date.setLocations(locations);
        date.setSavedCodes(codes);
        return date;
    }
}