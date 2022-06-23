package by.goncharov.epamsound.service.impl;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.TrackRepository;
import by.goncharov.epamsound.service.ServiceException;
import by.goncharov.epamsound.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Track service.
 * @author Goncharov Daniil
 * @see TrackRepository
 */
@Service
public class TrackServiceImpl implements TrackService {
    private static final String SUCCESS = "Success";
    /**
     * The Track dao.
     */
    @Autowired
    private TrackRepository trackRepository;

    /**
     * Add track string.
     * @param track the track
     * @return the string
     * @throws ServiceException the service exception
     */
    @Override
    public String save(final Track track) throws ServiceException {
        trackRepository.save(track);
        return SUCCESS;
    }

    /**
     * Delete track by id.
     *
     * @param track the track
     * @throws ServiceException the service exception
     */
    @Override
    public void delete(final Track track) throws ServiceException {
        trackRepository.delete(track);
    }

    /**
     * Find all tracks list.
     *
     * @return the list
     */
    @Override
    public List<Track> findAllTracks() {
        return trackRepository.findAll();
    }

    /**
     * Find track by id track.
     *
     * @param track the track
     * @return the track
     * @throws ServiceException the service exception
     */
    @Override
    public Track findTrackById(final Track track) throws ServiceException {
        Optional<Track> tracks = trackRepository.findById(track.getId());
        if (tracks.isPresent()) {
            return tracks.get();
        } else {
            throw new ServiceException("Track not found");
        }
    }

    /**
     * Find my tracks list.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    //public List<Track> findMyTracks(final User user) {
     //   return trackRepository.findTracksByUser(user);
    //}
    /**
     * Find tracks by genre list.
     *
     * @param genre the genre
     * @return the list
     */
    @Override
    public List<Track> findTracksByGenre(final Genre genre) {
        return trackRepository.findTracksByGenre(genre.getId());
    }
    @Override
    public List<Track> findTracksByName(final String name) {
        return trackRepository.findTracksByName(name);
    }
}
