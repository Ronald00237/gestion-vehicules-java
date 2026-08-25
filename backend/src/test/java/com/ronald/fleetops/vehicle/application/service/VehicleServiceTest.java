package com.ronald.fleetops.vehicle.application.service;
import com.ronald.fleetops.vehicle.application.exception.DuplicateVehicleVinException;
import com.ronald.fleetops.vehicle.application.exception.VehicleNotFoundException;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import com.ronald.fleetops.vehicle.infrastructure.persistence.InMemoryVehicleRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


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
    public void shouldRejectDuplicateVin() {
        InMemoryVehicleRepository inMemoryVehicleRepository =
                new InMemoryVehicleRepository();

        VehicleService vehicleService =
                new VehicleService(inMemoryVehicleRepository);

        Vehicle vehicle1 = new Vehicle(
                "ABCD",
                "PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        Vehicle vehicle2 = new Vehicle(
                "ABCD",
                "PLATE-002",
                "Honda",
                "Civic",
                2021,
                50000L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        vehicleService.registerVehicle(vehicle1);

        try {
            vehicleService.registerVehicle(vehicle2);
            fail("Une exception devait être levée");
        } catch (DuplicateVehicleVinException exception) {
            assertEquals(
                    "A vehicle with VIN " + vehicle2.getVin() + " already exists",
                    exception.getMessage()
            );
        }

        assertEquals(
                1,
                inMemoryVehicleRepository.findAll().size()
        );
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
    @Test
    public void shouldUpdateVehicle() {
        InMemoryVehicleRepository repository =
                new InMemoryVehicleRepository();

        VehicleService vehicleService =
                new VehicleService(repository);

        Vehicle vehicle = new Vehicle(
                "ABCD",
                "ABC123",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        Vehicle registeredVehicle =
                vehicleService.registerVehicle(vehicle);

        Vehicle updatedVehicle = vehicleService.updateVehicle(
                registeredVehicle.getId(),
                "XYZ789",
                "Honda",
                "Civic",
                2022,
                90000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        assertSame(registeredVehicle, updatedVehicle);
        assertEquals("ABCD", updatedVehicle.getVin());
        assertEquals("XYZ789", updatedVehicle.getLicensePlate());
        assertEquals("Honda", updatedVehicle.getBrand());
        assertEquals("Civic", updatedVehicle.getModel());
        assertEquals(2022, updatedVehicle.getManufacturingYear());
        assertEquals(90000L, updatedVehicle.getMileageInKilometers());
        assertEquals(VehicleType.SEDAN, updatedVehicle.getType());
        assertEquals(FuelType.HYBRID, updatedVehicle.getFuelType());
    }
    @Test
    public void shouldThrowExceptionWhenUpdatingUnknownVehicle() {
        InMemoryVehicleRepository repository =
                new InMemoryVehicleRepository();

        VehicleService vehicleService =
                new VehicleService(repository);

        UUID unknownId = UUID.randomUUID();

        VehicleNotFoundException exception = assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.updateVehicle(
                        unknownId,
                        "XYZ789",
                        "Honda",
                        "Civic",
                        2022,
                        90000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        assertEquals(
                "Vehicle not found with id " + unknownId,
                exception.getMessage()
        );
    }
}