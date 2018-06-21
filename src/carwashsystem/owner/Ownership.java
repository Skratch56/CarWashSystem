
package carwashsystem.owner;

import carwashsystem.carWashExeception.DataRepetitionException;

/**
 *
 * @author Joseph
 */
public class Ownership {
 private int idCust;
 private String idVehicle,type;

    public Ownership(int idCust, String idVehicle, String type) {
        this.idCust = idCust;
        this.idVehicle = idVehicle;
        this.type = type;
    }

    public int getIdCust() {
        return idCust;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public String getType() {
        return type;
    }
    
    public boolean addOwnership (Ownership owners) throws DataRepetitionException{
        return OwnershipDA.isOwnership(this);
    }
 
 
}
