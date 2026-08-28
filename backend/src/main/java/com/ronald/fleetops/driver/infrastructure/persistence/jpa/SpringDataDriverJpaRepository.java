
package com.ronald.fleetops.driver.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataDriverJpaRepository
        extends JpaRepository<DriverEntity, UUID> {

    Optional<DriverEntity> findByLicenseNumber(
            String licenseNumber
    );
}