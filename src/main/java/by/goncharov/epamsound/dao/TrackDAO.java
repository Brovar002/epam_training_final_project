package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.manager.ProxyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO extends AbstractDAO {
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
    public TrackDAO(final ProxyConnection connection) {
        super(connection);
    }
    public void addTrack(final String name, final String artist,
                         final double price, final int genreId,
                         final String path) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_TRACK);
            statement.setString(1, name);
            statement.setString(2, artist);
            statement.setInt(3, genreId);
            statement.setDouble(4, price);
            statement.setString(5, path);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during track addition", e);
        } finally {
            closeStatement(statement);
        }
    }
    public void deleteTrackById(final int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_TRACK);
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during track removal", e);
        } finally {
            closeStatement(statement);
        }
    }
    public List<Track> findAll() throws DAOException {
        List<Track> trackList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_TRACKS);
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during all tracks search", e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }
    public List<Track> findDeletedTracks() throws DAOException {
        List<Track> trackList;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_DELETED_TRACKS);
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during deleted tracks search", e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }
    public List<Track> findTracksByGenre(final String genre)
            throws DAOException {
        List<Track> trackList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACKS_BY_GENRE);
            statement.setString(1, genre.toUpperCase());
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during tracks by genre search", e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }
    public Track findTrackById(final int id) throws DAOException {
        Track track;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            track = formTrackList(set).get(0);
        } catch (SQLException e) {
            throw new DAOException("Exception during track by id search", e);
        } finally {
            closeStatement(statement);
        }
        return track;
    }
    public String findTrackPath(final int trackId) throws DAOException {
        String path = "";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_PATH);
            statement.setString(1, Integer.toString(trackId));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                path = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return path;
    }
    List<Track> formTrackList(final ResultSet set) throws DAOException {
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
            throw new DAOException("Exception during track list formation ", e);
        }
        return trackList;
    }
}
