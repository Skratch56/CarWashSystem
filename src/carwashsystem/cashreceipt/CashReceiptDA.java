package carwashsystem.cashreceipt;

import carwashsystem.owner.*;
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
public class CashReceiptDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public CashReceiptDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isAddCashReceipt(CashReceipt owner) throws DataRepetitionException {
        boolean isAdd = false;
        String insertQry = "INSERT INTO cashreceipt (amountreceived,bookingno,employeeno) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(insertQry);
            ps.setInt(1, owner.getAmountReceived());
            ps.setInt(2, owner.getBookingNo());
            ps.setInt(3, owner.getEmployeeID());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isAdd;
    }
      public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM cashreceipt ORDER BY cashreceiptno DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("cashreceiptno");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }

//    public static ArrayList<String> getCustomers() throws UnknownException {
//        ArrayList<String> arCust = new ArrayList<>();
//        String selQry = "SELECT firstname FROM tblcustomer";
//
//        try {
//            ps = connection.prepareStatement(selQry);
//            result = ps.executeQuery();
//
//            while (result.next()) {
//                arCust.add(result.getString("firstname"));
//            }
//        } catch (SQLException e) {
//            throw new UnknownException(e.getMessage());
//        }
//        return arCust;
//    }
//    
//    
//     public static ArrayList<String> getVehicle() throws UnknownException {
//        ArrayList<String> arVeh = new ArrayList<>();
//        String selQry = "SELECT vehicle_reg_no FROM vehicle";
//
//        try {
//            ps = connection.prepareStatement(selQry);
//            result = ps.executeQuery();
//
//            while (result.next()) {
//                arVeh.add(result.getString("vehicle_reg_no"));
//            }
//        } catch (SQLException e) {
//            throw new UnknownException(e.getMessage());
//        }
//        return arVeh;
//    }
//     
//     
//       
//     public static String getVehicleID(String regNo) throws UnknownException {
//      String id ="";
//        String selQry = "SELECT vehicle_reg_no FROM vehicle WHERE vehicle_reg_no= '" +regNo + "'";
//
//        try {
//            st = connection.createStatement();
//            ps.setString(1, regNo);
//            result = st.executeQuery(selQry);
//
//            if (result.next()) {
//               id = result.getString("vehicle_reg_no");
//            }
//        } catch (SQLException e) {
//            throw new UnknownException("" + e.getMessage());
//        }
//        return id;
//    }
//     
//     
//       
//     public static int getCustomerID(String fName) throws UnknownException {
//      int id =0;
//        String selQry = "SELECT customer_id FROM tblcustomer WHERE firstname=?";
//
//        try {
//            ps = connection.prepareStatement(selQry);
//            ps.setString(1, fName);
//            result = ps.executeQuery();
//
//            if (result.next()) {
//               id = result.getInt("customer_id");
//            }
//        } catch (SQLException e) {
//            throw new UnknownException(e.getMessage());
//        }
//        return id;
//    }
}
