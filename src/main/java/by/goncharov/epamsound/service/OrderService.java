package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.OrderDaoImpl;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import java.time.Clock;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Order service.
 * @author Goncharov Daniil
 * @see Order
 * @see OrderDaoImpl
 */
@Service
public class OrderService {
    /**
     * The Order dao.
     */
    @Autowired
    private OrderDaoImpl orderDao;
    @Autowired
    UserDaoImpl userDao;
    /**
     * Add order.
     *
     * @param price   the price
     * @param user    the user
     * @throws ServiceException the service exception
     */
    @Transactional
    public void addOrder(final Track track, final double price,
                         final User user) throws ServiceException {
        try {
            double money = user.getCash();
            userDao.changeCash(user.getId(), money - price);
            Order order = new Order();
            order.setTrack(track);
            order.setCustomer(user);
            order.setPrice(price);
            order.setDate(LocalDate.now(Clock.systemDefaultZone()));
            orderDao.add(order);
        } catch (DaoException e) {
                throw new ServiceException("Exception during order"
                        + " addition", e);
        }
    }

    /**
     * Find my tracks list.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    @Transactional
    public List<Track> findMyTracks(final User user)
            throws ServiceException {
        try {
            return orderDao.findOrders(user.getId());
        } catch (DaoException e) {
            throw new ServiceException("Exception during my tracks"
                    + " search", e);
        }
    }

    /**
     * Is ordered boolean.
     *
     * @param user  the user
     * @param track the track
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Transactional
    public boolean isOrdered(final User user, final Track track)
            throws ServiceException {
        try {
            return orderDao.isOrdered(user.getId(), track.getId());
        } catch (DaoException e) {
            throw new ServiceException("Can't find is order exist", e);
        }
    }
}
