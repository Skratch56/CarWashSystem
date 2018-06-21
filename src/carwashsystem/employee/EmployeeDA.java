package carwashsystem.employee;

import carwashsystem.cust.*;
import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class EmployeeDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public EmployeeDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean addCustomer(Employee employee) throws DataRepetitionException {
        boolean isAdd = false;
        String custQry = "INSERT INTO `employee` (`firstname`, `surname`, `empType`, `dob`, `id`"
                + ", `passport`, `gender`, `cellphone`, `email`, `salary`, `house_number`, `street_name`"
                + ", `city`, `postal_code`, `username`, `password`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setString(1, employee.getFirstname());
            ps.setString(2, employee.getSurname());
            ps.setString(3, employee.getEmpType());
            ps.setString(4, employee.getDob());
            ps.setString(5, employee.getId());
            ps.setString(6, employee.getPassport());
            ps.setString(7, employee.getGender());
            ps.setString(8, employee.getCellphone());
            ps.setString(9, employee.getEmail());
            ps.setString(10, employee.getSalary());
            ps.setString(11, employee.getHouse_number());
            ps.setString(12, employee.getStreet_name());
            ps.setString(13, employee.getCity());
            ps.setString(14, employee.getPostal_code());
            ps.setString(15, employee.getUsername());
            ps.setString(16, employee.getPassword());

            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return isAdd;
    }

    public static boolean deleteEmployee(Employee employee) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `employee` where employee_id = " + employee.getEmployeeID() + "";
        System.out.println(sqlLine);

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
            isDel = true;
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
        return isDel;
    }

    public static boolean updateEmployee(Employee employee) throws UnknownException {
        String editQry = "UPDATE `employee`  SET `firstname` = ?, `surname` = ?, `empType` = ?, `dob` = ?, `id` = ?, `passport` = ?, `gender` = ?, `cellphone` = ?, `email` = ?, `salary` = ?, `house_number` = ?, `street_name` = ?, `city` = ?, `postal_code` = ?, `username` = ?, `password` = ? WHERE `employee`.`employee_id` = ?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, employee.getFirstname());
            ps.setString(2, employee.getSurname());
            ps.setString(3, employee.getEmpType());
            ps.setString(4, employee.getDob());
            ps.setString(5, employee.getId());
            ps.setString(6, employee.getPassport());
            ps.setString(7, employee.getGender());
            ps.setString(8, employee.getCellphone());
            ps.setString(9, employee.getEmail());
            ps.setString(10, employee.getSalary());
            ps.setString(11, employee.getHouse_number());
            ps.setString(12, employee.getStreet_name());
            ps.setString(13, employee.getCity());
            ps.setString(14, employee.getPostal_code());
            ps.setString(15, employee.getUsername());
            ps.setString(16, employee.getPassword());
            ps.setInt(17, employee.getEmployeeID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static ArrayList<Employee> getAllEmployee() throws UnknownException {
        ArrayList<Employee> arCustmer = new ArrayList<>();

        String selQry = "SELECT * FROM `employee`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCustmer.add(new Employee(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9), result.getString(10), result.getString(11), result.getString(12), result.getString(13), result.getString(14), result.getString(15), result.getString(16), result.getString(17),null));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arCustmer;
    }

    public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM employee ORDER BY employee_id DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("employee_id");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }
}
