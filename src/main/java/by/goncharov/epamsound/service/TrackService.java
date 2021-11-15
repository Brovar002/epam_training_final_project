package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.GenreDAO;
import by.goncharov.epamsound.dao.TrackDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.manager.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TrackService implements Messenger {
    private final String SUCCESS = "Success";
    public String addTrack(final String name, final String artist,
                           final String price, final String genre,
                           final String path) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackValid(name, artist, price, genre)) {
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            GenreService GenreService = new GenreService();
            try {
                int genreId = GenreService.findGenreId(genre);
                double doublePrice = Double.valueOf(price);
                trackDAO.addTrack(name, artist, doublePrice, genreId, path);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Exception during track addition",
                        e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager.
                    ADD_TRACK_DATA_ERROR);
        }
    }


    public void deleteTrackById(final int id) throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.deleteTrackById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during track removal", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public List<Track> findAllTracks() throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception during all tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public List<Track> findSuitableTracks(final String str)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            List<Track> allTracks = trackDAO.findAll();
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
        } catch (DAOException e) {
            throw new ServiceException("Exception during tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public List<Track> findDeletedTracks() throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findDeletedTracks();
        } catch (DAOException e) {
            throw new ServiceException("Exception during deleted tracks search",
                    e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public Track findTrackById(final int id) throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during track by id search",
                    e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public List<Track> findTracksByGenre(final String genre)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTracksByGenre(genre);
        } catch (DAOException e) {
            throw new ServiceException("Exception during track list by"
                    + " genre search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public String findTrackPath(final int trackId)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackPath(trackId);
        } catch (DAOException e) {
            throw new ServiceException("Exception during track"
                    + " path search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public String changeArtist(final int trackId, final String newArtist)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackArtistValid(newArtist)) {
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            try {
                trackDAO.changeArtist(trackId, newArtist);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Error during changing"
                        + " track artist", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_ARTIST_ERROR);
        }
    }

    public String changeGenre(final int trackId, final String newGenre)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isGenreValid(newGenre)) {
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            GenreDAO genreDAO = new GenreDAO(connection);
            try {
                int genreId = genreDAO.findGenreId(newGenre);
                trackDAO.changeGenre(trackId, genreId);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Error during changing"
                        + " track genre", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_GENRE_ERROR);
        }
    }

    public String changeName(final int trackId, final String newName)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackNameValid(newName)) {
            Transaction connection = ConnectionPool.getInstance()
                    .getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            try {
                trackDAO.changeName(trackId, newName);
                return SUCCESS;
            } catch (DAOException e) {
                throw new ServiceException("Error during changing track"
                        + " name", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_NAME_ERROR);
        }
    }
    public String changePrice(final int trackId, final double prevPrice,
                              final String newPrice) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isPriceValid(newPrice)) {
            if (!Double.valueOf(newPrice).equals(prevPrice)) {
                Transaction connection = ConnectionPool.getInstance()
                        .getConnection();
                TrackDAO trackDAO = new TrackDAO(connection);
                try {
                    trackDAO.changePrice(trackId, Double.valueOf(newPrice));
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new ServiceException("Error during changing track"
                            + " price", e);
                } finally {
                    trackDAO.closeConnection(connection);
                }
            } else {
                return SUCCESS;
            }
        } else {
            return messageManager.getProperty(MessageManager
                    .CHANGE_TRACK_PRICE_ERROR);
        }
    }
    public List<Comment> findTrackComments(final int trackId)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackComments(trackId);
        } catch (DAOException e) {
            throw new ServiceException("Exception during all comments"
                    + " by track id search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
    public List<Track> lastTracks() throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findLastOrderedTracks();
        } catch (DAOException e) {
            throw new ServiceException("Exception during last ordered"
                    + " tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public void recoverTrackById(final int id) throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.recoverTrackById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during track recover", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
}
