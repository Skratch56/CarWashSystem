/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.allocation;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.service.Service;
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
 * @author CE
 */
public class AllocationDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public AllocationDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isAddAllocation(Allocation allocation) throws DataRepetitionException {
        boolean isAdd = false;
        String qry = "INSERT INTO allocation (service_code, equipment_id, description,qty) VALUES(?,?,?,?)";
        try {
            ps = connection.prepareStatement(qry);
            ps.setInt(1, allocation.getSerID());
            ps.setInt(2, allocation.getEquipID());
            ps.setString(3, allocation.getDes());
            ps.setString(4, allocation.getQty()+"");
            ps.executeUpdate();

            isAdd = true;
        } catch (SQLException e) {
            throw new DataRepetitionException("Allocation already added\n" + e.getMessage());
        }
        return isAdd;
    }

    /**
     * for updating, deleting and viewing
     *
     * @param service
     * @return
     * @throws UnknownException
     */
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

    public static boolean updateAllocation(Allocation allocation) throws UnknownException {
        String editQry = "UPDATE allocation SET service_code = ?, equipment_id= ?, description=? WHERE service_code=? and equipment_id=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, allocation.getSerID());
            ps.setInt(2, allocation.getEquipID());
            ps.setString(3, allocation.getDes());
            ps.setInt(4, allocation.getSerID());
            ps.setInt(5, allocation.getEquipID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }
    public static boolean updateQTY(int newqty,int id) throws UnknownException {
        String editQry = "UPDATE equipment SET qtyinstock = ? WHERE equipment_id=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, newqty);
            ps.setInt(2, id);
           
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }

    public static boolean deleteAllocation(Allocation allocation) throws UnknownException {
        String editQry = "DELETE FROM Allocation WHERE service_code = ? and equipment_id=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, allocation.getSerID());
            ps.setInt(2, allocation.getEquipID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return isUpdated;

    }
}
