package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.TrackDAO;
import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import by.goncharov.epamsound.manager.ProxyConnection;
import java.util.ArrayList;
import java.util.List;

public class TrackService implements Messenger {
    private final String SUCCESS = "Success";
    public String addTrack(final String name, final String artist,
                           final String price, final String genre,
                           final String path) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackValid(name, artist, price, genre)) {
            ProxyConnection connection = ConnectionPool.getInstance().
                    getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
        ProxyConnection connection = ConnectionPool.getInstance().
                getConnection();
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
}
