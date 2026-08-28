
package com.ronald.fleetops.assignment.infrastructure.persistence.jpa;

import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vehicle_assignments")
public class VehicleAssignmentEntity {

    @Id
    private UUID id;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    protected VehicleAssignmentEntity() {
    }

    private VehicleAssignmentEntity(
            UUID id,
            UUID vehicleId,
            UUID driverId,
            Instant startedAt,
            Instant endedAt
    ) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public static VehicleAssignmentEntity fromDomain(
            VehicleAssignment assignment
    ) {
        return new VehicleAssignmentEntity(
                assignment.getId(),
                assignment.getVehicleId(),
                assignment.getDriverId(),
                assignment.getStartedAt(),
                assignment.getEndedAt().orElse(null)
        );
    }

    public VehicleAssignment toDomain() {
        return VehicleAssignment.restore(
                id,
                vehicleId,
                driverId,
                startedAt,
                endedAt
        );
    }
}