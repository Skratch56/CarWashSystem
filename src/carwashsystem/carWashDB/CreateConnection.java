
package carwashsystem.carWashDB;

import carwashsystem.carWashExeception.DataStorageException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class CreateConnection {
     private static String url= "jdbc:mysql://localhost/CWDB";
    private static String username = "root";
    private static String password = "";
    private static String driver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    public static Connection initialise() throws DataStorageException {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
           
        } catch (ClassNotFoundException ex) {
        throw new DataStorageException("" +ex.getMessage());
             
        } catch (SQLException ex) {
        throw new DataStorageException(ex.getMessage());
       
        }
        return connection;
    } 
}
