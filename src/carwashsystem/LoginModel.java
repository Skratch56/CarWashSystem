package carwashsystem;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph
 */
public class LoginModel {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public LoginModel() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param login
     * @return true if logged in successful otherwise return false for every
     * failed logged in
     * @throws UnknownException
     */
    public static boolean isLogin(Login login) throws UnknownException {
        boolean res = false;
        String loginQry = "select * from employee where username= '" + login.getUsername() + "' AND password= '" + login.getPassword() + "' AND empType= '" + login.getType() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                res = true;
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }

        return res;

    }

    public static String returnEmp(Login login) throws org.omg.CORBA.portable.UnknownException {
        String emp = "";

        String loginQry = "select * from employee where username= '" + login.getUsername() + "' AND password= '" + login.getPassword() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                emp = result.getString("employee_id");
            }
        } catch (SQLException e) {

        }

        return emp;

    }

}
