

package com.ronald.fleetops.assignment.application.exception;

import java.util.UUID;

public class VehicleAssignmentNotFoundException
        extends RuntimeException {

    public VehicleAssignmentNotFoundException(UUID id) {
        super("Vehicle assignment not found with id " + id);
    }
}