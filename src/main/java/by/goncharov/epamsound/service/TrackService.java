package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.beans.Track;

import java.util.List;

public interface TrackService {
    String save(final Track track) throws ServiceException;
    void delete(final Track track) throws ServiceException;
    List<Track> findAllTracks();
    Track findTrackById(final Track track) throws ServiceException;
    List<Track> findTracksByGenre(final Genre genre);
    List<Track> findTracksByName(final String name);
}
