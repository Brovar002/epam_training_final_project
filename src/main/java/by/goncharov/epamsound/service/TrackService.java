package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.GenreDaoImpl;
import by.goncharov.epamsound.dao.impl.TrackDaoImpl;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Track service.
 * @author Goncharov Daniil
 * @see TrackDaoImpl
 */
@Service
public class TrackService implements Messenger {
    private final String SUCCESS = "Success";
    /**
     * The Track dao.
     */
    @Autowired
    private TrackDaoImpl trackDao;

    /**
     * Add track string.
     * @param track the track
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String addTrack(final Track track) throws ServiceException {
            try {
                trackDao.add(track);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Exception during track addition",
                        e);
        }
    }

    /**
     * Delete track by id.
     *
     * @param track the track
     * @throws ServiceException the service exception
     */
    @Transactional
    public void deleteTrackById(final Track track) throws ServiceException {
        try {
            trackDao.remove(track.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during track removal", e);
        }
    }

    /**
     * Find all tracks list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> findAllTracks() throws ServiceException {
        try {
            return trackDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception during all tracks search", e);
        }
    }

    /**
     * Find suitable tracks list.
     *
     * @param str the str
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> findSuitableTracks(final String str)
            throws ServiceException {
        try {
            List<Track> allTracks = trackDao.findAll();
            List<Track> res = new ArrayList<>();
            for (Track temp : allTracks) {
                if (temp.getName().toLowerCase().contains(str.toLowerCase())) {
                    res.add(temp);
                } else if (temp.getArtist().toLowerCase().contains(
                        str.toLowerCase())) {
                    res.add(temp);
                }
            }
            return res;
        } catch (DaoException e) {
            throw new ServiceException("Exception during tracks search", e);
        }
    }

    /**
     * Find deleted tracks list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> findDeletedTracks() throws ServiceException {
        try {
            return trackDao.findDeletedTracks();
        } catch (DaoException e) {
            throw new ServiceException("Exception during deleted tracks search",
                    e);
        }
    }

    /**
     * Find track by id track.
     *
     * @param track the track
     * @return the track
     * @throws ServiceException the service exception
     */
    @Transactional
    public Track findTrackById(final Track track) throws ServiceException {
        try {
            Optional<Track> tracks = trackDao.findById(track.getId());
            if (tracks.isPresent()) {
                return tracks.get();
            }
            throw new ServiceException();
        } catch (DaoException e) {
            throw new ServiceException("Exception during track by id search",
                    e);
        }
    }

    /**
     * Find tracks by genre list.
     *
     * @param genre the genre
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> findTracksByGenre(final Genre genre)
            throws ServiceException {
        try {
            return trackDao.findTracksByGenre(genre.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during track list by"
                    + " genre search", e);
        }
    }

    /**
     * Find track path string.
     *
     * @param track the track
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String findTrackPath(final Track track)
            throws ServiceException {
        try {
            return trackDao.findTrackPath(track.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during track"
                    + " path search", e);
        }
    }

    /**
     * Change artist string.
     *
     * @param track   the track
     * @param newArtist the new artist
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changeArtist(final Track track, final String newArtist)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackArtistValid(newArtist)) {
            try {
                trackDao.changeArtist(track.getId(), newArtist);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Error during changing"
                        + " track artist", e);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_ARTIST_ERROR);
        }
    }

    /**
     * Change genre string.
     *
     * @param newGenre the new genre
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changeGenre(final Track track, final Genre newGenre)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isGenreValid(newGenre)) {
            GenreDaoImpl genreDao = new GenreDaoImpl();
            try {
                int genreId = genreDao.findGenreId(newGenre);
                trackDao.changeGenre(track.getId(), genreId);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Error during changing"
                        + " track genre", e);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_GENRE_ERROR);
        }
    }

    /**
     * Change name string.
     *
     * @param track the track id
     * @param newName the new name
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changeName(final Track track, final String newName)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackNameValid(newName)) {
            try {
                trackDao.changeName(track.getId(), newName);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Error during changing track"
                        + " name", e);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_NAME_ERROR);
        }
    }

    /**
     * Change price string.
     *
     * @param track the track
     * @param newPrice  the new price
     * @return the string
     * @throws ServiceException the service exception
     */
    @Transactional
    public String changePrice(final Track track, final String newPrice) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isPriceValid(newPrice)) {
            if (!Double.valueOf(newPrice).equals(track.getPrice())) {
                try {
                    trackDao.changePrice(track.getId(), Double.valueOf(newPrice));
                    return SUCCESS;
                } catch (DaoException e) {
                    throw new ServiceException("Error during changing track"
                            + " price", e);
                }
            } else {
                return SUCCESS;
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_PRICE_ERROR);
        }
    }

    /**
     * Find track comments list.
     *
     * @param track the track
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Comment> findTrackComments(final Track track)
            throws ServiceException {
        try {
            return trackDao.findTrackComments(track.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during all comments"
                    + " by track id search", e);
        }
    }

    /**
     * Last tracks list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> lastTracks() throws ServiceException {
        try {
            return trackDao.findLastOrderedTracks();
        } catch (DaoException e) {
            throw new ServiceException("Exception during last ordered"
                    + " tracks search", e);
        }
    }

    /**
     * Recover track by id.
     *
     * @throws ServiceException the service exception
     */
    @Transactional
    public void recoverTrackById(final Track track) throws ServiceException {
        try {
            trackDao.recoverTrackById(track.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during track recover", e);
        }
    }
}
