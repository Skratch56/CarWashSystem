/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.mainform;

import carwashsystem.booking.Booking;
import carwashsystem.booking.BookingDA;
import carwashsystem.carWashExeception.DataRepetitionException;
import carwashsystem.carWashExeception.DataStorageException;
import carwashsystem.carWashExeception.UnknownException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class ItemController {

    @FXML
    private Label lblCustName;
    @FXML
    private Label lblTotalAmount;
    @FXML
    private Label lblVehRegNo;
    @FXML
    private Label lblBookingID;
    private Booking Booking;
    private long selectedBookingId;
    private BookingDA daBook;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblMonth;
    @FXML
    private Label lblYear;
    @FXML
    private Label lblBookingStatus;
    @FXML
    private Label lblService;
    @FXML
    private Label lblEmployee;

    /**
     * Initializes the controller class.
     */
    public ItemController() {
        lblCustName = new Label();
        lblVehRegNo = new Label();
        lblTotalAmount = new Label();
        lblCustName = new Label();
        lblDay = new Label();
        lblMonth = new Label();
        lblYear = new Label();
        lblBookingStatus = new Label();
        lblService = new Label();
        lblEmployee = new Label();
        daBook = new BookingDA();
    }

    public void setBooking(Booking booking, long selectedBookingId) {

        this.Booking = booking;
        this.selectedBookingId = selectedBookingId;
        setData();

    }

    private void setData() {
        try {
            ArrayList<String> arService = new ArrayList<>();
            ArrayList<String> arEmployee = new ArrayList<>();
            if ("0".equals(Booking.getTotAmt())) {
                lblBookingStatus.setText("Booking cancelled");
                lblService.setText("None Selected");
            } else {
                lblBookingStatus.setText("Booking Complete");
                arService = BookingDA.getAllService(Booking.getBookingID() + "");
                String service = "";
                for (int x = 0; x < arService.size(); x++) {
                    if (x == 0) {
                        service = BookingDA.getService(arService.get(x));
                    } else {
                        service += ", " + BookingDA.getService(arService.get(x));
                    }
                    System.out.println(service);
                }
                lblService.setText(service);

            }
            arEmployee = BookingDA.getAllEmployee(Booking.getBookingID() + "");
            String employee = "";
            for (int x = 0; x < arEmployee.size(); x++) {
                if (x == 0) {
                    employee = BookingDA.getEmployee(arEmployee.get(x));
                } else {
                    employee += ", " + BookingDA.getEmployee(arEmployee.get(x));
                }
                System.out.println(employee);
            }
            lblEmployee.setText(employee);

            lblBookingID.setText("Booking ID: " + Booking.getBookingID());
//            lblEmployee.setText(BookingDA.getEmployeeName2(Booking.getEmpID() + ""));
            lblTotalAmount.setText("R" + Booking.getTotAmt() + "");
            String custname = Booking.getCustomerName(Booking.getCustID() + "");
            lblCustName.setText(custname);
            lblVehRegNo.setText(Booking.getVhReg());

            LocalDate date = LocalDate.parse(Booking.getBookDate());

            lblYear.setText((date.getYear()) + "");
            lblMonth.setText((date.getMonth()) + "");
            lblDay.setText((date.getDayOfMonth()) + "");

        } catch (DataRepetitionException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
