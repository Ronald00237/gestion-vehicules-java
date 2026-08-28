
package com.ronald.fleetops.assignment.domain;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class VehicleAssignment {

    private final UUID id;
    private final UUID vehicleId;
    private final UUID driverId;
    private final Instant startedAt;
    private Instant endedAt;

    public VehicleAssignment(
            UUID vehicleId,
            UUID driverId,
            Instant startedAt
    ) {
        this(
                UUID.randomUUID(),
                vehicleId,
                driverId,
                startedAt,
                null
        );
    }

    private VehicleAssignment(
            UUID id,
            UUID vehicleId,
            UUID driverId,
            Instant startedAt,
            Instant endedAt
    ) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Assignment id must not be null"
            );
        }

        if (vehicleId == null) {
            throw new IllegalArgumentException(
                    "Vehicle id must not be null"
            );
        }

        if (driverId == null) {
            throw new IllegalArgumentException(
                    "Driver id must not be null"
            );
        }

        if (startedAt == null) {
            throw new IllegalArgumentException(
                    "Start date must not be null"
            );
        }

        this.id = id;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.startedAt = startedAt;
        this.endedAt = null;

        if (endedAt != null) {
            end(endedAt);
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Optional<Instant> getEndedAt() {
        return Optional.ofNullable(endedAt);
    }

    public void end(Instant endedAt) {
        if (this.endedAt != null) {
            throw new IllegalStateException(
                    "Assignment has already ended"
            );
        }

        if (endedAt == null) {
            throw new IllegalArgumentException(
                    "End date must not be null"
            );
        }

        if (!endedAt.isAfter(startedAt)) {
            throw new IllegalArgumentException(
                    "End date must be after start date"
            );
        }

        this.endedAt = endedAt;
    }

    public static VehicleAssignment restore(
            UUID id,
            UUID vehicleId,
            UUID driverId,
            Instant startedAt,
            Instant endedAt
    ) {
        return new VehicleAssignment(
                id,
                vehicleId,
                driverId,
                startedAt,
                endedAt
        );
    }
}