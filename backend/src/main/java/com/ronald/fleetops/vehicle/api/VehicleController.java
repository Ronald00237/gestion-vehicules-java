package com.ronald.fleetops.vehicle.api;

import com.ronald.fleetops.vehicle.application.exception.VehicleNotFoundException;
import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @Valid @RequestBody CreateVehicleRequest request
    ) {
        Vehicle vehicle = new Vehicle(
                request.vin(),
                request.licensePlate(),
                request.brand(),
                request.model(),
                request.manufacturingYear(),
                request.mileageInKilometers(),
                request.type(),
                request.fuelType()
        );

        Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle);
        VehicleResponse response = VehicleResponse.from(registeredVehicle);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> findAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        List<VehicleResponse> responses = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            responses.add(VehicleResponse.from(vehicle));
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> findVehicleById(
            @PathVariable UUID id
    ) {
        Optional<Vehicle> foundVehicle =
                vehicleService.findVehicleById(id);

        if (foundVehicle.isEmpty()) {
            throw new VehicleNotFoundException(id);
        }

        return ResponseEntity.ok(
                VehicleResponse.from(foundVehicle.get())
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleRequest request
    ) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(
                id,
                request.licensePlate(),
                request.brand(),
                request.model(),
                request.manufacturingYear(),
                request.mileageInKilometers(),
                request.type(),
                request.fuelType()
        );

        return ResponseEntity.ok(
                VehicleResponse.from(updatedVehicle)
        );
    }
    @PatchMapping("/{id}/maintenance")
    public ResponseEntity<VehicleResponse> sendVehicleToMaintenance(
            @PathVariable UUID id
    ) {
        Vehicle updatedVehicle =
                vehicleService.sendVehicleToMaintenance(id);

        return ResponseEntity.ok(
                VehicleResponse.from(updatedVehicle)
        );
    }
    @PatchMapping("/{id}/maintenance/complete")
    public ResponseEntity<VehicleResponse> completeVehicleMaintenance(
            @PathVariable UUID id
    ) {
        Vehicle updatedVehicle =
                vehicleService.completeVehicleMaintenance(id);

        return ResponseEntity.ok(
                VehicleResponse.from(updatedVehicle)
        );
    }

    @PatchMapping("/{id}/out-of-service")
    public ResponseEntity<VehicleResponse> markVehicleOutOfService(
            @PathVariable UUID id
    ) {
        Vehicle updatedVehicle =
                vehicleService.markVehicleOutOfService(id);

        return ResponseEntity.ok(
                VehicleResponse.from(updatedVehicle)
        );
    }
    @PatchMapping("/{id}/retire")
    public ResponseEntity<VehicleResponse> retireVehicle(
            @PathVariable UUID id
    ) {
        Vehicle updatedVehicle =
                vehicleService.retireVehicle(id);

        return ResponseEntity.ok(
                VehicleResponse.from(updatedVehicle)
        );
    }

}