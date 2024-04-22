package com.example.tms.Service;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.CameraRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CameraService {

    private CameraRepository cameraRepository;

    public CameraService(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
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
}
