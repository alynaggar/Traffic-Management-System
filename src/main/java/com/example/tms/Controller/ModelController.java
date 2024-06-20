package com.example.tms.Controller;

import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.ModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/traffic")
    public CustomResponseEntity<?> trafficPredict(@RequestBody String path){
        return modelService.trafficPredict(path);
    }

    @PostMapping("/accident")
    public CustomResponseEntity<?> accidentPredict(@RequestBody String path){
        return modelService.accidentPredict(path);
    }

    @PostMapping("/emergency/audio")
    public CustomResponseEntity<?> emergencyAudioPredict(@RequestBody String path){
        return modelService.emergencyAudioPredict(path);
    }
}
