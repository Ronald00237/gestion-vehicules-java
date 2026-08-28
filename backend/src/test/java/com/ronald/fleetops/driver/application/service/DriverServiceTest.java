
package com.ronald.fleetops.driver.application.service;

import com.ronald.fleetops.driver.application.exception.DriverNotFoundException;
import com.ronald.fleetops.driver.application.exception.DuplicateDriverLicenseException;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.driver.infrastructure.persistence.InMemoryDriverRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DriverServiceTest {

    @Test
    public void shouldRegisterDriver() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-011"
        );

        Driver registeredDriver = service.registerDriver(driver);

        assertSame(driver, registeredDriver);
        assertTrue(repository.findById(driver.getId()).isPresent());
    }

    @Test
    public void shouldRejectDuplicateLicenseNumber() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        service.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-012"
        ));

        DuplicateDriverLicenseException exception = assertThrows(
                DuplicateDriverLicenseException.class,
                () -> service.registerDriver(new Driver(
                        "Alex",
                        "Martin",
                        "LICENSE-012"
                ))
        );

        assertEquals(
                "A driver with license number LICENSE-012 already exists",
                exception.getMessage()
        );
    }

    @Test
    public void shouldReturnDriverById() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        Driver driver = service.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-013"
        ));

        Driver foundDriver = service.getDriverById(driver.getId());

        assertSame(driver, foundDriver);
    }
    @Test
    public void shouldRejectUnknownDriverId() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        UUID unknownId = UUID.randomUUID();

        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> service.getDriverById(unknownId)
        );

        assertEquals(
                "Driver not found with id " + unknownId,
                exception.getMessage()
        );
    }
    @Test
    public void shouldReturnAllDrivers() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        service.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-014"
        ));

        service.registerDriver(new Driver(
                "Alex",
                "Martin",
                "LICENSE-015"
        ));

        assertEquals(2, service.getAllDrivers().size());
    }
    @Test
    public void shouldDeactivateDriver() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        DriverService service = new DriverService(repository);

        Driver driver = service.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-016"
        ));

        Driver deactivatedDriver =
                service.deactivateDriver(driver.getId());

        assertFalse(deactivatedDriver.isActive());
    }
}