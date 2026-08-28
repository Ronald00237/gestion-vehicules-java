
package com.ronald.fleetops.driver.application.service;
import com.ronald.fleetops.driver.application.exception.DuplicateDriverLicenseException;
import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.driver.application.exception.DriverNotFoundException;
import java.util.List;
import java.util.UUID;
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver registerDriver(Driver driver) {
        driverRepository
                .findByLicenseNumber(driver.getLicenseNumber())
                .ifPresent(existingDriver -> {
                    throw new DuplicateDriverLicenseException(
                            "A driver with license number "
                                    + driver.getLicenseNumber()
                                    + " already exists"
                    );
                });

        return driverRepository.save(driver);
    }
    public Driver getDriverById(UUID id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(
                        "Driver not found with id " + id
                ));
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
    public Driver deactivateDriver(UUID id) {
        Driver driver = getDriverById(id);

        driver.deactivate();

        return driverRepository.save(driver);
    }
}