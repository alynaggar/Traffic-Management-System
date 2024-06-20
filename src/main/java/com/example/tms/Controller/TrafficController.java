package com.example.tms.Controller;

import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.TrafficService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("")
    public CustomResponseEntity<?> calculateTraffic(){
        return trafficService.calculateTraffic();
    }
}
