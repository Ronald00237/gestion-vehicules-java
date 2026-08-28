
package com.ronald.fleetops.driver.api;

import jakarta.validation.constraints.NotBlank;

public record CreateDriverRequest(

        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotBlank(message = "License number must not be blank")
        String licenseNumber
) {
}