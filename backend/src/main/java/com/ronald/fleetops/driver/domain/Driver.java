
package com.ronald.fleetops.driver.domain;

import java.util.UUID;

public class Driver {

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String licenseNumber;
    private boolean active;

    public Driver(
            String firstName,
            String lastName,
            String licenseNumber
    ) {
        this(
                UUID.randomUUID(),
                firstName,
                lastName,
                licenseNumber,
                true
        );
    }

    private Driver(
            UUID id,
            String firstName,
            String lastName,
            String licenseNumber,
            boolean active
    ) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Driver id must not be null"
            );
        }

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException(
                    "First name must not be blank"
            );
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException(
                    "Last name must not be blank"
            );
        }

        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "License number must not be blank"
            );
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isActive() {
        return active;
    }
    public void deactivate() {
        this.active = false;
    }
    public static Driver restore(
            UUID id,
            String firstName,
            String lastName,
            String licenseNumber,
            boolean active
    ) {
        return new Driver(
                id,
                firstName,
                lastName,
                licenseNumber,
                active
        );
    }
}