
package com.ronald.fleetops.driver.infrastructure.persistence.jpa;

import com.ronald.fleetops.assignment.infrastructure.persistence.jpa.SpringDataVehicleAssignmentJpaRepository;
import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostgreSqlDriverRepositoryTest {

    @Autowired
    private SpringDataVehicleAssignmentJpaRepository
            assignmentJpaRepository;

    @Autowired
    private SpringDataDriverJpaRepository jpaRepository;

    private DriverRepository repository;

    @BeforeEach
    public void setUp() {
        assignmentJpaRepository.deleteAll();
        jpaRepository.deleteAll();

        repository = new PostgreSqlDriverRepository(
                jpaRepository
        );
    }

    @Test
    public void shouldSaveAndFindDriverById() {
        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-025"
        );

        repository.save(driver);

        Driver restoredDriver = repository
                .findById(driver.getId())
                .orElseThrow();

        assertEquals(driver.getId(), restoredDriver.getId());
        assertEquals(
                "LICENSE-025",
                restoredDriver.getLicenseNumber()
        );
        assertTrue(restoredDriver.isActive());
    }

    @Test
    public void shouldFindDriverByLicenseNumber() {
        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-026"
        );

        repository.save(driver);

        Driver restoredDriver = repository
                .findByLicenseNumber("LICENSE-026")
                .orElseThrow();

        assertEquals(driver.getId(), restoredDriver.getId());
        assertEquals(
                "LICENSE-026",
                restoredDriver.getLicenseNumber()
        );
    }

    @Test
    public void shouldPersistDriverDeactivation() {
        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-027"
        );

        repository.save(driver);

        driver.deactivate();
        repository.save(driver);

        Driver restoredDriver = repository
                .findById(driver.getId())
                .orElseThrow();

        assertFalse(restoredDriver.isActive());
    }
    @Test
    public void shouldReturnAllDrivers() {
        repository.save(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-028"
        ));

        repository.save(new Driver(
                "Alex",
                "Martin",
                "LICENSE-029"
        ));

        assertEquals(2, repository.findAll().size());
    }

    @Test
    public void shouldReturnEmptyForUnknownDriverId() {
        assertTrue(
                repository.findById(UUID.randomUUID()).isEmpty()
        );
    }
}