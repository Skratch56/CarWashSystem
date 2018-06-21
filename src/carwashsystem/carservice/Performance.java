package carwashsystem.carservice;

import carwashsystem.service.*;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.UnknownException;

/**
 *
 * @author
 */
public class Performance {

    private int serID,bookingNo;
    private String description;


    public Performance(int serID,int bookingNo, String description) {
        this.serID = serID;
        this.bookingNo = bookingNo;
        this.description = description;
    }

    public int getBookingNo() {
        return bookingNo;
    }

    public String getDescription() {
        return description;
    }

    public int getSerID() {
        return serID;
    }

    public boolean isAddPerformance(Performance performance) throws DataRepetitionException {
        return PerformanceDA.isAddPerformance(this);
    }
//    public boolean deletePerformance(Performance performance) throws DataRepetitionException, UnknownException {
//        return PerformanceDA.deleteService(this);
//    }
//    public boolean updatePerformance(Performance performance) throws DataRepetitionException, UnknownException {
//        return PerformanceDA.updateService(this);
//    }

}
