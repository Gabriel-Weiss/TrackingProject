package md.ex.demo.mapper;

import md.ex.demo.dto.DateDto;
import md.ex.demo.dto.LocationDto;
import md.ex.demo.dto.UserDto;
import md.ex.demo.model.Date;
import md.ex.demo.model.Location;
import md.ex.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static LocationDto modelLocationToDtoLocation(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLongitude(location.getLongitude());
        locationDto.setLatitude(location.getLatitude());
        locationDto.setAltitude(location.getAltitude());
        locationDto.setTime(location.getTime());
        return locationDto;
    }

    public static List<LocationDto> listModelLocationToDtoLocation(List<Location> locations) {
        List<LocationDto> locationsDto = new ArrayList<>(locations.size());
        locations.forEach(location -> locationsDto.add(modelLocationToDtoLocation(location)));
        return locationsDto;
    }

    public static Location dtoLocationToModelLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setAltitude(locationDto.getAltitude());
        location.setLongitude(locationDto.getLongitude());
        location.setLatitude(locationDto.getLatitude());
        location.setTime(locationDto.getTime());
        return location;
    }
//
//    public static List<Location> listDtoLocationToModelLocation(List<LocationDto> locationsDto) {
//        List<Location> locations = new ArrayList<>(locationsDto.size());
//        locationsDto.forEach(locationDto -> locations.add(dtoLocationToModelLocation(locationDto)));
//        return locations;
//    }

    public static DateDto modelDateToDtoDate(Date date) {
        DateDto dateDto = new DateDto();
        List<LocationDto> locationsDto = date.getLocations().stream()
                .map(Mapper::modelLocationToDtoLocation)
                .collect(Collectors.toList());
        Set<String> savedCodes = date.getSavedCodes();
        dateDto.setDate(date.getDate());
        dateDto.setLocations(locationsDto);
        dateDto.setCodes(savedCodes);
        return dateDto;
    }


    public static List<DateDto> listModelDateToDtoDate(List<Date> dates) {
        List<DateDto> datesDto = new ArrayList<>(dates.size());
        dates.forEach(date -> datesDto.add(modelDateToDtoDate(date)));
        return datesDto;
    }

    public static Date dtoDateToModelDate(DateDto dateDto) {
        Date date = new Date();
        List<Location> locations = dateDto.getLocations().stream()
                .map(Mapper::dtoLocationToModelLocation)
                .collect(Collectors.toList());
        Set<String> codes = dateDto.getCodes();
        date.setDate(dateDto.getDate());
        date.setLocations(locations);
        date.setSavedCodes(codes);
        return date;
    }
//
//    public static List<Date> listDtoDateToModelDate(List<DateDto> datesDto) {
//        List<Date> dates = new ArrayList<>(datesDto.size());
//        datesDto.forEach(dateDto -> dates.add(dtoDateToModelDate(dateDto)));
//        return dates;
//    }

    public static UserDto modelUserToDtoUser(User user) {
        UserDto userDto = new UserDto();
        List<DateDto> datesDto = user.getDates().stream()
                .map(Mapper::modelDateToDtoDate)
                .collect(Collectors.toList());
        userDto.setUserId(user.getUserId());
        userDto.setUserStatus(user.getUserStatus());
        userDto.setDates(datesDto);
        return userDto;
    }
//
//    public static List<UserDto> listModelUserToDtoUser(List<User> users) {
//        List<UserDto> usersDto = new ArrayList<>(users.size());
//        users.forEach(user -> usersDto.add(modelUserToDtoUser(user)));
//        return usersDto;
//    }
//
//    public static User dtoUserToModelUser(UserDto userDto) {
//        User user = new User();
//        user.setUserId(userDto.getUserId());
//        user.setUserStatus(userDto.getUserStatus());
//        List<Date> dates = userDto.getDates().stream()
//                .map(Mapper::dtoDateToModelDate)
//                .collect(Collectors.toList());
//        user.setDates(dates);
//        return user;
//    }
//
//    public static List<User> listDtoUserToModelUser(List<UserDto> usersDto) {
//        List<User> users = new ArrayList<>(usersDto.size());
//        usersDto.forEach(userDto -> users.add(dtoUserToModelUser(userDto)));
//        return users;
//    }
}
