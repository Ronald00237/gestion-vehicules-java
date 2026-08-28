
package com.ronald.fleetops.driver.infrastructure.persistence;

import com.ronald.fleetops.driver.domain.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryDriverRepositoryTest {

    @Test
    public void shouldSaveAndFindDriverById() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-007"
        );

        repository.save(driver);

        assertTrue(repository.findById(driver.getId()).isPresent());
        assertSame(
                driver,
                repository.findById(driver.getId()).orElseThrow()
        );
    }

    @Test
    public void shouldFindDriverByLicenseNumber() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-008"
        );

        repository.save(driver);

        assertTrue(
                repository.findByLicenseNumber("LICENSE-008").isPresent()
        );

        assertSame(
                driver,
                repository.findByLicenseNumber("LICENSE-008")
                        .orElseThrow()
        );
    }

    @Test
    public void shouldReturnAllDrivers() {
        InMemoryDriverRepository repository =
                new InMemoryDriverRepository();

        repository.save(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-009"
        ));

        repository.save(new Driver(
                "Alex",
                "Martin",
                "LICENSE-010"
        ));

        assertEquals(2, repository.findAll().size());
    }
}