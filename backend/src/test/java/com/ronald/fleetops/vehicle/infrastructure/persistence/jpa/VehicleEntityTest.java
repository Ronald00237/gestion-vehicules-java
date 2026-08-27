package com.ronald.fleetops.vehicle.infrastructure.persistence.jpa;

import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleStatus;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleEntityTest {

    @Test
    public void shouldConvertDomainVehicleToEntityAndBack() {
        Vehicle originalVehicle = new Vehicle(
                "PERSISTENCE-VIN-001",
                "PERSISTENCE-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                85000L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        originalVehicle.sendToMaintenance();

        VehicleEntity entity =
                VehicleEntity.fromDomain(originalVehicle);

        Vehicle restoredVehicle = entity.toDomain();

        assertEquals(
                originalVehicle.getId(),
                restoredVehicle.getId()
        );
        assertEquals(
                originalVehicle.getVin(),
                restoredVehicle.getVin()
        );
        assertEquals(
                originalVehicle.getLicensePlate(),
                restoredVehicle.getLicensePlate()
        );
        assertEquals(
                originalVehicle.getBrand(),
                restoredVehicle.getBrand()
        );
        assertEquals(
                originalVehicle.getModel(),
                restoredVehicle.getModel()
        );
        assertEquals(
                originalVehicle.getManufacturingYear(),
                restoredVehicle.getManufacturingYear()
        );
        assertEquals(
                originalVehicle.getMileageInKilometers(),
                restoredVehicle.getMileageInKilometers()
        );
        assertEquals(
                VehicleType.SEDAN,
                restoredVehicle.getType()
        );
        assertEquals(
                FuelType.GASOLINE,
                restoredVehicle.getFuelType()
        );
        assertEquals(
                VehicleStatus.IN_MAINTENANCE,
                restoredVehicle.getStatus()
        );
    }
}