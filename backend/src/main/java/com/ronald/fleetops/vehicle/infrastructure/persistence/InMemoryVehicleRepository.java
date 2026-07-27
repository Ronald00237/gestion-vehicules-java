package com.ronald.fleetops.vehicle.infrastructure.persistence;
import java.util.*;

import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.Vehicle;

public class InMemoryVehicleRepository implements VehicleRepository {
    private final Map<UUID,Vehicle> vehicles = new HashMap<>();

        @Override
        public Vehicle save(Vehicle vehicle) {
            vehicles.put(vehicle.getId(),vehicle);
            return vehicle;
        }

        @Override
        public Optional<Vehicle> findById(UUID id){
            return Optional.ofNullable(vehicles.get(id));
        }

        @Override
        public Optional<Vehicle> findByVin(String vin) {
            for(Vehicle vehicle: vehicles.values()){
                if (vehicle.getVin().equals(vin)) {
                    return Optional.of(vehicle);
                }
            }
            return Optional.empty();
        }

        @Override
        public List<Vehicle> findAll() {
            return new ArrayList<>(vehicles.values());
        }
    }