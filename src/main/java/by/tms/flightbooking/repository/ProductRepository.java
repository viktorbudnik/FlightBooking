package by.tms.flightbooking.repository;

import by.tms.flightbooking.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findAllByCategoryId(Long categoryId);
}
