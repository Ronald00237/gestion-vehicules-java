
package com.ronald.fleetops.assignment.infrastructure.persistence.jpa;

import com.ronald.fleetops.assignment.application.port.VehicleAssignmentRepository;
import com.ronald.fleetops.assignment.domain.VehicleAssignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgreSqlVehicleAssignmentRepository
        implements VehicleAssignmentRepository {

    private final SpringDataVehicleAssignmentJpaRepository
            jpaRepository;

    public PostgreSqlVehicleAssignmentRepository(
            SpringDataVehicleAssignmentJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public VehicleAssignment save(
            VehicleAssignment assignment
    ) {
        VehicleAssignmentEntity savedEntity =
                jpaRepository.save(
                        VehicleAssignmentEntity.fromDomain(
                                assignment
                        )
                );

        return savedEntity.toDomain();
    }

    @Override
    public Optional<VehicleAssignment> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(VehicleAssignmentEntity::toDomain);
    }

    @Override
    public Optional<VehicleAssignment> findActiveByVehicleId(
            UUID vehicleId
    ) {
        return jpaRepository
                .findFirstByVehicleIdAndEndedAtIsNull(vehicleId)
                .map(VehicleAssignmentEntity::toDomain);
    }

    @Override
    public Optional<VehicleAssignment> findActiveByDriverId(
            UUID driverId
    ) {
        return jpaRepository
                .findFirstByDriverIdAndEndedAtIsNull(driverId)
                .map(VehicleAssignmentEntity::toDomain);
    }

    @Override
    public List<VehicleAssignment> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(VehicleAssignmentEntity::toDomain)
                .toList();
    }
}