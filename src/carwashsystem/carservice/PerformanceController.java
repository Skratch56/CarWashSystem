package carwashsystem.carservice;

import carwashsystem.service.*;
import carwashsystem.alert.AlertMessages;
import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingController;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.Customer;
import carwashsystem.cust.CustomerController;
import carwashsystem.cust.CustomerDA;
import carwashsystem.owner.OwnershipDA;
import carwashsystem.screens.OpenForm;
import carwashsystem.servicelist.ServiceList;
import carwashsystem.veh.VehicleController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author
 */
public class PerformanceController implements Initializable {

    private TextField txtType;
    private TextField txtCost;
    @FXML
    private Button btnSaveRec;
    private Button btnDel;
    private Button btnEditRec;
    private Button btnView;
    @FXML
    private TableView<Service> tblService;
    @FXML
    private TableColumn<Service, Integer> colCode;
    @FXML
    private TableColumn<Service, String> colType;
    @FXML
    private TableColumn<Service, Double> colCost;

    private PerformanceDA daService;
    private ServiceDA daService2;
    private BookingDA daService3;
    private int serID, bookingNo;

    private String description;
    private TextField txtServiceID;
    static ArrayList<Service> arService;
    @FXML
    private TableColumn<Service, CheckBox> colAdd;
    @FXML
    private Label D;
    @FXML
    private ComboBox<String> cmbBooking_No;
    @FXML
    private AnchorPane PerformanceForm;
    private int totalamount;
    @FXML
    private Label lblTotalAmount;
    @FXML
    private Button btnRefreshAmount;
    public static int globalTotalAmount = 0;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXComboBox<String> cmbDescription;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        daService = new PerformanceDA();
        daService3 = new BookingDA();
        daService2 = new ServiceDA();

        btnSaveRec.setDisable(true);
        if (BookingController.globalBookingID == 0) {
            populateBookingNo();
        } else {
             ObservableList<String> obList = FXCollections.observableArrayList();
            obList.add(BookingController.globalBookingID+ "");
            cmbBooking_No.setItems(obList);
            cmbBooking_No.setValue(BookingController.globalBookingID+ "");
            cmbBooking_No.setDisable(true);
        
        }

        initializeTable();
    }

    private void initializeTable() {

        colCode.setCellValueFactory(new PropertyValueFactory<>("serID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("sType"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        btnSaveRec.setDisable(false);
        try {
            arService = ServiceDA.getAllService();
        } catch (UnknownException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblService.getItems().setAll(arService);

    }
    
     public void populate() {
        ObservableList<String> vehTypes = FXCollections.observableArrayList();
        vehTypes.add("Bronze");
        vehTypes.add("Silver");
        vehTypes.add("Gold");

        cmbDescription.setItems(vehTypes);
     }

    @FXML
    private void onSaveRec(ActionEvent event) throws UnknownException {
        Performance service;
        
            getData();
            
                    int cnt = tblService.getItems().size();
                    try {
                        
                        for (int x = 0; x < cnt; x++) {

                            if (tblService.getItems().get(x).getCheckBox().isSelected()) {
//                                if(tblService.getItems().get(x).getSType()==)
                                serID = tblService.getItems().get(x).getSerID();
                                service = new Performance(serID, bookingNo, description);

                                if (service.isAddPerformance(service)) {
                                    
                                    
                                } else {
                                    AlertMessages.getInfo("Unsuccessful", "Service record not saved");
                                }

                            }

                        }
                        Booking book = new Booking(0, 0, Integer.parseInt(cmbBooking_No.getValue()), "", "", lblTotalAmount.getText());
                        BookingDA.updateCost(book);

                        globalTotalAmount = Integer.parseInt(lblTotalAmount.getText());
                        AlertMessages.getInfo("Saved", "Service record saved");
                        closeForm();
                        openDashboard();
                       
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("Data repetition", e.getMessage());
                    }

                
            
        
    }

    private void updatePrice() {
        int cnt = tblService.getItems().size();
        for (int x = 0; x < cnt; x++) {
            if (tblService.getItems().get(x).getCheckBox().isSelected()) {
                totalamount += tblService.getItems().get(x).getCost();
                lblTotalAmount.setText(totalamount + "");
            }
            
        }

    }

    private void onListServices(ActionEvent event) {
        txtCost.setText("");

        txtType.setText("");

        btnSaveRec.setDisable(false);

    }


   

    /**
     *
     * min characters
     */
    private void populateBookingNo() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = BookingDA.getBookingID();
            custList.forEach(cust -> {
                obList.add(cust);
            });
            cmbBooking_No.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(description)) {
            AlertMessages.getWarning("input mismatch", "Please enter service type");
            return true;
        }
        return false;
    }

    /**
     * get data
     */
    private void getData() {
        description = cmbDescription.getValue();
        bookingNo = Integer.parseInt(cmbBooking_No.getValue());

    }

    private void onSelectService(MouseEvent event) {

    }

    @FXML
    private void onSelectService2(MouseEvent event) {

    }

    @FXML
    private void refreshAmount(ActionEvent event) {
        btnSaveRec.setDisable(false);
        updatePrice();
        totalamount = 0;

    }

    public void openDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Cash Report";
        String screen = "/carwashsystem/cashreceipt/CashReceipt.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public void closeForm() {
        FormClose.closeForm(PerformanceForm);
    }

     @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(PerformanceForm);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }
}
