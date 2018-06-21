package carwashsystem.booking;

import carwashsystem.owner.*;
import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.CustomerController;
import carwashsystem.employee.Employee;
import carwashsystem.employee.EmployeeController;
import carwashsystem.employee.EmployeeDA;
import carwashsystem.screens.OpenForm;
import carwashsystem.veh.VehicleController;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author
 */
public class BookingController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomer;
    @FXML
    private JFXComboBox<String> cmbVeh;

    @FXML
    private Button btnSaveRecord;
    private BookingDA daOwners;
    private int custID, empID;
    private String bookDate, vId;
    @FXML
    private AnchorPane frmBooking;
    private ComboBox<String> cmbEmployee;
    @FXML
    private DatePicker bookingDate;
    public static int globalBookingID = 0;
    private String CustName;
    private String CustCell;
    @FXML
    private TableView<Employee> tblCustomer;
    @FXML
    private TableColumn<Employee, String> colEmployeeID;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, String> colSurname;
    @FXML
    private TableColumn<Employee, String> colEmployeeType;
    @FXML
    private TableColumn<Employee, String> colEmail;
    @FXML
    private TableColumn<Employee, String> colAdd;
    static ArrayList<Employee> arCustomer;
    private EmployeeDA daCust;
    int cntemps = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daOwners = new BookingDA();
        daCust = new EmployeeDA();
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

        populateTable();
        bookingDate.setValue(LocalDate.now());
        bookingDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    private void populateTable() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("empType"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        try {
            arCustomer = EmployeeDA.getAllEmployee();
        } catch (UnknownException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblCustomer.getItems().setAll(arCustomer);
    }

    @FXML
    private void onSaveRecord(ActionEvent event) {
        Booking owner;
        if (cmbCustomer.getValue() != null
                && cmbVeh.getValue() != null) {
            getData();
            owner = new Booking(CustomerController.globalCustomerID, empID, 0, vId + "", bookDate, "0");
            try {

                if (owner.addBooking(owner)) {
                    globalBookingID = owner.getLastAddedID();
                    int cnt = tblCustomer.getItems().size();
                    
                    for (int x = 0; x < cnt; x++) {
                        if (tblCustomer.getItems().get(x).getCheckBox().isSelected()) {
                            cntemps += 1;
                        }
                    }
                    if (cntemps <= 5 && cntemps != 0) {
                        for (int x = 0; x < cnt; x++) {
                            if (tblCustomer.getItems().get(x).getCheckBox().isSelected()) {
                                Assignment assign = new Assignment(globalBookingID, tblCustomer.getItems().get(x).getEmployeeID(), "");
                                owner.addAssingment(assign);
                            }
                        }
                        AlertMessages.getInfo("Saved", "Booking added");
                        closeForm();
                        openDashboard();
                    } else {
                        AlertMessages.getError("Unsuccessful", "");
                    }
                } else {
                    AlertMessages.getError("Unsuccessful", "You have selected " + cntemps +" employees \n Select five or less");
                }
            } catch (DataRepetitionException e) {
                AlertMessages.getError("Data repetition", e.getMessage());
            } catch (DataStorageException ex) {
                Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownException ex) {
                Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * load ownership combo
     */
//    private void populateEmployee() {
//        ArrayList<String> emlpoyeeList;
//        ObservableList<String> obList = FXCollections.observableArrayList();
//
//        try {
//            emlpoyeeList = BookingDA.getEmployees();
//            emlpoyeeList.forEach(emp -> {
//                obList.add(emp);
//            });
//            cmbEmployee.setItems(obList);
//        } catch (UnknownException e) {
//            AlertMessages.getError("Unknow exce", e.getMessage());
//        }
//    }
    /**
     * load customer name into a combo
     */
    private void populateCustomerName() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = BookingDA.getCustomers();
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
            VehRegList = BookingDA.getVehicle();
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
            id = BookingDA.getCustomerID(cmbCustomer.getValue());
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

    /**
     *
     * @return customer id whose name is passed as parameter
     */
    private String getVehID() {
        String id = "";
        try {
            id = BookingDA.getVehicleID(cmbVeh.getValue());
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

//    private int getEmpID() {
//        int id = 0;
//        try {
//
//            String str = cmbEmployee.getValue();
//            String[] splited = str.split("\\s+");
//            id = BookingDA.getEmployeeID(splited[2]);
//        } catch (UnknownException e) {
//            AlertMessages.getError("Unknown exce", e.getMessage());
//        }
//        return id;
//    }
    private String getDate() {
        LocalDate localDate = bookingDate.getValue();

        return localDate + "";
    }

    /**
     * accept data
     */
    private void getData() {
        custID = getCustID();
        vId = getVehID();
//        empID = getEmpID();
        bookDate = getDate();
    }

    /*
    open dashboard
     */
    public void openDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Performance";
        String screen = "/carwashsystem/carservice/Performance.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public void closeForm() {
        FormClose.closeForm(frmBooking);
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmBooking);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onTableClick(MouseEvent event) {
    }
}
