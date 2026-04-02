package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.LocationDto;
import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final ApiClient apiClient;
    private final UserService userService;

    @Transactional
    public void setLocationByCity(String email, String city) {

        UserEntity user = userService.findByEmail(email);


        LocationEntity location = locationRepository.findByCity(city)
                .orElseGet(() -> {
                    LocationDto geo = apiClient.getGeoResponse(city);

                    LocationEntity newLocation = new LocationEntity();
                    newLocation.setCity(city);
                    newLocation.setLatitude(geo.getLatitude());
                    newLocation.setLongitude(geo.getLongitude());

                    return locationRepository.save(newLocation);
                });

        user.setCurrentLocation(location);
        userService.save(user);
    }
}
