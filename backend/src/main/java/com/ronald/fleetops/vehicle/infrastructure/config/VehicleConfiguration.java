package com.ronald.fleetops.vehicle.infrastructure.config;

import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.infrastructure.persistence.InMemoryVehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleConfiguration {

     @Bean
    public VehicleRepository vehicleRepository(){
         return  new InMemoryVehicleRepository();
     }

     @Bean
    public VehicleService vehicleService(VehicleRepository vehicleRepository){
         return  new VehicleService(vehicleRepository);
     }
}