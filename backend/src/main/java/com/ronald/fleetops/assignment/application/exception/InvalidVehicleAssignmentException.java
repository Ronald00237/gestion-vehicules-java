
package com.ronald.fleetops.assignment.application.exception;

public class InvalidVehicleAssignmentException
        extends IllegalStateException {

    public InvalidVehicleAssignmentException(String message) {
        super(message);
    }
}