package carwashsystem.alert;

import javafx.scene.control.Alert;


/**
 *
 * @author
 */
public class AlertMessages {

    public static void getInfo(String title, String msg) {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle(title);
        al.setContentText(msg);
        al.showAndWait();
        
    }

    public static void getWarning(String title, String msg) {
        Alert al = new Alert(Alert.AlertType.WARNING);
        al.setTitle(title);
        al.setContentText(msg);
        al.showAndWait();

    }

    public static void getError(String title, String msg) {
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle(title);
        al.setContentText(msg);
        al.showAndWait();

    }
}
