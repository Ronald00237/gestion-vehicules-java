package com.ronald.fleetops.system.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class SystemStatusController {

    @GetMapping
    public SystemStatusResponse getStatus() {
        return new SystemStatusResponse("FleetOps", "UP");
    }
}