package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DaoException;
import by.goncharov.epamsound.dao.impl.OrderDaoImpl;
import by.goncharov.epamsound.dao.impl.UserDaoImpl;
import by.goncharov.epamsound.dao.ConnectionPool;
import by.goncharov.epamsound.dao.Transaction;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class OrderService {
    OrderDaoImpl orderDao = new OrderDaoImpl();
    public void addOrder(final int trackId, final double price,
                         final User user) throws ServiceException {

        UserDaoImpl userDao = new UserDaoImpl();
        try (Transaction connection = ConnectionPool.getInstance().getConnection()) {
            try {
                connection.setAutoCommit(false);
                double money = user.getCash();
                userDao.changeCash(user.getId(), money - price);
                Order order = new Order();
                order.setTrack(trackId);
                order.setCustomer(user);
                order.setPrice(price);
                order.setDate(LocalDate.now(Clock.systemDefaultZone()));
                orderDao.add(order);
                connection.commit();
            } catch (SQLException e) {
                throw new ServiceException("Exception during order"
                        + " addition", e);
            } catch (DaoException e) {
                connection.rollback();
                throw new ServiceException("Exception during order"
                        + " addition", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServiceException("Exception during order"
                    + " addition", e);
        }
    }
    public List<Track> findMyTracks(final int userId)
            throws ServiceException {
        try {
            return orderDao.findOrders(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during my tracks"
                    + " search", e);
        }
    }
    public boolean isOrdered(final int userId, final int trackId)
            throws ServiceException {
        try {
            return orderDao.isOrdered(userId, trackId);
        } catch (DaoException e) {
            throw new ServiceException("Can't find is order exist", e);
        }
    }
}
