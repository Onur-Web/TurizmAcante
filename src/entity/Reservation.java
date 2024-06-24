package entity;

import java.sql.Date;

public class Reservation {
    private int id;
    private int roomId;
    private String customerName;
    private String customerTC;
    private String customerEmail;
    private double totalPrice;
    private Date start;
    private Date finish;

    private int numberOfAdults;
    private int numberOfChildren;

    public Reservation() {
    }

    public Reservation(int id, int roomId, String customerName, String customerTC, String customerEmail, double totalPrice, Date start, Date finish, int numberOfAdults, int numberOfChildren) {
        this.id = id;
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerTC = customerTC;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.start = start;
        this.finish = finish;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTC() {
        return customerTC;
    }

    public void setCustomerTC(String customerTC) {
        this.customerTC = customerTC;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }
}
