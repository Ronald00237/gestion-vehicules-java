

package com.ronald.fleetops.assignment.infrastructure.config;

import com.ronald.fleetops.assignment.application.port.VehicleAssignmentRepository;
import com.ronald.fleetops.assignment.application.service.VehicleAssignmentService;
import com.ronald.fleetops.assignment.infrastructure.persistence.jpa.PostgreSqlVehicleAssignmentRepository;
import com.ronald.fleetops.assignment.infrastructure.persistence.jpa.SpringDataVehicleAssignmentJpaRepository;
import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleAssignmentConfiguration {

    @Bean
    public VehicleAssignmentRepository
    vehicleAssignmentRepository(
            SpringDataVehicleAssignmentJpaRepository jpaRepository
    ) {
        return new PostgreSqlVehicleAssignmentRepository(
                jpaRepository
        );
    }

    @Bean
    public VehicleAssignmentService vehicleAssignmentService(
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            VehicleAssignmentRepository assignmentRepository
    ) {
        return new VehicleAssignmentService(
                vehicleRepository,
                driverRepository,
                assignmentRepository
        );
    }
}