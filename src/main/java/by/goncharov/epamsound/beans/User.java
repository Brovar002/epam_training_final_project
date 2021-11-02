package by.goncharov.epamsound.beans;

public class User extends Entity {
    private int id;
    private String login;
    private String password;
    private double cash;
    private int role;
    private int discount;
    private String email;
    private int orderCount;
    public User(final int id, final String login, final String password,
                final double cash, final int role, final int discount,
                final String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.cash = cash;
        this.role = role;
        this.discount = discount;
        this.email = email;
    }

    public User(final int id, final String login,
                final int discount, final int orderCount) {
        this.id = id;
        this.login = login;
        this.discount = discount;
        this.orderCount = orderCount;
    }
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(final double cash) {
        this.cash = cash;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(final int discount) {
        this.discount = discount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(final int orderCount) {
        this.orderCount = orderCount;
    }
}
