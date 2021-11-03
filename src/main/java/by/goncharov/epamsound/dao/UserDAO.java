package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.manager.ProxyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    private static final String SQL_ADD_USER = "INSERT INTO user"
            + "(login,password, email) VALUES(?,?,?)";
    private static final String SQL_SELECT_ALL_CLIENTS = "SELECT user.id,"
            + " user.login, user.discount, COUNT(user.login) as count\n"
            + " FROM audio_track_order.`order` Left join `user` ON `order`."
            + "user_id=`user`.id GROUP BY user.login ORDER BY user.login";
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password"
            + " FROM user WHERE login=?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user"
            + " WHERE id=?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM"
            + " user WHERE login=?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM"
            + " user WHERE email=?";
    private static final String SQL_CHANGE_CASH = "UPDATE user SET"
            + " cash_account=? WHERE id=?";
    public UserDAO(final ProxyConnection connection) {
        super(connection);
    }
    public void addUser(final String login, final String password,
                        final String email) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }
    private List<User> createClientList(final ResultSet set)
            throws DAOException {
        List<User> clientList = new ArrayList<>();
        try {
            while (set.next()) {
                int userId = set.getInt("id");
                String login = set.getString("login");
                int discount = set.getInt("discount");
                int orderCount = set.getInt("count");
                clientList.add(new User(userId, login, discount, orderCount));
            }
            return clientList;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    private User createUser(final ResultSet set) throws DAOException {
        try {
            if (set.next()) {
                int id = set.getInt("id");
                String login = set.getString("login");
                String password = set.getString("password");
                double cash = set.getDouble("cash_account");
                int role = set.getInt("role");
                int discount = set.getInt("discount");
                String email = set.getString("email");
                return new User(id, login, password, cash,
                        role, discount, email);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    public List<User> findClients()throws DAOException {
        List<User> userList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_CLIENTS);
            ResultSet set = statement.executeQuery();
            userList = createClientList(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return userList;
    }
    public String findPassword(final String login) throws DAOException {
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    SQL_SELECT_PASSWORD_BY_LOGIN);
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return password;
    }
    public User findUser(final String login) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }
    public User findUserByEmail(final String email) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }
    public User findUserById(final int id) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }
    public void changeCash(final int userId, final Double cash)
            throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CASH);
            statement.setDouble(1, cash);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during cash change", e);
        } finally {
            closeStatement(statement);
        }
    }
}
