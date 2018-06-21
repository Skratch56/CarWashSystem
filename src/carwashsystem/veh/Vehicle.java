package carwashsystem.veh;

import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;

/**
 *
 * @author
 */
public class Vehicle {

    private String regNo, brand, vType, color;

    public Vehicle() {
        this("", "", "", "");
    }

    public Vehicle(String regNo, String brand, String vType, String color) {
        this.brand = brand;
        this.vType = vType;
        this.color = color;
        this.regNo = regNo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public String getVType() {
        return vType;
    }

    public String getColor() {
        return color;
    }

    public String getRegNo() {
        return regNo;
    }

    public boolean addVehicle(Vehicle vehicle) throws DataRepetitionException {
        return VehicleDA.addVehicle(this);
    }
    public boolean updateVehicle(Vehicle vehicle) throws DataRepetitionException, UnknownException {
        return VehicleDA.updateVehicle(this);
    }
     public boolean deleteVehicle(Vehicle vehicle) throws DataRepetitionException, UnknownException, DataStorageException {
        return VehicleDA.deleteVehicle(this);
    }

    public static java.util.ArrayList<Vehicle> getAllVehicle() throws DataStorageException, UnknownException {
        return VehicleDA.getAllVehicle();
    }
      public String getLastAddedID() throws DataRepetitionException, DataStorageException, UnknownException {
        return VehicleDA.getLastAddedID();
    }
}
