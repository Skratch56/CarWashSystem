/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.reports;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.closeform.FormClose;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class ChooseDateController implements Initializable {

    @FXML
    private Label lblBookingStatus;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXDatePicker dtBooking;
    @FXML
    private AnchorPane frmdatechoser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onDisplayClicked(ActionEvent event) throws JRException, DataStorageException {

        HashMap params = new HashMap();
        LocalDate localDate = dtBooking.getValue();
        params.put("BookingDate", localDate.toString());
        System.out.println(localDate.toString());
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\BookingByDate.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, params, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();

    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmdatechoser);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/reports/Reports.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

}
