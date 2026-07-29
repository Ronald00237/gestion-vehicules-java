package com.ronald.fleetops.vehicle.api;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

}