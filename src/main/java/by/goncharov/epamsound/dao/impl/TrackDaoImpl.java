package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;
import by.goncharov.epamsound.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@SuppressWarnings("Duplicates")
public class TrackDaoImpl implements TrackDao {
    @Override
    public void add(final Track track) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(track);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while adding track", e);
        }
    }
    @Override
    public void remove(final int id) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Track track = session.get(Track.class, id);
            track.setDeleted(true);
            session.update(track);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while removing track", e);
        }
    }
    @Override
    public List<Track> findAll() throws DaoException {
        Transaction transaction;
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tracks = session.createQuery("FROM Track WHERE deleted = 0",
                    Track.class).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding all tracks", e);
        }
        return tracks;
    }

    @Override
    public void update(final Track entity) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while updating track", e);
        }
    }

    public List<Track> findDeletedTracks() throws DaoException {
        Transaction transaction;
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tracks = session.createQuery("FROM Track WHERE deleted = 1",
                    Track.class).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding all deleted tracks", e);
        }
        return tracks;
    }
    @Override
    public List<Track> findTracksByGenre(final String genre)
            throws DaoException {
        Transaction transaction;
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tracks = session.createQuery("FROM Track WHERE genre = :genre AND deleted = 0",
                    Track.class).setParameter("genre", genre).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding tracks by genre", e);
        }
        return tracks;
    }
    @Override
    public Optional<Track> findById(final int id) throws DaoException {
        Transaction transaction;
        Track track;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            track = session.get(Track.class, id);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding track by id", e);
        }
        return Optional.ofNullable(track);
    }
    @Override
    public String findTrackPath(final int trackId) throws DaoException {
        Transaction transaction;
        String path;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            path = session.createQuery("SELECT path FROM Track WHERE id = ?",
                    String.class).setParameter(0, trackId).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding track path", e);
        }
        return path;
    }
    @Override
    public void changeArtist(final int trackId, final String newArtist)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("UPDATE Track SET artist_name = ? WHERE id = ?")
                    .setParameter(0, newArtist).setParameter(1, trackId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while changing artist", e);
        }
    }
    @Override
    public void changeGenre(final int trackId, final int newGenreId)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("UPDATE Track SET genre_id = ? WHERE id = ?")
                    .setParameter(0, newGenreId).setParameter(1, trackId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while changing genre", e);
        }
    }
    @Override
    public void changeName(final int trackId, final String newName)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("UPDATE Track SET name = ? WHERE id = ?")
                    .setParameter(0, newName).setParameter(1, trackId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while changing name", e);
        }
    }
    @Override
    public void changePrice(final int trackId, final double newPrice)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("UPDATE Track SET price = ? WHERE id = ?")
                    .setParameter(0, newPrice).setParameter(1, trackId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while changing price", e);
        }
    }
    @Override
    public List<Track> findLastOrderedTracks() throws DaoException {
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            tracks = session.createQuery("from Track ORDER BY id DESC",
                    Track.class).setMaxResults(10).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding last ordered tracks", e);
        }
        return tracks;
    }
    public List<Track> formTrackList(final ResultSet set) throws DaoException {
        List<Track> trackList = new ArrayList<>();
        try {
            while (set.next()) {
                String name = set.getString("name");
                String artist = set.getString("artist_name");
                String genre = set.getString("genre");
                double price = set.getDouble("price");
                trackList.add(new Track(name, artist, genre, price));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during track list formation ", e);
        }
        return trackList;
    }
    @Override
    public List<Comment> findTrackComments(final int trackId)
            throws DaoException {
        Transaction transaction;
        List<Comment> comments;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            comments = session.createQuery("FROM Comment WHERE audio_track_id = :trackId",
                    Comment.class).setParameter("trackId", trackId).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while finding track comments", e);
        }
        return comments;
    }
    @Override
    public void recoverTrackById(final int id) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("UPDATE Track SET deleted = 0 WHERE id = :id")
                    .setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException("Error while recovering track by id", e);
        }
    }
}
