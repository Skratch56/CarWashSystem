/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.reports;

import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.cashreceipt.CashReceiptDA;
import carwashsystem.closeform.FormClose;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
public class ReportController implements Initializable {

    @FXML
    private AnchorPane frmReports;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmReports);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onPrintCustomers(ActionEvent event) throws JRException, DataStorageException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\CustomerReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPrintEmployees(ActionEvent event) throws JRException, DataStorageException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\EmployeeReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPrintBookings(ActionEvent event) throws JRException, DataStorageException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\BookingReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPrintServices(ActionEvent event) throws JRException, DataStorageException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\ServiceReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPrintAlocated(ActionEvent event) throws JRException, DataStorageException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\OwnerShipReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPrintBookingByDate(ActionEvent event) throws JRException, DataStorageException {
        FormClose.closeForm(frmReports);
        OpenForm vehForm = new OpenForm();
        String title = "";
        String screen = "/carwashsystem/reports/ChooseDate.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

}
