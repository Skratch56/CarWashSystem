package carwashsystem.cust;

import carwashsystem.alert.AlertMessages;
import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingController2;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.custlist.CustomerList;
import carwashsystem.screens.OpenForm;
import carwashsystem.veh.Vehicle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
public class CustomerController implements Initializable {

    @FXML
    private ToggleGroup gender;
    @FXML
    private TableColumn<CustomerList, Integer> colID;
    @FXML
    private TableColumn<CustomerList, String> colName;
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
    @FXML
    private TextField txtZip;
    @FXML
    private Button btnSaveCustomer;
    @FXML
    private RadioButton radMale;
    @FXML
    private RadioButton radFemale;

    @FXML
    private Button btnEditCustomer;
    @FXML
    private Button btnListCustomer;
    @FXML
    private TableView<Customer> tblCustomer;
    @FXML
    private TableColumn<Customer, String> colSurname;
    @FXML
    private TableColumn<Customer, String> colGender;
    @FXML
    private TableColumn<Customer, String> colContact;
    @FXML
    private TableColumn<Customer, String> colHouseNo;
    @FXML
    private TableColumn<Customer, String> colStreet;
    @FXML
    private TableColumn<Customer, String> colCity;
    @FXML
    private TableColumn<Customer, String> colZip;

    private CustomerDA daCust;
    private int custid, houseNo;
    private String name, surname, cell, sGender, street, city, zip;
    @FXML
    private AnchorPane customerForm;
    static ArrayList<Customer> arCustomer;
    @FXML
    private Button btnDeleteCustomer;
    @FXML
    private TextField txtCustID;
    public static int globalCustomerID = 0;
    @FXML
    private Button btnGoToOwnership;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXComboBox<String> cmbCity;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private BarChart<String, Double> customerChart;
    @FXML
    private NumberAxis pyAxis;
    @FXML
    private CategoryAxis pxAxis;
    private Customer customer;
    
    int count = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCity();
        daCust = new CustomerDA();
        snackbar = new JFXSnackbar(customerForm);
        customer = new Customer();
        txtCustID.setDisable(true);
        btnEditCustomer.setDisable(true);
        btnDeleteCustomer.setDisable(true);
        btnListCustomer.setDisable(true);
        btnGoToOwnership.setDisable(true);
        colID.setCellValueFactory(new PropertyValueFactory<>("custid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("cell"));
        colHouseNo.setCellValueFactory(new PropertyValueFactory<>("houseNo"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colZip.setCellValueFactory(new PropertyValueFactory<>("zip"));

        try {
            arCustomer = CustomerDA.getAllCustmer();
        } catch (UnknownException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblCustomer.getItems().setAll(arCustomer);
        filterData();
        loadBookingsChart();
    }

    private void filterData() {
        ObservableList<Customer> oListemployee = FXCollections.observableArrayList(arCustomer);
        FilteredList<Customer> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getCell().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getStreet().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Customer> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
            tblCustomer.setItems(sortedData);
        });
    }

    private void loadBookingsChart() {
        try {
            ObservableList lists = FXCollections.observableArrayList();
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            Customer p = new Customer();

            count = 0;

            List<Customer> customers = p.getAllCustmer2();
            ArrayList<Customer> bestcustomers = new ArrayList<>();
            
 
            for (Customer c : customers) {
                count = p.getCustomerCount2(c.getCustid());
                if (count >= 3) {
                    bestcustomers.add(c);

                }

            }
            int count2 = 0;
            for (Customer s : bestcustomers) {

                count2 = p.getCustomerCount2(s.getCustid());
                series.getData().add(new XYChart.Data(s.getName() + " " + s.getSurname(), Double.parseDouble(count2 + "")));
                lists.add(s.getName() + " " + s.getSurname());
            }
            series.setName("Best Customer");
//            pxAxis.setCategories(lists);
            customerChart.getData().add(series);
        } catch (DataRepetitionException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownException ex) {
            Logger.getLogger(BookingController2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSaveRecord(ActionEvent event
    ) throws DataStorageException, UnknownException {
        Customer cust;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Customer(0, houseNo, name, surname, cell, sGender, street, city, zip);
                        try {
                            if (cust.addCustomer(cust)) {
                                globalCustomerID = cust.getLastAddedID();
                                AlertMessages.getInfo("Saved", "Customer successfully added");
                                clearData();
                                openOwnershipForm();
                                closeForm();
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
        txtContact.setText("");
        txtFname.setText("");
        txtSurname.setText("");
        txtContact.setText("");
        txtHouseNo.setText("");
        txtZip.setText("");
        txtStreet.setText("");
        txtCustID.setText("");
        cmbCity.setValue(null);
        radMale.setSelected(true);
        radFemale.setSelected(true);
        btnEditCustomer.setDisable(true);
        btnDeleteCustomer.setDisable(true);
        btnListCustomer.setDisable(true);
        btnGoToOwnership.setDisable(true);
    }

    /**
     * null value
     */
    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtFname.getText())) {
            AlertMessages.getWarning("null value", "First name is empty. Please enter customer name");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtSurname.getText())) {
            AlertMessages.getWarning("null value", " Surname is empty. Please enter customer surname");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtStreet.getText())) {
            AlertMessages.getWarning("null value", " Street name is empty. Please enter street name");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtHouseNo.getText())) {
            AlertMessages.getWarning("null value", " House no is empty. Please enter house no");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtContact.getText())) {
            AlertMessages.getWarning("null value", " contact no is empty. Please enter contact no");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtZip.getText())) {
            AlertMessages.getWarning("null value", " Zip code is empty. Please enter zip code");
            return true;
        } else if (cmbCity.getValue() == null) {
            AlertMessages.getWarning("null value", " Please select city ");
            return true;
        }
        return false;
    }

    /**
     * text only field
     */
    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(name)) {
            AlertMessages.getWarning("input mismatch", "First name may only contain characters");
            return true;
        } else if (!DataEntryCheck.textOnlyTextField(surname)) {
            AlertMessages.getWarning("input mismatch", "surname may only contain characters");
            return true;
        } else if (!DataEntryCheck.textOnlyTextField(street)) {
            AlertMessages.getWarning("input mismatch", "street may only contain characters");
            return true;
        }

        return false;
    }

    /**
     * is number
     */
    private boolean isNumberOnly() {
        if (!DataEntryCheck.onlyNumberTextField(cell)) {
            AlertMessages.getWarning("input mismatch", "cell phone may only contain number");
            return true;
        } else if (!DataEntryCheck.onlyNumberTextField(zip)) {
            AlertMessages.getWarning("input mismatch", "zip code may only contain number");
            return true;
        } else if (!DataEntryCheck.checkNegativeNumber(houseNo)) {
            AlertMessages.getWarning("input mismatch", "house no may only contain number");
            return true;
        }
        return false;
    }

    /**
     * check min characters
     */
    private boolean isMin() {
        if (!DataEntryCheck.minLength(name, 3)) {
            AlertMessages.getWarning("Minimum characters", "customer name is too short. Please enter more characters");
            return true;
        } else if (!DataEntryCheck.minLength(surname, 3)) {
            AlertMessages.getWarning("Minimum characters", "customer surname is too short. Please enter more characters");
            return true;
        } else if (!DataEntryCheck.minLength(street, 3)) {
            AlertMessages.getWarning("Minimum characters", "street is too short. Please enter more characters");
            return true;
        }
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
     * get customer data
     */
    private void getData() {
        if (!"".equals(txtCustID.getText())) {
            custid = Integer.parseInt(txtCustID.getText());
        }

        name = txtFname.getText();
        surname = txtSurname.getText();
        cell = txtContact.getText();
        street = txtStreet.getText();
        houseNo = Integer.parseInt(txtHouseNo.getText());
        zip = txtZip.getText();
        city = cmbCity.getValue();
        sGender = getGender();

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
        txtZip.setText("");
        txtStreet.setText("");
        cmbCity.setValue(null);
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
    }

    public void closeForm() {
        FormClose.closeForm(customerForm);
    }

    @FXML
    private void onTableClick(MouseEvent event) {

        Customer customer = tblCustomer.getSelectionModel().getSelectedItem();
        txtContact.setText(customer.getCell());
        txtFname.setText(customer.getName());
        txtSurname.setText(customer.getSurname());
        txtHouseNo.setText(customer.getHouseNo() + "");
        txtZip.setText(customer.getZip());
        txtStreet.setText(customer.getStreet());
        txtCustID.setText(customer.getCustid() + "");

        cmbCity.setValue(customer.getCity());
        if ("Male".equals(customer.getGender())) {
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
        btnGoToOwnership.setDisable(false);

    }

    @FXML
    private void onEditCustomer(ActionEvent event) throws DataStorageException, UnknownException {
        Customer cust;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Customer(custid, houseNo, name, surname, cell, sGender, street, city, zip);
                        try {
                            if (cust.updateCustomer(cust)) {
                                AlertMessages.getInfo("Updated", "Customer successfully updated");
                                clearData();
                                openCustomerForm();
                                closeForm();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Customer was not updated");
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
    private void onDeleteCustomer(ActionEvent event) throws DataStorageException {
        Customer cust;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (!isNumberOnly()) {

                        cust = new Customer(custid, houseNo, name, surname, cell, sGender, street, city, zip);
                        try {
                            if (cust.deleteCustomer(cust)) {
                                AlertMessages.getInfo("Deleted", "Customer successfully deleted");
                                clearData();
                                openCustomerForm();
                                closeForm();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Customer was not deleted");
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
    private void onContactLengthCheck(KeyEvent event) {
        String strNumber = txtContact.getText();
        if (strNumber.length() > 9) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtContact.getText().matches("[a-zA-Z]+\\.?")) {
            txtContact.setText(txtContact.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("Contact must only digits", 3000);
        }
    }

    @FXML
    private void onGoToOwnershipClicked(ActionEvent event) {
        globalCustomerID = Integer.parseInt(txtCustID.getText());
        openOwnershipForm();
        closeForm();
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(customerForm);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void firstnameValid(KeyEvent event) {
        if (!txtFname.getText().matches("[a-zA-Z]+\\.?")) {
            txtFname.setText(txtFname.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Firsname must only contain text", 3000);
        }

    }

    @FXML
    private void onStreetValid(KeyEvent event) {
        if (!txtStreet.getText().matches("[a-zA-Z]+\\.?")) {
            txtStreet.setText(txtStreet.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Street must only contain text", 3000);
        }
    }

    @FXML
    private void onSurnameValid(KeyEvent event) {
        if (!txtSurname.getText().matches("[a-zA-Z]+\\.?")) {
            txtSurname.setText(txtSurname.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Surname must only contain text", 3000);
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
            txtHouseNo.setText(txtContact.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("House Number must only digits", 3000);
        }
    }

    @FXML
    private void onZip(KeyEvent event) {
        String strNumber = txtZip.getText();
        if (strNumber.length() > 2) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtZip.getText().matches("[a-zA-Z]+\\.?")) {
            txtZip.setText(txtContact.getText().replaceAll("[a-zA-Z]", ""));
            snackbar.show("Zip must only contain digits", 3000);
        }
    }

}
