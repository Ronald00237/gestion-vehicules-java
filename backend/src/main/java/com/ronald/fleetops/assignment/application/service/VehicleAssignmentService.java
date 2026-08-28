
package com.ronald.fleetops.assignment.application.service;

import com.ronald.fleetops.assignment.application.port.VehicleAssignmentRepository;
import com.ronald.fleetops.assignment.domain.VehicleAssignment;
import com.ronald.fleetops.driver.application.exception.DriverNotFoundException;
import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.vehicle.application.exception.VehicleNotFoundException;
import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import java.util.List;
import java.time.Instant;
import java.util.UUID;
import com.ronald.fleetops.assignment.application.exception.InvalidVehicleAssignmentException;
import org.springframework.transaction.annotation.Transactional;
import com.ronald.fleetops.assignment.application.exception.VehicleAssignmentNotFoundException;

public class VehicleAssignmentService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final VehicleAssignmentRepository assignmentRepository;

    public VehicleAssignmentService(
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            VehicleAssignmentRepository assignmentRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.assignmentRepository = assignmentRepository;
    }
    @Transactional
    public VehicleAssignment startAssignment(
            UUID vehicleId,
            UUID driverId,
            Instant startedAt
    ) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(
                      vehicleId
                ));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(
                        "Driver not found with id " + driverId
                ));

        if (!driver.isActive()) {
            throw new InvalidVehicleAssignmentException(
                    "Inactive drivers cannot be assigned to vehicles"
            );
        }

        if (assignmentRepository
                .findActiveByVehicleId(vehicleId)
                .isPresent()) {

            throw new InvalidVehicleAssignmentException(
                    "Vehicle already has an active assignment"
            );
        }

        if (assignmentRepository
                .findActiveByDriverId(driverId)
                .isPresent()) {

            throw new InvalidVehicleAssignmentException(
                    "Driver already has an active assignment"
            );
        }

        vehicle.assign();

        VehicleAssignment assignment =
                new VehicleAssignment(
                        vehicle.getId(),
                        driver.getId(),
                        startedAt
                );

        vehicleRepository.save(vehicle);

        return assignmentRepository.save(assignment);
    }
    @Transactional
    public VehicleAssignment endAssignment(
            UUID assignmentId,
            Instant endedAt
    ) {
        VehicleAssignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new VehicleAssignmentNotFoundException(
                                        assignmentId
                                )
                        );

        Vehicle vehicle = vehicleRepository
                .findById(assignment.getVehicleId())
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                assignment.getVehicleId()
                        )
                );

        assignment.end(endedAt);
        vehicle.unassign();

        vehicleRepository.save(vehicle);

        return assignmentRepository.save(assignment);
    }
    public List<VehicleAssignment> getAssignmentHistory() {
        return assignmentRepository.findAll();
    }
}