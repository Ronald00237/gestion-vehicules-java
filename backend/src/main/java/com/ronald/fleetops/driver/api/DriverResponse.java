
package com.ronald.fleetops.driver.api;

import com.ronald.fleetops.driver.domain.Driver;

import java.util.UUID;

public record DriverResponse(
        UUID id,
        String firstName,
        String lastName,
        String licenseNumber,
        boolean active
) {

    public static DriverResponse from(Driver driver) {
        return new DriverResponse(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.isActive()
        );
    }
}