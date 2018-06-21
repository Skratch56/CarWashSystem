package carwashsystem.servicelist;

import carwashsystem.carWashDB.CreateConnection;
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
public class ServiceListDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public ServiceListDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<ServiceList> getAllServices() throws UnknownException {
        ArrayList<ServiceList> arServices = new ArrayList<>();

        String selQry = "SERVICE * FROM service";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arServices.add(new ServiceList(result.getInt(1), result.getString(2), result.getShort(3)));
            }
        } catch (SQLException e) {
            throw new UnknownException("" + e.getMessage());
        }

        return arServices;
    }
}
