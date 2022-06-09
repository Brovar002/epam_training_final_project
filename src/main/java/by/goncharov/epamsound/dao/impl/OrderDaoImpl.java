package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;
import by.goncharov.epamsound.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

/**
 * Class for order DAO.
 * @author Goncharov Daniil
 * @version 1.0
 * @see OrderDao
 * @see Order
 * @see Track
 * @see BaseDao
 */
public class OrderDaoImpl implements OrderDao {
    private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void add(final Order order) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }
    @Override
    public List<Track> findOrders(final int userId) throws DaoException {
        Transaction transaction = null;
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tracks = session.createQuery("FROM Track WHERE id IN "
                    + "(SELECT audio_track_id FROM Order WHERE user_id = :userId)",
                    Track.class).setParameter("userId", userId).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return tracks;
    }
    @Override
    public boolean isOrdered(final int userId, final int trackId)
            throws DaoException {
        Transaction transaction = null;
        boolean isOrdered;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            isOrdered = session.createQuery("SELECT EXISTS(SELECT"
                    + " id FROM `order` WHERE user_id = :userId AND audio_track_id=:trackId)",
                    Boolean.class).setParameter("userId", userId)
                    .setParameter("trackId", trackId).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return isOrdered;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        Transaction transaction = null;
        List<Order> orders;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            orders = session.createQuery("FROM Order", Order.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(final Long id) throws DaoException {
        Transaction transaction = null;
        Order order;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            order = session.get(Order.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
        return Optional.ofNullable(order);
    }


    @Override
    public void remove(final Long id) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(Order.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void update(final Order entity) throws DaoException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }
}
