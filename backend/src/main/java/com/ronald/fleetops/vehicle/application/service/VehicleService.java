package com.ronald.fleetops.vehicle.application.service;
import com.ronald.fleetops.vehicle.application.exception.DuplicateVehicleVinException;
import com.ronald.fleetops.vehicle.application.exception.VehicleNotFoundException;
import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.jspecify.annotations.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VehicleService {
        private final VehicleRepository vehicleRepository;

        public VehicleService(VehicleRepository vehicleRepository){
            this.vehicleRepository = vehicleRepository;
        }

        public Vehicle registerVehicle(@NonNull Vehicle vehicle){
            Optional<Vehicle> foundVehicle = vehicleRepository.findByVin(vehicle.getVin());
            if(foundVehicle.isPresent()){
               throw new DuplicateVehicleVinException(vehicle.getVin());
            }
            return vehicleRepository.save(vehicle);
        }
        public Optional<Vehicle> findVehicleById(UUID id){
            return vehicleRepository.findById(id);
        }

        public List<Vehicle> findAllVehicles(){
            return vehicleRepository.findAll();
        }

    public Vehicle updateVehicle(
            UUID id,
            String licensePlate,
            String brand,
            String model,
            int manufacturingYear,
            long mileageInKilometers,
            VehicleType type,
            FuelType fuelType) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new VehicleNotFoundException(id)

                );

        vehicle.updateDetails(
                licensePlate,
                brand,
                model,
                manufacturingYear,
                mileageInKilometers,
                type,
                fuelType
        );

        return vehicleRepository.save(vehicle);
    }

    }