package carwashsystem.cust;

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
public class CustomerDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public CustomerDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean addCustomer(Customer customer) throws DataRepetitionException {
        boolean isAdd = false;
        String custQry = "INSERT INTO tblcustomer (firstname,surname,gender,cellphone_number,house_no,street,city,zip) VALUES (?,?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getSurname());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getCell());
            ps.setInt(5, customer.getHouseNo());
            ps.setString(6, customer.getStreet());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getZip());

            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return isAdd;
    }

    public static boolean deleteCustomer(Customer customer) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `tblcustomer` where customer_id = " + customer.getCustid() + "";
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

    public static boolean updateCustomer(Customer customer) throws UnknownException {
        String editQry = "UPDATE `tblcustomer` SET firstname= ?, surname= ?, gender= ?, cellphone_number= ?, house_no= ?, street= ?, city= ?, zip= ? WHERE customer_id=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getSurname());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getCell());
            ps.setInt(5, customer.getHouseNo());
            ps.setString(6, customer.getStreet());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getZip());
            ps.setInt(9, customer.getCustid());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static ArrayList<Customer> getAllCustmer() throws UnknownException {
        ArrayList<Customer> arCustmer = new ArrayList<>();

        String selQry = "SELECT * FROM `tblcustomer`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCustmer.add(new Customer(result.getInt(1), result.getInt(6), result.getString(2), result.getString(3), result.getString(5), result.getString(4), result.getString(7), result.getString(8), result.getString(9)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arCustmer;
    }

    public ArrayList<Customer> getAllCustmer2() throws UnknownException {
        ArrayList<Customer> arCustmer = new ArrayList<>();

        String selQry = "SELECT * FROM `tblcustomer`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCustmer.add(new Customer(result.getInt(1), result.getInt(6), result.getString(2), result.getString(3), result.getString(5), result.getString(4), result.getString(7), result.getString(8), result.getString(9)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arCustmer;
    }

    public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM tblcustomer ORDER BY customer_id DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("customer_id");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static int getCustomerCount() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT `customer_id`,COUNT(`customer_id`) as value FROM `booking` GROUP BY `customer_id` ORDER BY value DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("value");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static int getCustomerCount2(int id) throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT `customer_id`,COUNT(`customer_id`) as value FROM `booking` where customer_id="+id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("value");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static String getCustomerName(int id) throws UnknownException {
        String arCust = "";
        String selQry = "SELECT firstname,surname FROM tblcustomer where customer_id=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("firstname") + " " + result.getString("surname");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }
}
