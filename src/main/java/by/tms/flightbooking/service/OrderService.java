package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Order;
import by.tms.flightbooking.repository.OrderRepository;
import by.tms.flightbooking.service.OrderServiceInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService implements OrderServiceInt {
    protected OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public boolean deleteOrder(Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);

    }
}
