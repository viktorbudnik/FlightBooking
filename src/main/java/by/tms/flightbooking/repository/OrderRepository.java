package by.tms.flightbooking.repository;

import by.tms.flightbooking.model.Order;
import by.tms.flightbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
