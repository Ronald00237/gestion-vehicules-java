
package com.ronald.fleetops.assignment.application.port;

import com.ronald.fleetops.assignment.domain.VehicleAssignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleAssignmentRepository {

    VehicleAssignment save(VehicleAssignment assignment);

    Optional<VehicleAssignment> findById(UUID id);

    Optional<VehicleAssignment> findActiveByVehicleId(
            UUID vehicleId
    );

    Optional<VehicleAssignment> findActiveByDriverId(
            UUID driverId
    );

    List<VehicleAssignment> findAll();
}