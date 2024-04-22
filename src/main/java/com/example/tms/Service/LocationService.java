package com.example.tms.Service;

import com.example.tms.Entity.Location;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public CustomResponseEntity<?> getLocationById(long id) {
        Optional<Location> location = locationRepository.findById(id);
        if(location.isPresent()){
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, location.get());
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public CustomResponseEntity getAllLocations() {
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, locationRepository.findAll());
    }

    public CustomResponseEntity<?> createLocation(Location location) {
        locationRepository.save(location);
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
    }
}
