package com.ronald.fleetops.vehicle.domain;

import java.rmi.server.UID;
import java.util.UUID;

public class Vehicle {
   private final UUID id;
   private String vin;
   private String licensePlate;
   private String brand;
   private String model;
   private int manufacturingYear;
   private long mileageInKilometers;
   private VehicleType type;
   private FuelType fuelType;
   private VehicleStatus status = VehicleStatus.AVAILABLE;

     public Vehicle(String vin,
                    String licensePlate,
                    String brand,
                    String model,
                    int manufacturingYear,
                    long mileageInKilometers,
                    VehicleType type,
                    FuelType fuelType) {
         this.id = UUID.randomUUID();
         this.vin = vin;
         this.licensePlate = licensePlate;
         this.brand = brand;
         this.model = model;
         this.manufacturingYear = manufacturingYear;
         this.mileageInKilometers = mileageInKilometers;
         this.fuelType = fuelType;
         this.type = type;
     }

     public UUID getId(){
         return this.id;
     }

     public String getVin(){
         return this.vin;
     }

     public String getLicensePlate(){
         return this.licensePlate;
     }

     public String getBrand(){
         return this.brand;
     }

     public String getModel(){
         return this.model;
     }

     public int getManufacturingYear(){
         return this.manufacturingYear;
     }

    public long getMileageInKilometers() {
        return this.mileageInKilometers;
    }

    public VehicleType getType() {
        return this.type;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }
    public VehicleStatus getStatus(){
         return this.status;
    }
}