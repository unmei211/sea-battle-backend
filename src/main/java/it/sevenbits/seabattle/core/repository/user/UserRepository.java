package it.sevenbits.seabattle.core.repository.user;

import it.sevenbits.seabattle.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
