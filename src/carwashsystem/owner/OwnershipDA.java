package carwashsystem.owner;

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
public class OwnershipDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public OwnershipDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isOwnership(Ownership owner) throws DataRepetitionException {
        boolean isAdd = false;
        String insertQry = "INSERT INTO ownership (vehicle_reg_no,customer_id,ownership_type) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(insertQry);
            ps.setString(1, owner.getIdVehicle());
            ps.setInt(2, owner.getIdCust());
            ps.setString(3, owner.getType());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isAdd;
    }

    public static boolean ownershipExists(int id, String vehregno) throws DataRepetitionException {
        boolean isFound = false;
        String selQry = "Select * from ownership where vehicle_reg_no='" + vehregno + "' and customer_id=" + id;
        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                isFound = true;
            }

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isFound;
    }

    public static ArrayList<String> getCustomers() throws UnknownException {
        ArrayList<String> arCust = new ArrayList<>();
        String selQry = "SELECT firstname FROM tblcustomer";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust.add(result.getString("firstname"));
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
    public static String getOwnershipType(int id, String vehregno) throws UnknownException {
        String arCust = "";
        String selQry = "Select * from ownership where vehicle_reg_no='" + vehregno + "' and customer_id=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("ownership_type");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

    public static String getCustomerCell(int id) throws UnknownException {
        String arCust = "";
        String selQry = "SELECT cellphone_number FROM tblcustomer where customer_id=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("cellphone_number");
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
}
