package com.example.tms.Service;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Location;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.CameraRepository;
import com.example.tms.Repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CameraService {

    private CameraRepository cameraRepository;
    private LocationRepository locationRepository;

    public CameraService(CameraRepository cameraRepository, LocationRepository locationRepository) {
        this.cameraRepository = cameraRepository;
        this.locationRepository = locationRepository;
    }

    public CustomResponseEntity<?> getCameraById(long id) {
        Optional<Camera> camera = cameraRepository.findById(id);
        if(camera.isPresent()){
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, camera.get());
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public CustomResponseEntity<?> getAllCameras() {
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, cameraRepository.findAll());
    }

    public CustomResponseEntity<?> createCamera(Camera camera) {
        Optional<Location> location = locationRepository.findByName(camera.getLocationName());
        if(location.isPresent()){
            camera.setLocation(location.get());
            cameraRepository.save(camera);
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }
}
