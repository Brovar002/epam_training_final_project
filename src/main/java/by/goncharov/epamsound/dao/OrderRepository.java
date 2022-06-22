package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Order dao.
 * @author Goncharov Daniil
 * @see Order
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
