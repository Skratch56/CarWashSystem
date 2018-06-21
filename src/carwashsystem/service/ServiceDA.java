package carwashsystem.service;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
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
public class ServiceDA {
    
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;
    
    public ServiceDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static boolean isAddService(Service service) throws DataRepetitionException {
        boolean isAdd = false;
        String qry = "INSERT INTO SERVICE (service_type, service_cost) VALUES(?,?)";
        try {
            ps = connection.prepareStatement(qry);
            ps.setString(1, service.getSType());
            ps.setDouble(2, service.getCost());
            ps.executeUpdate();
            
            isAdd = true;
        } catch (SQLException e) {
            throw new DataRepetitionException("Service already added\n" + e.getMessage());
        }
        return isAdd;
    }
    
    public static ArrayList<String> getAllServiceCodes() throws UnknownException {
        ArrayList<String> arCodes = new ArrayList<>();
        String selQry = "Select service_type from service";
        
        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();
            while (result.next()) {
                arCodes.add(result.getString("service_type"));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCodes;
    }
    
    public static ArrayList<ServiceList> getAllServiceByID(int id) throws UnknownException {
        ArrayList<ServiceList> arCodes = new ArrayList<>();
        String selQry = "Select * from service where service_code =?";
        
        try {
            ps = connection.prepareStatement(selQry);
            ps.setInt(1, id);
            result = ps.executeQuery();
            while (result.next()) {
                arCodes.add(new ServiceList(result.getInt(1), result.getString(2), result.getDouble(3)));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCodes;
    }

    /**
     * for updating, deleting and viewing
     *
     * @param service
     * @return
     * @throws UnknownException
     */
    public static int getServiceCodes(String service) throws UnknownException {
        
        String selQry = "Select service_code from service WHERE service_type=?";
        int id = 0;
        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, service);
            result = ps.executeQuery();
            while (result.next()) {
                id = result.getInt("service_code");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return id;
    }

    /**
     *
     * @return @throws carwashsystem.carWashExeception.UnknownException
     */
    public static ArrayList<Service> getAllService() throws UnknownException {
        ArrayList<Service> arCodes = new ArrayList<>();
        String selQry = "Select * from service";
        
        try {
            ps = connection.prepareStatement(selQry);
            
            result = ps.executeQuery();
            while (result.next()) {
                arCodes.add(new Service(result.getInt(1), result.getString(2), result.getDouble(3),null));
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCodes;
    }
    
    public static boolean updateService(Service service) throws UnknownException {
        String editQry = "UPDATE service SET service_cost=?, service_type=? WHERE service_code=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setDouble(1, service.getCost());
            ps.setString(2, service.getSType());
            ps.setInt(3, service.getSerID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;
        
    }
    
    public static boolean deleteService(Service service) throws UnknownException {
        String editQry = "DELETE FROM service WHERE service_code=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, service.getSerID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;
        
    }
    
}
