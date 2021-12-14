package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Class for order DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see OrderDao
 * @see Order
 * @see Track
 * @see BaseDao
 */
public class OrderDaoImpl implements OrderDao {
    private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String SQL_ADD_ORDER = "INSERT INTO"
            + " `order`(audio_track_id, user_id, price,`date`)"
            + " VALUES(?,?,?,?);";
    private static final String SQL_SELECT_USER_ORDERS = "SELECT"
            + " audio_track.id, audio_track.name, genre.genre, "
            + "audio_track.artist_name, audio_track.price FROM `order`\n"
            + "JOIN audio_track ON audio_track.id=`order`.audio_track_id\n"
            + "LEFT JOIN genre ON audio_track.genre_id=genre.id\n"
            + "WHERE `order`.user_id=?\n"
            + "ORDER BY audio_track.name";
    private static final String SQL_SELECT_EXISTS = "SELECT EXISTS(SELECT"
            + " id FROM `order` WHERE user_id = ? AND audio_track_id=?)";

    private void fillOrderData(final Order order,
                               final PreparedStatement statement)
            throws SQLException {
        statement.setInt(1, order.getTrack());
        statement.setInt(2, order.getCustomer().getId());
        statement.setDouble(3, order.getPrice());
        statement.setDate(4, Date.valueOf(order.getDate()));
    }

    @Override
    public boolean add(final Order order) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_ADD_ORDER)) {
            fillOrderData(order, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception during track addition", e);
        }
    }
    @Override
    public List<Track> findOrders(final int userId) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SELECT_USER_ORDERS)) {
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            TrackDaoImpl trackDAOImpl = new TrackDaoImpl();
            return trackDAOImpl.formTrackList(set);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean isOrdered(final int userId, final int trackId)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance()
                .getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SQL_SELECT_EXISTS)) {
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            ResultSet set = statement.executeQuery();
            return set.next() && set.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findById(final Long id) throws DaoException {
        return Optional.empty();
    }


    @Override
    public boolean removeById(final Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(final Order entity) throws DaoException {
        return false;
    }
}
