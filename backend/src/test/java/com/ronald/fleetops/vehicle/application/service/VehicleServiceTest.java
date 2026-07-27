package com.ronald.fleetops.vehicle.application.service;
import com.ronald.fleetops.vehicle.application.port.VehicleRepository;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import com.ronald.fleetops.vehicle.infrastructure.persistence.InMemoryVehicleRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import java.util.UUID;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class VehicleServiceTest {
        @Test
        public void shouldRegisterVehicle(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            VehicleService vehicleService = new VehicleService(inMemoryVehicleRepository);
            Vehicle vehicle = new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle);
            Optional<Vehicle> foundVehicle = inMemoryVehicleRepository.findByVin(vehicle.getVin());
            assertTrue(foundVehicle.isPresent());
            assertSame(vehicle,registeredVehicle);
            assertSame(vehicle, foundVehicle.get());

        }

        @Test
        public void shouldRejectDuplicateVin(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            VehicleService vehicleService= new VehicleService(inMemoryVehicleRepository);
            Vehicle vehicle1 = new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            Vehicle vehicle2 = new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
                    vehicleService.registerVehicle(vehicle1);
            try {
                    vehicleService.registerVehicle(vehicle2);
                fail("Une exception doit etre levee");
            } catch(IllegalArgumentException exception){
                assertEquals("A vehicle with this VIN already exists", exception.getMessage());
            }
            assertEquals(1, inMemoryVehicleRepository.findAll().size());
        }

    @Test
    public void shouldFindRegisteredVehicleById() {
        InMemoryVehicleRepository inMemoryVehicleRepository =
                new InMemoryVehicleRepository();

        VehicleService vehicleService =
                new VehicleService(inMemoryVehicleRepository);

        Vehicle vehicle = new Vehicle(
                "ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        Vehicle registeredVehicle =
                vehicleService.registerVehicle(vehicle);

        Optional<Vehicle> foundVehicle =
                vehicleService.findVehicleById(registeredVehicle.getId());

        assertTrue(foundVehicle.isPresent());
        assertSame(registeredVehicle, foundVehicle.get());
    }

    @Test
    public void shouldReturnEmptyWhenVehicleIdDoesNotExist(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            VehicleService vehicleService = new VehicleService(inMemoryVehicleRepository);
            Optional<Vehicle> foundVehicle = vehicleService.findVehicleById(UUID.randomUUID());
            assertTrue(foundVehicle.isEmpty());
    }
}