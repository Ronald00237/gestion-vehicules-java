
package com.ronald.fleetops.driver.infrastructure.persistence;

import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryDriverRepository implements DriverRepository {

    private final Map<UUID, Driver> drivers = new HashMap<>();

    @Override
    public Driver save(Driver driver) {
        drivers.put(driver.getId(), driver);
        return driver;
    }

    @Override
    public Optional<Driver> findById(UUID id) {
        return Optional.ofNullable(drivers.get(id));
    }

    @Override
    public Optional<Driver> findByLicenseNumber(String licenseNumber) {
        return drivers.values()
                .stream()
                .filter(driver ->
                        driver.getLicenseNumber().equals(licenseNumber)
                )
                .findFirst();
    }

    @Override
    public List<Driver> findAll() {
        return new ArrayList<>(drivers.values());
    }
}