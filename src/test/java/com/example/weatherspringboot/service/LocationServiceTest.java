package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.LocationDto;
import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ApiClient apiClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private LocationService locationService;

    @Test
    void setLocationByCity_shouldAttachExistingLocationToUser() {
        String email = "test@test.com";
        String city = "Kyiv";

        UserEntity user = new UserEntity();
        LocationEntity location = new LocationEntity();
        location.setCity(city);
        location.setLatitude(50.45);
        location.setLongitude(30.52);

        when(userService.findByEmail(email)).thenReturn(user);
        when(locationRepository.findByCity(city)).thenReturn(Optional.of(location));

        locationService.setLocationByCity(email, city);

        assertEquals(location, user.getCurrentLocation());
        verify(userService).findByEmail(email);
        verify(locationRepository).findByCity(city);
        verify(userService).save(user);
        verify(apiClient, never()).getGeoResponse(anyString());
        verify(locationRepository, never()).save(any(LocationEntity.class));
    }

    @Test
    void setLocationByCity_shouldCreateLocation_whenCityNotExists() {
        String email = "test@test.com";
        String city = "Lviv";

        UserEntity user = new UserEntity();

        LocationDto geo = new LocationDto();
        geo.setLatitude(49.84);
        geo.setLongitude(24.03);

        LocationEntity savedLocation = new LocationEntity();
        savedLocation.setCity(city);
        savedLocation.setLatitude(49.84);
        savedLocation.setLongitude(24.03);

        when(userService.findByEmail(email)).thenReturn(user);
        when(locationRepository.findByCity(city)).thenReturn(Optional.empty());
        when(apiClient.getGeoResponse(city)).thenReturn(geo);
        when(locationRepository.save(any(LocationEntity.class))).thenReturn(savedLocation);

        locationService.setLocationByCity(email, city);

        ArgumentCaptor<LocationEntity> captor = ArgumentCaptor.forClass(LocationEntity.class);
        verify(locationRepository).save(captor.capture());

        LocationEntity newLocation = captor.getValue();
        assertEquals(city, newLocation.getCity());
        assertEquals(49.84, newLocation.getLatitude());
        assertEquals(24.03, newLocation.getLongitude());

        assertEquals(savedLocation, user.getCurrentLocation());
        verify(userService).save(user);
    }
}