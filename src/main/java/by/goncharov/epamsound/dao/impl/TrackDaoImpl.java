package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Class for track DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see TrackDao
 * @see Comment
 * @see Track
 * @see BaseDao
 */
@Repository
public class TrackDaoImpl implements TrackDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void add(final Track track) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.save(track);
        } catch (Exception e) {
            throw new DaoException("Error while adding track", e);
        }
    }
    @Override
    public void remove(final int id) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            Track track = session.get(Track.class, id);
            track.setDeleted(true);
            session.update(track);
        } catch (Exception e) {
            throw new DaoException("Error while removing track", e);
        }
    }
    @Override
    public List<Track> findAll() throws DaoException {
        List<Track> tracks;
        try (Session session = sessionFactory.getCurrentSession()) {
            tracks = session.createQuery("FROM Track WHERE deleted = 0",
                    Track.class).list();
        } catch (Exception e) {
            throw new DaoException("Error while finding all tracks", e);
        }
        return tracks;
    }

    @Override
    public void update(final Track entity) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.update(entity);
        } catch (Exception e) {
            throw new DaoException("Error while updating track", e);
        }
    }

    public List<Track> findDeletedTracks() throws DaoException {
        List<Track> tracks;
        try (Session session = sessionFactory.getCurrentSession()) {
            tracks = session.createQuery("FROM Track WHERE deleted = 1",
                    Track.class).list();
        } catch (Exception e) {
            throw new DaoException("Error while finding all deleted tracks", e);
        }
        return tracks;
    }
    @Override
    public List<Track> findTracksByGenre(final int genreId)
            throws DaoException {
        List<Track> tracks;
        try (Session session = sessionFactory.getCurrentSession()) {
            tracks = session.createQuery("FROM Track WHERE genreId = :genreId AND deleted = 0",
                    Track.class).setParameter("genreId", genreId).list();
        } catch (Exception e) {
            throw new DaoException("Error while finding tracks by genre", e);
        }
        return tracks;
    }
    @Override
    public Optional<Track> findById(final int id) throws DaoException {
        Track track;
        try (Session session = sessionFactory.getCurrentSession()) {
            track = session.get(Track.class, id);
        } catch (Exception e) {
            throw new DaoException("Error while finding track by id", e);
        }
        return Optional.ofNullable(track);
    }
    @Override
    public String findTrackPath(final int trackId) throws DaoException {
        String path;
        try (Session session = sessionFactory.getCurrentSession()) {
            path = session.createQuery("SELECT path FROM Track WHERE id = :trackId",
                    String.class).setParameter("trackId", trackId).uniqueResult();
        } catch (Exception e) {
            throw new DaoException("Error while finding track path", e);
        }
        return path;
    }
    @Override
    public void changeArtist(final int trackId, final String newArtist)
            throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.createQuery("UPDATE Track SET artist_name = ? WHERE id = ?")
                    .setParameter(0, newArtist).setParameter(1, trackId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Error while changing artist", e);
        }
    }
    @Override
    public void changeGenre(final int trackId, final int newGenreId)
            throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.createQuery("UPDATE Track SET genre_id = ? WHERE id = ?")
                    .setParameter(0, newGenreId).setParameter(1, trackId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Error while changing genre", e);
        }
    }
    @Override
    public void changeName(final int trackId, final String newName)
            throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.createQuery("UPDATE Track SET name = ? WHERE id = ?")
                    .setParameter(0, newName).setParameter(1, trackId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Error while changing name", e);
        }
    }
    @Override
    public void changePrice(final int trackId, final double newPrice)
            throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.createQuery("UPDATE Track SET price = ? WHERE id = ?")
                    .setParameter(0, newPrice).setParameter(1, trackId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Error while changing price", e);
        }
    }
    @Override
    public List<Track> findLastOrderedTracks() throws DaoException {
        List<Track> tracks;
        try (Session session = sessionFactory.getCurrentSession()) {
            tracks = session.createQuery("from Track ORDER BY id DESC",
                    Track.class).setMaxResults(10).list();
        } catch (Exception e) {
            throw new DaoException("Error while finding last ordered tracks", e);
        }
        return tracks;
    }
    @Override
    public List<Comment> findTrackComments(final int trackId)
            throws DaoException {
        List<Comment> comments;
        try (Session session = sessionFactory.getCurrentSession()) {
            comments = session.createQuery("FROM Comment WHERE audio_track_id = :trackId",
                    Comment.class).setParameter("trackId", trackId).list();
        } catch (Exception e) {
            throw new DaoException("Error while finding track comments", e);
        }
        return comments;
    }
    @Override
    public void recoverTrackById(final int id) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.createQuery("UPDATE Track SET deleted = 0 WHERE id = :id")
                    .setParameter("id", id).executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Error while recovering track by id", e);
        }
    }
}
