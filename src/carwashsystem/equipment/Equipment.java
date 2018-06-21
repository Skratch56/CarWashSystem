package carwashsystem.equipment;

import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Joseph
 */
public class Equipment {

    private int id,qty;
    private String equipmentType, equipDesc;
    private TextField textField;
    private CheckBox checkBox;

    public Equipment(int id,String equipmentType, String equipDesc,int qty, TextField textField,CheckBox checkBox) {
        this.equipmentType = equipmentType;
        this.equipDesc = equipDesc;
        this.id = id;
        this.qty = qty;
        this.textField = new TextField();
        this.checkBox = new CheckBox();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    
    
    public TextField getTextField() {
        return textField;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public String getEquipDesc() {
        return equipDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public void setEquipDesc(String equipDesc) {
        this.equipDesc = equipDesc;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
    
    public boolean addEquipment (Equipment equipment) throws DataRepetitionException{
        return EquipmentDA.addEquipment(this);
    }
     public boolean deleteEquipment (Equipment equipment) throws DataRepetitionException, UnknownException{
        return EquipmentDA.deleteEquipment(this);
    }
      public boolean updateEquipment (Equipment equipment) throws DataRepetitionException, UnknownException{
        return EquipmentDA.editEquipment(this);
    }

}
