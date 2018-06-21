/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.allocation;

import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.CustomerController;
import carwashsystem.equipment.Equipment;
import carwashsystem.equipment.EquipmentDA;
import carwashsystem.screens.OpenForm;
import carwashsystem.service.Service;
import carwashsystem.service.ServiceDA;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class AllocationController implements Initializable {

    @FXML
    private AnchorPane serviceForm;
    @FXML
    private Button btnSaveRec;
    @FXML
    private Button btnView;
    @FXML
    private TableView<Service> tblService;
    @FXML
    private TableColumn<Service, String> colCode;
    @FXML
    private TableColumn<Service, String> colType;
    @FXML
    private TableColumn<Service, String> colCost;
    @FXML
    private TextField txtServiceID;
    @FXML
    private TextField txtEquipmentID;
    @FXML
    private TextField txtDescriotion;
    @FXML
    private TableView<Equipment> tblEquiment;
    @FXML
    private TableColumn<Equipment, String> colEquipmentID;
    @FXML
    private TableColumn<Equipment, String> colDescription;
    @FXML
    private TableColumn<Equipment, String> colEquipmentType;
    static ArrayList<Service> arService;
    static ArrayList<Equipment> arEquipment;
    private AllocationDA daAllocation;
    private ServiceDA daService;
    private EquipmentDA daEquipment;
    private int serID, equipID;
    private String des;
    @FXML
    private JFXTextField searchService;
    @FXML
    private JFXTextField searchEquipment;
    @FXML
    private TableColumn<?, ?> colQtyInStock;
    @FXML
    private TableColumn<?, ?> colAdd;
    @FXML
    private TableColumn<?, ?> colAddEquip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daAllocation = new AllocationDA();
        daService = new ServiceDA();
        daEquipment = new EquipmentDA();
        initializeTables();
    }

    private void initializeTables() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("serID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("sType"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        colDescription.setCellValueFactory(new PropertyValueFactory<>("equipDesc"));
        colEquipmentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEquipmentType.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        colQtyInStock.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("textField"));
        colAddEquip.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        try {
            arService = ServiceDA.getAllService();
            arEquipment = EquipmentDA.getAllEquipment();
        } catch (UnknownException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblService.getItems().setAll(arService);
        tblEquiment.getItems().setAll(arEquipment);
        filterDataEquipment();
        filterDataService();
    }

    private void filterDataService() {
        ObservableList<Service> oListemployee = FXCollections.observableArrayList(arService);
        FilteredList<Service> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchService.setOnKeyReleased(e -> {
            searchService.textProperty().addListener((observable, oldValue, newValue) -> {
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

    private void filterDataEquipment() {
        ObservableList<Equipment> oListemployee = FXCollections.observableArrayList(arEquipment);
        FilteredList<Equipment> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchEquipment.setOnKeyReleased(e -> {
            searchEquipment.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(equipment -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (equipment.getEquipDesc().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (equipment.getEquipmentType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Equipment> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblEquiment.comparatorProperty());
            tblEquiment.setItems(sortedData);
        });
    }

    @FXML
    private void onSaveRec(ActionEvent event) throws DataStorageException, UnknownException {
        Allocation alloq;
        if (!nullValue()) {
            getData();
            if (!isTextOnly()) {
                if (!isMin()) {

                    for (int x = 0; x < arEquipment.size(); x++) {
                        if (tblEquiment.getItems().get(x).getCheckBox().isSelected()) {
                            if (tblEquiment.getItems().get(x).getTextField().getText() != null) {
                                alloq = new Allocation(serID, tblEquiment.getItems().get(x).getId(), des, Integer.parseInt(tblEquiment.getItems().get(x).getTextField().getText()));
                                try {
                                    if (alloq.addAllocation(alloq)) {
                                        
                                        int oldqty = tblEquiment.getItems().get(x).getQty();
                                        if(oldqty > Integer.parseInt(tblEquiment.getItems().get(x).getTextField().getText())){
                                        int newqty = oldqty - Integer.parseInt(tblEquiment.getItems().get(x).getTextField().getText());
                                        alloq.updateQty(newqty, alloq.getEquipID());
                                        }else{
                                             AlertMessages.getError("Unsuccessful", "Qty is higher than qty in stock");
                                        }
//                                        AlertMessages.getInfo("Saved", "Allocation record saved");
                                    } else {
                                        AlertMessages.getError("Unsuccessful", "Allocation record was not saved");
                                    }
                                } catch (DataRepetitionException e) {
                                    AlertMessages.getError("Data repetition", e.getMessage());
                                }
                            } else {
                                AlertMessages.getError("Unsuccessfull", "Please enter the quantity");
                            }
                        }
                    }
                    AlertMessages.getInfo("Saved", "Allocation record saved");
                    initializeTables();
                }
            }
        }
    }

    @FXML
    private void onListServices(ActionEvent event) {
        txtDescriotion.setText("");
        txtServiceID.setText("");
//        txtEquipmentID.setText("");
    }

    @FXML
    private void onSelectEquipment(MouseEvent event) {
        Equipment eq = tblEquiment.getSelectionModel().getSelectedItem();

//        txtEquipmentID.setText(String.valueOf(eq.getId()));
    }

    private void getData() {

        des = txtDescriotion.getText();
        serID = Integer.parseInt(txtServiceID.getText());
//        equipID = Integer.parseInt(txtEquipmentID.getText());
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtServiceID.getText())) {
            AlertMessages.getWarning("null value", "Service ID is empty. Please enter service id");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtDescriotion.getText())) {
            AlertMessages.getWarning("null value", "Descriotion is empty. Please enter service Descriotion");
            return true;
        }
        return false;
    }

    /**
     *
     * min characters
     */
    private boolean isMin() {
        if (!DataEntryCheck.minLength(des, 3)) {
            AlertMessages.getWarning("min character", "Description too short. Please enter more characters");
            return true;
        }
        return false;
    }

    /**
     *
     * min characters
     */
    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(des)) {
            AlertMessages.getWarning("input mismatch", "Please enter description with text only");
            return true;
        }
        return false;
    }

    @FXML
    private void onSelectService2(MouseEvent event) {
        Service serv = tblService.getSelectionModel().getSelectedItem();

        txtServiceID.setText(String.valueOf(serv.getSerID()));
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
