package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Genre;
import by.goncharov.epamsound.dao.*;
import by.goncharov.epamsound.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
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
@SuppressWarnings("Duplicates")
public class GenreDaoImpl implements GenreDao {
    @Override
    public int findGenreId(final String genre) throws DaoException {
        Transaction transaction = null;
        int genreId;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            genreId = session.createQuery("SELECT id FROM Genre WHERE genre = :genre",
                    Integer.class).setParameter("genre", genre).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return genreId;
    }

    @Override
    public List<String> findGenres() throws DaoException {
        Transaction transaction = null;
        List<String> genres;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            genres = session.createQuery("SELECT genre FROM Genre").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return genres;
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        Transaction transaction = null;
        List<Genre> genreList;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            genreList = session.createQuery("FROM Genre").list();
            transaction.commit();
            return genreList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Genre> findById(final int id) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            Genre genre = session.get(Genre.class, id);
            transaction.commit();
            return Optional.of(genre);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void add(final Genre genre) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.save(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(final int id) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            Genre genre = session.get(Genre.class, id);
            session.delete(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void update(final Genre genre) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.update(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }
}
