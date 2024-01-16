package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Order;

public interface OrderServiceInt {
    void saveOrder(Order order);
    boolean deleteOrder(Long id);
}
