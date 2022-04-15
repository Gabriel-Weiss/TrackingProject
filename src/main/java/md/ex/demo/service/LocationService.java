package md.ex.demo.service;

import lombok.AllArgsConstructor;
import md.ex.demo.dto.LocationDto;
import md.ex.demo.mapper.Mapper;
import md.ex.demo.model.Location;
import md.ex.demo.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "Location object with id: " + id + " not present"
                        )
                );
    }

    public LocationDto getLocation(Long id) {
        Location location = getLocationById(id);
        return Mapper.modelLocationToDtoLocation(location);
    }

    public LocationDto addLocation(LocationDto locationDto) {
        Location location = Mapper.dtoLocationToModelLocation(locationDto);
        Location save = locationRepository.save(location);
        return Mapper.modelLocationToDtoLocation(save);
    }

    public List<LocationDto> getLocations() {
        List<Location> locations = locationRepository.findAll();
        return Mapper.listModelLocationToDtoLocation(locations);
    }

    public LocationDto deleteLocation(Long id, LocationDto locationDto) {
        Location location = getLocationById(id);
        locationRepository.delete(location);
        return Mapper.modelLocationToDtoLocation(location);
    }
}
