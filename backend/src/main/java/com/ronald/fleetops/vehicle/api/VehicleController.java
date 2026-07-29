package com.ronald.fleetops.vehicle.api;

import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleRequest request){
        Vehicle vehicle = new Vehicle(
                request.vin(),
                request.licensePlate(),
                request.brand(),
                request.model(),
                request.manufacturingYear(),
                request.mileageInKilometers(),
                request.type(),
                request.fuelType());

        Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle);
        VehicleResponse response = VehicleResponse.from((registeredVehicle));

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}