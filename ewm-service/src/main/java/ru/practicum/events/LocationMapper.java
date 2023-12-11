package ru.practicum.events;

import lombok.experimental.UtilityClass;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.model.Location;

@UtilityClass
public class LocationMapper {

    public LocationDto returnLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location returnLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}