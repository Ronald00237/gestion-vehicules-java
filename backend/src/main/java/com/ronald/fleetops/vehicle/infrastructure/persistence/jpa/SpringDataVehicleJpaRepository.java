package com.ronald.fleetops.vehicle.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataVehicleJpaRepository
        extends JpaRepository<VehicleEntity, UUID> {

    Optional<VehicleEntity> findByVin(String vin);
}