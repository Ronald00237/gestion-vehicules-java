package com.ronald.fleetops.vehicle.infrastructure.persistence;
import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryVehicleRepositoryTest {

        @Test
        public void shouldSaveAndFindVehicleById(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            Vehicle vehicle = new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            inMemoryVehicleRepository.save(vehicle);
           Optional<Vehicle> foundVehicle = inMemoryVehicleRepository.findById(vehicle.getId());
           assertTrue(foundVehicle.isPresent());
           assertSame(vehicle, foundVehicle.get());
        }


       @Test
    public void shouldReturnEmptyWhenVehicleIdDoesNotExist(){
        InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
        Optional<Vehicle> foundVehicle = inMemoryVehicleRepository.findById(UUID.randomUUID());
        assertTrue(foundVehicle.isEmpty());
       }

       @Test
        public void shouldSaveAndFindVehicleByVin(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
             Vehicle vehicle = new Vehicle("ABCD",
                   "OHVHI",
                   "Toyota",
                   "Corolla",
                   2020,
                   84446L,
                   VehicleType.SEDAN,
                   FuelType.GASOLINE);
             inMemoryVehicleRepository.save(vehicle);
             Optional<Vehicle> foundVehicle = inMemoryVehicleRepository.findByVin("ABCD");
             assertTrue(foundVehicle.isPresent());
             assertSame(vehicle, foundVehicle.get());
       }

       @Test
        public void shouldReturnEmptyWhenVehicleVinDoesNotExist(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            Optional<Vehicle> foundVehicle = inMemoryVehicleRepository.findByVin("UNKNOWN");
            assertTrue(foundVehicle.isEmpty());
       }
       @Test
        public void shouldReturnAllSaveVehicles(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
           Vehicle vehicle1 = new Vehicle("ABCD",
                   "OHVHI",
                   "Toyota",
                   "Corolla",
                   2020,
                   84446L,
                   VehicleType.SEDAN,
                   FuelType.GASOLINE);
           Vehicle vehicle2 = new Vehicle("NEWONE",
                   "MAPLAQUE",
                   "Toyota",
                   "Corolla",
                   2020,
                   84446L,
                   VehicleType.SEDAN,
                   FuelType.GASOLINE);
           inMemoryVehicleRepository.save(vehicle1);
           inMemoryVehicleRepository.save(vehicle2);
           List<Vehicle> vehicles = inMemoryVehicleRepository.findAll();
           assertEquals(2,vehicles.size());
           assertTrue(vehicles.contains(vehicle1));
           assertTrue(vehicles.contains(vehicle2));

       }

       @Test
    public void shouldReturnEmptyListWhenNoVehicleIsSaved(){
            InMemoryVehicleRepository inMemoryVehicleRepository = new InMemoryVehicleRepository();
            List<Vehicle> vehicles = inMemoryVehicleRepository.findAll();
            assertTrue(vehicles.isEmpty());
       }

}