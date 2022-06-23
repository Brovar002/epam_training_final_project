package by.goncharov.epamsound.beans;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Class for describing the essence of an order.
 * @author Goncharov Daniil
 * @version 1.0
 * @see LocalDate
 * @see Entity
 */
@Entity
@Table(name = "orders")
public class Order extends by.goncharov.epamsound.beans.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "price")
    private double price;
    @Column(name = "date")
    private LocalDate date;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User customer;
    @OneToOne
    @JoinColumn(name = "audio_track_id")
    private Track track;


    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param track    the track
     * @param customer the customer
     * @param price    the price
     * @param date     the date
     */
    public Order(final Track track, final User customer,
                 final double price, final LocalDate date) {
        this.track = track;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    public Order(int id, double price, LocalDate date, User customer, Track track) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.customer = customer;
        this.track = track;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final int id) {
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
    public Track getTrack() {
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
    public void setTrack(final Track track) {
        this.track = track;
    }
}
