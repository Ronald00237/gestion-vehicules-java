
package com.ronald.fleetops.vehicle.infrastructure.persistence.jpa;

import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgreSqlVehicleRepository
        implements VehicleRepository {

    private final SpringDataVehicleJpaRepository jpaRepository;

    public PostgreSqlVehicleRepository(
            SpringDataVehicleJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity =
                VehicleEntity.fromDomain(vehicle);

        VehicleEntity savedEntity =
                jpaRepository.save(entity);

        return savedEntity.toDomain();
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(VehicleEntity::toDomain);
    }

    @Override
    public Optional<Vehicle> findByVin(String vin) {
        return jpaRepository.findByVin(vin)
                .map(VehicleEntity::toDomain);
    }

    @Override
    public List<Vehicle> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(VehicleEntity::toDomain)
                .toList();
    }
}