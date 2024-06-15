package com.example.tms.Repository;

import com.example.tms.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);

    @Query("select new com.example.tms.Entity.Location(l.id, l.name) from Location l")
    List<Location> findAllLocationsIdAndName();

}
