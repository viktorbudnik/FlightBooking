package by.tms.flightbooking.repository;

import by.tms.flightbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByLogin(String login);
    User findByUsername(String username);
}
