package carwashsystem.equipment;

import carwashsystem.alert.AlertMessages;
import carwashsystem.carWashExeception.DataEntryCheck;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;
import carwashsystem.closeform.FormClose;
import carwashsystem.cust.Customer;
import carwashsystem.screens.OpenForm;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joseph
 */
public class EquipmentController implements Initializable {

    @FXML
    private AnchorPane frmEquipment;
    @FXML
    private TextField txtEquipType;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnEditOnEditRecord;
    @FXML
    private TableView<Equipment> tblEquipment;
    @FXML
    private TableColumn<Equipment, Integer> colEquipID;
    @FXML
    private TableColumn<Equipment, String> colEquipType;
    @FXML
    private TableColumn<Equipment, String> colEquipDesc;
    @FXML
    private Button btnDeleteEquipment;
    @FXML
    private Button btnCreateNew;
    @FXML
    private TextField txtEquipmentID;
    @FXML
    private Button btnRefresh;
    private EquipmentDA daEquipment;
    private String type, desc;
    private Equipment eq;
    private int id, qty;
    @FXML
    private JFXTextField searchField;
    ArrayList<Equipment> arEquipment;
    @FXML
    private JFXTextField txtQuantity;
    @FXML
    private TableColumn<?, ?> colQty;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDeleteEquipment.setDisable(true);
        btnEditOnEditRecord.setDisable(true);
        btnCreateNew.setDisable(true);
        txtEquipmentID.setDisable(true);
        daEquipment = new EquipmentDA();
        setUpTableEquipment();
        loadData();
        filterData();
    }

    private void filterData() {
        ObservableList<Equipment> oListemployee = FXCollections.observableArrayList(arEquipment);
        FilteredList<Equipment> searchedData = new FilteredList<>(oListemployee, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
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
            sortedData.comparatorProperty().bind(tblEquipment.comparatorProperty());
            tblEquipment.setItems(sortedData);
        });
    }

    @FXML
    private void onSaveRecord(ActionEvent event) {

        Equipment equip;
        try {
            if (!checkEmptyField()) {
                getData();
                if (!checkMinCharacters()) {
                    equip = new Equipment(0, type, desc, qty, null,null);

                    try {
                        if (equip.addEquipment(equip)) {
                            AlertMessages.getInfo("Record saved", equip.getEquipmentType() + " Successfully created");
                            clearData();

                            loadData();
                        } else {
                            AlertMessages.getError("Failed", "Equipment record was not saved");
                        }
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("data repetition err", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void onDeleteEquipment(ActionEvent event) {

        if (!"".equals(txtEquipmentID.getText())) {
            id = Integer.parseInt(txtEquipmentID.getText());
        }

        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Confirmation");
        al.setContentText("Are you sure want to delete equipment: " + txtEquipType.getText() + " ?");
        Optional<ButtonType> btnOption = al.showAndWait();
        if (btnOption.get() == ButtonType.OK) {
            Equipment equip;
            try {
                if (!checkEmptyField()) {
                    type = txtEquipType.getText();
                    desc = taDescription.getText();
                    if (!checkMinCharacters()) {
                        equip = new Equipment(id, type, desc, qty, null,null);

                        try {
                            if (equip.deleteEquipment(equip)) {
                                AlertMessages.getInfo("Record saved", equip.getEquipmentType() + " Successfully created");
                                loadData();
                            } else {
                                AlertMessages.getError("Failed", "Equipment record was not saved");
                            }
                        } catch (DataRepetitionException e) {
                            AlertMessages.getError("data repetition err", e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {

            }
        } else {
            AlertMessages.getInfo("Cancelled", "Deletion operation cancelled");
        }

    }

    @FXML
    private void onCreateNewEquipment(ActionEvent event) {
        clearData();
        btnSave.setDisable(false);
        btnDeleteEquipment.setDisable(true);
        btnEditOnEditRecord.setDisable(true);
        btnCreateNew.setDisable(true);

    }

    @FXML
    private void onRefreshTable(ActionEvent event) {

    }

    /**
     * set up table equipment
     */
    private void setUpTableEquipment() {
        colEquipDesc.setCellValueFactory(new PropertyValueFactory<>("equipDesc"));
        colEquipID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEquipType.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    /**
     * populate table equipment with data
     */
    private void loadData() {

        ObservableList<Equipment> arEquips = FXCollections.observableArrayList();
        try {
            arEquipment = EquipmentDA.getAllEquipment();
            arEquipment.forEach(e -> {

                arEquips.add(e);
            });
            tblEquipment.getItems().setAll(arEquips);
        } catch (Exception e) {

        }

    }

    private boolean checkEmptyField() {
        if (!DataEntryCheck.textFieldNotEmpty(txtEquipType.getText())) {
            AlertMessages.getError("Empty field", "Please enter equipment type");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(taDescription.getText())) {
            AlertMessages.getError("Empty field", "Please enter equipment description");
            return true;
        }
         else if (!DataEntryCheck.textFieldNotEmpty(txtQuantity.getText())) {
            AlertMessages.getError("Empty field", "Please enter equipment description");
            return true;
        }
        return false;
    }

    private boolean checkMinCharacters() {
        if (!DataEntryCheck.minLength(type, 3)) {
            AlertMessages.getError("Minimum character check", "Please enter more characters for equipment type");
            return true;
        } else if (!DataEntryCheck.minLength(desc, 5)) {
            AlertMessages.getError("Minimum character check", "Please enter equipment description");
            return true;
        }
        return false;
    }

    /**
     * get equipment data
     */
    private void getData() {
        type = txtEquipType.getText();
        desc = taDescription.getText();
        qty = Integer.parseInt(txtQuantity.getText());
    }

    private void clearData() {
        txtEquipType.setText("");
        taDescription.setText("");
        txtQuantity.setText("");
    }

    @FXML
    private void onSetFields(MouseEvent event) {

        if (tblEquipment.getSelectionModel().getSelectedItem() != null) {
            btnCreateNew.setDisable(false);
            btnDeleteEquipment.setDisable(false);
            btnEditOnEditRecord.setDisable(false);
            btnSave.setDisable(true);

            eq = tblEquipment.getSelectionModel().getSelectedItem();
            txtEquipType.setText(eq.getEquipmentType());
            txtEquipmentID.setText(String.valueOf(eq.getId()));
            taDescription.setText(eq.getEquipDesc());
            txtQuantity.setText(eq.getQty()+"");

        }
    }

    @FXML
    private void onUpdateRecord(ActionEvent event) {

        if (!"".equals(txtEquipmentID.getText())) {
            id = Integer.parseInt(txtEquipmentID.getText());
        }
        type = txtEquipType.getText();
        desc = taDescription.getText();

        Equipment equip;
        try {
            if (!checkEmptyField()) {
                getData();
                if (!checkMinCharacters()) {
                    equip = new Equipment(id, type, desc, qty, null,null);

                    try {
                        if (equip.updateEquipment(equip)) {

                            AlertMessages.getInfo("Record saved", equip.getEquipmentType() + " Successfully created");
                            loadData();
                        } else {
                            AlertMessages.getError("Failed", "Equipment record was not saved");
                        }
                    } catch (DataRepetitionException e) {
                        AlertMessages.getError("data repetition err", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmEquipment);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/carwashsystem/mainform/Home.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onQuantity(KeyEvent event) {
        String strNumber = txtQuantity.getText();
        if (strNumber.length() > 2) {
            if (event.getCode() != event.getCode().BACK_SPACE) {
                event.consume();
            }

        }
        if (txtQuantity.getText().matches("[a-zA-Z]+\\.?")) {
            txtQuantity.setText(txtQuantity.getText().replaceAll("[a-zA-Z]", ""));

        }
    }
}
