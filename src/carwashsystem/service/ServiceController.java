package carwashsystem.service;

import carwashsystem.alert.AlertMessages;
import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.Customer;
import carwashsystem.cust.CustomerController;
import carwashsystem.cust.CustomerDA;
import carwashsystem.screens.OpenForm;
import carwashsystem.servicelist.ServiceList;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class ServiceController implements Initializable {

    @FXML
    private AnchorPane serviceForm;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtCost;
    @FXML
    private Button btnSaveRec;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnEditRec;
    @FXML
    private Button btnView;
    @FXML
    private TableView<Service> tblService;
    @FXML
    private TableColumn<Service, Integer> colCode;
    @FXML
    private TableColumn<Service, String> colType;
    @FXML
    private TableColumn<Service, Double> colCost;

    private ServiceDA daService;
    private int serID;
    private double price;
    private String sType;
    @FXML
    private TextField txtServiceID;
    static ArrayList<Service> arService;
    @FXML
    private JFXTextField searchField;
    @FXML
    private LineChart<String, Number> serviceChart;
    @FXML
    private CategoryAxis pxAxis;
    private Booking booking;
    private BookingDA bookingDA;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        booking = new Booking();
        daService = new ServiceDA();
        bookingDA = new BookingDA();
        btnSaveRec.setDisable(false);
        initializeTable();
    }

    private void loadProductSalesChart(Service p) throws DataRepetitionException, DataStorageException, UnknownException {

        if (p != null) {

            String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
            ObservableList lists = FXCollections.observableArrayList(months);
//            pxAxis.setCategories(lists);

            serviceChart.getData().clear();

            List<Booking> sales = booking.getAllBookingByID(p.getSerID());

            XYChart.Series series = new XYChart.Series();
            series.setName(p.getSType());
            int count = 0;
            for (Booking s : sales) {
                String month = convertDate(s.getBookDate());
                count +=BookingDA.getCountService(p.getSerID(), s.getBookDate(),s.getBookingID());
                System.out.println(BookingDA.getCountService(p.getSerID(), s.getBookDate(),s.getBookingID()));
                series.getData().add(new XYChart.Data(month, count));
            }

            serviceChart.getData().addAll(series);
        }

    }

    private String convertDate(String date) {

        int d = Integer.parseInt(date.substring(5, 7));

        return new DateFormatSymbols().getMonths()[d-1];
    }

    private void initializeTable() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("serID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("sType"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        btnDel.setDisable(true);
        btnEditRec.setDisable(true);
        btnSaveRec.setDisable(false);
        btnView.setDisable(true);
        try {
            arService = ServiceDA.getAllService();
        } catch (UnknownException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblService.getItems().setAll(arService);
        filterData();
    }

    private void filterData() {
        ObservableList<Service> oListemployee = FXCollections.observableArrayList(arService);
        FilteredList<Service> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(service -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (service.getSType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Service> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblService.comparatorProperty());
            tblService.setItems(sortedData);
        });
    }

    @FXML
    private void onSaveRec(ActionEvent event) {
        Service service;
        if (!nullValue()) {
            sType = txtType.getText();
            price = Double.parseDouble(txtCost.getText());
            if (!isTextOnly()) {
                if (!isMin()) {
                    if (!checkCost()) {
                        service = new Service(serID, sType, price, null);
                        try {
                            if (service.isAddService(service)) {
                                initializeTable();
                                txtCost.setText("");
                                txtServiceID.setText("");
                                txtType.setText("");
                                AlertMessages.getInfo("Saved", "Service record saved");
                            } else {
                                AlertMessages.getError("Unsuccessful", "Service record was not saved");
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
    private void onRemoveRec(ActionEvent event) throws UnknownException {
        Service service;
        if (!nullValue()) {
            getData();
            if (!isTextOnly()) {
                if (!isMin()) {
                    if (!checkCost()) {
                        service = new Service(serID, sType, price, null);
                        try {
                            if (service.deleteService(service)) {
                                txtCost.setText("");
                                txtServiceID.setText("");
                                txtType.setText("");
                                initializeTable();
                                AlertMessages.getInfo("Deleted", "Service record deleted");
                            } else {
                                AlertMessages.getError("Unsuccessful", "Service record was not deleted");
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
    private void onEditRec(ActionEvent event) throws UnknownException {
        Service service;
        if (!nullValue()) {
            getData();
            if (!isTextOnly()) {
                if (!isMin()) {
                    if (!checkCost()) {
                        service = new Service(serID, sType, price, null);
                        try {
                            if (service.updateService(service)) {
                                initializeTable();
                                txtCost.setText("");
                                txtServiceID.setText("");
                                txtType.setText("");
                                AlertMessages.getInfo("Updated", "Service record Updated");
                            } else {
                                AlertMessages.getError("Unsuccessful", "Service record was not Updated");
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
    private void onListServices(ActionEvent event) {
        txtCost.setText("");
        txtServiceID.setText("");
        txtType.setText("");
        btnDel.setDisable(true);
        btnEditRec.setDisable(true);
        btnSaveRec.setDisable(false);
        btnView.setDisable(true);
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtType.getText())) {
            AlertMessages.getWarning("null value", "Service type is empty. Please enter service type");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtCost.getText())) {
            AlertMessages.getWarning("null value", "Service cost is empty. Please enter service cost");
            return true;
        }
        return false;
    }

    /**
     *
     * min characters
     */
    private boolean isMin() {
        if (!DataEntryCheck.minLength(sType, 3)) {
            AlertMessages.getWarning("min character", "Service type too short. Please enter more characters");
            return true;
        }
        return false;
    }

    /**
     *
     * min characters
     */
    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(sType)) {
            AlertMessages.getWarning("input mismatch", "Please enter service type");
            return true;
        }
        return false;
    }

    private boolean checkCost() {
        if (!DataEntryCheck.checkNegativeDoubleNumber(price)) {
            AlertMessages.getWarning("Numberformat", "Please enter service cost");
            return true;
        }
        return false;
    }

    /**
     * get data
     */
    private void getData() {

        serID = Integer.parseInt(txtServiceID.getText());

        sType = txtType.getText();
        price = Double.parseDouble(txtCost.getText());
    }

    private void onSelectService(MouseEvent event) {

    }

    @FXML
    private void onSelectService2(MouseEvent event) {
        try {
            Service serv = tblService.getSelectionModel().getSelectedItem();
            txtCost.setText(serv.getCost() + "");
            txtServiceID.setText(serv.getSerID() + "");
            txtType.setText(serv.getSType());
            loadProductSalesChart(serv);
            btnSaveRec.setDisable(true);
            btnEditRec.setDisable(false);
            btnDel.setDisable(false);
            btnView.setDisable(false);
        } catch (DataStorageException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataRepetitionException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(serviceForm);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }
}
