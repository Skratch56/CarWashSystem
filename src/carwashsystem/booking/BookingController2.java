package carwashsystem.booking;

import carwashsystem.owner.*;
import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.Customer;
import carwashsystem.cust.CustomerController;
import carwashsystem.cust.CustomerDA;
import carwashsystem.employee.Employee;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author
 */
public class BookingController2 implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomer;
    @FXML
    private JFXComboBox<String> cmbVeh;

    private BookingDA daOwners;
    private int custID, empID, bookingID;
    private String bookDate, vId, totAmt;
    @FXML
    private ComboBox<String> cmbEmployee;
    @FXML
    private DatePicker bookingDate;
    @FXML
    private AnchorPane frmBookingManagement;
    @FXML
    private Button btnSave;
    private Button btnEditOnEditRecord;
    @FXML
    private Button btnCreateNew;
    @FXML
    private TextField txtBookingID;
    @FXML
    private TextField txtTotalAmount;
    @FXML
    private TableView<Booking> tblBooking;
    @FXML
    private TableColumn<Booking, String> colBookingID;
    @FXML
    private TableColumn<Booking, String> colBookingDate;
    @FXML
    private TableColumn<Booking, String> colTotAmount;
    @FXML
    private TableColumn<Booking, String> colVehicleRegNo;
    @FXML
    private TableColumn<Booking, String> colCustID;
    @FXML
    private TableColumn<Booking, String> colEmployee;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    static ArrayList<Booking> arBooking;
    @FXML
    private JFXTextField searchField;
    @FXML
    private BarChart<String, Double> bookingChart;
    @FXML
    private NumberAxis pyAxis;
    @FXML
    private CategoryAxis pxAxis;
    private Booking booking;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daOwners = new BookingDA();
        booking = new Booking();
        txtBookingID.setDisable(true);
        txtTotalAmount.setDisable(false);
        initializeTable();
        populateCustomerName();
        populateCustomerRegNo();
        populateEmployee();

        bookingDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        filterData();
        loadBookingsChart();
    }

    private void loadBookingsChart() {
        try {
            ObservableList lists = FXCollections.observableArrayList();
            XYChart.Series<String, Double> series = new XYChart.Series<>();

            for (Booking p : booking.getAllBooking()) {
                series.getData().add(new XYChart.Data(p.getBookDate()+"", Double.parseDouble(p.getTotAmt())));
                lists.add(p.getBookDate()+"");
            }
            series.setName("Bookings");
//            pxAxis.setCategories(lists);
            bookingChart.getData().add(series);
        } catch (DataRepetitionException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void filterData() {
        ObservableList<Booking> oListemployee = FXCollections.observableArrayList(arBooking);
        FilteredList<Booking> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(booking -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (booking.getBookDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (booking.getTotAmt().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Booking> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblBooking.comparatorProperty());
            tblBooking.setItems(sortedData);
        });
    }

    private void initializeTable() {
        colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookDate"));
        colTotAmount.setCellValueFactory(new PropertyValueFactory<>("totAmt"));
        colVehicleRegNo.setCellValueFactory(new PropertyValueFactory<>("vhReg"));
        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("empID"));

        try {
            arBooking = BookingDA.getAllBooking();
        } catch (UnknownException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblBooking.getItems().setAll(arBooking);
    }

    @FXML
    private void onSaveRecord(ActionEvent event) {
        Booking owner;
        if (cmbCustomer.getValue() != null
                && cmbEmployee.getValue() != null
                && cmbVeh.getValue() != null) {
            getData();
            owner = new Booking(custID, empID, 0, vId + "", bookDate, "0");
            try {
                if (owner.addBooking(owner)) {
                    AlertMessages.getInfo("Saved", "Booking added");
                    initializeTable();
                    //FormClose.closeForm(ownershipFrm);
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
    private void populateEmployee() {
        ArrayList<String> emlpoyeeList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            emlpoyeeList = BookingDA.getEmployees();
            emlpoyeeList.forEach(emp -> {
                obList.add(emp);
            });
            cmbEmployee.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

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

            String str = cmbCustomer.getValue();
            String[] splited = str.split("\\s+");
            id = BookingDA.getCustomerID2(splited[2]);
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

    private int getEmpID() {
        int id = 0;
        try {

            String str = cmbEmployee.getValue();
            String[] splited = str.split("\\s+");
            id = BookingDA.getEmployeeID(splited[2]);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

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
        empID = getEmpID();
        bookDate = getDate();
        if (!"".equals(txtBookingID.getText())) {
            bookingID = Integer.parseInt(txtBookingID.getText());
        }
        totAmt = txtTotalAmount.getText();
    }

    /*
    open dashboard
     */
    public void openDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/MainForm.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onUpdateRecord(ActionEvent event) throws DataStorageException, UnknownException {
        Booking owner;
        if (cmbCustomer.getValue() != null
                && cmbEmployee.getValue() != null
                && cmbVeh.getValue() != null) {
            getData();
            owner = new Booking(custID, empID, bookingID, vId + "", bookDate, totAmt);
            try {
                if (owner.updateBooking(owner)) {
                    AlertMessages.getInfo("Updated", "Booking successfully updated");
                    initializeTable();
                    //FormClose.closeForm(ownershipFrm);
                } else {
                    AlertMessages.getError("Unsuccessful", "record was not updated");
                }
            } catch (DataRepetitionException e) {
                AlertMessages.getError("Data repetition", e.getMessage());
            }

        }
    }

    @FXML
    private void onSetFields(MouseEvent event) throws UnknownException {
        Booking book = tblBooking.getSelectionModel().getSelectedItem();
        txtBookingID.setText(book.getBookingID() + "");
        cmbEmployee.setValue(BookingDA.getEmployeeName(book.getEmpID() + ""));
        cmbCustomer.setValue(BookingDA.getCustomerName(book.getCustID() + ""));
        txtTotalAmount.setText(book.getTotAmt());
        cmbVeh.setValue(book.getVhReg());
        bookingDate.setValue(LocalDate.parse(book.getBookDate()));
        btnSave.setDisable(true);
        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
        btnCreateNew.setDisable(false);
    }

    @FXML
    private void onDeleteEquipment(ActionEvent event) throws DataStorageException {
        Booking owner;
        if (cmbCustomer.getValue() != null
                && cmbEmployee.getValue() != null
                && cmbVeh.getValue() != null) {
            getData();
            owner = new Booking(custID, empID, bookingID, vId + "", bookDate, totAmt);
            try {
                if (owner.deleteBooking(owner)) {
                    AlertMessages.getInfo("Deleted", "Booking successfully deleted");
                    initializeTable();
                    //FormClose.closeForm(ownershipFrm);
                } else {
                    AlertMessages.getError("Unsuccessful", "record was not deleted");
                }
            } catch (DataRepetitionException e) {
                AlertMessages.getError("Data repetition", e.getMessage());
            }

        }
    }

    @FXML
    private void onCreateNewEquipment(ActionEvent event) {
        txtBookingID.setText("");

        cmbEmployee.setValue("");
        txtTotalAmount.setText("");
        cmbVeh.setValue("");
        bookingDate.setValue(null);
        cmbCustomer.setValue("");

        btnSave.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnCreateNew.setDisable(true);
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmBookingManagement);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

}
