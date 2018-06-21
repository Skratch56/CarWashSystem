package carwashsystem.booking;

import carwashsystem.owner.*;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import java.util.ArrayList;

/**
 *
 * @author Joseph
 */
public class Booking {

    int custID, empID, bookingID;
    String vhReg, bookDate, totAmt;

    public Booking() {
        this(0, 0, 0, "", "", "");
    }

    public Booking(int custID, int empID, int bookingID, String vhReg, String bookDate, String totAmt) {
        this.custID = custID;
        this.empID = empID;
        this.bookingID = bookingID;
        this.vhReg = vhReg;
        this.bookDate = bookDate;
        this.totAmt = totAmt;
    }

    public int getCustID() {
        return custID;
    }

    public int getEmpID() {
        return empID;
    }

    public String getVhReg() {
        return vhReg;
    }

    public String getBookDate() {
        return bookDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getTotAmt() {
        return totAmt;
    }

    @Override
    public String toString() {
        return "Booking{" + "custID=" + custID + ", empID=" + empID + ", bookingID=" + bookingID + ", vhReg=" + vhReg + ", bookDate=" + bookDate + ", totAmt=" + totAmt + '}';
    }

    public boolean addBooking(Booking owners) throws DataRepetitionException {
        return BookingDA.isBooking(this);
    }
    public boolean addAssingment(Assignment owners) throws DataRepetitionException {
        return BookingDA.isBookingEmployee(owners);
    }

    public boolean deleteBooking(Booking owners) throws DataRepetitionException, DataStorageException {
        return BookingDA.deleteBooking(this);
    }

    public boolean updateBooking(Booking owners) throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.updateBooking(this);
    }

    public boolean updateTotCost(Booking owners) throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.updateCost(this);
    }

    public int getLastAddedID() throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.getLastAddedID();
    }

    public String getCustomerName(String id) throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.getCustomerName(id);
    }

    public ArrayList<Booking> getAllBookingByID(int id) throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.getAllBookingByID(id);
    }

    public ArrayList<Booking> getAllBooking() throws DataRepetitionException, DataStorageException, UnknownException {
        return BookingDA.getAllBooking();
    }
}
