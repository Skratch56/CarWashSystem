/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carwashsystem.booking;

/**
 *
 * @author CE
 */
public class Assignment {

    private int booking_no, employee_id;
    private String description;

    public Assignment() {
        this(0, 0, "");
    }

    public Assignment(int booking_no, int employee_id, String description) {
        this.booking_no = booking_no;
        this.employee_id = employee_id;
        this.description = description;
    }

    public int getBooking_no() {
        return booking_no;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getDescription() {
        return description;
    }

    public void setBooking_no(int booking_no) {
        this.booking_no = booking_no;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
