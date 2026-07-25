package com.ronald.fleetops.vehicle.domain;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.time.Year;

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

   @Test
    public void shouldRejectNullVin() {
       try {
           new Vehicle(null,
                   "OHVHI",
                   "Toyota",
                   "Corolla",
                   2020,
                   84446L,
                   VehicleType.SEDAN,
                   FuelType.GASOLINE);

         fail("Une exception devrait être lancée");

      } catch (IllegalArgumentException exception) {
           assertEquals("VIN must not be blank", exception.getMessage());
       }

    }

    @Test
    public void shouldRejectBlankVin(){
        try{
            new Vehicle(" ",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    844446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("VIN must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectNullLicensePlate() {
        try {
            new Vehicle("ABCD", null,
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception) {
            assertEquals("License plate must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectBlankLicensePlate(){
        try{
            new Vehicle("ABCD",
                    " ",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch(IllegalArgumentException exceptiopn){
            assertEquals("License plate must not be blank", exceptiopn.getMessage());
        }
    }

    @Test
    public void shouldRejectNullBrand(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    null,
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Brand must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectBlankBrand(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    " ",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Brand must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectNullModel(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    null,
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Model must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectBlankModel(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    " ",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Model must not be blank", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectNegativeMileage(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    -1L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Mileage must not be negative", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectNullVehicleType(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    null,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Vehicle type must not be null", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectNullFuelType(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    null);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Fuel type must not be null", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectManufacturingYearBefore1886(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    1884,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Manufacturing year is invalid", exception.getMessage());
        }
    }

    @Test
    public void shouldRejectManufacturingYearTooFarInTheFuture(){
        try{
            new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    Year.now().getValue() +2,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            fail("Une exception devrait etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Manufacturing year is invalid", exception.getMessage());
        }
    }

    @Test
    public void shouldAssignAvailableVehicle(){
           Vehicle vehicle = new Vehicle("ABCD",
                    "OHVHI",
                    "Toyota",
                    "Corolla",
                    2020,
                    84446L,
                    VehicleType.SEDAN,
                    FuelType.GASOLINE);
            vehicle.assign();
            assertEquals(VehicleStatus.ASSIGNED, vehicle.getStatus());
    }

    @Test
    public void shouldRejectAssignmentWhenVehicleIsNotAvailable(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.assign();

        try{
            vehicle.assign();
            fail("une exception doit etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Only available vehicles can be assigned", exception.getMessage());
        }
    }
}