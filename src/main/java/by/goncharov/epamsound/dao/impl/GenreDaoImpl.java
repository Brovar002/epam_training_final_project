package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Class for genre DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see GenreDao
 * @see BaseDao
 * @see Genre
 */
@Repository
public class GenreDaoImpl implements GenreDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public int findGenreId(final Genre genre) throws DaoException {
        int genreId;
        try (Session session = sessionFactory.getCurrentSession()) {
            genreId = session.createQuery("SELECT id FROM Genre WHERE genre = :genre",
                    Integer.class).setParameter("genre", genre).getSingleResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return genreId;
    }

    @Override
    public List<String> findGenres() throws DaoException {
        List<String> genres;
        try (Session session = sessionFactory.getCurrentSession()) {
            genres = session.createQuery("SELECT genre FROM Genre").list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return genres;
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        List<Genre> genreList;
        try (Session session = sessionFactory.getCurrentSession()) {
            genreList = session.createQuery("FROM Genre").list();
            return genreList;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Genre> findById(final int id) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            Genre genre = session.get(Genre.class, id);
            return Optional.of(genre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void add(final Genre genre) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.save(genre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(final int id) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            Genre genre = session.get(Genre.class, id);
            session.delete(genre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(final Genre genre) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.update(genre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
