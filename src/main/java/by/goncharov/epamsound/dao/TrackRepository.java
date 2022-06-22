package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Track dao.
 * @author Goncharov Daniil
 * @see Track
 */
public interface TrackRepository extends JpaRepository<Track, Integer> {

    List<Track> findTracksByGenre(int genreId);

    List<Track> findTracksByName(String name);

    //List<Track> findTracksByUser(User user);

}
