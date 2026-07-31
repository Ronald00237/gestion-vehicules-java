package com.ronald.fleetops.vehicle.application.exception;


public class DuplicateVehicleVinException extends RuntimeException{
    public DuplicateVehicleVinException(String vin){
        super("A vehicle with VIN "+ vin + " already exists");
    }
}