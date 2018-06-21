package carwashsystem.cust;

import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import java.util.ArrayList;

/**
 *
 * @author
 */
public class Customer {

    private int custid, houseNo;
    private String name, surname, cell, gender, street, city, zip;

    public Customer() {
        this(0, 0, "", "", "", "", "", "", "");
    }

    public Customer(int custid, int houseNo, String name, String surname, String cell, String gender, String street, String city, String zip) {
        this.custid = custid;
        this.name = name;
        this.surname = surname;
        this.cell = cell;
        this.gender = gender;
        this.street = street;
        this.houseNo = houseNo;
        this.city = city;
        this.zip = zip;
    }

    public int getCustid() {
        return custid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCell() {
        return cell;
    }

    public String getGender() {
        return gender;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public boolean addCustomer(Customer customer) throws DataRepetitionException {
        return CustomerDA.addCustomer(this);
    }

    public boolean deleteCustomer(Customer customer) throws DataRepetitionException, DataStorageException {
        return CustomerDA.deleteCustomer(this);
    }

    public boolean updateCustomer(Customer customer) throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.updateCustomer(this);
    }

    public int getLastAddedID() throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getLastAddedID();
    }

    public int getCustomerCount() throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getCustomerCount();
    }

    public int getCustomerCount2(int id) throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getCustomerCount2(id);
    }

    public static ArrayList<Customer> getAllCustmer() throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getAllCustmer();
    }
    
    public ArrayList<Customer> getAllCustmer2() throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getAllCustmer();
    }
     public String getCustomerName(int id) throws DataRepetitionException, DataStorageException, UnknownException {
        return CustomerDA.getCustomerName(id);
    }

}
