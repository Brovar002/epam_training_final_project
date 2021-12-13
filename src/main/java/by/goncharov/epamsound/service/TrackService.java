package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.GenreDaoImpl;
import by.goncharov.epamsound.dao.impl.TrackDaoImpl;
import by.goncharov.epamsound.manager.MessageManager;
import by.goncharov.epamsound.manager.Messenger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackService implements Messenger {
    private final String SUCCESS = "Success";
    TrackDaoImpl trackDao = new TrackDaoImpl();
    public String addTrack(final String name, final String artist,
                           final String price, final String genre,
                           final String path) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackValid(name, artist, price, genre)) {
            GenreService GenreService = new GenreService();
            try {
                int genreId = GenreService.findGenreId(genre);
                double doublePrice = Double.parseDouble(price);
                Track track = new Track();
                track.setName(name);
                track.setArtist(artist);
                track.setPrice(doublePrice);
                track.setGenre(String.valueOf(genreId));
                track.setPath(path);
                trackDao.add(track);
                return SUCCESS;
            } catch (DaoException e) {
                throw new ServiceException("Exception during track addition",
                        e);
            }
        } else {
            return messageManager.getProperty(MessageManager.
                    ADD_TRACK_DATA_ERROR);
        }
    }
    public void deleteTrackById(final int id) throws ServiceException {
        try {
            trackDao.removeById((long) id);
        } catch (DaoException e) {
            throw new ServiceException("Exception during track removal", e);
        }
    }
    public List<Track> findAllTracks() throws ServiceException {
        try {
            return trackDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception during all tracks search", e);
        }
    }
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
    public List<Track> findDeletedTracks() throws ServiceException {
        try {
            return trackDao.findDeletedTracks();
        } catch (DaoException e) {
            throw new ServiceException("Exception during deleted tracks search",
                    e);
        }
    }
    public Track findTrackById(final int id) throws ServiceException {
        try {
            Optional<Track> track = trackDao.findById(Long.valueOf(id));
            if (track.isPresent()) {
                return track.get();
            }
            throw new ServiceException();
        } catch (DaoException e) {
            throw new ServiceException("Exception during track by id search",
                    e);
        }
    }
    public List<Track> findTracksByGenre(final String genre)
            throws ServiceException {
        try {
            return trackDao.findTracksByGenre(genre);
        } catch (DaoException e) {
            throw new ServiceException("Exception during track list by"
                    + " genre search", e);
        }
    }
    public String findTrackPath(final int trackId)
            throws ServiceException {
        try {
            return trackDao.findTrackPath(trackId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during track"
                    + " path search", e);
        }
    }
    public String changeArtist(final int trackId, final String newArtist)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackArtistValid(newArtist)) {
            try {
                trackDao.changeArtist(trackId, newArtist);
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
    public String changeGenre(final int trackId, final String newGenre)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isGenreValid(newGenre)) {
            GenreDaoImpl genreDao = new GenreDaoImpl();
            try {
                int genreId = genreDao.findGenreId(newGenre);
                trackDao.changeGenre(trackId, genreId);
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
    public String changeName(final int trackId, final String newName)
            throws ServiceException {
        Validator validator = new Validator();
        if (validator.isTrackNameValid(newName)) {
            try {
                trackDao.changeName(trackId, newName);
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
    public String changePrice(final int trackId, final double prevPrice,
                              final String newPrice) throws ServiceException {
        Validator validator = new Validator();
        if (validator.isPriceValid(newPrice)) {
            if (!Double.valueOf(newPrice).equals(prevPrice)) {
                try {
                    trackDao.changePrice(trackId, Double.valueOf(newPrice));
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
    public List<Comment> findTrackComments(final int trackId)
            throws ServiceException {
        try {
            return trackDao.findTrackComments(trackId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during all comments"
                    + " by track id search", e);
        }
    }
    public List<Track> lastTracks() throws ServiceException {
        try {
            return trackDao.findLastOrderedTracks();
        } catch (DaoException e) {
            throw new ServiceException("Exception during last ordered"
                    + " tracks search", e);
        }
    }
    public void recoverTrackById(final int id) throws ServiceException {
        try {
            trackDao.recoverTrackById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception during track recover", e);
        }
    }
}
