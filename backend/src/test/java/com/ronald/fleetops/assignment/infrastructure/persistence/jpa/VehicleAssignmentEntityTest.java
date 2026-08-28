

package com.ronald.fleetops.assignment.infrastructure.persistence.jpa;

import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleAssignmentEntityTest {

    @Test
    public void shouldConvertAssignmentToEntityAndBack() {
        UUID assignmentId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        Instant startedAt = Instant.parse(
                "2026-08-27T10:00:00Z"
        );

        Instant endedAt = Instant.parse(
                "2026-08-27T12:00:00Z"
        );

        VehicleAssignment originalAssignment =
                VehicleAssignment.restore(
                        assignmentId,
                        vehicleId,
                        driverId,
                        startedAt,
                        endedAt
                );

        VehicleAssignmentEntity entity =
                VehicleAssignmentEntity.fromDomain(
                        originalAssignment
                );

        VehicleAssignment restoredAssignment =
                entity.toDomain();

        assertEquals(
                originalAssignment.getId(),
                restoredAssignment.getId()
        );
        assertEquals(
                originalAssignment.getVehicleId(),
                restoredAssignment.getVehicleId()
        );
        assertEquals(
                originalAssignment.getDriverId(),
                restoredAssignment.getDriverId()
        );
        assertEquals(
                originalAssignment.getStartedAt(),
                restoredAssignment.getStartedAt()
        );
        assertEquals(
                originalAssignment.getEndedAt(),
                restoredAssignment.getEndedAt()
        );
    }
}