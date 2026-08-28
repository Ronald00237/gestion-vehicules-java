
package com.ronald.fleetops.driver.infrastructure.persistence.jpa;

import com.ronald.fleetops.driver.domain.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DriverEntityTest {

    @Test
    public void shouldConvertDriverToEntityAndBack() {
        Driver originalDriver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-024"
        );

        originalDriver.deactivate();

        DriverEntity entity =
                DriverEntity.fromDomain(originalDriver);

        Driver restoredDriver = entity.toDomain();

        assertEquals(
                originalDriver.getId(),
                restoredDriver.getId()
        );
        assertEquals(
                originalDriver.getFirstName(),
                restoredDriver.getFirstName()
        );
        assertEquals(
                originalDriver.getLastName(),
                restoredDriver.getLastName()
        );
        assertEquals(
                originalDriver.getLicenseNumber(),
                restoredDriver.getLicenseNumber()
        );
        assertFalse(restoredDriver.isActive());
    }
}