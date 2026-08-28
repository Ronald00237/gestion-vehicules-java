
package com.ronald.fleetops.driver.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    @Test
    public void shouldCreateActiveDriver() {
        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-001"
        );

        assertNotNull(driver.getId());
        assertEquals("Ronald", driver.getFirstName());
        assertEquals("Taylor", driver.getLastName());
        assertEquals(
                "LICENSE-001",
                driver.getLicenseNumber()
        );
        assertTrue(driver.isActive());
    }
    @Test
    public void shouldRejectBlankFirstName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Driver(
                        " ",
                        "Taylor",
                        "LICENSE-002"
                )
        );

        assertEquals(
                "First name must not be blank",
                exception.getMessage()
        );
    }
    @Test
    public void shouldRejectBlankLastName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Driver(
                        "Ronald",
                        " ",
                        "LICENSE-003"
                )
        );

        assertEquals(
                "Last name must not be blank",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectBlankLicenseNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Driver(
                        "Ronald",
                        "Taylor",
                        " "
                )
        );

        assertEquals(
                "License number must not be blank",
                exception.getMessage()
        );
    }

    @Test
    public void shouldDeactivateDriver() {
        Driver driver = new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-004"
        );

        driver.deactivate();

        assertFalse(driver.isActive());
    }

    @Test
    public void shouldRestoreDriverWithItsIdentityAndStatus() {
        UUID persistedId = UUID.randomUUID();

        Driver driver = Driver.restore(
                persistedId,
                "Ronald",
                "Taylor",
                "LICENSE-005",
                false
        );

        assertEquals(persistedId, driver.getId());
        assertEquals("Ronald", driver.getFirstName());
        assertEquals("Taylor", driver.getLastName());
        assertEquals("LICENSE-005", driver.getLicenseNumber());
        assertFalse(driver.isActive());
    }

    @Test
    public void shouldRejectNullIdWhenRestoringDriver() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Driver.restore(
                        null,
                        "Ronald",
                        "Taylor",
                        "LICENSE-006",
                        true
                )
        );

        assertEquals(
                "Driver id must not be null",
                exception.getMessage()
        );
    }
}