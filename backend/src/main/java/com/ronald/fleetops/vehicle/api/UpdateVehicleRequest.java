package com.ronald.fleetops.vehicle.api;

import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateVehicleRequest(

        @NotBlank
        String licensePlate,

        @NotBlank
        String brand,

        @NotBlank
        String model,

        @Min(1886)
        int manufacturingYear,

        @PositiveOrZero
        long mileageInKilometers,

        @NotNull
        VehicleType type,

        @NotNull
        FuelType fuelType
) {
}