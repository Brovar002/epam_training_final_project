package by.goncharov.epamsound.beans;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Class for describing the essence of a user.
 *
 * @author Goncharov Daniil
 * @version 1.0
 * @see Entity
 */
@Entity
@Table(name = "user")
public class User extends by.goncharov.epamsound.beans.Entity {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "cash_account")
    private double cash;
    @Column(name = "role")
    private int role;
    @Column(name = "discount")
    private int discount;
    @Column(name = "email")
    private String email;
    @Column(name = "order_count")
    private int orderCount;

    /**
     * Instantiates a new User.
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     //* @param id       the id
     * @param login    the login
     * @param password the password
     * @param cash     the cash
     * @param role     the role
     * @param discount the discount
     * @param email    the email
     */
    public User(final String login, final String password,
                final double cash, final int role, final int discount,
                final String email) {
        //this.id = id;
        this.login = login;
        this.password = password;
        this.cash = cash;
        this.role = role;
        this.discount = discount;
        this.email = email;
    }

    /**
     * Instantiates a new User.
     *
     * @param id         the id
     * @param login      the login
     * @param discount   the discount
     * @param orderCount the order count
     */
    public User(final int id, final String login,
                final int discount, final int orderCount) {
        this.id = id;
        this.login = login;
        this.discount = discount;
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", cash=" + cash +
                ", role=" + role +
                ", discount=" + discount +
                ", email='" + email + '\'' +
                ", orderCount=" + orderCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Double.compare(user.cash, cash) == 0 && role == user.role && discount == user.discount && orderCount == user.orderCount && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, cash, role, discount, email, orderCount);
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * Gets cash.
     *
     * @return the cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * Sets cash.
     *
     * @param cash the cash
     */
    public void setCash(final double cash) {
        this.cash = cash;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Sets discount.
     *
     * @param discount the discount
     */
    public void setDiscount(final int discount) {
        this.discount = discount;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets order count.
     *
     * @return the order count
     */
    public int getOrderCount() {
        return orderCount;
    }

    /**
     * Sets order count.
     *
     * @param orderCount the order count
     */
    public void setOrderCount(final int orderCount) {
        this.orderCount = orderCount;
    }
}
