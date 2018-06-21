package carwashsystem.employee;

import carwashsystem.cust.*;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import javafx.scene.control.CheckBox;

/**
 *
 * @author
 */
public class Employee {

    private int employeeID;
    private String firstname, surname, empType, dob, id, passport, gender, cellphone, email, salary, house_number, street_name, city, postal_code, username, password;
    private CheckBox checkBox;

    public Employee(int employeeID, String firstname, String surname, String empType, String dob, String id, String passport, String gender, String cellphone, String email, String salary, String house_number, String street_name, String city, String postal_code, String username, String password, CheckBox checkBox) {
        this.employeeID = employeeID;
        this.firstname = firstname;
        this.surname = surname;
        this.empType = empType;
        this.dob = dob;
        this.id = id;
        this.passport = passport;
        this.gender = gender;
        this.cellphone = cellphone;
        this.email = email;
        this.salary = salary;
        this.house_number = house_number;
        this.street_name = street_name;
        this.city = city;
        this.postal_code = postal_code;
        this.username = username;
        this.password = password;
        this.checkBox = new CheckBox();
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmpType() {
        return empType;
    }

    public String getDob() {
        return dob;
    }

    public String getId() {
        return id;
    }

    public String getPassport() {
        return passport;
    }

    public String getGender() {
        return gender;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    public String getSalary() {
        return salary;
    }

    public String getHouse_number() {
        return house_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getCity() {
        return city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public boolean addEmployee(Employee customer) throws DataRepetitionException {
        return EmployeeDA.addCustomer(this);
    }

    public boolean deleteEmployee(Employee customer) throws DataRepetitionException, DataStorageException {
        return EmployeeDA.deleteEmployee(this);
    }

    public boolean updateEmployee(Employee customer) throws DataRepetitionException, DataStorageException, UnknownException {
        return EmployeeDA.updateEmployee(this);
    }

    public int getLastAddedID() throws DataRepetitionException, DataStorageException, UnknownException {
        return EmployeeDA.getLastAddedID();
    }
}
