package com.ronald.fleetops.vehicle.infrastructure.persistence.jpa;

import com.ronald.fleetops.vehicle.domain.FuelType;
import com.ronald.fleetops.vehicle.domain.Vehicle;
import com.ronald.fleetops.vehicle.domain.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostgreSqlVehicleRepositoryTest {

    @Test
    public void shouldSaveVehicle() {
        SpringDataVehicleJpaRepository jpaRepository =
                mock(SpringDataVehicleJpaRepository.class);

        PostgreSqlVehicleRepository repository =
                new PostgreSqlVehicleRepository(jpaRepository);

        Vehicle vehicle = new Vehicle(
                "POSTGRES-VIN-001",
                "POSTGRES-PLATE-001",
                "Toyota",
                "Corolla",
                2020,
                85000L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        when(jpaRepository.save(any(VehicleEntity.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        Vehicle savedVehicle = repository.save(vehicle);

        assertEquals(vehicle.getId(), savedVehicle.getId());
        assertEquals(vehicle.getVin(), savedVehicle.getVin());
        assertEquals(
                vehicle.getLicensePlate(),
                savedVehicle.getLicensePlate()
        );
        assertEquals(vehicle.getStatus(), savedVehicle.getStatus());
    }

    @Test
    public void shouldFindVehicleById() {
        SpringDataVehicleJpaRepository jpaRepository =
                mock(SpringDataVehicleJpaRepository.class);

        PostgreSqlVehicleRepository repository =
                new PostgreSqlVehicleRepository(jpaRepository);

        Vehicle vehicle = new Vehicle(
                "POSTGRES-VIN-002",
                "POSTGRES-PLATE-002",
                "Honda",
                "Civic",
                2021,
                50000L,
                VehicleType.SEDAN,
                FuelType.GASOLINE
        );

        when(jpaRepository.findById(vehicle.getId()))
                .thenReturn(
                        java.util.Optional.of(
                                VehicleEntity.fromDomain(vehicle)
                        )
                );

        Vehicle foundVehicle = repository
                .findById(vehicle.getId())
                .orElseThrow();

        assertEquals(vehicle.getId(), foundVehicle.getId());
        assertEquals(vehicle.getVin(), foundVehicle.getVin());
        assertEquals(
                vehicle.getLicensePlate(),
                foundVehicle.getLicensePlate()
        );
    }
    @Test
    public void shouldFindVehicleByVin() {
        SpringDataVehicleJpaRepository jpaRepository =
                mock(SpringDataVehicleJpaRepository.class);

        PostgreSqlVehicleRepository repository =
                new PostgreSqlVehicleRepository(jpaRepository);

        Vehicle vehicle = new Vehicle(
                "POSTGRES-VIN-003",
                "POSTGRES-PLATE-003",
                "Ford",
                "Transit",
                2022,
                40000L,
                VehicleType.VAN,
                FuelType.DIESEL
        );

        when(jpaRepository.findByVin(vehicle.getVin()))
                .thenReturn(
                        java.util.Optional.of(
                                VehicleEntity.fromDomain(vehicle)
                        )
                );

        Vehicle foundVehicle = repository
                .findByVin(vehicle.getVin())
                .orElseThrow();

        assertEquals(vehicle.getId(), foundVehicle.getId());
        assertEquals(vehicle.getVin(), foundVehicle.getVin());
        assertEquals(VehicleType.VAN, foundVehicle.getType());
        assertEquals(FuelType.DIESEL, foundVehicle.getFuelType());
    }

    @Test
    public void shouldFindAllVehicles() {
        SpringDataVehicleJpaRepository jpaRepository =
                mock(SpringDataVehicleJpaRepository.class);

        PostgreSqlVehicleRepository repository =
                new PostgreSqlVehicleRepository(jpaRepository);

        Vehicle firstVehicle = new Vehicle(
                "POSTGRES-VIN-004",
                "POSTGRES-PLATE-004",
                "Toyota",
                "RAV4",
                2022,
                30000L,
                VehicleType.SUV,
                FuelType.HYBRID
        );

        Vehicle secondVehicle = new Vehicle(
                "POSTGRES-VIN-005",
                "POSTGRES-PLATE-005",
                "Ford",
                "F-150",
                2021,
                60000L,
                VehicleType.PICKUP,
                FuelType.GASOLINE
        );

        when(jpaRepository.findAll())
                .thenReturn(
                        java.util.List.of(
                                VehicleEntity.fromDomain(firstVehicle),
                                VehicleEntity.fromDomain(secondVehicle)
                        )
                );

        java.util.List<Vehicle> vehicles =
                repository.findAll();

        assertEquals(2, vehicles.size());
        assertEquals(
                firstVehicle.getId(),
                vehicles.get(0).getId()
        );
        assertEquals(
                secondVehicle.getId(),
                vehicles.get(1).getId()
        );
    }
    @Test
    public void shouldReturnEmptyWhenVehicleDoesNotExist() {
        SpringDataVehicleJpaRepository jpaRepository =
                mock(SpringDataVehicleJpaRepository.class);

        PostgreSqlVehicleRepository repository =
                new PostgreSqlVehicleRepository(jpaRepository);

        java.util.UUID unknownId =
                java.util.UUID.randomUUID();

        when(jpaRepository.findById(unknownId))
                .thenReturn(java.util.Optional.empty());

        when(jpaRepository.findByVin("UNKNOWN-VIN"))
                .thenReturn(java.util.Optional.empty());

        assertEquals(
                java.util.Optional.empty(),
                repository.findById(unknownId)
        );

        assertEquals(
                java.util.Optional.empty(),
                repository.findByVin("UNKNOWN-VIN")
        );
    }
}