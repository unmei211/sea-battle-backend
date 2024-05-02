package it.sevenbits.seabattle.core.repository.user;

import it.sevenbits.seabattle.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * user repos
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    Optional<User> findByLogin(String login);
}
