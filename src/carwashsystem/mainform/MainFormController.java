package carwashsystem.mainform;

import carwashsystem.LoginController;
import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashDB.CreateConnection;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class MainFormController implements Initializable {

    @FXML
    private VBox pnl_scroll;
    @FXML
    private Label lbl_currentprojects;

    private BookingDA daBook;
    @FXML
    private VBox vboxAdmin;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private AnchorPane frmMain;
    @FXML
    private JFXButton btnHelp;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            daBook = new BookingDA();
            refreshNodes();
            if (!"Manager".equals(LoginController.globalEmployeeType)) {
                vboxAdmin.setVisible(false);
            }
        } catch (UnknownException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onVehicleManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Vehicle Management";
        String screen = "/carwashsystem/veh/Vehicle.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    @FXML
    private void onCustomerManagement(ActionEvent event) {
        OpenForm custForm = new OpenForm();
        String title = "Customer Management";
        String screen = "/carwashsystem/cust/Customer.fxml";
        custForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);

    }

    @FXML
    private void onBookingManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Booking Management";
        String screen = "/carwashsystem/booking/BookingManagement.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    @FXML
    private void onEmployeeManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Employee Management";
        String screen = "/carwashsystem/employee/Employee.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    @FXML
    private void onServiceManagement(ActionEvent event) {
        OpenForm serviceForm = new OpenForm();
        String title = "Service Management";
        String screen = "/carwashsystem/service/Service.fxml";
        serviceForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    @FXML
    private void onEquipmentManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Equipment Management";
        String screen = "/carwashsystem/equipment/Equipment.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    private void onPaymentManagement(ActionEvent event) throws JRException, DataStorageException {

        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\CarWashSystem\\src\\carwashsystem\\reports\\report1.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
//        JasperPrintManager.printReport(jp, false);
    }

    @FXML
    private void onReportsManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Performance";
        String screen = "/carwashsystem/reports/Reports.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws UnknownException {
        refreshNodes();
    }

    @FXML
    private void onAllocationManagement(ActionEvent event) {
        OpenForm vehForm = new OpenForm();
        String title = "Allocation Management";
        String screen = "/carwashsystem/allocation/Allocation.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmMain);
    }

    private void refreshNodes() throws UnknownException {
        pnl_scroll.getChildren().clear();
        ArrayList<Booking> daBook2 = BookingDA.getAllBooking2();
        Node[] nodes = new Node[daBook2.size()];
        lbl_currentprojects.setText("Total Bookings (" + daBook2.size() + ")");
        for (int i = 0; i < daBook2.size(); i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(MainFormController.class.getResource("Item.fxml"));
                Pane pane = loader.load();

                ItemController c = (ItemController) loader.getController();

                c.setBooking(daBook2.get(i), daBook2.get(i).getBookingID());

                pnl_scroll.getChildren().add(pane);

            } catch (IOException ex) {
                Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
        FormClose.closeForm(frmMain);
      
    }

    @FXML
    private void onHelpClicked(ActionEvent event) throws IOException {
        File myfile = new File("Help.docx");
        Desktop.getDesktop().open(myfile);
    }

}
