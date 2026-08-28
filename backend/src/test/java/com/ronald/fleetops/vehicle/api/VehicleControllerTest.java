package com.ronald.fleetops.vehicle.api;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.ronald.fleetops.assignment.infrastructure.persistence.jpa.SpringDataVehicleAssignmentJpaRepository;
import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import com.ronald.fleetops.vehicle.infrastructure.persistence.jpa.SpringDataVehicleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private SpringDataVehicleAssignmentJpaRepository
            assignmentJpaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private SpringDataVehicleJpaRepository jpaRepository;

    @BeforeEach
    public void cleanDatabase() {
        jpaRepository.deleteAll();
    }

    @Test
    public void shouldCreateVehicle() throws Exception {
        String json = """
                {
                  "vin": "API-VIN-001",
                  "licensePlate": "API-PLATE-001",
                  "brand": "Toyota",
                  "model": "Corolla",
                  "manufacturingYear": 2020,
                  "mileageInKilometers": 84446,
                  "type": "SEDAN",
                  "fuelType": "GASOLINE"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.vin").value("API-VIN-001"))
                .andExpect(jsonPath("$.licensePlate").value("API-PLATE-001"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    public void shouldReturnAllVehicles() throws Exception {
        Vehicle vehicle1 = new Vehicle(
                "LIST-VIN-001",
                "LIST-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        Vehicle vehicle2 = new Vehicle(
                "LIST-VIN-002",
                "LIST-PLATE-002",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle1);
        vehicleService.registerVehicle(vehicle2);

        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoVehicleExists()
            throws Exception {
        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void shouldFindVehicleById() throws Exception {
        Vehicle vehicle = new Vehicle(
                "GET-VIN-001",
                "GET-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        mockMvc.perform(
                        get("/api/v1/vehicles/{id}", vehicle.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.vin").value("GET-VIN-001"))
                .andExpect(jsonPath("$.licensePlate")
                        .value("GET-PLATE-001"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }
    @Test
    public void shouldReturnNotFoundWhenVehicleIdDoesNotExist()
            throws Exception {

        UUID unknownId = UUID.randomUUID();

        mockMvc.perform(
                        get("/api/v1/vehicles/" + unknownId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(
                        "Vehicle not found with id " + unknownId))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/vehicles/" + unknownId
                ));
    }

    @Test
    public void shouldReturnConflictWhenVinAlreadyExists() throws Exception {
        String json = """
            {
              "vin": "DUPLICATE-VIN-001",
              "licensePlate": "DUPLICATE-PLATE-001",
              "brand": "Toyota",
              "model": "Corolla",
              "manufacturingYear": 2020,
              "mileageInKilometers": 84446,
              "type": "SEDAN",
              "fuelType": "GASOLINE"
            }
            """;

        mockMvc.perform(
                        post("/api/v1/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        post("/api/v1/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value(
                        "A vehicle with VIN DUPLICATE-VIN-001 already exists"
                ))
                .andExpect(jsonPath("$.path").value("/api/v1/vehicles"));
    }

    @Test
    public void shouldReturnBadRequestWhenVehicleDataIsInvalid()
            throws Exception {

        String json = """
            {
              "vin": "",
              "licensePlate": "VALID-PLATE",
              "brand": "",
              "model": "Corolla",
              "manufacturingYear": 1800,
              "mileageInKilometers": -1,
              "type": null,
              "fuelType": null
            }
            """;

        mockMvc.perform(
                        post("/api/v1/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Request validation failed"))
                .andExpect(jsonPath("$.fieldErrors.vin").exists())
                .andExpect(jsonPath("$.fieldErrors.brand").exists())
                .andExpect(jsonPath(
                        "$.fieldErrors.manufacturingYear"
                ).exists())
                .andExpect(jsonPath(
                        "$.fieldErrors.mileageInKilometers"
                ).exists())
                .andExpect(jsonPath("$.fieldErrors.type").exists())
                .andExpect(jsonPath("$.fieldErrors.fuelType").exists())
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/vehicles"));
    }
    @Test
    public void shouldUpdateVehicle() throws Exception {
        Vehicle vehicle = new Vehicle(
                "UPDATE-VIN-001",
                "OLD-PLATE",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        String json = """
            {
              "licensePlate": "NEW-PLATE",
              "brand": "Honda",
              "model": "Civic",
              "manufacturingYear": 2022,
              "mileageInKilometers": 90000,
              "type": "SEDAN",
              "fuelType": "HYBRID"
            }
            """;

        mockMvc.perform(
                        put("/api/v1/vehicles/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.vin").value("UPDATE-VIN-001"))
                .andExpect(jsonPath("$.licensePlate").value("NEW-PLATE"))
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("Civic"))
                .andExpect(jsonPath("$.manufacturingYear").value(2022))
                .andExpect(jsonPath("$.mileageInKilometers").value(90000))
                .andExpect(jsonPath("$.type").value("SEDAN"))
                .andExpect(jsonPath("$.fuelType").value("HYBRID"));
    }
    @Test
    public void shouldReturnNotFoundWhenUpdatingUnknownVehicle()
            throws Exception {

        UUID unknownId = UUID.randomUUID();

        String json = """
            {
              "licensePlate": "NEW-PLATE",
              "brand": "Honda",
              "model": "Civic",
              "manufacturingYear": 2022,
              "mileageInKilometers": 90000,
              "type": "SEDAN",
              "fuelType": "HYBRID"
            }
            """;

        mockMvc.perform(
                        put("/api/v1/vehicles/{id}", unknownId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(
                        "Vehicle not found with id " + unknownId
                ))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/vehicles/" + unknownId
                ));
    }
    @Test
    public void shouldReturnBadRequestWhenUpdateDataIsInvalid()
            throws Exception {

        Vehicle vehicle = new Vehicle(
                "VALIDATION-VIN-001",
                "OLD-PLATE",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        String json = """
            {
              "licensePlate": "",
              "brand": "",
              "model": "",
              "manufacturingYear": 1800,
              "mileageInKilometers": -1,
              "type": null,
              "fuelType": null
            }
            """;

        mockMvc.perform(
                        put("/api/v1/vehicles/{id}", vehicle.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Request validation failed"))
                .andExpect(jsonPath("$.fieldErrors.licensePlate").exists())
                .andExpect(jsonPath("$.fieldErrors.brand").exists())
                .andExpect(jsonPath("$.fieldErrors.model").exists())
                .andExpect(jsonPath(
                        "$.fieldErrors.manufacturingYear"
                ).exists())
                .andExpect(jsonPath(
                        "$.fieldErrors.mileageInKilometers"
                ).exists())
                .andExpect(jsonPath("$.fieldErrors.type").exists())
                .andExpect(jsonPath("$.fieldErrors.fuelType").exists())
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/vehicles/" + vehicle.getId()
                ));
    }
    @Test
    public void shouldSendVehicleToMaintenance() throws Exception {
        Vehicle vehicle = new Vehicle(
                "API-MAINTENANCE-VIN-001",
                "API-MAINTENANCE-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/maintenance",
                                vehicle.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.status")
                        .value("IN_MAINTENANCE"));
    }
    @Test
    public void shouldCompleteVehicleMaintenance() throws Exception {
        Vehicle vehicle = new Vehicle(
                "API-COMPLETE-MAINTENANCE-VIN-001",
                "API-COMPLETE-MAINTENANCE-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);
        vehicleService.sendVehicleToMaintenance(vehicle.getId());

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/maintenance/complete",
                                vehicle.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.status")
                        .value("AVAILABLE"));
    }
    @Test
    public void shouldMarkVehicleOutOfService() throws Exception {
        Vehicle vehicle = new Vehicle(
                "API-OUT-OF-SERVICE-VIN-001",
                "API-OUT-OF-SERVICE-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/out-of-service",
                                vehicle.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.status")
                        .value("OUT_OF_SERVICE"));
    }
    @Test
    public void shouldRetireVehicle() throws Exception {
        Vehicle vehicle = new Vehicle(
                "API-RETIRED-VIN-001",
                "API-RETIRED-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);
        vehicleService.markVehicleOutOfService(vehicle.getId());

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/retire",
                                vehicle.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.status")
                        .value("RETIRED"));
    }

    @Test
    public void shouldReturnConflictWhenRetiringAvailableVehicle()
            throws Exception {

        Vehicle vehicle = new Vehicle(
                "API-INVALID-RETIRE-VIN-001",
                "API-INVALID-RETIRE-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle);

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/retire",
                                vehicle.getId()
                        )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value(
                        "Only out-of-service vehicles can be retired"
                ))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/vehicles/" +
                                vehicle.getId() +
                                "/retire"
                ));
    }

    @Test
    public void shouldReturnNotFoundWhenSendingUnknownVehicleToMaintenance()
            throws Exception {

        UUID unknownId = UUID.randomUUID();

        mockMvc.perform(
                        patch(
                                "/api/v1/vehicles/{id}/maintenance",
                                unknownId
                        )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(
                        "Vehicle not found with id " + unknownId
                ))
                .andExpect(jsonPath("$.path").value(
                        "/api/v1/vehicles/" +
                                unknownId +
                                "/maintenance"
                ));
    }
}