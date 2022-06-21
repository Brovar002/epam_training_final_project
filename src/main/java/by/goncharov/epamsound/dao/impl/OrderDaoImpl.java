package by.goncharov.epamsound.dao.impl;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(final Order order) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.save(order);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public int getOrderCount(final int userId) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            int count = session.createQuery("SELECT orderCount FROM User WHERE id = :userId", Integer.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return count;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
    @Override
    public boolean isOrdered(final int userId, final int trackId)
            throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            boolean isExist = getOrderCount(userId) > 0;
            return isExist;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders;
        try (Session session = sessionFactory.getCurrentSession()) {
            orders = session.createQuery("FROM Order", Order.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(final int id) throws DaoException {
        Order order;
        try (Session session = sessionFactory.getCurrentSession()) {
            order = session.get(Order.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(order);
    }
    public List<Track> findOrders(final int userId) throws DaoException {
        List<Track> tracks;
        try (Session session = sessionFactory.getCurrentSession()) {
            tracks = session.createQuery("SELECT track FROM Order WHERE user_id = :userId",
                    Track.class).setParameter("userId", userId).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return tracks;
    }

    @Override
    public void remove(final int id) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.delete(session.get(Order.class, id));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(final Order entity) throws DaoException {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.update(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
