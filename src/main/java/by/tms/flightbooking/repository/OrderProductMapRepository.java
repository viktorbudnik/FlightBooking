package by.tms.flightbooking.repository;

import by.tms.flightbooking.model.OrderProductMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductMapRepository extends JpaRepository<OrderProductMap, Long> {
    List<OrderProductMap> findAllByOrderId(Long id);
}
