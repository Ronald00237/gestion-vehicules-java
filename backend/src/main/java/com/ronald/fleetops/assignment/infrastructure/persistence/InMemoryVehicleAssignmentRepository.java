
package com.ronald.fleetops.assignment.infrastructure.persistence;

import com.ronald.fleetops.assignment.application.port.VehicleAssignmentRepository;
import com.ronald.fleetops.assignment.domain.VehicleAssignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVehicleAssignmentRepository
        implements VehicleAssignmentRepository {

    private final Map<UUID, VehicleAssignment> assignments =
            new HashMap<>();

    @Override
    public VehicleAssignment save(
            VehicleAssignment assignment
    ) {
        assignments.put(assignment.getId(), assignment);
        return assignment;
    }

    @Override
    public Optional<VehicleAssignment> findById(UUID id) {
        return Optional.ofNullable(assignments.get(id));
    }

    @Override
    public Optional<VehicleAssignment> findActiveByVehicleId(
            UUID vehicleId
    ) {
        return assignments.values()
                .stream()
                .filter(assignment ->
                        assignment.getVehicleId().equals(vehicleId))
                .filter(assignment ->
                        assignment.getEndedAt().isEmpty())
                .findFirst();
    }

    @Override
    public Optional<VehicleAssignment> findActiveByDriverId(
            UUID driverId
    ) {
        return assignments.values()
                .stream()
                .filter(assignment ->
                        assignment.getDriverId().equals(driverId))
                .filter(assignment ->
                        assignment.getEndedAt().isEmpty())
                .findFirst();
    }

    @Override
    public List<VehicleAssignment> findAll() {
        return new ArrayList<>(assignments.values());
    }
}