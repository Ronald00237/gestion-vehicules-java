package com.ronald.fleetops.vehicle.api;

import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleStatus;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record VehicleResponse(

        UUID id,
        String vin,
        String licensePlate,
        String brand,
        String model,
        int manufacturingYear,
        long mileageInKilometers,
        VehicleType type,
        FuelType fuelType,
        VehicleStatus status
) {
    public static VehicleResponse from(Vehicle vehicle){
        return  new VehicleResponse(vehicle.getId(), vehicle.getVin(), vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getManufacturingYear(),
                vehicle.getMileageInKilometers(),
                vehicle.getType(),
                vehicle.getFuelType(),
                vehicle.getStatus() );
    }
}