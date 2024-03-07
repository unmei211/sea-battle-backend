package it.sevenbits.sea_battle.repository;

import it.sevenbits.sea_battle.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
