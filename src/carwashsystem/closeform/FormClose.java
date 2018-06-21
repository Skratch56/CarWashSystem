
package carwashsystem.closeform;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Joseph
 */
public class FormClose {
   public static void closeForm(AnchorPane pane) {
        Stage screen = (Stage) pane.getScene().getWindow();
        screen.close();
    }

    public static void closeStackPane(StackPane pane) {
        Stage screen = (Stage) pane.getScene().getWindow();
        screen.close();
    } 
}
