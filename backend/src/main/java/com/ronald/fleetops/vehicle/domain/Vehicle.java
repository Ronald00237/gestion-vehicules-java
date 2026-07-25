package com.ronald.fleetops.vehicle.domain;

import java.rmi.server.UID;
import java.util.UUID;
import java.time.Year;

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
         if(vin == null || vin.isBlank()){
             throw new IllegalArgumentException("VIN must not be blank");
         }
         if(licensePlate == null || licensePlate.isBlank()){
             throw new IllegalArgumentException("License plate must not be blank");
         }
         if(brand == null || brand.isBlank()){
             throw new IllegalArgumentException("Brand must not be blank");
         }
         if(model == null || model.isBlank()){
             throw new IllegalArgumentException("Model must not be blank");
         }
         if(mileageInKilometers < 0){
             throw new IllegalArgumentException("Mileage must not be negative");
         }
         if(type == null){
             throw new IllegalArgumentException("Vehicle type must not be null");
         }
         if(fuelType == null){
             throw new IllegalArgumentException("Fuel type must not be null");
         }
         int currentYear = Year.now().getValue();

         if(manufacturingYear < 1886 || manufacturingYear > currentYear +1 ){
             throw new IllegalArgumentException("Manufacturing year is invalid");
         }
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

     public void assign(){
         if(status == VehicleStatus.AVAILABLE){
             status = VehicleStatus.ASSIGNED;
         }else {
             throw new IllegalArgumentException("Only available vehicles can be assigned");
         }
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

    public void unassign(){
         if(status == VehicleStatus.ASSIGNED){
             status = VehicleStatus.AVAILABLE;
         }else{
             throw new IllegalArgumentException("Only assigned vehicles can be unassigned");
         }
    }

    public void sendToMaintenance(){
         if(status == VehicleStatus.AVAILABLE){
             status = VehicleStatus.IN_MAINTENANCE;
         }else{
             throw new IllegalArgumentException("Only available vehicles can be sent to maintenance");
         }
    }

    public void completeMaintenance(){
         if(status == VehicleStatus.IN_MAINTENANCE){
             status = VehicleStatus.AVAILABLE;
         }else{
             throw new IllegalArgumentException("Only vehicles in maintenance can complete maintenance");
        }
    }
}