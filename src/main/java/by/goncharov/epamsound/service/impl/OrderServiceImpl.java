package by.goncharov.epamsound.service.impl;

import by.goncharov.epamsound.beans.Order;

import by.goncharov.epamsound.dao.OrderRepository;
import by.goncharov.epamsound.service.OrderService;
import by.goncharov.epamsound.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Order service.
 * @author Goncharov Daniil
 * @see OrderRepository
 * @see
 */
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * The Order dao.
     */
    @Autowired
    private OrderRepository orderRepository;
    /**
     * Add order.
     *
     * @throws ServiceException the service exception
     */
    @Override
    public void save(final Order order) throws ServiceException {
        orderRepository.save(order);
    }
    @Override
    public Order findOrderById(final Order order) throws ServiceException {
        Optional<Order> orders = orderRepository.findById(order.getId());
        if (orders.isPresent()) {
            return orders.get();
        } else {
            throw new ServiceException("Track not found");
        }
    }

}
