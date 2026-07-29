package com.ronald.fleetops.vehicle.api;
import com.ronald.fleetops.vehicle.application.service.VehicleService;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VehicleService vehicleService;

    @Test
    public void shouldCreateVehicle() throws Exception{

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
    public void shouldFindVehicleById() throws Exception{
        Vehicle vehicle = new Vehicle(
                "GET-VIN-001",
                "GET-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);

        vehicleService.registerVehicle(vehicle);

        mockMvc.perform(
                        get("/api/v1/vehicles/{id}", vehicle.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(vehicle.getId().toString()))
                .andExpect(jsonPath("$.vin").value("GET-VIN-001"))
                .andExpect(jsonPath("$.licensePlate").value("GET-PLATE-001"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    public void shouldReturnNotFoundWhenVehicleIdDoesNotExist() throws Exception {
        UUID unknownId = UUID.randomUUID();

        mockMvc.perform(
                        get("/api/v1/vehicles/{id}", unknownId)
                )
                .andExpect(status().isNotFound());
    }
}