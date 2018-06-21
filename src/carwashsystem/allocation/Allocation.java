/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.allocation;

import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;

/**
 *
 * @author CE
 */
public class Allocation {
    int serID,equipID,qty;
    String des;

    public Allocation(int serID, int equipID, String des,int qty) {
        this.serID = serID;
        this.equipID = equipID;
        this.des = des;
        this.qty = qty;
    }

    public void setSerID(int serID) {
        this.serID = serID;
    }

    public void setEquipID(int equipID) {
        this.equipID = equipID;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getSerID() {
        return serID;
    }

    public int getQty() {
        return qty;
    }

    public int getEquipID() {
        return equipID;
    }

    public String getDes() {
        return des;
    }
     public boolean addAllocation(Allocation Allocation) throws DataRepetitionException {
        return AllocationDA.isAddAllocation(this);
    }
    
    public boolean deleteBooking(Allocation Allocation) throws DataRepetitionException, DataStorageException, UnknownException {
        return AllocationDA.deleteAllocation(this);
    }
    public boolean updateAllocation(Allocation Allocation) throws DataRepetitionException, DataStorageException, UnknownException {
        return AllocationDA.updateAllocation(this);
    }
    public boolean updateQty(int newqty,int id) throws DataRepetitionException, DataStorageException, UnknownException {
        return AllocationDA.updateQTY(newqty,id);
    }
    
}
