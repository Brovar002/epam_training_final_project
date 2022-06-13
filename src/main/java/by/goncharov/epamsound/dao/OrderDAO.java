package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Order;
import by.goncharov.epamsound.beans.Track;

import java.util.List;

/**
 * The interface Order dao.
 * @author Goncharov Daniil
 * @see BaseDao
 * @see Order
 */
public interface OrderDao extends BaseDao<Order> {
    /**
     * Find orders list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Track> findOrders(int userId) throws DaoException;

    /**
     * Is ordered boolean.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean isOrdered(int userId, int trackId)
            throws DaoException;
}
