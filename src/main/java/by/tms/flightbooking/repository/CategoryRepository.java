package by.tms.flightbooking.repository;

import by.tms.flightbooking.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
Category findByName(String name);
}
