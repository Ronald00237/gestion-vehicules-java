

package com.ronald.fleetops.assignment.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataVehicleAssignmentJpaRepository
        extends JpaRepository<VehicleAssignmentEntity, UUID> {

    Optional<VehicleAssignmentEntity>
    findFirstByVehicleIdAndEndedAtIsNull(UUID vehicleId);

    Optional<VehicleAssignmentEntity>
    findFirstByDriverIdAndEndedAtIsNull(UUID driverId);
}