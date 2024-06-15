package com.example.tms.Repository;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    @Query("SELECT new com.example.tms.Entity.Camera(c.id, c.name, c.url) FROM Camera c WHERE c.location.name = :locationName")
    List<Camera> findCamerasIdAndNameAndUrlByLocationName(@Param("locationName") String locationName);
    List<Camera> findAllByLocation(Location location);

}
