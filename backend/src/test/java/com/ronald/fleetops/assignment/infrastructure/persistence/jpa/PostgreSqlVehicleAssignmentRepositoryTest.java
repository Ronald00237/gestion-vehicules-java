
package com.ronald.fleetops.assignment.infrastructure.persistence.jpa;

import com.ronald.fleetops.assignment.application.port.VehicleAssignmentRepository;
import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.driver.infrastructure.persistence.jpa.SpringDataDriverJpaRepository;
import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import com.ronald.fleetops.vehicle.infrastructure.persistence.jpa.SpringDataVehicleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostgreSqlVehicleAssignmentRepositoryTest {

    @Autowired
    private SpringDataVehicleAssignmentJpaRepository
            assignmentJpaRepository;

    @Autowired
    private SpringDataVehicleJpaRepository vehicleJpaRepository;

    @Autowired
    private SpringDataDriverJpaRepository driverJpaRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    private VehicleAssignmentRepository assignmentRepository;

    @BeforeEach
    public void setUp() {
        assignmentJpaRepository.deleteAll();
        vehicleJpaRepository.deleteAll();
        driverJpaRepository.deleteAll();

        assignmentRepository =
                new PostgreSqlVehicleAssignmentRepository(
                        assignmentJpaRepository
                );
    }

    @Test
    public void shouldSaveAndFindAssignmentById() {
        Vehicle vehicle = vehicleRepository.save(
                new Vehicle(
                        "JPA-ASSIGNMENT-VIN-001",
                        "JPA-ASSIGNMENT-PLATE-001",
                        "Toyota",
                        "Corolla",
                        2022,
                        25000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        Driver driver = driverRepository.save(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "JPA-ASSIGNMENT-LICENSE-001"
                )
        );

        VehicleAssignment assignment =
                new VehicleAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        Instant.parse("2026-08-27T10:00:00Z")
                );

        assignmentRepository.save(assignment);

        VehicleAssignment restoredAssignment =
                assignmentRepository
                        .findById(assignment.getId())
                        .orElseThrow();

        assertEquals(
                assignment.getId(),
                restoredAssignment.getId()
        );
        assertEquals(
                vehicle.getId(),
                restoredAssignment.getVehicleId()
        );
        assertEquals(
                driver.getId(),
                restoredAssignment.getDriverId()
        );
        assertTrue(restoredAssignment.getEndedAt().isEmpty());
    }
    @Test
    public void shouldFindActiveAssignmentByVehicleAndDriver() {
        Vehicle vehicle = vehicleRepository.save(
                new Vehicle(
                        "JPA-ASSIGNMENT-VIN-002",
                        "JPA-ASSIGNMENT-PLATE-002",
                        "Toyota",
                        "Corolla",
                        2022,
                        25000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        Driver driver = driverRepository.save(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "JPA-ASSIGNMENT-LICENSE-002"
                )
        );

        VehicleAssignment assignment =
                assignmentRepository.save(
                        new VehicleAssignment(
                                vehicle.getId(),
                                driver.getId(),
                                Instant.now()
                        )
                );

        assertEquals(
                assignment.getId(),
                assignmentRepository
                        .findActiveByVehicleId(vehicle.getId())
                        .orElseThrow()
                        .getId()
        );

        assertEquals(
                assignment.getId(),
                assignmentRepository
                        .findActiveByDriverId(driver.getId())
                        .orElseThrow()
                        .getId()
        );
    }

    @Test
    public void shouldPersistEndAndRemoveAssignmentFromActiveSearches() {
        Vehicle vehicle = vehicleRepository.save(
                new Vehicle(
                        "JPA-ASSIGNMENT-VIN-003",
                        "JPA-ASSIGNMENT-PLATE-003",
                        "Toyota",
                        "Corolla",
                        2022,
                        25000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        Driver driver = driverRepository.save(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "JPA-ASSIGNMENT-LICENSE-003"
                )
        );

        VehicleAssignment assignment =
                assignmentRepository.save(
                        new VehicleAssignment(
                                vehicle.getId(),
                                driver.getId(),
                                Instant.parse(
                                        "2026-08-27T10:00:00Z"
                                )
                        )
                );

        assignment.end(
                Instant.parse("2026-08-27T12:00:00Z")
        );

        assignmentRepository.save(assignment);

        assertTrue(
                assignmentRepository
                        .findActiveByVehicleId(vehicle.getId())
                        .isEmpty()
        );

        assertTrue(
                assignmentRepository
                        .findActiveByDriverId(driver.getId())
                        .isEmpty()
        );
    }

    @Test
    public void shouldReturnAssignmentHistory() {
        Vehicle vehicle = vehicleRepository.save(
                new Vehicle(
                        "JPA-ASSIGNMENT-VIN-004",
                        "JPA-ASSIGNMENT-PLATE-004",
                        "Toyota",
                        "Corolla",
                        2022,
                        25000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        Driver driver = driverRepository.save(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "JPA-ASSIGNMENT-LICENSE-004"
                )
        );

        assignmentRepository.save(
                new VehicleAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        Instant.now()
                )
        );

        assertEquals(1, assignmentRepository.findAll().size());
    }
}