
package com.ronald.fleetops.assignment.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleAssignmentTest {

    @Test
    public void shouldCreateActiveVehicleAssignment() {
        UUID vehicleId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();
        Instant startedAt = Instant.now();

        VehicleAssignment assignment =
                new VehicleAssignment(
                        vehicleId,
                        driverId,
                        startedAt
                );

        assertNotNull(assignment.getId());
        assertEquals(vehicleId, assignment.getVehicleId());
        assertEquals(driverId, assignment.getDriverId());
        assertEquals(startedAt, assignment.getStartedAt());
        assertFalse(assignment.getEndedAt().isPresent());
    }

    @Test
    public void shouldRejectNullVehicleId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new VehicleAssignment(
                        null,
                        UUID.randomUUID(),
                        Instant.now()
                )
        );

        assertEquals(
                "Vehicle id must not be null",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectNullDriverId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new VehicleAssignment(
                        UUID.randomUUID(),
                        null,
                        Instant.now()
                )
        );

        assertEquals(
                "Driver id must not be null",
                exception.getMessage()
        );
    }
    @Test
    public void shouldRejectNullStartDate() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null
                )
        );

        assertEquals(
                "Start date must not be null",
                exception.getMessage()
        );
    }
    @Test
    public void shouldEndVehicleAssignment() {
        Instant startedAt = Instant.parse(
                "2026-08-27T10:00:00Z"
        );

        Instant endedAt = Instant.parse(
                "2026-08-27T12:00:00Z"
        );

        VehicleAssignment assignment =
                new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        startedAt
                );

        assignment.end(endedAt);

        assertEquals(
                endedAt,
                assignment.getEndedAt().orElseThrow()
        );
    }
    @Test
    public void shouldRejectEndDateBeforeStartDate() {
        Instant startedAt = Instant.parse(
                "2026-08-27T10:00:00Z"
        );

        VehicleAssignment assignment =
                new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        startedAt
                );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> assignment.end(
                        Instant.parse("2026-08-27T09:00:00Z")
                )
        );

        assertEquals(
                "End date must be after start date",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectEndingAssignmentTwice() {
        VehicleAssignment assignment =
                new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        Instant.parse("2026-08-27T10:00:00Z")
                );

        assignment.end(
                Instant.parse("2026-08-27T11:00:00Z")
        );

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> assignment.end(
                        Instant.parse("2026-08-27T12:00:00Z")
                )
        );

        assertEquals(
                "Assignment has already ended",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectNullEndDate() {
        VehicleAssignment assignment =
                new VehicleAssignment(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        Instant.now()
                );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> assignment.end(null)
        );

        assertEquals(
                "End date must not be null",
                exception.getMessage()
        );
    }
    @Test
    public void shouldRestoreEndedVehicleAssignment() {
        UUID assignmentId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        Instant startedAt = Instant.parse(
                "2026-08-27T10:00:00Z"
        );

        Instant endedAt = Instant.parse(
                "2026-08-27T12:00:00Z"
        );

        VehicleAssignment assignment =
                VehicleAssignment.restore(
                        assignmentId,
                        vehicleId,
                        driverId,
                        startedAt,
                        endedAt
                );

        assertEquals(assignmentId, assignment.getId());
        assertEquals(vehicleId, assignment.getVehicleId());
        assertEquals(driverId, assignment.getDriverId());
        assertEquals(startedAt, assignment.getStartedAt());
        assertEquals(
                endedAt,
                assignment.getEndedAt().orElseThrow()
        );
    }

    @Test
    public void shouldRejectNullIdWhenRestoringAssignment() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> VehicleAssignment.restore(
                        null,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        Instant.now(),
                        null
                )
        );

        assertEquals(
                "Assignment id must not be null",
                exception.getMessage()
        );
    }


}