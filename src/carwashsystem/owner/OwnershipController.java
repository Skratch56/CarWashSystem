package carwashsystem.owner;

import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.CustomerController;
import carwashsystem.screens.OpenForm;
import carwashsystem.veh.VehicleController;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author
 */
public class OwnershipController implements Initializable {

    @FXML
    private AnchorPane ownershipFrm;
    @FXML
    private JFXComboBox<String> cmbCustomer;
    @FXML
    private JFXComboBox<String> cmbVeh;
    @FXML
    private JFXComboBox<String> cmbType;
    @FXML
    private Button btnSaveRecord;
    private OwnershipDA daOwners;
    private int custID;
    private String ownershipType, vId;
    private String CustName;
    private String CustCell;
    @FXML
    private Button btnGoToBooking;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daOwners = new OwnershipDA();
        if ("".equals(VehicleController.globalVehicleID)) {
            populateCustomerRegNo();
        } else {
            ObservableList<String> obList = FXCollections.observableArrayList();
            obList.add(VehicleController.globalVehicleID);
            cmbVeh.setItems(obList);
            cmbVeh.setValue(VehicleController.globalVehicleID);
            cmbVeh.setDisable(true);
        }
        if (CustomerController.globalCustomerID == 0) {
            populateCustomerName();
        } else {
            try {
                ObservableList<String> obList2 = FXCollections.observableArrayList();
                CustName = OwnershipDA.getCustomerName(CustomerController.globalCustomerID);
                CustCell = OwnershipDA.getCustomerCell(CustomerController.globalCustomerID);
                obList2.add(CustName + " " + CustCell);
                cmbCustomer.setItems(obList2);
                cmbCustomer.setValue(CustName + " " + CustCell);
                cmbCustomer.setDisable(true);
            } catch (UnknownException ex) {
                Logger.getLogger(OwnershipController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            if (OwnershipDA.ownershipExists(CustomerController.globalCustomerID, VehicleController.globalVehicleID)) {
               
                String type = OwnershipDA.getOwnershipType(CustomerController.globalCustomerID, VehicleController.globalVehicleID);
                ObservableList<String> obList3 = FXCollections.observableArrayList();
                obList3.add(type);
                cmbType.setItems(obList3);
                cmbType.setValue(type);
                cmbType.setDisable(true);
                btnSaveRecord.setDisable(true);
                btnGoToBooking.setDisable(false);
                AlertMessages.getInfo("Exists", "Ownership for this vehicle already exists. Please continue to booking");
            } else {
                populateOwnershipType();
                btnSaveRecord.setDisable(false);
                btnGoToBooking.setDisable(true);
            }
        } catch (DataRepetitionException ex) {
            Logger.getLogger(OwnershipController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownException ex) {
            Logger.getLogger(OwnershipController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onSaveRecord(ActionEvent event) {
        Ownership owner;
        if (cmbCustomer.getValue() != null
                && cmbType.getValue() != null
                && cmbVeh.getValue() != null) {
            getData();
            owner = new Ownership(CustomerController.globalCustomerID, vId, ownershipType);
            try {
                if (owner.addOwnership(owner)) {
                    AlertMessages.getInfo("Saved", "Ownership added");
                    openDashboard();
                    FormClose.closeForm(ownershipFrm);
                } else {
                    AlertMessages.getError("Unsuccessful", "record was not added");
                }
            } catch (DataRepetitionException e) {
                AlertMessages.getError("Data repetition", e.getMessage());
            }

        }
    }

    /**
     * load ownership combo
     */
    private void populateOwnershipType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("First owner");
        obList.add("Second owner");
        cmbType.setItems(obList);
    }

    /**
     * load customer name into a combo
     */
    private void populateCustomerName() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = OwnershipDA.getCustomers();
            custList.forEach(cust -> {
                obList.add(cust);
            });
            cmbCustomer.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

    /**
     * load customer name into a combo
     */
    private void populateCustomerRegNo() {
        ArrayList<String> VehRegList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            VehRegList = OwnershipDA.getVehicle();
            VehRegList.forEach(reg -> {
                obList.add(reg);
            });
            cmbVeh.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
    }

    /**
     *
     * @return customer id whose name is passed as parameter
     */
    private int getCustID() {
        int id = 0;
        try {
            id = OwnershipDA.getCustomerID(CustName);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

    /**
     *
     * @return customer id whose name is passed as parameter
     */
//    private String getVehID() {
//        String id = "";
//        try {
//            id = OwnershipDA.getVehicleID()+"";
//        } catch (UnknownException e) {
//            AlertMessages.getError("Unknown exce", e.getMessage());
//        }
//        return id;
//    }
    /**
     * accept data
     */
    private void getData() {
        vId = cmbVeh.getValue();
        ownershipType = cmbType.getValue();

    }

    /*
    open dashboard
     */
    public void openDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/booking/Booking.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void hdhdh(MouseEvent event) {
    }

    @FXML
    private void onBookingClicked(ActionEvent event) {
        openDashboard();
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
