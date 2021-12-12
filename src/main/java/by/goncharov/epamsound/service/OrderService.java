package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.beans.User;
import by.goncharov.epamsound.dao.DAOException;
import by.goncharov.epamsound.dao.OrderDAO;
import by.goncharov.epamsound.dao.UserDAO;
import by.goncharov.epamsound.dao.ConnectionPool;
import by.goncharov.epamsound.dao.Transaction;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    public void addOrder(final int trackId, final double price,
                         final User user) throws ServiceException {
        Transaction connection = ConnectionPool.getInstance().
                getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        try {
            try {
                connection.setAutoCommit(false);
                double money = user.getCash();
                userDAO.changeCash(user.getId(), money - price);
                orderDAO.addOrder(trackId, price, user.getId());
                connection.commit();
            } catch (SQLException e) {
                throw new ServiceException("Exception during order"
                        + " addition", e);
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException("Exception during order"
                        + " addition", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServiceException("Exception during order"
                    + " addition", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
    public List<Track> findMyTracks(final int userId)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance().
                getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.findOrders(userId);
        } catch (DAOException e) {
            throw new ServiceException("Exception during my tracks"
                    + " search", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
    public boolean isOrdered(final int userId, final int trackId)
            throws ServiceException {
        Transaction connection = ConnectionPool.getInstance()
                .getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.isOrdered(userId, trackId);
        } catch (DAOException e) {
            throw new ServiceException("Can't find is order exist", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
}
