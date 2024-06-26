package com.example.tms.Controller;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.CameraService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camera")
public class CameraController {

    private CameraService cameraService;

    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getCameraById(@PathVariable long id){
        return cameraService.getCameraById(id);
    }

    @GetMapping("/all")
    public CustomResponseEntity<?> getAllCameras(){
        return cameraService.getAllCameras();
    }

    @PostMapping("/create")
    public CustomResponseEntity<?> createCamera(@RequestBody Camera camera){
        return cameraService.createCamera(camera);
    }
}
