package carwashsystem.service;

import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;
import javafx.scene.control.CheckBox;

/**
 *
 * @author
 */
public class Service {

    private int serID;
    private String sType;
    private double cost;
    private CheckBox checkBox;

    public Service(int serID, String sType, double cost, CheckBox checkBox) {
        this.serID = serID;
        this.sType = sType;
        this.cost = cost;
        this.checkBox = new CheckBox();
    }

    public String getSType() {
        return sType;
    }

    public double getCost() {
        return cost;
    }

    public int getSerID() {
        return serID;
    }

    public String getsType() {
        return sType;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public boolean isAddService(Service service) throws DataRepetitionException {
        return ServiceDA.isAddService(this);
    }

    public boolean deleteService(Service service) throws DataRepetitionException, UnknownException {
        return ServiceDA.deleteService(this);
    }

    public boolean updateService(Service service) throws DataRepetitionException, UnknownException {
        return ServiceDA.updateService(this);
    }

}
