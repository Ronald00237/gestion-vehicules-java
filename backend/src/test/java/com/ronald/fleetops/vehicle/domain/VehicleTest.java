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

    @Test
    public void shouldUnassignAssignedVehicle(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.assign();
        vehicle.unassign();
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    public void shouldRejectUnassignmentWhenVehicleIsNotAssigned(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);

        try{
            vehicle.unassign();
            fail("une exception doit etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Only assigned vehicles can be unassigned", exception.getMessage());
        }
    }

    @Test
    public void shouldSendAvailableVehicleToMaintenance(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.sendToMaintenance();
        assertEquals(VehicleStatus.IN_MAINTENANCE, vehicle.getStatus());
    }

    @Test
    public void shouldRejectMaintenanceWhenVehicleIsNotAvailable(){
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
            vehicle.sendToMaintenance();
            fail("une exception doit etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Only available vehicles can be sent to maintenance", exception.getMessage());
        }
    }

    @Test
    public void shouldCompleteVehicleMaintenance(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.sendToMaintenance();
        vehicle.completeMaintenance();
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());

    }

    @Test
    public void shouldRejectMaintenanceCompletionWhenVehicleIsNotInMaintenance(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());

        try{
            vehicle.completeMaintenance();
            fail("une exception doit etre lancee");
        } catch (IllegalArgumentException exception){
            assertEquals("Only vehicles in maintenance can complete maintenance", exception.getMessage());
        }
    }


    @Test
    public void shouldMarkAvailableVehicleOutOfService(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
              vehicle.markOutOfService();
        assertEquals(VehicleStatus.OUT_OF_SERVICE, vehicle.getStatus());
    }

    @Test
    public void shouldRejectOutOfServiceWhenVehicleIsAssigned(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
                vehicle.assign();
        try {
            vehicle.markOutOfService();
            fail("Une exception doit etre lancee");
        } catch(IllegalArgumentException exception){
            assertEquals("Only available vehicles or vehicles in maintenance can be marked out of service",exception.getMessage());
        }
    }
    @Test
    public void shouldMarkVehicleInMaintenanceOutOfService(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.sendToMaintenance();
        vehicle.markOutOfService();
        assertEquals(VehicleStatus.OUT_OF_SERVICE, vehicle.getStatus());
    }

    @Test
    public  void shouldRejectRetirementWhenVehicleIsNotOutOfService(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        assertEquals(VehicleStatus.AVAILABLE,vehicle.getStatus());

        try{
            vehicle.retire();
            fail("Une exception doit etre lancee");
        } catch(IllegalArgumentException exeption) {
            assertEquals("Only out-of-service vehicles can be retired", exeption.getMessage());
        }
    }
    @Test
    public void shouldRetireOutOfServiceVehicle(){
        Vehicle vehicle = new Vehicle("ABCD",
                "OHVHI",
                "Toyota",
                "Corolla",
                2020,
                84446L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        vehicle.markOutOfService();
        vehicle.retire();
        assertEquals(VehicleStatus.RETIRED,vehicle.getStatus());
    }
    @Test
    public void shouldUpdateVehicleDetails() {
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

        vehicle.updateDetails(
                "XYZ789",
                "Honda",
                "Civic",
                2022,
                90000L,
                VehicleType.SEDAN,
                FuelType.HYBRID
        );

        assertEquals("ABCD", vehicle.getVin());
        assertEquals("XYZ789", vehicle.getLicensePlate());
        assertEquals("Honda", vehicle.getBrand());
        assertEquals("Civic", vehicle.getModel());
        assertEquals(2022, vehicle.getManufacturingYear());
        assertEquals(90000L, vehicle.getMileageInKilometers());
        assertEquals(VehicleType.SEDAN, vehicle.getType());
        assertEquals(FuelType.HYBRID, vehicle.getFuelType());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    public void shouldRejectMileageLowerThanCurrentMileageWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        "Honda",
                        "Civic",
                        2022,
                        80000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        assertEquals(
                "Mileage must not be lower than current mileage",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectBlankLicensePlateWhenUpdating(){
        Vehicle vehicle = new Vehicle("ABCD",
                "ABCD123",
                "Toyota",
                "Corolla",
                2020,
                8444L,
                VehicleType.SEDAN,
                FuelType.GASOLINE);
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class,() ->vehicle.updateDetails(" ",
                "Honda","Civic",
                2022,
                90000L,
                 VehicleType.SEDAN,
                FuelType.HYBRID));

        assertEquals("License plate must not be blank", exception.getMessage());
    }
    @Test
    public void shouldRejectBlankBrandWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        " ",
                        "Civic",
                        2022,
                        90000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        assertEquals("Brand must not be blank", exception.getMessage());
    }

    @Test
    public void shouldRejectBlankModelWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        "Honda",
                        " ",
                        2022,
                        90000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        assertEquals("Model must not be blank", exception.getMessage());
    }

    @Test
    public void shouldRejectInvalidManufacturingYearWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        "Honda",
                        "Civic",
                        1885,
                        90000L,
                        VehicleType.SEDAN,
                        FuelType.HYBRID
                )
        );

        assertEquals(
                "Manufacturing year is invalid",
                exception.getMessage()
        );
    }

    @Test
    public void shouldRejectNullVehicleTypeWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        "Honda",
                        "Civic",
                        2022,
                        90000L,
                        null,
                        FuelType.HYBRID
                )
        );

        assertEquals(
                "Vehicle type must not be null",
                exception.getMessage()
        );
    }
    @Test
    public void shouldRejectNullFuelTypeWhenUpdating() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updateDetails(
                        "XYZ789",
                        "Honda",
                        "Civic",
                        2022,
                        90000L,
                        VehicleType.SEDAN,
                        null
                )
        );

        assertEquals(
                "Fuel type must not be null",
                exception.getMessage()
        );
    }
}