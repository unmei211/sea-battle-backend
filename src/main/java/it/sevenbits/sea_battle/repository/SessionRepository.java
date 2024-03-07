package it.sevenbits.sea_battle.repository;

import it.sevenbits.sea_battle.entity.Session;
import it.sevenbits.sea_battle.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface SessionRepository extends ListCrudRepository<Session, Long> {
}
