
package com.ronald.fleetops.assignment.infrastructure.persistence;

import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryVehicleAssignmentRepositoryTest {

    @Test
    public void shouldSaveAndFindAssignmentById() {
        InMemoryVehicleAssignmentRepository repository =
                new InMemoryVehicleAssignmentRepository();

        VehicleAssignment assignment =
                new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        Instant.now()
                );

        repository.save(assignment);

        assertTrue(
                repository.findById(assignment.getId())
                        .isPresent()
        );

        assertSame(
                assignment,
                repository.findById(assignment.getId())
                        .orElseThrow()
        );
    }

    @Test
    public void shouldFindActiveAssignmentByVehicleAndDriver() {
        InMemoryVehicleAssignmentRepository repository =
                new InMemoryVehicleAssignmentRepository();

        UUID vehicleId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        VehicleAssignment assignment =
                new VehicleAssignment(
                        vehicleId,
                        driverId,
                        Instant.now()
                );

        repository.save(assignment);

        assertSame(
                assignment,
                repository.findActiveByVehicleId(vehicleId)
                        .orElseThrow()
        );

        assertSame(
                assignment,
                repository.findActiveByDriverId(driverId)
                        .orElseThrow()
        );
    }

    @Test
    public void shouldIgnoreEndedAssignmentWhenSearchingActive() {
        InMemoryVehicleAssignmentRepository repository =
                new InMemoryVehicleAssignmentRepository();

        UUID vehicleId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        VehicleAssignment assignment =
                new VehicleAssignment(
                        vehicleId,
                        driverId,
                        Instant.parse("2026-08-27T10:00:00Z")
                );

        assignment.end(
                Instant.parse("2026-08-27T11:00:00Z")
        );

        repository.save(assignment);

        assertTrue(
                repository.findActiveByVehicleId(vehicleId)
                        .isEmpty()
        );

        assertTrue(
                repository.findActiveByDriverId(driverId)
                        .isEmpty()
        );
    }


}