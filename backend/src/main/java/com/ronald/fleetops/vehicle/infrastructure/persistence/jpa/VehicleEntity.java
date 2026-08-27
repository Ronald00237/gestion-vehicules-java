package com.ronald.fleetops.vehicle.infrastructure.persistence.jpa;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.VehicleStatus;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "manufacturing_year", nullable = false)
    private int manufacturingYear;

    @Column(name = "mileage_in_kilometers", nullable = false)
    private long mileageInKilometers;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    protected VehicleEntity() {
    }
    public static VehicleEntity fromDomain(Vehicle vehicle) {
        VehicleEntity entity = new VehicleEntity();

        entity.id = vehicle.getId();
        entity.vin = vehicle.getVin();
        entity.licensePlate = vehicle.getLicensePlate();
        entity.brand = vehicle.getBrand();
        entity.model = vehicle.getModel();
        entity.manufacturingYear =
                vehicle.getManufacturingYear();
        entity.mileageInKilometers =
                vehicle.getMileageInKilometers();
        entity.type = vehicle.getType();
        entity.fuelType = vehicle.getFuelType();
        entity.status = vehicle.getStatus();

        return entity;
    }

    public Vehicle toDomain() {
        return Vehicle.restore(
                id,
                vin,
                licensePlate,
                brand,
                model,
                manufacturingYear,
                mileageInKilometers,
                type,
                fuelType,
                status
        );
    }
}