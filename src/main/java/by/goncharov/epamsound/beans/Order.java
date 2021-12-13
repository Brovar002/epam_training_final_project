package by.goncharov.epamsound.beans;

import java.time.LocalDate;

public class Order extends Entity {
    private long id;
    private double price;
    private LocalDate date;
    private User customer;
    private int track;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public Order() {
    }

    public Order(long id, int track, User customer,
                 double price, LocalDate date) {
        this.id = id;
        this.track = track;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }
}