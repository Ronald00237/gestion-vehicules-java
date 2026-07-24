package com.ronald.fleetops.vehicle.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class VehicleTest {

    @Test
   public void shouldCreateVehicleWithProvidedValues() {
       Vehicle vehicleTest = new Vehicle("ABCD", "OHVHI", "Toyota", "Corolla", 2020, 84446L, VehicleType.SEDAN, FuelType.GASOLINE);
     assertNotNull(vehicleTest.getId());
     assertEquals("ABCD", vehicleTest.getVin());
     assertEquals("OHVHI",vehicleTest.getLicensePlate());
     assertEquals("Toyota", vehicleTest.getBrand());
     assertEquals("Corolla", vehicleTest.getModel());
     assertEquals(2020, vehicleTest.getManufacturingYear());
     assertEquals(84446L, vehicleTest.getMileageInKilometers());
     assertEquals(VehicleType.SEDAN, vehicleTest.getType());
     assertEquals(FuelType.GASOLINE, vehicleTest.getFuelType());
     assertEquals(VehicleStatus.AVAILABLE, vehicleTest.getStatus());
   }
}