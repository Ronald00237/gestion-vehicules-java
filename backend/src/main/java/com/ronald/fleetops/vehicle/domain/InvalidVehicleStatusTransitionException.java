package com.ronald.fleetops.vehicle.domain;

public class InvalidVehicleStatusTransitionException extends IllegalArgumentException {

    public InvalidVehicleStatusTransitionException(String message) {
        super(message);
    }
}