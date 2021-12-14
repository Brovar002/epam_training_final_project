package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.ConnectionPool;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.Transaction;
import by.goncharov.epamsound.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for user DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see UserDao
 * @see User
 * @see Transaction
 * @see Comment
 */
@SuppressWarnings("Duplicates")
public class UserDaoImpl implements UserDao {
    private static final String SQL_ADD_USER = "INSERT INTO user"
            + "(login,password, email) VALUES(?,?,?)";
    private static final String SQL_SELECT_ALL_CLIENTS = "SELECT user.id,"
            + " user.login, user.discount, COUNT(user.login) as count\n"
            + " FROM track_db.`order` Left join `user` ON `order`."
            + "user_id=`user`.id GROUP BY user.id ORDER BY user.login";
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
    private static final String SQL_ADD_COMMENT = "INSERT INTO comment"
            + "(user_id,audio_track_id,date,text) VALUES(?,?,?,?)";
    private static final String SQL_CHANGE_EMAIL = "UPDATE user SET email=?"
            + " WHERE id=?";
    private static final String SQL_CHANGE_LOGIN = "UPDATE user SET login=?"
            + " WHERE id=?";
    private static final String SQL_CHANGE_PASS = "UPDATE user SET"
            + " password=? WHERE id=?";
    private static final String SQL_SELECT_CASH = "SELECT cash_account"
            + " FROM user WHERE id=?";
    private static final String SQL_SET_BONUS = "UPDATE user SET"
            + " discount=? WHERE id=?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login = ?,"
            + " password = ?, email = ?, cash_account = ?,"
            + " role = ? WHERE id = ?";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM users"
            + " WHERE id = ?";



    private void fillUserData(final User user,
                              final PreparedStatement statement)
            throws SQLException {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
    }

    private void fillUpdatingUserData(final User user,
                                      final PreparedStatement statement)
            throws SQLException {
        fillUserData(user, statement);
        statement.setDouble(4, user.getCash());
        statement.setInt(5, user.getRole());

    }

    private User findUserByParameter(final String parameter,
                                     final String findSQLScript)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(findSQLScript)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            User user;
            user = createUser(resultSet);
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(final User user) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_ADD_USER)) {
            fillUserData(user, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(final Long id) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_REMOVE_USER_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<User> createClientList(final ResultSet set)
            throws DaoException {
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
            throw new DaoException(e);
        }
    }

    private User createUser(final ResultSet set) throws DaoException {
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
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(final User user) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_UPDATE_USER)) {
            statement.setLong(6, user.getId());
            fillUpdatingUserData(user, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(SQL_SELECT_ALL_CLIENTS);
            List<User> users;
            users = createClientList(resultSet);
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public String findPassword(final String login) throws DaoException {
        String password = null;
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                password = set.getString(1);
            }
            return password;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User findByEmail(final String email) throws DaoException {
        return findUserByParameter(email, SQL_SELECT_USER_BY_EMAIL);
    }

    @Override
    public User findByLogin(final String login) throws DaoException {
        return findUserByParameter(login, SQL_SELECT_USER_BY_LOGIN);
    }

    @Override
    public Optional<User> findById(final Long id) throws DaoException {
        User user;
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            user = createUser(resultSet);
            return user.getId() != 0 ? Optional.of(user) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void changeCash(final int userId, final Double cash)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_CHANGE_CASH)) {
            statement.setDouble(1, cash);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during cash change", e);
        }
    }
    public void addComment(final int userId, final String text,
                           final int trackId) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_ADD_COMMENT)) {
            Comment comment = new Comment(userId, trackId, text);
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            statement.setString(3, comment.getDateTime());
            statement.setString(4, text);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during comment addition", e);
        }
    }
    public void changeEmail(final int userId, final String newEmail)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_CHANGE_EMAIL)) {
            statement.setString(1, newEmail);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing email", e);
        }
    }
    public void changeLogin(final int userId, final String newLogin)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_CHANGE_LOGIN)) {
            statement.setString(1, newLogin);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing login", e);
        }
    }
    @Override
    public void changePassword(final int userId, final String newPass)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_CHANGE_PASS)) {
            statement.setString(1, newPass);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing password", e);
        }
    }

    /**
     * Find cash double.
     *
     * @param userId the user id
     * @return the double
     * @throws DaoException the dao exception
     */
    public double findCash(final int userId) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SELECT_CASH)) {
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt("cash_account");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during cash search", e);
        }
    }
    @Override
    public void setBonus(final int userId, final int bonus)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SET_BONUS)) {
            statement.setInt(1, bonus);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during bonus setting", e);
        }
    }
}
