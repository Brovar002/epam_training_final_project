package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;

import java.sql.ResultSet;
import java.util.List;

/**
 * The interface Track dao.
 * @author Goncharov Daniil
 * @see BaseDao
 * @see Track
 */
public interface TrackDao extends BaseDao<Long, Track> {
    /**
     * Find deleted tracks list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Track> findDeletedTracks() throws DaoException;

    /**
     * Find tracks by genre list.
     *
     * @param genre the genre
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Track> findTracksByGenre(String genre) throws DaoException;

    /**
     * Find track path string.
     *
     * @param trackId the track id
     * @return the string
     * @throws DaoException the dao exception
     */
    String findTrackPath(int trackId) throws DaoException;

    /**
     * Change artist.
     *
     * @param trackId   the track id
     * @param newArtist the new artist
     * @throws DaoException the dao exception
     */
    void changeArtist(int trackId, String newArtist) throws DaoException;

    /**
     * Change genre.
     *
     * @param trackId    the track id
     * @param newGenreId the new genre id
     * @throws DaoException the dao exception
     */
    void changeGenre(int trackId, int newGenreId) throws DaoException;

    /**
     * Change name.
     *
     * @param trackId the track id
     * @param newName the new name
     * @throws DaoException the dao exception
     */
    void changeName(int trackId, String newName) throws DaoException;

    /**
     * Change price.
     *
     * @param trackId  the track id
     * @param newPrice the new price
     * @throws DaoException the dao exception
     */
    void changePrice(int trackId, double newPrice) throws DaoException;

    /**
     * Find last ordered tracks list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Track> findLastOrderedTracks() throws DaoException;

    /**
     * Find track comments list.
     *
     * @param trackId the track id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Comment> findTrackComments(int trackId) throws DaoException;

    /**
     * Recover track by id.
     *
     * @param id the id
     * @throws DaoException the dao exception
     */
    void recoverTrackById(int id) throws DaoException;
}
