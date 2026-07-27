package com.ronald.fleetops.vehicle.application.port;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository {
    public Vehicle save(Vehicle vehicle);
    public Optional<Vehicle> findById(UUID id);
    public Optional<Vehicle> findByVin(String vin);
    public  List<Vehicle> findAll();
}