

package com.ronald.fleetops.driver.infrastructure.persistence.jpa;

import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgreSqlDriverRepository
        implements DriverRepository {

    private final SpringDataDriverJpaRepository jpaRepository;

    public PostgreSqlDriverRepository(
            SpringDataDriverJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Driver save(Driver driver) {
        DriverEntity savedEntity = jpaRepository.save(
                DriverEntity.fromDomain(driver)
        );

        return savedEntity.toDomain();
    }

    @Override
    public Optional<Driver> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(DriverEntity::toDomain);
    }

    @Override
    public Optional<Driver> findByLicenseNumber(
            String licenseNumber
    ) {
        return jpaRepository
                .findByLicenseNumber(licenseNumber)
                .map(DriverEntity::toDomain);
    }

    @Override
    public List<Driver> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(DriverEntity::toDomain)
                .toList();
    }
}