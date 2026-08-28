
package com.ronald.fleetops.driver.api;

import com.ronald.fleetops.driver.application.service.DriverService;
import com.ronald.fleetops.driver.domain.Driver;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import java.util.UUID;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(
            @Valid @RequestBody CreateDriverRequest request
    ) {
        Driver driver = new Driver(
                request.firstName(),
                request.lastName(),
                request.licenseNumber()
        );

        Driver registeredDriver =
                driverService.registerDriver(driver);

        DriverResponse response =
                DriverResponse.from(registeredDriver);

        return ResponseEntity
                .created(URI.create(
                        "/api/v1/drivers/" + response.id()
                ))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriverById(
            @PathVariable UUID id
    ) {
        Driver driver = driverService.getDriverById(id);

        return ResponseEntity.ok(
                DriverResponse.from(driver)
        );
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {
        List<DriverResponse> responses =
                driverService.getAllDrivers()
                        .stream()
                        .map(DriverResponse::from)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<DriverResponse> deactivateDriver(
            @PathVariable UUID id
    ) {
        Driver driver = driverService.deactivateDriver(id);

        return ResponseEntity.ok(
                DriverResponse.from(driver)
        );
    }
}