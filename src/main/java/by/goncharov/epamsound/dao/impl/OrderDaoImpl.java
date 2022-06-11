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

    @Override
    public void add(final Order order) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public int getOrderCount(final int userId) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int count = session.createQuery("SELECT orderCount FROM User WHERE id = :userId", Integer.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            transaction.commit();
            return count;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
    @Override
    public boolean isOrdered(final int userId, final int trackId)
            throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean isExist = getOrderCount(userId) > 0;
            transaction.commit();
            return isExist;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        Transaction transaction;
        List<Order> orders;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            orders = session.createQuery("FROM Order", Order.class).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(final int id) throws DaoException {
        Transaction transaction = null;
        Order order;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            order = session.get(Order.class, id);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(order);
    }
    public List<Track> findOrders(final int userId) throws DaoException {
        Transaction transaction;
        List<Track> tracks;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            tracks = session.createQuery("SELECT track FROM Order WHERE user_id = :userId",
                    Track.class).setParameter("userId", userId).list();
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return tracks;
    }

    @Override
    public void remove(final int id) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(Order.class, id));
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(final Order entity) throws DaoException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
