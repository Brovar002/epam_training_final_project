package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.manager.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDAO extends AbstractDAO {
    private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String SQL_ADD_ORDER = "INSERT INTO"
            + " `order`(price,`date` ,user_id, audio_track_id)"
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
    public OrderDAO(final Transaction connection) {
        super(connection);
    }
    public void addOrder(final int trackId,
                         final double price, final int userId)
            throws DAOException {
        PreparedStatement statement = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.
                    ofPattern(DATE_PATTERN);
            LocalDateTime now = LocalDateTime.now();
            String dateTime = now.format(formatter);
            statement = connection.prepareStatement(SQL_ADD_ORDER);
            statement.setDouble(1, price);
            statement.setString(2, dateTime);
            statement.setInt(3, userId);
            statement.setInt(4, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during order addition", e);
        } finally {
            closeStatement(statement);
        }
    }
    public List<Track> findOrders(final int userId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_ORDERS);
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            TrackDAO trackDAO = new TrackDAO(connection);
            return trackDAO.formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }
    public boolean isOrdered(final int userId, final int trackId)
            throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_EXISTS);
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            ResultSet set = statement.executeQuery();
            return set.next() && set.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }
}
