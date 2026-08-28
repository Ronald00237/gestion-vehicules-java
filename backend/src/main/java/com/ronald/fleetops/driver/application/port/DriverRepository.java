
package com.ronald.fleetops.driver.application.port;

import com.ronald.fleetops.driver.domain.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {

    Driver save(Driver driver);

    Optional<Driver> findById(UUID id);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    List<Driver> findAll();
}