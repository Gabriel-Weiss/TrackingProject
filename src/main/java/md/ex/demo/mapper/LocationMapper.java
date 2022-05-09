package md.ex.demo.mapper;

import md.ex.demo.dto.LocationDto;
import md.ex.demo.model.Location;

public class LocationMapper {

    public static LocationDto modelLocationToDtoLocation(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLongitude(location.getLongitude());
        locationDto.setLatitude(location.getLatitude());
        locationDto.setAltitude(location.getAltitude());
        locationDto.setTime(location.getTime());
        return locationDto;
    }

    public static Location dtoLocationToModelLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setAltitude(locationDto.getAltitude());
        location.setLongitude(locationDto.getLongitude());
        location.setLatitude(locationDto.getLatitude());
        location.setTime(locationDto.getTime());
        return location;
    }
}
