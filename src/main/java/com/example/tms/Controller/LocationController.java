package com.example.tms.Controller;

import com.example.tms.Entity.Location;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.LocationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getLocationById(@PathVariable long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/all")
    public CustomResponseEntity<?> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping("/create")
    public CustomResponseEntity<?> createLocation(@RequestBody Location location) {
        try {
            return locationService.createLocation(location);
        } catch (
                DataIntegrityViolationException dive) {
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
    }
}
