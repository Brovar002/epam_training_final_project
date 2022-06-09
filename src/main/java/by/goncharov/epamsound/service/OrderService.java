package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.OrderDaoImpl;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import java.time.Clock;
import java.time.LocalDate;

import by.goncharov.epamsound.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
import java.util.List;

/**
 * The type Order service.
 * @author Goncharov Daniil
 * @see Order
 * @see OrderDaoImpl
 */
public class OrderService {
    /**
     * The Order dao.
     */
    private final OrderDaoImpl orderDao = new OrderDaoImpl();

    /**
     * Add order.
     *
     * @param trackId the track id
     * @param price   the price
     * @param user    the user
     * @throws ServiceException the service exception
     */
    public void addOrder(final int trackId, final double price,
                         final User user) throws ServiceException {

        UserDaoImpl userDao = new UserDaoImpl();
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                double money = user.getCash();
                userDao.changeCash(user.getId(), money - price);
                Order order = new Order();
                order.setTrack(trackId);
                order.setCustomer(user);
                order.setPrice(price);
                order.setDate(LocalDate.now(Clock.systemDefaultZone()));
                orderDao.add(order);
                transaction.commit();
            } catch (DaoException e) {
                transaction.rollback();
                throw new ServiceException("Exception during order"
                        + " addition", e);
            }
        }
    }

    /**
     * Find my tracks list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Track> findMyTracks(final int userId)
            throws ServiceException {
        try {
            return orderDao.findOrders(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during my tracks"
                    + " search", e);
        }
    }

    /**
     * Is ordered boolean.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean isOrdered(final int userId, final int trackId)
            throws ServiceException {
        try {
            return orderDao.isOrdered(userId, trackId);
        } catch (DaoException e) {
            throw new ServiceException("Can't find is order exist", e);
        }
    }
}
