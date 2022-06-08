package by.goncharov.epamsound.beans;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for describing the essence of an order.
 * @author Goncharov Daniil
 * @version 1.0
 * @see LocalDate
 * @see Entity
 */
@Entity
@Table(name = "order")
public class Order extends by.goncharov.epamsound.beans.Entity {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "price")
    private double price;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "user_id")
    private User customer;
    @Column(name = "audio_track_id")
    private int track;


    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param id       the id
     * @param track    the track
     * @param customer the customer
     * @param price    the price
     * @param date     the date
     */
    public Order(final long id, final int track, final User customer,
                 final double price, final LocalDate date) {
        this.id = id;
        this.track = track;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(final double price) {
        this.price = price;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(final User customer) {
        this.customer = customer;
    }

    /**
     * Gets track.
     *
     * @return the track
     */
    public int getTrack() {
        return track;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                ", customer=" + customer +
                ", track=" + track +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.price, price) == 0 && track == order.track && Objects.equals(date, order.date) && Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, date, customer, track);
    }

    /**
     * Sets track.
     *
     * @param track the track
     */
    public void setTrack(final int track) {
        this.track = track;
    }
}
