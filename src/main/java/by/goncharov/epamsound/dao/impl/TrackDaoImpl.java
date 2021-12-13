package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class TrackDaoImpl implements TrackDao {
    private static final String SQL_ADD_TRACK = "INSERT INTO audio_track "
            + "(`name`, `artist_name`, `genre_id`, `price`, `path`) "
            + "VALUES ( ?,?,?,?,?)";
    private static final String SQL_DELETE_TRACK = "UPDATE audio_track"
            + " SET audio_track.deleted=1 WHERE id=?";
    private static final String SQL_SELECT_ALL_TRACKS = "SELECT "
            + "audio_track.id, audio_track.name, genre.genre, "
            + "audio_track.artist_name, audio_track.price\n"
            + "FROM audio_track\n"
            + "LEFT JOIN genre ON audio_track.genre_id=genre.id\n"
            + "WHERE audio_track.deleted =0 ORDER BY audio_track.name";
    private static final String SQL_SELECT_TRACKS_BY_GENRE = "SELECT"
            + " audio_track.id, audio_track.name, genre.genre,"
            + " audio_track.artist_name, audio_track.price\n"
            + "FROM audio_track LEFT JOIN genre ON "
            + "audio_track.genre_id=genre.id WHERE genre=? "
            + "AND audio_track.deleted = 0 ORDER BY audio_track.name";
    private static final String SQL_SELECT_TRACK_BY_ID = "SELECT "
            + "audio_track.id, audio_track.name, genre.genre,"
            + " audio_track.artist_name, audio_track.price FROM audio_track\n"
            + "LEFT JOIN genre ON audio_track.genre_id=genre.id\n"
            + "WHERE audio_track.id=?";
    private static final String SQL_SELECT_TRACK_PATH = "SELECT "
            + "audio_track.path FROM audio_track WHERE id=?";
    private static final String SQL_SELECT_DELETED_TRACKS = "SELECT "
            + "audio_track.id, audio_track.name, genre.genre,"
            + " audio_track.artist_name, audio_track.price\n"
            + "FROM `audio_track` JOIN genre ON audio_track.genre_id=genre.id\n"
            + "WHERE audio_track.deleted = 1 ORDER BY audio_track.name";
    private static final String SQL_CHANGE_ARTIST = "UPDATE audio_track"
            + " SET artist_name=? WHERE id=?";
    private static final String SQL_CHANGE_GENRE = "UPDATE audio_track"
            + " SET genre_id=? WHERE id=?";
    private static final String SQL_CHANGE_NAME = "UPDATE audio_track"
            + " SET name=? WHERE id=?";
    private static final String SQL_CHANGE_PRICE = "UPDATE audio_track"
            + " SET price=? WHERE id=?";
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT audio_track"
            + ".id, audio_track.name, genre.genre,  audio_track.artist_name,"
            + " audio_track.price FROM `order` JOIN audio_track ON "
            + "`order`.audio_track_id=audio_track.id LEFT JOIN"
            + " genre ON audio_track.genre_id=genre.id WHERE"
            + " audio_track.deleted = 0 GROUP BY audio_track.name"
            + " ORDER BY `order`.date DESC LIMIT 5 ";
    private static final String SQL_SELECT_TRACK_COMMENTS = "SELECT user.login,"
            + " comment.date, comment.text FROM comment JOIN user ON user.id ="
            + " comment.user_id WHERE comment.audio_track_id=? ORDER BY"
            + " comment.date DESC;";
    private static final String SQL_RECOVER_TRACK = "UPDATE audio_track SET"
            + " audio_track.deleted=0 WHERE id=?";


    private void fillTrackData(Track track, PreparedStatement statement) throws SQLException {
        statement.setString(1, track.getName());
        statement.setString(2, track.getArtist());
        statement.setString(3, track.getGenre());
        statement.setDouble(4, track.getPrice());
        statement.setString(5, track.getPath());
    }

    @Override
    public boolean add(Track track) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_TRACK)) {
            fillTrackData(track, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception during track addition", e);
        }
    }
    @Override
    public boolean removeById(Long id) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TRACK)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public List<Track> findAll() throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TRACKS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Track> tracks;
            tracks = formTrackList(resultSet);
            return tracks;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Track entity) throws DaoException {
        return false;
    }

    public List<Track> findDeletedTracks() throws DaoException {
        List<Track> trackList;
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(SQL_SELECT_DELETED_TRACKS);
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DaoException("Exception during deleted tracks search", e);
        }
        return trackList;
    }
    @Override
    public List<Track> findTracksByGenre(final String genre)
            throws DaoException {
        List<Track> trackList;
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACKS_BY_GENRE)) {
            statement.setString(1, genre.toUpperCase());
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DaoException("Exception during tracks by genre search",
                    e);
        }
        return trackList;
    }
    @Override
    public Optional<Track> findById(Long id) throws DaoException {
        Track track;
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            track = formTrackList(resultSet).get(0);
            return track.getId() != 0 ? Optional.of(track) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public String findTrackPath(final int trackId) throws DaoException {
        String path = "";
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK_PATH)) {
            statement.setString(1, Integer.toString(trackId));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                path = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return path;
    }
    @Override
    public List<Track> formTrackList(final ResultSet set) throws DaoException {
        List<Track> trackList = new ArrayList<>();
        try {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String artist = set.getString("artist_name");
                String genre = set.getString("genre");
                double price = set.getDouble("price");
                trackList.add(new Track(id, name, artist, genre, price));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during track list formation ", e);
        }
        return trackList;
    }
    @Override
    public void changeArtist(final int trackId, final String newArtist)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_ARTIST)) {
            statement.setString(1, newArtist);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing track artist", e);
        }
    }
    @Override
    public void changeGenre(final int trackId, final int newGenreId)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_GENRE)) {
            statement.setInt(1, newGenreId);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing track genre", e);
        }
    }
    @Override
    public void changeName(final int trackId, final String newName)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_NAME)) {
            statement.setString(1, newName);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing track name", e);
        }
    }
    @Override
    public void changePrice(final int trackId, final double newPrice)
            throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_PRICE)) {
            statement.setDouble(1, newPrice);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during changing track price", e);
        }
    }
    @Override
    public List<Track> findLastOrderedTracks() throws DaoException {
        List<Track> trackList;
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LAST_ORDERS)) {
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DaoException("Exception during last ordered"
                    + " tracks search", e);
        }
        return trackList;
    }
    @Override
    public List<Comment> findTrackComments(final int trackId)
            throws DaoException {
        List<Comment> comments = new ArrayList<>();
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRACK_COMMENTS)) {
            statement.setInt(1, trackId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String login = set.getString("login");
                String dateTime = set.getString("date");
                dateTime = dateTime.substring(0, dateTime.length() - 2);
                String text = set.getString("text");
                comments.add(new Comment(login, dateTime, text));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during track comments"
                    + " search", e);
        }
        return comments;
    }
    @Override
    public void recoverTrackById(final int id) throws DaoException {
        try (Transaction connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_RECOVER_TRACK)) {
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during track recover", e);
        }
    }
}
