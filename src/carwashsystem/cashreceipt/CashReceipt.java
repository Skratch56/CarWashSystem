package carwashsystem.cashreceipt;

import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingDA;
import carwashsystem.owner.*;
import carwashsystem.carWashExeception.DataRepetitionException;

/**
 *
 * @author Joseph
 */
public class CashReceipt {

    int cashReceiptNo, amountReceived, bookingNo, employeeID;

    public CashReceipt() {
        this(0, 0, 0, 0);
    }

    public CashReceipt(int cashReceiptNo, int amountReceived, int bookingNo, int employeeID) {
        this.cashReceiptNo = cashReceiptNo;
        this.amountReceived = amountReceived;
        this.bookingNo = bookingNo;
        this.employeeID = employeeID;
    }

    public int getCashReceiptNo() {
        return cashReceiptNo;
    }

    public int getAmountReceived() {
        return amountReceived;
    }

    public int getBookingNo() {
        return bookingNo;
    }

    public int getEmployeeID() {
        return employeeID;
    }

     public boolean addCashReceipt(CashReceipt owners) throws DataRepetitionException {
        return CashReceiptDA.isAddCashReceipt(this);
    }
    
}
