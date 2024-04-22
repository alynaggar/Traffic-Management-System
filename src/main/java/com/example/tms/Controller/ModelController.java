package com.example.tms.Controller;

import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.ModelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/predict")
    public CustomResponseEntity<?> predict(@RequestBody String path){
        return modelService.predict(path);
    }
}