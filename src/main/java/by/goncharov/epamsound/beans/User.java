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
}
