package com.ronald.fleetops.vehicle.api;

import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(
        classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD
)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleService vehicleService;

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
                        get("/api/v1/vehicles/{id}", unknownId)
                )
                .andExpect(status().isNotFound());
    }
}