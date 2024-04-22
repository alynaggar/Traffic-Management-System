package com.example.tms.Repository;

import com.example.tms.Entity.TrafficLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficLightRepository extends JpaRepository<TrafficLight, Long> {
}
