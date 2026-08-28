package com.ronald.fleetops.driver.infrastructure.persistence.jpa;

import com.ronald.fleetops.driver.domain.Driver;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "drivers")
public class DriverEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(
            name = "license_number",
            nullable = false,
            unique = true
    )
    private String licenseNumber;

    @Column(nullable = false)
    private boolean active;

    protected DriverEntity() {
    }

    private DriverEntity(
            UUID id,
            String firstName,
            String lastName,
            String licenseNumber,
            boolean active
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.active = active;
    }

    public static DriverEntity fromDomain(Driver driver) {
        return new DriverEntity(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.isActive()
        );
    }

    public Driver toDomain() {
        return Driver.restore(
                id,
                firstName,
                lastName,
                licenseNumber,
                active
        );
    }
}