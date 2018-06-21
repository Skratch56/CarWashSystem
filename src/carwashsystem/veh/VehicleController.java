package carwashsystem.veh;

import carwashsystem.alert.AlertMessages;
import static carwashsystem.booking.BookingController.globalBookingID;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.employee.Employee;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author
 */
public class VehicleController implements Initializable {

    @FXML
    private AnchorPane vehicleForm;
    private TextField txtBrand;
    private TextField txtVType;
    @FXML
    private TextField txtColor;
    @FXML
    private Button btnSave;
    private String brand, color, type, vNo;
    private VehicleDA objVehDA;
    @FXML
    private TableView<Vehicle> tblVehicle;
    @FXML
    private TableColumn<Vehicle, String> colReg;
    @FXML
    private TableColumn<Vehicle, String> colBrand;
    @FXML
    private TableColumn<Vehicle, String> colVehType;
    @FXML
    private TableColumn<Vehicle, String> colColr;
    @FXML
    private TableColumn<Vehicle, String> colOwner;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnView;
    @FXML
    private TextField txtRegistration;
    static ArrayList<Vehicle> arVehicle;
    @FXML
    private Button btnNextCustomerPage;
    public static String globalVehicleID = "";
    @FXML
    private JFXComboBox<String> cmbType;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private JFXComboBox<String> cmbBrand;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        objVehDA = new VehicleDA();
        snackbar = new JFXSnackbar(vehicleForm);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnView.setDisable(true);
        btnNextCustomerPage.setDisable(true);
        txtRegistration.setDisable(false);
        colReg.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colVehType.setCellValueFactory(new PropertyValueFactory<>("vType"));
        colColr.setCellValueFactory(new PropertyValueFactory<>("color"));

        try {
            arVehicle = VehicleDA.getAllVehicle();
            tblVehicle.getItems().setAll(arVehicle);

        } catch (UnknownException ex) {
            Logger.getLogger(VehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populate();
        filterData();
    }

    private void filterData() {
        ObservableList<Vehicle> oListemployee = FXCollections.observableArrayList(arVehicle);
        FilteredList<Vehicle> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(vehicle -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (vehicle.getRegNo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (vehicle.getVType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (vehicle.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (vehicle.getColor().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Vehicle> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblVehicle.comparatorProperty());
            tblVehicle.setItems(sortedData);
        });
    }

    public void populate() {
        ObservableList<String> vehTypes = FXCollections.observableArrayList();
        vehTypes.add("Normal Car");
        vehTypes.add("Bakkie");
        vehTypes.add("Double Cab Bakkie");
        vehTypes.add("Avanza");
        vehTypes.add("4x4");
        vehTypes.add("x5");
        vehTypes.add("Sedan");
        vehTypes.add("HatchBack");
        vehTypes.add("Other");
        cmbType.setItems(vehTypes);
        ObservableList<String> cmbBrands = FXCollections.observableArrayList();
        cmbBrands.add("Toyota");
        cmbBrands.add("BMW");
        cmbBrands.add("Volkswaggen");
        cmbBrands.add("Mahindra");
        cmbBrands.add("Hyundai");
        cmbBrands.add("Porsche");
        cmbBrands.add("Alfa Romeo");
        cmbBrands.add("Honda");
        cmbBrands.add("Kia");
        cmbBrands.add("Lexus");
        cmbBrands.add("Suzuki");
        cmbBrands.add("Dodge");
        cmbBrands.add("Land Rover");
        cmbBrands.add("Mazda");
        cmbBrands.add("Volvo");
        cmbBrands.add("Other");
        cmbBrand.setItems(cmbBrands);
    }

    @FXML
    private void onSaveVehicle(ActionEvent event) throws DataStorageException {
        Vehicle vehicle;

        if (!nullValue()) {
            brand = cmbBrand.getValue();
            type = cmbType.getValue();
            color = txtColor.getText();
            vNo = txtRegistration.getText();

            if (!isTextOnly()) {
                if (!isMin()) {
                    vehicle = new Vehicle(vNo, brand, type, color);

                    try {
                        if (vehicle.addVehicle(vehicle)) {
                            globalVehicleID = vNo;
                            AlertMessages.getInfo("Saved", "Vehicle record saved");

                            openCustomer();
                            clearText();
                            closeForm();
                        } else {
                            AlertMessages.getError("Unsuccessful", "Vehicle record not saved");
                        }
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("Data repetition", e.getMessage());
                    }
                }
            }
        }

    }

    /**
     * exception tracking
     */
    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtRegistration.getText())) {
            AlertMessages.getWarning("isNull", "Please enter vehicle registration number");
            return true;

        } else if (!DataEntryCheck.textFieldNotEmpty(cmbBrand.getValue())) {
            AlertMessages.getWarning("isNull", "Please enter vehicle brand");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtColor.getText())) {
            AlertMessages.getWarning("isNull", "Please enter vehicle color");
            return true;
        }
        return false;
    }

    /**
     * text only
     */
    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(brand)) {
            AlertMessages.getWarning("input mismatch", "Vehicle brand field takes only characters");
            return true;
        } else if (!DataEntryCheck.textOnlyTextField(color)) {
            AlertMessages.getWarning("input mismatch", "Vehicle color field takes only characters");
            return true;
        }
        return false;
    }

    private boolean isMin() {

        if (!DataEntryCheck.minLength(vNo, 3)) {
            AlertMessages.getWarning("Min field ex", "Characters too short. Please enter more characters for vehicle registration number");
            return true;

        } else if (!DataEntryCheck.minLength(brand, 3)) {
            AlertMessages.getWarning("Min field ex", "Characters too short. Please enter more characters for brand");
            return true;
        } else if (!DataEntryCheck.minLength(color, 3)) {
            AlertMessages.getWarning("Min field ex", "Characters too short. Please enter more characters for color");
            return true;
        }
        return false;
    }

    @FXML
    private void onEditVehicle(ActionEvent event) throws UnknownException {
        Vehicle vehicle;

        if (!nullValue()) {
            brand = cmbBrand.getValue();
            type = cmbType.getClass().toString();
            color = txtColor.getText();
            vNo = txtRegistration.getText();

            if (!isTextOnly()) {
                if (!isMin()) {
                    vehicle = new Vehicle(vNo, brand, type, color);

                    try {
                        if (vehicle.updateVehicle(vehicle)) {
                            AlertMessages.getInfo("Updated", "Vehicle record successfully updated");
                            clearText();
                            openVehicle();
                            closeForm();
                        } else {
                            AlertMessages.getError("Unsuccessful", "Vehicle record not updated");
                        }
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("Data repetition", e.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    private void onDelVehicle(ActionEvent event) throws UnknownException, DataStorageException {
        Vehicle vehicle;

        if (!nullValue()) {
            brand = cmbBrand.getValue();
            type = cmbType.getValue();
            color = txtColor.getText();
            vNo = txtRegistration.getText();

            if (!isTextOnly()) {
                if (!isMin()) {
                    vehicle = new Vehicle(vNo, brand, type, color);

                    try {
                        if (vehicle.deleteVehicle(vehicle)) {
                            AlertMessages.getInfo("Deleted", "Vehicle record successfully deleted");
                            clearText();
                            openVehicle();
                            closeForm();
                        } else {
                            AlertMessages.getError("Unsuccessful", "Vehicle record not deleted");
                        }
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("Data repetition", e.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    private void onListVehicle(ActionEvent event) {
        txtBrand.setText("");
        txtRegistration.setText("");
        txtColor.setText("");
        populate();
        btnDelete.setDisable(true);
        btnView.setDisable(true);
        btnNextCustomerPage.setDisable(true);
        btnEdit.setDisable(true);
        txtRegistration.setDisable(false);
        btnSave.setDisable(false);
    }

    /**
     * clear
     */
    public void clearText() {
        txtBrand.setText("");
        txtRegistration.setText("");
        txtColor.setText("");

    }

    /*
    open dashboard
     */
    public void openCustomer() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/cust/Customer.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public void openVehicle() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/veh/Vehicle.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public void closeForm() {
        FormClose.closeForm(vehicleForm);
    }

    @FXML
    private void onClickRow(MouseEvent event) {
        Vehicle vehicle = tblVehicle.getSelectionModel().getSelectedItem();
        cmbBrand.setValue(vehicle.getBrand());
        txtRegistration.setText(vehicle.getRegNo());
        txtColor.setText(vehicle.getColor());
        cmbType.setValue(vehicle.getVType());
        btnDelete.setDisable(false);
        btnView.setDisable(false);
        btnNextCustomerPage.setDisable(false);
        btnEdit.setDisable(false);
        btnSave.setDisable(true);
        txtRegistration.setDisable(true);
    }

    @FXML
    private void onCustomerButtonCLicked(ActionEvent event) {
        globalVehicleID = txtRegistration.getText();
        openCustomer();
        closeForm();
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        closeForm();
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    private void onBrand(KeyEvent event) {
        if (!cmbBrand.getValue().matches("[a-zA-Z]+\\.?")) {
            txtBrand.setText(cmbBrand.getValue().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Brand must only contain text", 3000);
        }
    }

    @FXML
    private void onColor(KeyEvent event) {
        if (!txtColor.getText().matches("[a-zA-Z]+\\.?")) {
            txtColor.setText(txtColor.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Color must only contain text", 3000);
        }
    }

}
