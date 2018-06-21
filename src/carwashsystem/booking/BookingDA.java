package carwashsystem.booking;

import carwashsystem.owner.*;
import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.carservice.Performance;
import carwashsystem.cust.Customer;
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
public class BookingDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;
    private static PreparedStatement ps2;
    private static ResultSet result2;

    public BookingDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isBooking(Booking owner) throws DataRepetitionException {
        boolean isAdd = false;
        String insertQry = "INSERT INTO booking (booking_date,vehicle_reg_no,customer_id) VALUES (?,?,?)";
        try {
             ps = connection.prepareStatement(insertQry);
            ps.setString(1, owner.getBookDate());
            ps.setString(2, owner.getVhReg());
            ps.setInt(3, owner.getCustID());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isAdd;
    }
    public static boolean isBookingEmployee(Assignment owner) throws DataRepetitionException {
        boolean isAdd = false;
        String insertQry = "INSERT INTO assignment (booking_no,employee_id) VALUES (?,?)";
        try {
            ps = connection.prepareStatement(insertQry);
            ps.setInt(1, owner.getBooking_no());
            ps.setInt(2, owner.getEmployee_id());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isAdd;
    }

    public static boolean deleteBooking(Booking book) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `booking` where booking_no = " + book.getBookingID() + "";
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

    public static boolean updateBooking(Booking book) throws UnknownException {
        String editQry = "UPDATE `booking` SET booking_date= ?, vehicle_reg_no= ?, customer_id= ?, employee_id= ? WHERE booking_no=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, book.getBookDate());
            ps.setString(2, book.getVhReg());
            ps.setInt(3, book.getCustID());
            ps.setInt(4, book.getEmpID());
            ps.setInt(5, book.getBookingID());

            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static boolean updateCost(Booking book) throws UnknownException {
        String editQry = "UPDATE `booking` SET total_amount= ? WHERE booking_no=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, book.getTotAmt());
            ps.setInt(2, book.getBookingID());

            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static ArrayList<String> getCustomers() throws UnknownException {
        ArrayList<String> arCust = new ArrayList<>();
        String selQry = "SELECT firstname,surname,cellphone_number FROM tblcustomer";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust.add(result.getString("firstname") + " " + result.getString("surname") + " " + result.getString("cellphone_number"));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM booking ORDER BY booking_no DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("booking_no");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static ArrayList<String> getEmployees() throws UnknownException {
        ArrayList<String> arCust = new ArrayList<>();
        String selQry = "SELECT firstname,surname,cellphone FROM employee where empType='Washer'";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust.add(result.getString("firstname") + " " + result.getString("surname") + " " + result.getString("cellphone"));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static ArrayList<String> getVehicle() throws UnknownException {
        ArrayList<String> arVeh = new ArrayList<>();
        String selQry = "SELECT vehicle_reg_no FROM vehicle";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arVeh.add(result.getString("vehicle_reg_no"));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arVeh;
    }

    public static String getVehicleID(String regNo) throws UnknownException {
        String id = "";
        String selQry = "SELECT vehicle_reg_no FROM vehicle WHERE vehicle_reg_no= '" + regNo + "'";

        try {
            st = connection.createStatement();
            ps.setString(1, regNo);
            result = st.executeQuery(selQry);

            if (result.next()) {
                id = result.getString("vehicle_reg_no");
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }
        return id;
    }

    public static int getCustomerID(String fName) throws UnknownException {
        int id = 0;
        String selQry = "SELECT customer_id FROM tblcustomer WHERE firstname=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, fName);
            result = ps.executeQuery();

            if (result.next()) {
                id = result.getInt("customer_id");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return id;
    }

    public static int getCustomerID2(String phone) throws UnknownException {
        int id = 0;
        String selQry = "SELECT customer_id FROM tblcustomer WHERE cellphone_number=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, phone);
            result = ps.executeQuery();

            if (result.next()) {
                id = result.getInt("customer_id");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return id;
    }

    public static int getEmployeeID(String phone) throws UnknownException {
        int id = 0;
        String selQry = "SELECT employee_id FROM employee WHERE cellphone=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, phone);
            result = ps.executeQuery();

            if (result.next()) {
                id = result.getInt("employee_id");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return id;
    }

    public static ArrayList<Booking> getAllBooking() throws UnknownException {
        ArrayList<Booking> arBooking = new ArrayList<>();

        String selQry = "SELECT * FROM `booking`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arBooking.add(new Booking(result.getInt(5), result.getInt(6), result.getInt(1), result.getString(4), result.getString(2), result.getString(3)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arBooking;
    }

    public static ArrayList<Booking> getAllBookingByID(int id) throws UnknownException {
        ArrayList<Booking> arBooking = new ArrayList<>();
        Performance perf;
        String selQry2 = "SELECT * FROM `performance` where service_code=" + id;

        try {
            ps2 = connection.prepareStatement(selQry2);
            result2 = ps2.executeQuery();

            while (result2.next()) {
                perf = new Performance(result2.getInt(1), result2.getInt(2), result2.getString(3));
                String selQry = "SELECT * FROM `booking` where booking_no=" + perf.getBookingNo();
                ps = connection.prepareStatement(selQry);
                result = ps.executeQuery();
                while (result.next()) {
                    arBooking.add(new Booking(result.getInt(5), result.getInt(6), result.getInt(1), result.getString(4), result.getString(2), result.getString(3)));
                }
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arBooking;
    }

    public static int getCountService(int id2, String date,int bookingno) throws UnknownException {
        int id = 0;
        String selQry = "select COUNT(performance.Service_Code) as count, booking.booking_date from `performance` performance INNER JOIN `booking` booking ON performance.`Booking_No` = booking.`booking_no` Where performance.Service_Code=" + id2 + " and booking.booking_date='"+date+"' and booking.`booking_no`="+bookingno;
        try {
            ps = connection.prepareStatement(selQry);

            result = ps.executeQuery();

            if (result.next()) {
                id = result.getInt("count");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return id;
    }

    public static ArrayList<Booking> getAllBooking2() throws UnknownException {
        ArrayList<Booking> arBooking = new ArrayList<>();

        String selQry = "SELECT * FROM `booking` ORDER BY `booking_date` DESC";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arBooking.add(new Booking(result.getInt(5), result.getInt(6), result.getInt(1), result.getString(4), result.getString(2), result.getString(3)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arBooking;
    }

    public static ArrayList<String> getAllService(String BookingNo) throws UnknownException {
        ArrayList<String> arBooking = new ArrayList<>();

        String selQry = "SELECT * FROM `performance` where booking_no=" + BookingNo;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arBooking.add(result.getString("service_code"));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arBooking;
    }
    public static ArrayList<String> getAllEmployee(String BookingNo) throws UnknownException {
        ArrayList<String> arBooking = new ArrayList<>();

        String selQry = "SELECT * FROM `assignment` where booking_no=" + BookingNo;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arBooking.add(result.getString("employee_id") );
                 
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arBooking;
    }

    public static String getService(String id) throws UnknownException {
        String name = "";
        String selQry = "SELECT * FROM service WHERE service_code=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, id);
            result = ps.executeQuery();

            if (result.next()) {
                name = result.getString("service_type");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return name;
    }
    public static String getEmployee(String id) throws UnknownException {
        String name = "";
        String selQry = "SELECT * FROM employee WHERE employee_id=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, id);
            result = ps.executeQuery();

            if (result.next()) {
                name = result.getString("firstname") + " "+result.getString("surname")+" "+result.getString("cellphone");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return name;
    }

    public static ArrayList<String> getBookingID() throws UnknownException {
        ArrayList<String> arCust = new ArrayList<>();
        String selQry = "SELECT booking_no FROM booking";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust.add(result.getString("booking_no"));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static String getCustomerName(String id) throws UnknownException {
        String name = "";
        String selQry = "SELECT * FROM tblcustomer WHERE customer_id=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, id);
            result = ps.executeQuery();

            if (result.next()) {
                name = result.getString("firstname") + " " + result.getString("surname") + " " + result.getString("cellphone_number");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return name;
    }

    public static String getEmployeeName(String id) throws UnknownException {
        String name = "";
        String selQry = "SELECT * FROM employee WHERE employee_id=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, id);
            result = ps.executeQuery();

            if (result.next()) {
                name = result.getString("firstname") + " " + result.getString("surname") + " " + result.getString("cellphone");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return name;
    }

    public static String getEmployeeName2(String id) throws UnknownException {
        String name = "";
        String selQry = "SELECT * FROM employee WHERE employee_id=?";

        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, id);
            result = ps.executeQuery();

            if (result.next()) {
                name = result.getString("firstname") + " " + result.getString("surname") + " " + result.getString("cellphone");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return name;
    }
}
