package carwashsystem.veh;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.cust.Customer;
import carwashsystem.servicelist.ServiceList;
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
public class VehicleDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public VehicleDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean addVehicle(Vehicle vehicle) throws DataRepetitionException {
        boolean isAdd = false;
        String qry = "INSERT INTO vehicle (vehicle_reg_no,brand,vehicle_type,color) VALUES (?,?,?,?)";
        try {
            ps = connection.prepareStatement(qry);
            ps.setString(1, vehicle.getRegNo());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getVType());
            ps.setString(4, vehicle.getColor());
            ps.executeUpdate();
            isAdd = true;
        } catch (SQLException e) {
            throw new DataRepetitionException("Record already added. Please add another record.\n" + e.getMessage());
        }
        return isAdd;
    }

    public static ArrayList<Vehicle> getAllVehicle() throws UnknownException {
        ArrayList<Vehicle> arVehicle = new ArrayList<>();

        String selQry = "SELECT * FROM `vehicle`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arVehicle.add(new Vehicle(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arVehicle;
    }

    public static boolean updateVehicle(Vehicle vehicle) throws UnknownException {
        String editQry = "UPDATE vehicle SET brand= ?, vehicle_type= ?, color= ? WHERE vehicle_reg_no=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getVType());
            ps.setString(3, vehicle.getColor());
            ps.setString(4, vehicle.getRegNo());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static boolean deleteVehicle(Vehicle vehicle) throws DataStorageException {

        String sqlLine = "delete from vehicle where vehicle_reg_no = " + vehicle.getRegNo() + "";
        System.out.println(sqlLine);
        boolean isDeleted = false;
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
            isDeleted = true;
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
        return isDeleted;
    }
    public static String getLastAddedID() throws UnknownException {
        String arCust = "";
        String selQry = "SELECT * FROM vehicle ORDER BY vehicle_reg_no DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("vehicle_reg_no");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }
}
