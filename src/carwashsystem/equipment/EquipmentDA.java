/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.equipment;

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
public class EquipmentDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public EquipmentDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean addEquipment(Equipment equip) throws DataRepetitionException {
        boolean flag = false;

        String inQry = "INSERT INTO EQUIPMENT (equipment_type, equip_description,qtyinstock) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(inQry);
            ps.setString(1, equip.getEquipmentType());
            ps.setString(2, equip.getEquipDesc());
            ps.setString(3, equip.getQty() + "");

            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new DataRepetitionException();
        }

        return flag;

    }

    public static boolean editEquipment(Equipment equip) throws UnknownException {
        boolean flag = false;

        String inQry = "UPDATE equipment SET equipment_type = ?, equip_description=? , qtyinstock=? WHERE equipment_id= ?";
        try {
            ps = connection.prepareStatement(inQry);
            ps.setString(1, equip.getEquipmentType());
            ps.setString(2, equip.getEquipDesc());
            ps.setString(3, equip.getQty() + "");
            ps.setInt(4, equip.getId());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new UnknownException();
        }

        return flag;

    }

    public static boolean deleteEquipment(Equipment equip) throws UnknownException {
        boolean flag = false;

        String inQry = "DELETE FROM equipment WHERE equipment_id= ?";
        try {
            ps = connection.prepareStatement(inQry);

            ps.setInt(1, equip.getId());

            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new UnknownException();
        }

        return flag;

    }

    public static ArrayList<Equipment> getAllEquipment() {
        ArrayList<Equipment> arEquipment = new ArrayList<>();
        String selQry = "SELECT * FROM equipment";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arEquipment.add(new Equipment(result.getInt("equipment_id"), result.getString("equipment_type"), result.getString("equip_description"), result.getInt("qtyinstock"), null,null));
            }
        } catch (SQLException e) {

        }
        return arEquipment;

    }

    /**
     *
     * @param type
     * @return return equipment list whose type is passed as a parameter
     */
    public static ArrayList<Equipment> getEquipmentByType(String type) {
        ArrayList<Equipment> arEquipment = new ArrayList<>();
        String selQry = "SELECT * FROM equipment WHERE equipment_type=?";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arEquipment.add(new Equipment(result.getInt("equipment_id"), result.getString("equipment_type"), result.getString("equip_description"), result.getInt("qtyinstock"), null,null));
            }
        } catch (SQLException e) {

        }
        return arEquipment;

    }
}
