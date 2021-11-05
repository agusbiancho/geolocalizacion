package com.meli.geolocalizacion.model.repository;

import com.meli.geolocalizacion.model.entity.IPDirection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPDirectionRepository extends JpaRepository<IPDirection, Long> {

    public IPDirection findByIpDirection(String ip);
}
