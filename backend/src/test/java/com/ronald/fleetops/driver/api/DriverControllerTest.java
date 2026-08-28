
package com.ronald.fleetops.driver.api;

import com.ronald.fleetops.assignment.infrastructure.persistence.jpa.SpringDataVehicleAssignmentJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ronald.fleetops.driver.application.service.DriverService;
import com.ronald.fleetops.driver.domain.Driver;
import com.ronald.fleetops.driver.infrastructure.persistence.jpa.SpringDataDriverJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest {

    @Autowired
    private SpringDataVehicleAssignmentJpaRepository
            assignmentJpaRepository;

    @Autowired
    private SpringDataDriverJpaRepository jpaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverService driverService;

    @BeforeEach
    public void cleanDatabase() {
        assignmentJpaRepository.deleteAll();
        jpaRepository.deleteAll();
    }
    @Test
    public void shouldCreateDriver() throws Exception {
        String json = """
                {
                  "firstName": "Ronald",
                  "lastName": "Taylor",
                  "licenseNumber": "LICENSE-017"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Ronald"))
                .andExpect(jsonPath("$.lastName").value("Taylor"))
                .andExpect(jsonPath("$.licenseNumber")
                        .value("LICENSE-017"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void shouldReturnDriverById() throws Exception {
        Driver driver = driverService.registerDriver(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "LICENSE-018"
                )
        );

        mockMvc.perform(
                        get(
                                "/api/v1/drivers/{id}",
                                driver.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(driver.getId().toString()))
                .andExpect(jsonPath("$.firstName")
                        .value("Ronald"))
                .andExpect(jsonPath("$.lastName")
                        .value("Taylor"))
                .andExpect(jsonPath("$.licenseNumber")
                        .value("LICENSE-018"))
                .andExpect(jsonPath("$.active").value(true));
    }
    @Test
    public void shouldReturnAllDrivers() throws Exception {
        driverService.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-019"
        ));

        driverService.registerDriver(new Driver(
                "Alex",
                "Martin",
                "LICENSE-020"
        ));

        mockMvc.perform(
                        get("/api/v1/drivers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].id").exists());
    }
    @Test
    public void shouldDeactivateDriver() throws Exception {
        Driver driver = driverService.registerDriver(
                new Driver(
                        "Ronald",
                        "Taylor",
                        "LICENSE-021"
                )
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/drivers/{id}/deactivate",
                                driver.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(driver.getId().toString()))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    public void shouldReturnConflictForDuplicateLicenseNumber()
            throws Exception {

        driverService.registerDriver(new Driver(
                "Ronald",
                "Taylor",
                "LICENSE-022"
        ));

        String json = """
            {
              "firstName": "Alex",
              "lastName": "Martin",
              "licenseNumber": "LICENSE-022"
            }
            """;

        mockMvc.perform(
                        post("/api/v1/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value(
                        "A driver with license number LICENSE-022 already exists"
                ))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/drivers"
                ));
    }

    @Test
    public void shouldReturnNotFoundForUnknownDriver()
            throws Exception {

        UUID unknownId = UUID.randomUUID();

        mockMvc.perform(
                        get(
                                "/api/v1/drivers/{id}",
                                unknownId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(
                        "Driver not found with id " + unknownId
                ))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/drivers/" + unknownId
                ));
    }

    @Test
    public void shouldReturnBadRequestForBlankFirstName()
            throws Exception {

        String json = """
            {
              "firstName": " ",
              "lastName": "Taylor",
              "licenseNumber": "LICENSE-023"
            }
            """;

        mockMvc.perform(
                        post("/api/v1/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Request validation failed"))
                .andExpect(jsonPath("$.fieldErrors.firstName")
                        .value("First name must not be blank"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/drivers"));
    }
}