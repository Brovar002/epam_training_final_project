package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Order;

public interface OrderService {
    void save(Order order) throws ServiceException;
    Order findOrderById(Order order) throws ServiceException;
}
