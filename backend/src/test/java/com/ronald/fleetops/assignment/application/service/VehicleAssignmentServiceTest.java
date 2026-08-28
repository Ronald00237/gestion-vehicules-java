
package com.ronald.fleetops.assignment.application.service;

import com.ronald.fleetops.assignment.application.exception.InvalidVehicleAssignmentException;
import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import com.ronald.fleetops.assignment.infrastructure.persistence.InMemoryVehicleAssignmentRepository;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.driver.infrastructure.persistence.InMemoryDriverRepository;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleStatus;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import com.ronald.fleetops.vehicle.infrastructure.persistence.InMemoryVehicleRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import com.ronald.fleetops.assignment.application.exception.VehicleAssignmentNotFoundException;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleAssignmentServiceTest {

    @Test
    public void shouldAssignAvailableVehicleToActiveDriver() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle vehicle = new Vehicle(
                "ASSIGNMENT-VIN-001",
                "ASSIGNMENT-PLATE-001",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-001"
        );

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        VehicleAssignment assignment =
                service.startAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        Instant.parse("2026-08-27T10:00:00Z")
                );

        assertEquals(
                VehicleStatus.ASSIGNED,
                vehicle.getStatus()
        );
        assertEquals(vehicle.getId(), assignment.getVehicleId());
        assertEquals(driver.getId(), assignment.getDriverId());
        assertSame(
                assignment,
                assignmentRepository
                        .findById(assignment.getId())
                        .orElseThrow()
        );
    }
    @Test
    public void shouldRejectInactiveDriver() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle vehicle = new Vehicle(
                "ASSIGNMENT-VIN-002",
                "ASSIGNMENT-PLATE-002",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-002"
        );

        driver.deactivate();

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.startAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        Instant.now()
                )
        );

        assertEquals(
                "Inactive drivers cannot be assigned to vehicles",
                exception.getMessage()
        );

        assertEquals(
                VehicleStatus.AVAILABLE,
                vehicle.getStatus()
        );
    }

    @Test
    public void shouldRejectSecondActiveAssignmentForVehicle() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle vehicle = new Vehicle(
                "ASSIGNMENT-VIN-003",
                "ASSIGNMENT-PLATE-003",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Driver firstDriver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-003"
        );

        Driver secondDriver = new Driver(
                "Alex",
                "Martin",
                "ASSIGNMENT-LICENSE-004"
        );

        vehicleRepository.save(vehicle);
        driverRepository.save(firstDriver);
        driverRepository.save(secondDriver);

        service.startAssignment(
                vehicle.getId(),
                firstDriver.getId(),
                Instant.parse("2026-08-27T10:00:00Z")
        );

        InvalidVehicleAssignmentException exception = assertThrows(
                InvalidVehicleAssignmentException.class,
                () -> service.startAssignment(
                        vehicle.getId(),
                        secondDriver.getId(),
                        Instant.parse("2026-08-27T11:00:00Z")
                )
        );

        assertEquals(
                "Vehicle already has an active assignment",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectSecondActiveAssignmentForDriver() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle firstVehicle = new Vehicle(
                "ASSIGNMENT-VIN-004",
                "ASSIGNMENT-PLATE-004",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Vehicle secondVehicle = new Vehicle(
                "ASSIGNMENT-VIN-005",
                "ASSIGNMENT-PLATE-005",
                "Honda",
                "Civic",
                2023,
                15000L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-005"
        );

        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
        driverRepository.save(driver);

        service.startAssignment(
                firstVehicle.getId(),
                driver.getId(),
                Instant.parse("2026-08-27T10:00:00Z")
        );

        InvalidVehicleAssignmentException exception = assertThrows(
                InvalidVehicleAssignmentException.class,
                () -> service.startAssignment(
                        secondVehicle.getId(),
                        driver.getId(),
                        Instant.parse("2026-08-27T11:00:00Z")
                )
        );

        assertEquals(
                "Driver already has an active assignment",
                exception.getMessage()
        );

        assertEquals(
                VehicleStatus.AVAILABLE,
                secondVehicle.getStatus()
        );
    }
    @Test
    public void shouldEndAssignmentAndReleaseVehicle() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle vehicle = new Vehicle(
                "ASSIGNMENT-VIN-006",
                "ASSIGNMENT-PLATE-006",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-006"
        );

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        VehicleAssignment assignment =
                service.startAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        Instant.parse("2026-08-27T10:00:00Z")
                );

        VehicleAssignment endedAssignment =
                service.endAssignment(
                        assignment.getId(),
                        Instant.parse("2026-08-27T12:00:00Z")
                );

        assertEquals(
                VehicleStatus.AVAILABLE,
                vehicle.getStatus()
        );

        assertEquals(
                Instant.parse("2026-08-27T12:00:00Z"),
                endedAssignment.getEndedAt().orElseThrow()
        );
    }
    @Test
    public void shouldRejectUnknownAssignmentId() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        UUID unknownId = UUID.randomUUID();

        VehicleAssignmentNotFoundException exception =
                assertThrows(
                        VehicleAssignmentNotFoundException.class,
                        () -> service.endAssignment(
                                unknownId,
                                Instant.now()
                        )
                );

        assertEquals(
                "Vehicle assignment not found with id " + unknownId,
                exception.getMessage()
        );
    }
    @Test
    public void shouldReturnAssignmentHistory() {
        InMemoryVehicleRepository vehicleRepository =
                new InMemoryVehicleRepository();

        InMemoryDriverRepository driverRepository =
                new InMemoryDriverRepository();

        InMemoryVehicleAssignmentRepository assignmentRepository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignmentService service =
                new VehicleAssignmentService(
                        vehicleRepository,
                        driverRepository,
                        assignmentRepository
                );

        Vehicle vehicle = new Vehicle(
                "ASSIGNMENT-VIN-007",
                "ASSIGNMENT-PLATE-007",
                "Toyota",
                "Corolla",
                2022,
                25000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "ASSIGNMENT-LICENSE-007"
        );

        vehicleRepository.save(vehicle);
        driverRepository.save(driver);

        service.startAssignment(
                vehicle.getId(),
                driver.getId(),
                Instant.now()
        );

        assertEquals(1, service.getAssignmentHistory().size());
    }
}