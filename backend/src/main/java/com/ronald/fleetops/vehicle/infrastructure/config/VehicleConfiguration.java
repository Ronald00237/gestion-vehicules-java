package com.ronald.fleetops.vehicle.infrastructure.config;

import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.application.service.VehicleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ronald.fleetops.vehicle.infrastructure.persistence.jpa.PostgreSqlVehicleRepository;
import com.ronald.fleetops.vehicle.infrastructure.persistence.jpa.SpringDataVehicleJpaRepository;

@Configuration
public class VehicleConfiguration {

    @Bean
    public VehicleRepository vehicleRepository(
            SpringDataVehicleJpaRepository jpaRepository
    ) {
        return new PostgreSqlVehicleRepository(jpaRepository);
    }
     @Bean
    public VehicleService vehicleService(VehicleRepository vehicleRepository){
         return  new VehicleService(vehicleRepository);
     }
}