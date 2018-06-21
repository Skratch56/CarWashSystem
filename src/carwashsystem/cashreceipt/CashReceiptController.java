package carwashsystem.cashreceipt;

import carwashsystem.LoginController;
import carwashsystem.owner.*;
import carwashsystem.alert.AlertMessages;
import carwashsystem.booking.BookingController;
import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.carservice.PerformanceController;
import carwashsystem.closeform.FormClose;
import carwashsystem.screens.OpenForm;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author
 */
public class CashReceiptController implements Initializable {

    @FXML
    private AnchorPane ownershipFrm;
    private ComboBox<String> cmbCustomer;
    private ComboBox<String> cmbVeh;
    private ComboBox<String> cmbType;
    @FXML
    private Button btnSaveRecord;
    private CashReceiptDA daOwners;
    int cashReceiptNo, amountReceived, bookingNo, employeeID;
    @FXML
    private TextField txtAmountReceived;
    @FXML
    private TextField txtBookingNo;
    @FXML
    private TextField txtEmployeeNo;
    @FXML
    private Label lblTotalAmount;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daOwners = new CashReceiptDA();
        txtBookingNo.setText(BookingController.globalBookingID + "");
        txtEmployeeNo.setText(LoginController.globalEmployeeId + "");
        lblTotalAmount.setText(PerformanceController.globalTotalAmount + "");
    }

    @FXML
    private void onSaveRecord(ActionEvent event) throws JRException, DataStorageException, UnknownException {
        CashReceipt owner;

        getData();
        if (PerformanceController.globalTotalAmount > amountReceived) {
            AlertMessages.getError("Invalid Amount", "Amount received cant be less that total amount");
        } else {
            owner = new CashReceipt(0, amountReceived, bookingNo, employeeID);
            try {
                if (owner.addCashReceipt(owner)) {
                    AlertMessages.getInfo("Saved", "Cash Receipt success");
                    AlertMessages.getInfo("Change For the customer", "The change is R" + (amountReceived - PerformanceController.globalTotalAmount));

                    HashMap params = new HashMap();
                    params.put("CashRecNo", CashReceiptDA.getLastAddedID());

                    JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\Final Presentation\\CarWashSystem\\src\\carwashsystem\\reports\\CashReceipt.jrxml");
                    JasperPrint jp = JasperFillManager.fillReport(jr, params, CreateConnection.initialise());
                    JRViewer viewer = new JRViewer(jp);
                    viewer.setOpaque(true);
                    viewer.setVisible(true);
                    JFrame jj = new JFrame();
                    jj.add(viewer);
                    jj.show();
                    FormClose.closeForm(ownershipFrm);
                    OpenForm vehForm = new OpenForm();
                    String title = "Dashboard";
                    String screen = "/carwashsystem/mainform/Home.fxml";
                    vehForm.openServiceListScreen(screen, title);
                } else {
                    AlertMessages.getError("Unsuccessful", "record was not added");
                }
            } catch (DataRepetitionException e) {
                AlertMessages.getError("Data repetition", e.getMessage());
            }
        }

    }

    private void getData() {
        amountReceived = Integer.parseInt(txtAmountReceived.getText());
        bookingNo = Integer.parseInt(txtBookingNo.getText());
        employeeID = Integer.parseInt(txtEmployeeNo.getText());
    }

    /*
    open dashboard
     */
    public void closeForm() {
        FormClose.closeForm(ownershipFrm);
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(ownershipFrm);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

}
