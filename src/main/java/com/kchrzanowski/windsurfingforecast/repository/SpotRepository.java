package com.kchrzanowski.windsurfingforecast.repository;

import com.kchrzanowski.windsurfingforecast.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    void deleteSpotById(Long id);
}
