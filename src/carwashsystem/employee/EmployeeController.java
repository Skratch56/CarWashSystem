package carwashsystem.employee;

import carwashsystem.cust.*;
import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.custlist.CustomerList;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joseph
 */
public class EmployeeController implements Initializable {

    @FXML
    private ToggleGroup gender;
    @FXML
    private TableColumn<Employee, Integer> colID;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TextField txtFname;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtHouseNo;
    @FXML
    private TextField txtContact;
    private TextField txtZip;
    @FXML
    private Button btnSaveCustomer;
    @FXML
    private RadioButton radMale;
    @FXML
    private RadioButton radFemale;
    @FXML
    private JFXComboBox<String> cmbCity;
    @FXML
    private Button btnEditCustomer;
    @FXML
    private Button btnListCustomer;
    @FXML
    private TableView<Employee> tblCustomer;
    @FXML
    private TableColumn<Employee, String> colSurname;
    @FXML
    private TableColumn<Employee, String> colGender;
    private TableColumn<Employee, String> colContact;
    private TableColumn<Employee, String> colHouseNo;
    @FXML
    private TableColumn<Employee, String> colStreet;
    @FXML
    private TableColumn<Employee, String> colCity;

    private EmployeeDA daCust;
    private int employeeID;
    private String firstname, surname, empType, dob, id, passport, cellphone, email, salary, house_number, street_name, city, postal_code, username, password, gender2;
    private AnchorPane employeeForm;
    static ArrayList<Employee> arCustomer;
    @FXML
    private Button btnDeleteCustomer;
    private TextField txtCustID;

    private Button btnGoToOwnership;
    @FXML
    private AnchorPane customerForm;
    @FXML
    private TableColumn<Employee, String> colEmployeeType;
    @FXML
    private TableColumn<Employee, String> colDOB;
    @FXML
    private TableColumn<Employee, String> colPassport;
    @FXML
    private TableColumn<Employee, String> colEmail;
    @FXML
    private TableColumn<Employee, String> colSalary;
    @FXML
    private TableColumn<Employee, String> colHouse;
    @FXML
    private TableColumn<Employee, String> colPostal;
    @FXML
    private TableColumn<Employee, String> colUsername;
    @FXML
    private TableColumn<Employee, String> colPassword;
    @FXML
    private TableColumn<Employee, String> colEmployeeID;
    @FXML
    private TableColumn<Employee, String> colCellphone;
    @FXML
    private TextField txtPostal;
    @FXML
    private TextField txtEmpID;
    @FXML
    private ComboBox<String> cmbType;
    @FXML
    private DatePicker dtDOB;
    @FXML
    private TextField txtIDNo;
    @FXML
    private TextField txtPassport;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private JFXTextField searchField;
    @FXML
    private AnchorPane frmEmployee;
    @FXML
    private JFXSnackbar snackbar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCity();
        daCust = new EmployeeDA();
        snackbar = new JFXSnackbar(customerForm);
        snackbar.applyCss();
        
        txtEmpID.setDisable(true);
        btnEditCustomer.setDisable(true);
        btnDeleteCustomer.setDisable(true);
        btnListCustomer.setDisable(true);
        populateTable();
        filterData();
    }

    private void populateTable() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("empType"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPassport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colCellphone.setCellValueFactory(new PropertyValueFactory<>("cellphone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colHouse.setCellValueFactory(new PropertyValueFactory<>("house_number"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street_name"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostal.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        try {
            arCustomer = EmployeeDA.getAllEmployee();
        } catch (UnknownException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblCustomer.getItems().setAll(arCustomer);
    }

    private void filterData() {
        ObservableList<Employee> oListemployee = FXCollections.observableArrayList(arCustomer);
        FilteredList<Employee> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (employee.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Employee> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
            tblCustomer.setItems(sortedData);
        });
    }

    @FXML
    private void onSaveRecord(ActionEvent event
    ) throws DataStorageException, UnknownException {
        Employee cust;
        if (!nullValue()) {
            getData();

            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Employee(0, firstname, surname, empType, dob, id, passport, gender2, cellphone, email, salary, house_number, street_name, city, postal_code, username, password,null);
                        try {
                            if (cust.addEmployee(cust)) {

                                AlertMessages.getInfo("Saved", "Employee successfully added");
                                clearData();
                                populateTable();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Customer was not added");
                            }
                        } catch (DataRepetitionException e) {
                            AlertMessages.getError("Data repetition", e.getMessage());
                        }
                    }

                }
            }

        }
    }

    @FXML
    private void onListCustomer(ActionEvent event
    ) {
        txtEmpID.setText("");
        txtContact.setText("");
        txtFname.setText("");
        txtSurname.setText("");
        txtContact.setText("");
        txtHouseNo.setText("");
        txtStreet.setText("");
        txtFname.setText("");
        txtSurname.setText("");
        txtContact.setText("");
        txtStreet.setText("");
        txtPassport.setText("");
        txtPostal.setText("");
        cmbCity.setValue(null);
        txtIDNo.setText("");
        txtPassword.setText("");
        txtSalary.setText("");
        dtDOB.setValue(null);
        cmbType.setValue(null);
        txtPostal.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        radMale.setSelected(true);
        txtEmail.setText("");
        btnEditCustomer.setDisable(true);
        btnDeleteCustomer.setDisable(true);
        btnListCustomer.setDisable(true);

    }

    /**
     * null value
     */
    private boolean nullValue() {

        return false;
    }

    /**
     * text only field
     */
    private boolean isTextOnly() {
//        if (!DataEntryCheck.textOnlyTextField(name)) {
//            AlertMessages.getWarning("input mismatch", "First name may only contain characters");
//            return true;
//        } else if (!DataEntryCheck.textOnlyTextField(surname)) {
//            AlertMessages.getWarning("input mismatch", "surname may only contain characters");
//            return true;
//        } else if (!DataEntryCheck.textOnlyTextField(street)) {
//            AlertMessages.getWarning("input mismatch", "street may only contain characters");
//            return true;
//        }

        return false;
    }

    /**
     * is number
     */
    private boolean isNumberOnly() {
//        if (!DataEntryCheck.onlyNumberTextField(cell)) {
//            AlertMessages.getWarning("input mismatch", "cell phone may only contain number");
//            return true;
//        } else if (!DataEntryCheck.onlyNumberTextField(zip)) {
//            AlertMessages.getWarning("input mismatch", "zip code may only contain number");
//            return true;
//        } else if (!DataEntryCheck.checkNegativeNumber(houseNo)) {
//            AlertMessages.getWarning("input mismatch", "house no may only contain number");
//            return true;
//        }
        return false;
    }

    /**
     * check min characters
     */
    private boolean isMin() {
//        if (!DataEntryCheck.minLength(name, 3)) {
//            AlertMessages.getWarning("Minimum characters", "employee name is too short. Please enter more characters");
//            return true;
//        } else if (!DataEntryCheck.minLength(surname, 3)) {
//            AlertMessages.getWarning("Minimum characters", "employee surname is too short. Please enter more characters");
//            return true;
//        } else if (!DataEntryCheck.minLength(street, 3)) {
//            AlertMessages.getWarning("Minimum characters", "street is too short. Please enter more characters");
//            return true;
//        }
        return false;
    }

    private boolean checkCellNo() {
//        if (!DataEntryCheck.phoneTextField1(txtContact.getText())) {
//            AlertMessages.getWarning("Minimum characters", "Please enter valid cellphone number");
        return true;
//        }
//        return false;
    }

    /**
     * get employee data
     */
    private void getData() {
        if (!"".equals(txtEmpID.getText())) {
            employeeID = Integer.parseInt(txtEmpID.getText());
        }
        firstname = txtFname.getText();
        surname = txtSurname.getText();
        cellphone = txtContact.getText();
        street_name = txtStreet.getText();
        house_number = txtHouseNo.getText();
        postal_code = txtPostal.getText();
        city = cmbCity.getValue();
        gender2 = getGender();
        id = txtIDNo.getText();
        passport = txtPassword.getText();
        salary = txtSalary.getText();
        email = txtEmail.getText();
        dob = dtDOB.getValue().toString();
        empType = cmbType.getValue();
        postal_code = txtPostal.getText();
        username = txtUsername.getText();
        password = txtPassword.getText();

    }

    private String getGender() {
        String g;
        if (radFemale.isSelected()) {
            g = "Female";
        } else {
            g = "Male";
        }
        return g;
    }

    /*
    open Ownership Form
     */
    public void openOwnershipForm() {
        OpenForm vehForm = new OpenForm();
        String title = "Ownership";
        String screen = "/carwashsystem/owner/Ownership.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public void openCustomerForm() {
        OpenForm vehForm = new OpenForm();
        String title = "Ownership";
        String screen = "/carwashsystem/cust/Customer.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    private void clearData() {
        txtContact.setText("");
        txtFname.setText("");
        txtSurname.setText("");
        txtContact.setText("");
        txtHouseNo.setText("");
        txtEmail.setText("");
        txtStreet.setText("");
        txtFname.setText("");
        txtSurname.setText("");
        txtContact.setText("");
        txtStreet.setText("");
        txtHouseNo.setText("");
        txtEmpID.setText("");
        txtPostal.setText("");
        cmbCity.setValue(null);
        txtIDNo.setText("");
        txtPassword.setText("");
        txtPassport.setText("");
        txtSalary.setText("");
        dtDOB.setValue(null);
        cmbType.setValue(null);
        txtPostal.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        radMale.setSelected(true);

    }

    /**
     * load ownership combo
     */
    private void populateCity() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Alexandra");
        obList.add("Johannesburg");
        obList.add("Lenasia");
        obList.add("Midrand");
        obList.add("Roodepoort");
        obList.add("Sandton");
        obList.add("Soweto");
        obList.add("Mshongo");
        obList.add("Johannesburg");
        obList.add("Klipfontienview");
        obList.add("Orange Farm");
        obList.add("Alberton");
        obList.add("Germiston");
        obList.add("Benoni");
        obList.add("Boksburg");
        obList.add("Brakpan");
        obList.add("Clayville");
        obList.add("Daveyton");
        obList.add("Devon");
        obList.add("Duduza");
        obList.add("Edenvale");
        obList.add("Ennerdale");
        obList.add("Germiston");
        obList.add("Impumelelo");
        obList.add("Isando");
        obList.add("Katlehong");
        obList.add("Kempton Park");
        obList.add("KwaThema");
        obList.add("Nigel");
        obList.add("Olifantsfontein");
        obList.add("Reiger Park");
        obList.add("Springs");
        obList.add("Tembisa");
        obList.add("Thokoza");
        obList.add("Tsakane");
        obList.add("Vosloorus");
        obList.add("Wattville");
        obList.add("Bronberg");
        obList.add("Bronkhorstspruit");
        obList.add("Cullinan");
        obList.add("Centurion");
        obList.add("Ekangala");
        obList.add("Ga - Rankuwa");
        obList.add("Hammanskraal");
        obList.add("Irene");
        obList.add("Mabopane");
        obList.add("Mamelodi");
        obList.add("Pretoria");
        obList.add("Rayton");
        obList.add("Refilwe");
        obList.add("Soshanguve");
        obList.add("Zithobeni");
        obList.add("Boipatong");
        obList.add("Bophelong");
        obList.add("Evaton");
        obList.add("Sebokeng");
        obList.add("Sharpeville");
        obList.add("Vanderbijlpark");
        obList.add("Vereeniging");
        cmbCity.setItems(obList);
        ObservableList<String> obList2 = FXCollections.observableArrayList();
        obList2.add("Manager");
        obList2.add("Cashier");
        obList2.add("Washer");
        cmbType.setItems(obList2);
    }

    public void closeForm() {
        FormClose.closeForm(employeeForm);
    }

    @FXML
    private void onTableClick(MouseEvent event) {

        Employee employee = tblCustomer.getSelectionModel().getSelectedItem();
        txtContact.setText(employee.getCellphone());
        txtFname.setText(employee.getFirstname());
        txtSurname.setText(employee.getSurname());
        txtHouseNo.setText(employee.getHouse_number());
        txtStreet.setText(employee.getStreet_name());
        txtIDNo.setText(employee.getId());
        txtPassword.setText(employee.getPassword());
        txtSalary.setText(employee.getSalary());
        dtDOB.setValue(LocalDate.parse(employee.getDob()));
        cmbType.setValue(employee.getEmpType());
        txtPostal.setText(employee.getPostal_code());
        txtUsername.setText(employee.getUsername());
        txtEmpID.setText(employee.getEmployeeID() + "");
        txtSalary.setText(employee.getSalary());
        txtEmail.setText(employee.getEmail());
        txtPassport.setText(employee.getPassport());

        cmbCity.setValue(employee.getCity());
        if ("Male".equals(employee.getGender())) {
            radMale.setSelected(true);
            radFemale.setSelected(false);
        } else {
            radMale.setSelected(false);
            radFemale.setSelected(true);
        }
        btnSaveCustomer.setDisable(true);
        btnEditCustomer.setDisable(false);
        btnDeleteCustomer.setDisable(false);
        btnListCustomer.setDisable(false);

    }

    @FXML
    private void onEditCustomer(ActionEvent event) throws DataStorageException, UnknownException {
        Employee cust;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Employee(employeeID, firstname, surname, empType, dob, id, passport, gender2, cellphone, email, salary, house_number, street_name, city, postal_code, username, password,null);
                        try {
                            if (cust.updateEmployee(cust)) {

                                AlertMessages.getInfo("Updated", "Employee updated successfully");
                                clearData();
                                populateTable();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Employee was not added updated");
                            }
                        } catch (DataRepetitionException e) {
                            AlertMessages.getError("Data repetition", e.getMessage());
                        }
                    }

                }
            }

        }
    }

    @FXML
    private void onDeleteCustomer(ActionEvent event) throws DataStorageException, UnknownException {
        Employee cust;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Employee(employeeID, firstname, surname, empType, dob, id, passport, gender2, cellphone, email, salary, house_number, street_name, city, postal_code, username, password,null);
                        try {
                            if (cust.deleteEmployee(cust)) {

                                AlertMessages.getInfo("Deleted", "Employee deleted successfully");
                                clearData();
                                populateTable();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Employee was not added deleted");
                            }
                        } catch (DataRepetitionException e) {
                            AlertMessages.getError("Data repetition", e.getMessage());
                        }
                    }

                }
            }

        }
    }

    private void onContactLengthCheck(KeyEvent event) {
        String strNumber = txtContact.getText();
        if (strNumber.length() > 9) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtContact.getText().matches("[a-zA-Z]+\\.?")) {
            txtContact.setText(txtContact.getText().replaceAll("[a-zA-Z]+", ""));
            snackbar.show("Contact must only digits", 3000);
        }

    }

    private void onGoToOwnershipClicked(ActionEvent event) {
//        globalCustomerID = Integer.parseInt(txtCustID.getText());
//        openOwnershipForm();
//        closeForm();
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmEmployee);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onIDReleased(KeyEvent event) {
        String strNumber = txtIDNo.getText();
        if (strNumber.length() > 13) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
                snackbar.show("ID Number must be 13 digits long", 3000);
            }

        }
    }

    @FXML
    private void onPassportReleased(KeyEvent event) {
        String strNumber = txtPassport.getText();
        if (strNumber.length() > 9 && strNumber.length() < 13) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                snackbar.show("ID Number must be between 9 and 13 Characters long", 3000);
            }

        } else if (strNumber.length() < 13) {
            event.consume();
        }
    }

    @FXML
    private void onContactReleased(KeyEvent event) {
        String strNumber = txtContact.getText();
        if (strNumber.length() > 9) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
    }

    @FXML
    private void onFirstname(KeyEvent event) {
        if (!txtFname.getText().matches("[a-zA-Z]+\\.?")) {
            txtFname.setText(txtFname.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Firstname must only contain text", 3000);
        }
    }

    @FXML
    private void onSurname(KeyEvent event) {
        if (!txtSurname.getText().matches("[a-zA-Z]+\\.?")) {
            txtSurname.setText(txtSurname.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Surname must only contain text", 3000);
        }
    }

    @FXML
    private void onSalary(KeyEvent event) {
        String strNumber = txtSalary.getText();
        if (strNumber.length() > 5) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtSalary.getText().matches("[a-zA-Z]+\\.?")) {
            txtSalary.setText(txtSalary.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("Salary must only digits", 3000);
        }
    }

    @FXML
    private void onHouseNumber(KeyEvent event) {
        String strNumber = txtHouseNo.getText();
        if (strNumber.length() > 3) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtHouseNo.getText().matches("[a-zA-Z]+\\.?")) {
            txtHouseNo.setText(txtHouseNo.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("House Number must only digits", 3000);
        }
    }

    @FXML
    private void onStreet(KeyEvent event) {
        if (!txtStreet.getText().matches("[a-zA-Z]+\\.?")) {
            txtStreet.setText(txtStreet.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("Surname must only contain text", 3000);
        }
    }

    @FXML
    private void onPostal(KeyEvent event) {
        String strNumber = txtZip.getText();
        if (strNumber.length() > 2) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtZip.getText().matches("[a-zA-Z]+\\.?")) {
            txtZip.setText(txtZip.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("Zip must only contain digits", 3000);
        }
    }

    @FXML
    private void onUsername(KeyEvent event) {
    }

    @FXML
    private void onPassword(KeyEvent event) {
    }

}
